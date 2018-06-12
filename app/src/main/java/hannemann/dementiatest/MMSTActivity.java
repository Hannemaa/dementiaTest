package hannemann.dementiatest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ViewFlipper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MMSTActivity extends AppCompatActivity {
    private ViewFlipper vf;
    private EditText actualDateTxf, expertTxf, firstnameTxf, surnameTxf, othersTxf, ageGroupTxf;
    private CheckBox agreementCxb;
    private Mmse test;
    private int taskNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmst);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actualDateTxf = (EditText) this.findViewById(R.id.actualDateTxf);
        getCurrentDate();

        ageGroupTxf = (EditText) this.findViewById(R.id.ageGroupTxf);
        expertTxf = (EditText) this.findViewById(R.id.expertNameTxf);
        firstnameTxf = (EditText) this.findViewById(R.id.firstnameTxf);
        surnameTxf = (EditText) this.findViewById(R.id.surnameTxf);
        othersTxf = (EditText) this.findViewById(R.id.othersTxf);
        agreementCxb = ((CheckBox) this.findViewById(R.id.agreementCxb));

        vf = (ViewFlipper)findViewById(R.id.vf);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (taskNumber) {
                    case 0:
                        if(!checkInput(expertTxf) || !checkInput(actualDateTxf) ||
                                !checkInput(surnameTxf) || !checkInput(firstnameTxf)
                                || !checkInput(ageGroupTxf)) {
                            break;
                        } else {
                            int yearOfBirth = Integer.parseInt(ageGroupTxf.getText().toString());
                            if (yearOfBirth >= Calendar.getInstance().get(Calendar.YEAR)
                                    ||  yearOfBirth < 1900) {
                                ageGroupTxf.setError(getString(R.string.impossibleInputError));
                                break;
                            }
                            //boolean agreement = ((CheckBox) view.findViewById(R.id.agreementCxb)).isChecked();
                            final Patient p = new Patient(surnameTxf.getText().toString(),
                                    firstnameTxf.getText().toString(),
                                    yearOfBirth, othersTxf.getText().toString(),
                                    agreementCxb.isChecked());
                            if (!p.isAgreed()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MMSTActivity.this);
                                builder.setMessage(R.string.dialog_noAgreement_message)
                                        .setTitle(R.string.dialog_noAgreement_title);
                                builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        p.setAgreed();
                                    }
                                });
                                builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //TODO save patient and stopp text (switch to resultview or start page)
                                        // weiterhin nein = Test beenden
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                if (!p.isAgreed()) {
                                    break;
                                }
                            }
                            SimpleDateFormat mdformat = new SimpleDateFormat("dd.MM.yyyy");
                            Date date;
                            try {
                                date = mdformat.parse(actualDateTxf.getText().toString());
                                test = new Mmse(p, expertTxf.getText().toString(), date);
                            } catch (ParseException e) {
                            }
                        }
                    default: taskNumber++;
                        if (taskNumber < 11) {
                            vf.setDisplayedChild(taskNumber);
                            Snackbar.make(view, "Number " + taskNumber, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                }
            }
        });

    }

    private boolean checkInput (EditText textfield){
        if(textfield.getText().toString().isEmpty()) {
            textfield.setError(getString(R.string.notEmptyError));
            return false;
        }
        return true;
    }

    public void getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd.MM.yyyy");
        actualDateTxf.setText(mdformat.format(c.getTime()));
    }

}
