package hannemann.dementiatest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MMSTActivity extends AppCompatActivity {
    private ViewFlipper vf;
    private EditText actualDateTxf, expertTxf, firstnameTxf, surnameTxf, othersTxf, ageGroupTxf, task04answTxf;
    private TextView task04Txf;
    private CheckBox agreementCxb;
    private Mmse test;
    private int taskNumber = 0;
    private PaintView paintView1, paintView10, paintView11;
    private DisplayMetrics metrics;
    private int num = 0;
    private ImageView task06Img;

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
        task04Txf = (TextView) this.findViewById(R.id.task04_text);
        task04answTxf = (EditText) this.findViewById(R.id.task04_answerTxt);
        task06Img = (ImageView) this.findViewById(R.id.task06image);

        paintView1 = (PaintView) findViewById(R.id.task01_paintView);
        paintView10 = (PaintView) findViewById(R.id.task10_paintView);
        paintView11 = (PaintView) findViewById(R.id.task11_paintView);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView1.init(metrics);
        paintView10.init(metrics);
        paintView11.init(metrics);

        vf = (ViewFlipper)findViewById(R.id.vf);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (taskNumber) {
                   case 0: hideKeyboard(view);
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
                            final Patient p = new Patient(surnameTxf.getText().toString(),
                                    firstnameTxf.getText().toString(),
                                    yearOfBirth, othersTxf.getText().toString(),
                                    agreementCxb.isChecked());
                            if (!p.isAgreed()) {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(MMSTActivity.this);
                                builder.setMessage(R.string.dialog_noAgreement_message)
                                        .setTitle(R.string.dialog_noAgreement_title);
                                builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        p.setAgreed();
                                        startTest(p);
                                    }
                                });
                                builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        test = new Mmse(p, expertTxf.getText().toString(), actualDateTxf.getText().toString());
                                        new FileReaderSaver(builder.getContext()).saveMMSE(test);
                                        MMSTActivity.super.onBackPressed();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                startTest(p);
                            }
                        } break;
                    case 4:
                        String answer = task04answTxf.getText().toString();
                        if (num != 0 && !answer.isEmpty()) {
                            task04Txf.setText(getResources().getString(R.string.task04) + " " + answer);
                            task04answTxf.setText("");
                            num++;
                        }
                        vf.setDisplayedChild(taskNumber);
                        Snackbar.make(view, "Number " + taskNumber, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        if (num < 5) {
                            break;
                        } else {
                            hideKeyboard(view);
                        }
                    case 3:
                        nextTask(view);
                        //preparing case 4
                            task04Txf.setText(getResources().getString(R.string.task04) + " 100");
                            num++;
                        break;
                    case 6:
                        if (num < 1) {
                            task06Img.setImageDrawable(getResources().getDrawable(R.drawable.bleistift));
                            num++;
                            break;
                        } else {
                            num = 0;
                        } nextTask(view); break;
                    default:
                        nextTask(view);
                }
            }

            private void startTest(Patient p) {
                test = new Mmse(p, expertTxf.getText().toString(), actualDateTxf.getText().toString());
                agreementCxb.setChecked(true);
                taskNumber++;
                vf.setDisplayedChild(taskNumber);
            }

            private void nextTask(View view) {
                taskNumber++;
                if (taskNumber < 13) {
                    vf.setDisplayedChild(taskNumber);
                    num = 0;
                    Snackbar.make(view, "Number " + taskNumber, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    new FileReaderSaver(view.getContext()).saveMMSE(test);
                    MMSTActivity.super.onBackPressed();
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
    private static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}
