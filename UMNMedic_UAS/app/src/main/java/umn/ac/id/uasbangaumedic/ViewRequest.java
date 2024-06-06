package umn.ac.id.uasbangaumedic;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ViewRequest extends AppCompatActivity {
    public static LinkedList<FormMedic> request;
    private RecyclerView mRecyclerView;
    private DaftarRequestAdapter mAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        request  = new LinkedList<>();
        db = FirebaseFirestore.getInstance();

        Toast.makeText(getApplicationContext(), "Retrieving your requests", Toast.LENGTH_LONG).show();

        db.collection("form")
                .whereEqualTo("email", MainActivity.email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(int i = 0; i < list.size(); i++) {
//                                FormMedic form = list.get(i).toObject(FormMedic.class);
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
                            request.add(form);
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

        Toolbar tb = findViewById(R.id.toolbar);
        tb.setTitle("View Request");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.viewRequestView);
        mAdapter = new DaftarRequestAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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