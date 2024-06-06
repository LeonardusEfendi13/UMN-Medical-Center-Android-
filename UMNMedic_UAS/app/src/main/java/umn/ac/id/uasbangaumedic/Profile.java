package umn.ac.id.uasbangaumedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {
    private Button logout;
    private FirebaseFirestore db;
    TextView nama, email, nim, prodi, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar tb = findViewById(R.id.toolbar);
        tb.setTitle("Profile");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();

        nama = findViewById(R.id.namaProfile);
        email = findViewById(R.id.emailProfile);
        nim = findViewById(R.id.nimProfile);
        prodi = findViewById(R.id.prodiProfile);
        status = findViewById(R.id.statusProfile);

        db = FirebaseFirestore.getInstance();
        db.collection("Student")
                .whereEqualTo("emailReg", MainActivity.email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc: task.getResult()){
                                nama.setText(doc.getString("namaReg"));
                                email.setText(doc.getString("emailReg"));
                                nim.setText(doc.getString("nimReg"));
                                prodi.setText(doc.getString("prodi"));
                                status.setText(doc.getString("statusReg"));
                            }
                        }
                    }
                });

        logout = findViewById(R.id.logoutBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}