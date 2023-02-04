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
import com.elorrieta.didaktikapp.model.views.GameRecordView;

import java.util.List;

public class GameRecordAdapter extends ArrayAdapter<GameRecordView> {

    private final Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView txtDate;
        TextView txtName;
        TextView txtCompletions;
    }

    public GameRecordAdapter(@NonNull Context context, @NonNull List<GameRecordView> objects) {
        super(context, R.layout.record_row, objects);
        this.context = context;
    }

    private int lastPosition = -1;

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        GameRecordView dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.record_row, parent, false);
            viewHolder.txtDate = convertView.findViewById(R.id.record_date);
            viewHolder.txtName = convertView.findViewById(R.id.record_name);
            viewHolder.txtCompletions = convertView.findViewById(R.id.record_completions);

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
        viewHolder.txtDate.setText(dataModel.date.toString());
        viewHolder.txtName.setText(dataModel.name);
        viewHolder.txtCompletions.setText(String.valueOf(dataModel.completions));

        // Return the completed view to render on screen
        return convertView;
    }
}
