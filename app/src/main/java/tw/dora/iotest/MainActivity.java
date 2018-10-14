package tw.dora.iotest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private File sdroot, approot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Log.v("brad","Permission:NO");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);
        }else{
            // Permission is granted
            Log.v("brad","Permission:YES");
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
            Log.v("brad","Permission was granted!!");
        }else{
            Log.v("brad","Permission was not granted!!");
            finish();
        }
    }

    private void init(){
        sp = getSharedPreferences("gamedata",MODE_PRIVATE);
        editor = sp.edit();
        sdroot = Environment.getExternalStorageDirectory();
        Log.v("brad",sdroot.getAbsolutePath());

        approot = new File(sdroot,
                "Android/data/"+getPackageName());
        if(!approot.exists()){
            Log.v("brad","no app dir");
            approot.mkdir();
            Log.v("brad","app dir made");
        }



    }

    public void test1(View view) {
        editor.putString("username","brad");
        editor.putInt("stage",7);
        editor.putBoolean("sound",true);

        //到這一行才正式寫入設定檔中
        editor.commit();

        showDialog("訊息","存擋好了");
    }

    public void test2(View view) {
        String username = sp.getString("username","nobody");
        Boolean isSound = sp.getBoolean("sound",false);
        int stage = sp.getInt("stage",0);

        showDialog("取資料","UserName: " + username + "\n"+
                "Stage: "+stage+"\n"+
                "Sound: "+(isSound?"On":"Off"));
    }

    private void showDialog(String title, String mesg){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title);
//        builder.setMessage(mesg);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(mesg)
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("cancel",null)
                .setNeutralButton("wait",null)
                .create()
                .show();



    }

    public void test3(View view) {
        try {
            FileOutputStream fout = openFileOutput("test1.txt",MODE_PRIVATE);
            fout.write("Hello, World".getBytes());
            fout.flush();
            fout.close();
            showDialog("Message","Save OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test4(View view) {
        try {
            FileInputStream fin = openFileInput("test1.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(fin));
            String mesg = reader.readLine();
            fin.close();

            showDialog("取回",mesg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test5(View view) {
        //SD CARD/Android/data/Package name/ 與內存特性相同,共存亡
        File file1 = new File(sdroot,"brad2.txt");
        try {
            FileOutputStream fout =
                    new FileOutputStream(file1);
            fout.write("Hello, Brad2".getBytes());
            fout.flush();
            fout.close();
            showDialog("OK1","OK1");
        } catch (Exception e) {
            Log.v("brad","test5"+e.toString());
        }
    }

    public void test6(View view) {
        //自訂路徑,與程式存亡不相關
        File file1 = new File(approot,"brad3.txt");
        try {
            FileOutputStream fout =
                    new FileOutputStream(file1);
            fout.write("Hello, Brad3".getBytes());
            fout.flush();
            fout.close();
            showDialog("OK2","OK2");
        } catch (Exception e) {
            Log.v("brad","test6"+e.toString());
        }
    }

    public void test7(View view) {
        //對應test5(),檔案存在sdroot
        File file1 = new File(sdroot,"brad2.txt");
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file1)));
            String mesg = reader.readLine();
            reader.close();
            showDialog("test7",mesg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void test8(View view) {
        //對應test6(),檔案存在approot
        File file1 = new File(approot,"brad3.txt");
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file1)));
            String mesg = reader.readLine();
            reader.close();
            showDialog("test8",mesg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
