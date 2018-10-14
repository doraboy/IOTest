package tw.dora.iotest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("gamedata",MODE_PRIVATE);
        editor = sp.edit();
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

    }

    public void test4(View view) {

    }
}
