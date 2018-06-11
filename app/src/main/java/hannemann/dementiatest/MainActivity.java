package hannemann.dementiatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startBtn = (Button) findViewById(R.id.startMmseBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mmstIntent = new Intent(MainActivity.this, MMSTActivity.class);
                MainActivity.this.startActivity(mmstIntent);

            }
        });

        final Button openResultsBtn = (Button) findViewById(R.id.openResultViewBtn);
        openResultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openResultsBtn.setText("KLick"); //TODO
            }
        });
    }
}
