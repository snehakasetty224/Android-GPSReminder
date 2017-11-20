package com.example.sneha.gpsreminder;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivityGPSReminder extends Activity {

    TaskAdapter taskAdapter = null;

    ListView listView = null;

    ArrayList<Task> array = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gpsreminder);
        startService(new Intent(this, MyService.class));
        refreshListView();
    }


    public void onClickButtonAdd(View v){
        Intent intent = new Intent(MainActivityGPSReminder.this,AddTaskActivity.class);
        startActivityForResult(intent,0);
    }

    public void showMap(View view){
        Intent intent = new Intent(MainActivityGPSReminder.this,MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            String taskName = data.getStringExtra("name");
            String taskDesc = data.getStringExtra("description");
            String taskLocation = data.getStringExtra("location");
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            DataController dataController = new DataController(getBaseContext());
            dataController.open();
            long retValue= dataController.insert(taskName,taskDesc,taskLocation,latitude,longitude);
            dataController.close();
            refreshListView();
        }
    }

    public void refreshListView(){

        DataController dataController=new DataController(getBaseContext());
        dataController.open();
        Cursor cursor = dataController.retrieve();
        array = new ArrayList<Task>();
        if(cursor != null)
        {
            while(cursor.moveToNext())
            {
                Task t = new Task();
                t.setName(cursor.getString(0));
                t.setDescription(cursor.getString(1));
                t.setLocation(cursor.getString(2));
                array.add(t);
            }
        }
        cursor.close();
        dataController.close();

        if(taskAdapter == null){
            taskAdapter =  new TaskAdapter(this,array);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(taskAdapter);
        }else{
            taskAdapter.clear();
            taskAdapter.addAll(array);
        }
    }

    public void btnRemoveClick(View v)
    {
        final int position = listView.getPositionForView((View) v.getParent());
        DataController dataController=new DataController(getBaseContext());
        String name = array.get(position).getName();
        dataController.open();
        dataController.delete(name);
        dataController.close();
        refreshListView();
    }
}
