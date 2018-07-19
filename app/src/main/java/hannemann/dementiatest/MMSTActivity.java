package hannemann.dementiatest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MMSTActivity extends AppCompatActivity {
    private ViewFlipper vf;
    private EditText actualDateTxf, expertTxf, firstnameTxf, surnameTxf, othersTxf, ageGroupTxf, answerTxf;
    private TextView taskX;
    private CheckBox agreementCxb;
    private Mmse test;
    private int taskNumber = 0;
    private PaintView paintView10, paintView11;
    private DisplayMetrics metrics;
    private int expectedResult = 100;
    private LocationManager locationManager;

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
        taskX = (TextView) findViewById(R.id.taskX_text);
        answerTxf = (EditText) this.findViewById(R.id.answerTxf);

        paintView10 = (PaintView) findViewById(R.id.task10_paintView);
        paintView11 = (PaintView) findViewById(R.id.task11_paintView);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView10.init(metrics);
        paintView11.init(metrics);

        vf = (ViewFlipper) findViewById(R.id.taskSpecific);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                switch (taskNumber) {
                    case 0:
                        checkInputToStartTest(view);
                        taskX.setText(R.string.task01_year);
                        recordSpeech(R.id.task01_recBtn);
                        break;
                    case 1: //Jahr
                        if (answerTxf.getText().toString().contains(Integer.toString(
                                new GregorianCalendar().get(Calendar.YEAR)))) {
                            test.setTaskPointSuccessful(taskNumber);
                        }
                        nextTask(view, R.string.task01_season);
                        recordSpeech(R.id.task02_recBtn);
                        break;
                    case 2: //Jahreszeit anhand Monat
                        int month = new GregorianCalendar().get(Calendar.MONTH);
                        if (month > 1 && month < 5) { // 2,3,4 spring
                            if (answerTxf.getText().toString().contains("Frühling")) {
                                test.setTaskPointSuccessful(taskNumber);
                            }
                        } else if (month > 4 && month < 8) { // 5,6,7 summer
                            if (answerTxf.getText().toString().contains("Sommer")) {
                                test.setTaskPointSuccessful(taskNumber);
                            }
                        }
                        if (month > 7 && month < 11) { // 8,9,10 fall
                            if (answerTxf.getText().toString().contains("Herbst")) {
                                test.setTaskPointSuccessful(taskNumber);
                            }
                        } else { // 11,0,1 winter
                            if (answerTxf.getText().toString().contains("Winter")) {
                                test.setTaskPointSuccessful(taskNumber);
                            }
                        }
                        nextTask(view, R.string.task01_day);
                        recordSpeech(R.id.task03_recBtn);

                        break;
                    case 3:  //Tag
                        if (answerTxf.getText().toString().contains(Integer.toString(
                                new GregorianCalendar().get(Calendar.DAY_OF_MONTH)))) {
                            test.setTaskPointSuccessful(taskNumber);
                        }
                        nextTask(view, R.string.task01_weekday);
                        recordSpeech(R.id.task04_recBtn);
                        break;
                    case 4: //Wochentag
                        if (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY &&
                                answerTxf.getText().toString().contains("Samstag") || answerTxf.getText().toString().contains("Sonnabend")) {
                            test.setTaskPointSuccessful(taskNumber);
                        } else if (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY &&
                                answerTxf.getText().toString().contains("Sonntag")) {
                            test.setTaskPointSuccessful(taskNumber);
                        } else if (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY &&
                                answerTxf.getText().toString().contains("Montag")) {
                            test.setTaskPointSuccessful(taskNumber);
                        } else if (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY &&
                                answerTxf.getText().toString().contains("Dienstag")) {
                            test.setTaskPointSuccessful(taskNumber);
                        } else if (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY &&
                                answerTxf.getText().toString().contains("Mittwoch")) {
                            test.setTaskPointSuccessful(taskNumber);
                        } else if (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY &&
                                answerTxf.getText().toString().contains("Donnerstag")) {
                            test.setTaskPointSuccessful(taskNumber);
                        } else if (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY &&
                                answerTxf.getText().toString().contains("Freitag")) {
                            test.setTaskPointSuccessful(taskNumber);
                        }
                        nextTask(view, R.string.task01_month);
                        recordSpeech(R.id.task05_recBtn);
                        break;
                    case 5: //Monat
                        switch (new GregorianCalendar().get(Calendar.DAY_OF_WEEK)) {
                            case Calendar.JANUARY:
                                if (answerTxf.getText().toString().contains("Januar")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.FEBRUARY:
                                if (answerTxf.getText().toString().contains("Februar")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.MARCH:
                                if (answerTxf.getText().toString().contains("März")
                                        || answerTxf.getText().toString().contains("Maerz")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.APRIL:
                                if (answerTxf.getText().toString().contains("April")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.MAY:
                                if (answerTxf.getText().toString().contains("Mai")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.JUNE:
                                if (answerTxf.getText().toString().contains("Juni")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.JULY:
                                if (answerTxf.getText().toString().contains("Juli")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.AUGUST:
                                if (answerTxf.getText().toString().contains("August")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.SEPTEMBER:
                                if (answerTxf.getText().toString().contains("September")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.OCTOBER:
                                if (answerTxf.getText().toString().contains("Oktober")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.NOVEMBER:
                                if (answerTxf.getText().toString().contains("November")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                            case Calendar.DECEMBER:
                                if (answerTxf.getText().toString().contains("Dezember")) {
                                    test.setTaskPointSuccessful(taskNumber);
                                }
                        }
                        nextTask(view, R.string.task02_country);
                        recordSpeech(R.id.task06_recBtn);
                        break;
                    case 6: //Land TODO Standort
                            /*String country = "";
                            String canton = "";
                            String city = "";
                            String address = "";
                            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                            String provider = locationManager.getBestProvider(new Criteria(), false);
                            Location loc;
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            } else{
                                loc = locationManager.getLastKnownLocation(provider);
                                Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                                List<Address> addresses;
                                try {
                                    addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                                    if (addresses.size() > 0) {
                                        System.out.println(addresses.get(0).getLocality());
                                        country = addresses.get(0).getCountryName();
                                        city = addresses.get(0).getLocality();
                                        canton = addresses.get(0).getSubAdminArea();
                                        address = addresses.get(0).getAddressLine(0);
                                        answerTxf.setText("land: " + country + ", stadt: " + city + ", canton: "+canton + ", address: " + address);
                                    }
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return;
                            }*/
                        if (answerTxf.getText().toString().contains("Deutschland")) {
                            test.setTaskPointSuccessful(taskNumber);
                        }
                        nextTask(view, R.string.task02_canton);
                        recordSpeech(R.id.task07_recBtn);
                        break;
                    case 7: //Kanton TODO
                        if (answerTxf.getText().toString().contains("Hannover")) {
                            test.setTaskPointSuccessful(taskNumber);
                        }
                        nextTask(view, R.string.task02_locality);
                        recordSpeech(R.id.task08_recBtn);
                        break;
                    case 8: //Ort TODO
                        if (answerTxf.getText().toString().contains("Hannover")) {
                            test.setTaskPointSuccessful(taskNumber);
                        }
                        nextTask(view, R.string.task02_level);
                        recordSpeech(R.id.task09_recBtn);
                        break;
                    case 9: //Points Stockwerk Gutachter
                        nextTask(view, R.string.task02_adress);
                        recordSpeech(R.id.task10_recBtn);
                        break;
                    case 10: //Adresse TODO
                        if (answerTxf.getText().toString().contains("Hannover")) {
                            test.setTaskPointSuccessful(taskNumber);
                        }
                        nextTask(view, R.string.task03);
                        recordSpeech(R.id.task11_recBtn);
                        break;
                    case 11: case 12: taskNumber = 13;
                    case 13: //preparing case 14
                        if(answerTxf.getText().toString().contains(getString(R.string.task03_wordsApfel))) {
                            test.setTaskPointSuccessful(11);
                        }
                        if(answerTxf.getText().toString().contains(getString(R.string.task03_wordsPfennig))) {
                            test.setTaskPointSuccessful(12);
                        }
                        if(answerTxf.getText().toString().contains(getString(R.string.task03_wordsTisch))) {
                            test.setTaskPointSuccessful(13);
                        }
                        answerTxf.setRawInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        answerTxf.setHint("Ergebnis");
                        nextTask(view, getResources().getString(R.string.task04) + " " + expectedResult);
                        break;
                    case 14: case 15: case 16: case 17: //rechnen
                        calculation(view);
                        break;
                    case 18:
                        calculation(view);
                        hideKeyboard(view);
                        answerTxf.setRawInputType(InputType.TYPE_CLASS_TEXT);
                        answerTxf.setHint("");
                        recordSpeech(R.id.task19_recBtn);
                        nextTask(view, R.string.task05);
                        break;
                    case 19: case 20: case 21:
                        if(answerTxf.getText().toString().contains(getString(R.string.task03_wordsApfel))) {
                            test.setTaskPointSuccessful(19);
                        }
                        if(answerTxf.getText().toString().contains(getString(R.string.task03_wordsPfennig))) {
                            test.setTaskPointSuccessful(20);
                        }
                        if(answerTxf.getText().toString().contains(getString(R.string.task03_wordsTisch))) {
                            test.setTaskPointSuccessful(21);
                        }
                        taskNumber = 21;
                        recordSpeech(R.id.task22_recBtn);
                        nextTask(view, R.string.task06_StiftArmbanduhr);
                        if (answerTxf.getText().toString().contains(getString(R.string.task06_Armbanduhr)) ||
                                answerTxf.getText().toString().contains(getString(R.string.task06_Uhr))) {
                            test.setTaskPointSuccessful(taskNumber);
                            test.setTaskInformation(taskNumber, answerTxf.getText().toString());
                        }
                        break;
                    case 22:
                        if(answerTxf.getText().toString().contains(getString(R.string.task06_Bleistift))||
                                answerTxf.getText().toString().contains(getString(R.string.task06_Stift))||
                                answerTxf.getText().toString().contains(getString(R.string.task06_Armbanduhr))||
                                answerTxf.getText().toString().contains(getString(R.string.task06_Uhr))) {
                            test.setTaskPointSuccessful(taskNumber);
                        }
                        recordSpeech(R.id.task23_recBtn);
                        nextTask(view, R.string.task06_StiftArmbanduhr);
                        if (answerTxf.getText().toString().contains(getString(R.string.task06_Stift))) {
                            test.setTaskPointSuccessful(taskNumber);
                            test.setTaskInformation(taskNumber, answerTxf.getText().toString());
                        }
                        break;
                    case 23:
                        if(answerTxf.getText().toString().contains(getString(R.string.task06_Bleistift))||
                                answerTxf.getText().toString().contains(getString(R.string.task06_Stift))||
                                answerTxf.getText().toString().contains(getString(R.string.task06_Armbanduhr))||
                                answerTxf.getText().toString().contains(getString(R.string.task06_Uhr))) {
                            test.setTaskPointSuccessful(taskNumber);
                        }
                        recordSpeech(R.id.task24_recBtn);
                        nextTask(view, R.string.task07);
                        final Button soundBtn7 = (Button) findViewById(R.id.task07_soundBtn);
                        soundBtn7.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.task7sound);
                                mediaPlayer.start();
                            }
                        });
                        break;
                    case 24:
                        if(answerTxf.getText().toString().toLowerCase().contains(
                                getString(R.string.task07_sound).toLowerCase())) {
                            test.setTaskPointSuccessful(taskNumber);
                        }
                        answerTxf.setVisibility(View.INVISIBLE);
                        nextTask(view, R.string.task08);
                        break;
                    case 25: //case 26: case 27: Bewertung Gutachter, anders nicht moeglich
                        taskNumber = 27;
                        nextTask(view, R.string.task09);
                        break;
                    case 28:
                        test.setTaskInformation(taskNumber, paintView10);
                        nextTask(view, R.string.task10);
                        break;
                    case 29:
                        test.setTaskInformation(taskNumber, paintView11);
                        nextTask(view, R.string.task11);
                        break;
                    case 30:
                        nextTask(view, R.string.taskEnd);
                        taskX.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        new FileReaderSaver(view.getContext()).saveMMSE(test);
                        MMSTActivity.super.onBackPressed();
                }
            }

            private void calculation(View view) {
                String answer = answerTxf.getText().toString();
                if (!answer.isEmpty()) {
                    taskX.setText(getResources().getString(R.string.task04) + " " + expectedResult);
                    if ((expectedResult - 7) == Integer.parseInt(answer)) {
                        test.setTaskPointSuccessful(taskNumber);
                        test.setTaskInformation(taskNumber, answer);
                    } else {
                        test.setTaskPointFailed(taskNumber);
                        test.setTaskInformation(taskNumber, answer);
                    }
                    expectedResult = Integer.parseInt(answer);
                    vf.setDisplayedChild(taskNumber);
                    nextTask(view, getResources().getString(R.string.task04) + " " + expectedResult);
                    answerTxf.setText("");
                }
            }

            private void checkInputToStartTest(View view) {
                hideKeyboard(view);
                if (!checkInput(expertTxf) || !checkInput(actualDateTxf) ||
                        !checkInput(surnameTxf) || !checkInput(firstnameTxf)
                        || !checkInput(ageGroupTxf)) {
                    return;
                } else {
                    int yearOfBirth = Integer.parseInt(ageGroupTxf.getText().toString());
                    if (yearOfBirth >= Calendar.getInstance().get(Calendar.YEAR)
                            || yearOfBirth < 1900) {
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
                answerTxf.setVisibility(View.VISIBLE);
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
                taskX.setVisibility(View.VISIBLE);
                findViewById(android.R.id.content).invalidate();
                vf.setDisplayedChild(taskNumber);
            }

            private void nextTask(View view, int taskText) {
                nextTask(view, getResources().getString(taskText));
            }

            private void nextTask(View view, String taskText) {
                taskNumber++;
                answerTxf.setText("");
                taskX.setText(taskText);
                vf.setDisplayedChild(taskNumber);
                Snackbar.make(view, "Number " + taskNumber + ", Points: " + test.getPoints(),
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
                if (!(taskNumber >= 15 && taskNumber <= 18)) {
                    hideKeyboard(view);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemExpert:
                if (!checkInput(expertTxf) || !checkInput(actualDateTxf) ||
                        !checkInput(surnameTxf) || !checkInput(firstnameTxf)
                        || !checkInput(ageGroupTxf)) {
                } else {
                    int yearOfBirth = Integer.parseInt(ageGroupTxf.getText().toString());
                    if (yearOfBirth >= Calendar.getInstance().get(Calendar.YEAR)
                            || yearOfBirth < 1900) {
                        ageGroupTxf.setError(getString(R.string.impossibleInputError));
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
                                test = new Mmse(p, expertTxf.getText().toString(), actualDateTxf.getText().toString());
                                agreementCxb.setChecked(true);

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
                    }
                    FileReaderSaver frs = new FileReaderSaver(this);
                    frs.saveMMSE(test);
                    Intent mmstIntent = new Intent(MMSTActivity.this, ExpertViewActivity.class);
                    //mmstIntent.putExtra("id", id);;*/
                    startActivityForResult(mmstIntent, 0);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkInput(EditText textfield) {
        if (textfield.getText().toString().isEmpty()) {
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
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && null != data) {
            String recordResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            test.setTaskInformation(taskNumber, recordResult);
            answerTxf.setText(recordResult);
        }
    }
}
