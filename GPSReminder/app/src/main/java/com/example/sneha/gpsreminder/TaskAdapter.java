package com.example.sneha.gpsreminder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sneha on 4/26/17.
 */

public class TaskAdapter extends ArrayAdapter<Task>{

    protected ListView mListView;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple, parent, false);

        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.tvDistance);
        // Populate the data into the template view using the data object
        tvName.setText(task.getName());
        tvLocation.setText(task.getLocation());
        // Return the completed view to render on screen
        return convertView;
    }

}
