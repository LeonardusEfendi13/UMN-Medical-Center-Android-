package umn.ac.id.uasbangaumedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterPage extends AppCompatActivity {
    private FirebaseFirestore db;
    private EditText nama, nim, prodi, emailReg, passwordReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Button register = findViewById(R.id.btnRegister);
        nama = findViewById(R.id.namaReg);
        nim = findViewById(R.id.nimReg);
        prodi = findViewById(R.id.prodiReg);
        emailReg = findViewById(R.id.emailReg);
        passwordReg = findViewById(R.id.passwordReg);
        TextView loginNow = findViewById(R.id.LoginNow);
        db = FirebaseFirestore.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String takeNama  = nama.getText().toString();
                String takeNim = nim.getText().toString();
                String takeProdi = prodi.getText().toString();
                String takeEmail = emailReg.getText().toString();
                String takePass = passwordReg.getText().toString();
                if(emailReg.getText().toString().isEmpty() || nama.getText().toString().isEmpty() || nim.getText().toString().isEmpty() || passwordReg.getText().toString().isEmpty()){
                    Toast.makeText(RegisterPage.this, "Silahkan mengisi semua field", Toast.LENGTH_SHORT).show();
                }else if(!(emailReg.getText().toString().contains("umn.ac.id"))) {
                    Toast.makeText(RegisterPage.this, "Harus menggunakan email UMN", Toast.LENGTH_SHORT).show();
                } else {
                    String hashedPass = md5(takePass);
                    addToDbReg(takeNama, takeNim, takeProdi, takeEmail, hashedPass);
                }
            }
        });
        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }

    private String md5(String pass){
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(pass.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    private void addToDbReg(String nama, String nim, String prodi, String emailReg, String passwordReg) {
        CollectionReference dbStudent = db.collection("Student");
        StudentData student = new StudentData(nama, nim, prodi, emailReg, passwordReg, "Mahasiswa");
        dbStudent.add(student).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterPage.this, "Your Data has been registered", Toast.LENGTH_SHORT).show();
                Intent home = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(home);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterPage.this, "Fail to add data \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}