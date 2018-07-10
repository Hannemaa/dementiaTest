package hannemann.dementiatest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MMSTActivity extends AppCompatActivity {
    private ViewFlipper vf;
    private EditText actualDateTxf, expertTxf, firstnameTxf, surnameTxf, othersTxf, ageGroupTxf, task04answTxf;
    private TextView task04Txf, task06Txf;
    private CheckBox agreementCxb;
    private Mmse test;
    private int taskNumber = 0;
    private PaintView paintView1, paintView10, paintView11;
    private DisplayMetrics metrics;
    private int num = 0;
    private ImageView task06Img;
    private int expectedResult = 100;
//TODO    private final MediaPlayer mp = MediaPlayer.create(this, R.raw.task7sound);

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
        task06Img = (ImageView) this.findViewById(R.id.task06_img);

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
                   case 0:
                       checkInputToStartTest(view);
                       break;
                    case 1:
                        nextTask(view);
                        break;
                    case 2:
                        nextTask(view);
                        recordSpeech(R.id.task03_recBtn);
                        break;
                    case 3:
                        nextTask(view);
                        //preparing case 4
                        task04Txf.setText(getResources().getString(R.string.task04) + " 100");
                        num++;
                        break;
                    case 4:
                        String answer = task04answTxf.getText().toString();
                        if (num != 0 && !answer.isEmpty()) {
                            if ((expectedResult - 7) == Integer.parseInt(answer)) {
                                test.setTaskPointSuccessful(taskNumber);
                                test.setTaskInformation(taskNumber, answer);
                            } else {
                                test.setTaskPointFailed(taskNumber);
                                test.setTaskInformation(taskNumber, answer);
                            }
                            expectedResult = Integer.parseInt(answer);
                            task04Txf.setText(getResources().getString(R.string.task04) + " " + answer);
                            task04answTxf.setText("");
                            num++;
                        }
                        vf.setDisplayedChild(taskNumber);
                        if (num < 5) {
                            break;
                        } else {
                            hideKeyboard(view);
                        }
                        nextTask(view);
                        recordSpeech(R.id.task05_recBtn);
                        break;
                    case 5:
                        nextTask(view);
                        recordSpeech(R.id.task06_recBtn);
                        break;
                    case 6:
                        task06Txf = (TextView) findViewById(R.id.task06_txf);
                        if (num < 1) {
                            String s = task06Txf.getText().toString();
                            if (s.contains(getString(R.string.task06_Armbanduhr))||
                                    s.contains(getString(R.string.task06_Uhr))) {
                                test.setTaskPointSuccessful(taskNumber);
                                test.setTaskInformation(taskNumber, s);
                            }
                            task06Img.setImageDrawable(getResources().getDrawable(R.drawable.bleistift));
                            num++;
                            break;
                        } else {
                            String s = task06Txf.getText().toString();
                            if (s.contains(getString(R.string.task06_Bleistift))||
                                    s.contains(getString(R.string.task06_Stift))){
                                test.setTaskPointSuccessful(taskNumber);
                                test.setTaskInformation(taskNumber, s);
                            }
                            num = 0;
                        }
                        nextTask(view);
                        recordSpeech(R.id.task07_recBtn);
                        break;
                    case 7:
                        final Button soundBtn7 = (Button) findViewById(R.id.task07_soundBtn);
                        soundBtn7.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v) {
                                soundBtn7.setText("TODO");//mp.start();
                            }
                        });
                        nextTask(view);
                        break;
                    case 8:
                        nextTask(view);
                        break;
                    case 9:
                        nextTask(view);
                    case 10:
                        nextTask(view);
                        break;
                    case 11:
                        nextTask(view);
                        break;
                    default:
                        nextTask(view);
                }
            }

            private void checkInputToStartTest(View view) {
                hideKeyboard(view);
                if(!checkInput(expertTxf) || !checkInput(actualDateTxf) ||
                        !checkInput(surnameTxf) || !checkInput(firstnameTxf)
                        || !checkInput(ageGroupTxf)) {
                    return;
                } else {
                    int yearOfBirth = Integer.parseInt(ageGroupTxf.getText().toString());
                    if (yearOfBirth >= Calendar.getInstance().get(Calendar.YEAR)
                            ||  yearOfBirth < 1900) {
                        ageGroupTxf.setError(getString(R.string.impossibleInputError));
                        return;
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
                }
            }

            private void recordSpeech(int id) {
                Button recBtn = (Button) findViewById(id);
                recBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        displaySpeechRecognizer();
                    }
                });
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
                    Snackbar.make(view, "Number " + taskNumber + ", Points: " + test.getPoints(), Snackbar.LENGTH_LONG)
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

    private void displaySpeechRecognizer() {
        final int SPEECH_REQUEST_CODE = 0;
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.dialog_voiceInput));
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (ActivityNotFoundException a) {
            Log.e(a.toString(), "startSpeechInput " + a.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    String recordResult = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                    test.setTaskInformation(taskNumber, recordResult);
                    switch (taskNumber) { // TODO check spoken text bzw. recordResult
                        case 3:
                            //TODO "MErken"
                        case 5:
                            if(recordResult.contains(getString(R.string.task03_wordsApfel))) {
                                test.setTaskPointSuccessful(taskNumber);
                            }
                            if(recordResult.contains(getString(R.string.task03_wordsPfennig))) {
                                test.setTaskPointSuccessful(taskNumber);
                            }
                            if(recordResult.contains(getString(R.string.task03_wordsTisch))) {
                                test.setTaskPointSuccessful(taskNumber);
                            }
                            break;
                        case 6:
                            if(recordResult.contains(getString(R.string.task06_Bleistift))||
                                    recordResult.contains(getString(R.string.task06_Stift))||
                                    recordResult.contains(getString(R.string.task06_Armbanduhr))||
                                    recordResult.contains(getString(R.string.task06_Uhr))) {
                                //test.setTaskPointSuccessful(taskNumber);
                                task06Txf.setText(recordResult);
                            }
                            break;
                        case 7:
                            if(recordResult.contains(getString(R.string.task07_sound))) {
                                test.setTaskPointSuccessful(taskNumber);
                            }
                            break;
                        default: test.setTaskPointFailed(taskNumber);
                    }

                }
                break;
            }
        }
    }
}
