package hannemann.dementiatest;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExpertViewActivity extends ListActivity {
    //private Mmse mmsetest;
    //private String[] tasklist;

//private MyCustomAdapter dataAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_view);
        /*tasklist = new FileReaderSaver(this.getBaseContext()).readFile().split("\\[");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, tasklist);
        setListAdapter(adapter);*/
    }
}
