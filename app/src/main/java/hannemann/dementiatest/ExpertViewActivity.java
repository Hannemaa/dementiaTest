package hannemann.dementiatest;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ExpertViewActivity extends ListActivity {
    //private Mmse mmsetest;
    //private String[] tasklist;
    private ListView testList;

    //private MyCustomAdapter dataAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO setContentView(R.layout.activity_expert_view);

        //testList = (ListView) this.findViewById(R.id.testListView);
        /*tasklist = new FileReaderSaver(this.getBaseContext()).readFile().split("\\[");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, tasklist);
        setListAdapter(adapter);*/
    }
}
