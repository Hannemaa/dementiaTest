package hannemann.dementiatest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ResultListActivity extends AppCompatActivity {
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expListView = (ExpandableListView) findViewById(R.id.resultList);
        HashMap<String, List<String>> data = prepareListData();
        List<String> header = new ArrayList<String>(data.keySet());
        listAdapter = new ExpandableResultListAdapter(this, header, data);
        expListView.setAdapter(listAdapter);

        LinearLayout empty = (LinearLayout) findViewById(R.id.empty_list_item);
        Button startBtn = (Button) findViewById(R.id.startMmseBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mmstIntent = new Intent(ResultListActivity.this, MMSTActivity.class);
                ResultListActivity.this.startActivity(mmstIntent);
            }
        });
        expListView.setEmptyView(empty);
    }

    private HashMap<String, List<String>> prepareListData() {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        String[] dataArray = new FileReaderSaver(this.getBaseContext()).readFile().split("\\[");
        for (String s : dataArray) {
            s = s.replaceAll(".*=", "");
            s = s.replaceAll("]", "");
            if (!s.isEmpty()) {
                List<String> testData = new ArrayList<>();
                testData = Arrays.asList(s.split(", "));
                result.put(testData.get(0), testData);
        }}
        return result;
    }
}
