package com.elorrieta.didaktikapp.records;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.model.pojo.GameRecordPOJO;

import java.util.List;

public class GameRecordAdapter extends ArrayAdapter<GameRecordPOJO> {

    private final Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView txtDate;
        TextView txtName;
        TextView txtCompletions;
    }

    public GameRecordAdapter(@NonNull Context context, @NonNull List<GameRecordPOJO> objects) {
        super(context, R.layout.record_row, objects);
        this.context = context;
    }

    private int lastPosition = -1;

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        GameRecordPOJO dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.record_row, parent, false);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.record_date);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.record_name);
            viewHolder.txtCompletions = (TextView) convertView.findViewById(R.id.record_completions);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        // Populate the data into the template view using the data object
        viewHolder.txtDate.setText(dataModel.getDate().toString());
        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtCompletions.setText(String.valueOf(dataModel.getCompletions()));

        // Return the completed view to render on screen
        return convertView;
    }
}
