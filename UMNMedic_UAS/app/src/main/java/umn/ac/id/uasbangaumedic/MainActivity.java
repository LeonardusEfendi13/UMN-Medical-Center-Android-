package umn.ac.id.uasbangaumedic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
//    ini agek tempat kw tarok email yg lgi login
    public static String email = "email";
    public static String nama = "nama";
    public static String nim = "nim";
    public static String status = "status";
    public static String LOGGED_IN_EMAIL_KEY = "email_key";
    public static String LOGGED_IN_NAME_KEY = "nama_key";
    public static String LOGGED_IN_NIM_KEY = "nim_key";
    public static String LOGGED_IN_STATUS_KEY = "status_key";
    Timer timer;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        email = sharedPref.getString(LOGGED_IN_EMAIL_KEY, "");
        nama = sharedPref.getString(LOGGED_IN_NAME_KEY, "");
        nim = sharedPref.getString(LOGGED_IN_NIM_KEY, "");
        status = sharedPref.getString(LOGGED_IN_STATUS_KEY, "");

        timer = new Timer();

        if(email.equals("") || nim.equals("") || nama.equals("")) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                }
            }, 1500);
        } else if(status.equals("Admin")){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, HomeAdmin.class);
                    startActivity(intent);
                    finish();
                }
            }, 1500);
        }
        else{
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, HomeUser.class);
                    startActivity(intent);
                    finish();
                }
            }, 1500);
        }



    }
}