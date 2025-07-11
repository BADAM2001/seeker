package com.example.seeker;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.seeker.R;
import com.example.seeker.database.DBHelper;
import com.example.seeker.models.BatteryLog;
import java.util.List;
import com.example.seeker.adapters.LogsAdapter;

public class LogsActivity extends AppCompatActivity {
    private ListView logsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        logsListView = findViewById(R.id.logsListView);
        loadLogs();
    }

    private void loadLogs() {
        DBHelper dbHelper = new DBHelper(this);
        List<BatteryLog> logs = dbHelper.getAllLogs();
        dbHelper.close();

        LogsAdapter adapter = new LogsAdapter(this, logs);
        logsListView.setAdapter(adapter);
    }
}