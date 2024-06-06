package umn.ac.id.uasbangaumedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginPage extends AppCompatActivity {
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Button login = findViewById(R.id.btnLogin);
        EditText email = findViewById(R.id.emailLogin);
        EditText password = findViewById(R.id.passwordLogin);
        TextView regNow = findViewById(R.id.registerNow);
        db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                        Toast.makeText(LoginPage.this, "Silahkan mengisi semua field", Toast.LENGTH_SHORT).show();
                }else{
                    db.collection("Student")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    int stat = 0;
                                    if(task.isSuccessful()){
                                        for(QueryDocumentSnapshot doc: task.getResult()){
                                            String a = doc.getString("emailReg");
                                            String b = doc.getString("passwordReg");
                                            String status = doc.getString("statusReg");
                                            String namaReg = doc.getString("namaReg");
                                            String nimReg = doc.getString("nimReg");
                                            String a1 = email.getText().toString().trim();
                                            String b1 = password.getText().toString().trim();
                                            String hashedPass = md5(b1);
                                            if(a.equalsIgnoreCase(a1) & b.equalsIgnoreCase(hashedPass)){
                                                Toast.makeText(LoginPage.this, "Logged as "+namaReg, Toast.LENGTH_SHORT).show();
                                                MainActivity.email = a;
                                                MainActivity.nama = namaReg;
                                                MainActivity.nim = nimReg;
                                                editor.putString(MainActivity.LOGGED_IN_EMAIL_KEY, a);
                                                editor.putString(MainActivity.LOGGED_IN_NAME_KEY, namaReg);
                                                editor.putString(MainActivity.LOGGED_IN_NIM_KEY, nimReg);
                                                editor.putString(MainActivity.LOGGED_IN_STATUS_KEY, status);
                                                editor.apply();
                                                if(status.equalsIgnoreCase("Mahasiswa")){
                                                    Intent intent = new Intent(LoginPage.this, HomeUser.class);
                                                    String nama = doc.getString("namaReg");
                                                    startActivity(intent);
                                                }
                                                else if(status.equalsIgnoreCase("Admin")){
                                                    Intent intent = new Intent(LoginPage.this, HomeAdmin.class);
                                                    String nama = doc.getString("namaReg");
                                                    startActivity(intent);
                                                }
                                                stat = 1;
                                                break;
                                            }
                                        }
                                        if(stat == 0){
                                            email.getText().clear();
                                            password.getText().clear();
                                            email.requestFocus();
                                            Toast.makeText(LoginPage.this, "Email or Password Incorrect", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
        regNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }
    private String md5(String pass){
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(pass.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
