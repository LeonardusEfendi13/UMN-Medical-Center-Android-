package umn.ac.id.uasbangaumedic;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ViewJob extends AppCompatActivity {
    public static LinkedList<FormMedic> job;
    private RecyclerView mRecyclerView;
    private ViewJobAdapter mAdapter;
    private ImageView backBtn;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);
        job  = new LinkedList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("form")
                .whereEqualTo("status", 0)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(int i = 0; i < list.size(); i++) {
                            Map<String, Object> tes = list.get(i).getData();

                            FormMedic form = new FormMedic(
                                    (String) tes.get("email"),
                                    (String) tes.get("nama"),
                                    (String) tes.get("nim"),
                                    (String) tes.get("lineId"),
                                    (String) tes.get("organisasi"),
                                    (String) tes.get("jabatan"),
                                    (String) tes.get("acara"),
                                    (String) tes.get("tanggalPelaksanaan"),
                                    (String) tes.get("waktuTempatPelaksanaan"),
                                    (String) tes.get("deskripsi"),
                                    ((Long) tes.get("jumlahMedis")).intValue(),
                                    (String) tes.get("keterangan"),
                                    (String) tes.get("tanggalPengembalian"),
                                    (boolean) tes.get("request"),
                                    ((Long) tes.get("status")).intValue(),
                                    (String) tes.get("id"),
                                    (String) tes.get("imagePath"),
                                    (String) tes.get("imageFileName"));
                            Log.d("Nama", form.getNama());
                            job.add(form);
                        }
                        if(list.size() == 0) {
                            Toast.makeText(getApplicationContext(), "You have no request", Toast.LENGTH_LONG).show();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Fail db", "Fail to get data");
                    }
                });

        mRecyclerView = findViewById(R.id.viewRequestView);
        mAdapter = new ViewJobAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        backBtn = findViewById(R.id.backCustom);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(ViewJob.this, HomeAdmin.class);
                startActivity(form);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}