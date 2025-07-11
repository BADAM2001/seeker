package com.example.seeker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.seeker.R;
import com.example.seeker.models.BatteryLog;
import java.util.List;

public class LogsAdapter extends ArrayAdapter<BatteryLog> {
    public LogsAdapter(Context context, List<BatteryLog> logs) {
        super(context, 0, logs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BatteryLog log = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_log, parent, false);
        }

        TextView tvTime = convertView.findViewById(R.id.tvTime);
        TextView tvPercent = convertView.findViewById(R.id.tvPercent);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        tvTime.setText(log.getFormattedTime());
        tvPercent.setText(log.getPercentage() + "%");
        tvStatus.setText(log.getStatus());

        return convertView;
    }
}