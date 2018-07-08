package hannemann.dementiatest;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ann on 08.07.2018.
 */
public class FileReaderSaver {
    private Context fileContext;
    public FileReaderSaver(Context context) {
        this.fileContext = context;
    }

    public void saveMMSE(Mmse test){
        String fileContents = readFile();
        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
        listDataChild.put(test.getPatientUnderTest().getSurnamePatient(), test.getTestData());
        fileContents += "\n" + listDataChild.entrySet().toString();
        try {
            FileOutputStream fOut = fileContext.openFileOutput("mmseData", Context.MODE_PRIVATE);
            fOut.write(fileContents.getBytes());
            fOut.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String readFile() {
        String result = "";
        try {
            FileInputStream fin = fileContext.openFileInput("mmseData");
            int c;
            while( (c = fin.read()) != -1){
                result = result + Character.toString((char)c);
            }
        } catch(Exception e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public List<String> readList() {
        List<String> result = new ArrayList();
        try {
            FileInputStream fin = fileContext.openFileInput("mmseData");
            int c;
            while( (c = fin.read()) != -1){
                result.add(Character.toString((char)c));
            }
        } catch(Exception e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
