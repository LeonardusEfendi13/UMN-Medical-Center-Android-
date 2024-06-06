package umn.ac.id.uasbangaumedic;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MemberSchedule extends AppCompatActivity {
    public static LinkedList<JadwalPiket> jadwalPiket ;
    public static LinkedList<JadwalPiket> allSchedule = new LinkedList<>();
    public static String checkIntent = "";

    private ImageView nextDay;
    private TextView date;
    private String TanggalHariIni;

    private RecyclerView mRecyclerView;
    private MemberScheduleAdapter mAdapter;

    private FirebaseFirestore db;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_schedule);
        jadwalPiket  = new LinkedList<>();
        nextDay = findViewById(R.id.nextDay);
        date = findViewById(R.id.dateValue);
        backBtn = findViewById(R.id.backCustom);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, MMM d, yyyy", Locale.US);
        TanggalHariIni = dateFormat.format(new Date());
        allSchedule.clear();
        db = FirebaseFirestore.getInstance();



        TanggalHariIni = TanggalHariIni.substring(0, 3);
        if(TanggalHariIni.equalsIgnoreCase("Mon")){
            TanggalHariIni = "Senin";
        } else if(TanggalHariIni.equalsIgnoreCase("Tue")){
            TanggalHariIni = "Selasa";
        } else if(TanggalHariIni.equalsIgnoreCase("Wed")){
            TanggalHariIni = "Rabu";
        } else if(TanggalHariIni.equalsIgnoreCase("Thu")){
            TanggalHariIni = "Kamis";
        } else if(TanggalHariIni.equalsIgnoreCase("Fri")){
            TanggalHariIni = "Jumat";
        } else if(TanggalHariIni.equalsIgnoreCase("Sat")){
            TanggalHariIni = "Sabtu";
        } else{
            TanggalHariIni = "Minggu";
        }
        date.setText(TanggalHariIni);


        db.collection("schedule")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(int i = 0; i < list.size(); i++) {
//                                FormMedic form = list.get(i).toObject(FormMedic.class);
                            Map<String, Object> tes = list.get(i).getData();

                            JadwalPiket form = new JadwalPiket(
                                    (String) tes.get("Hari"),
                                    (String) tes.get("Jam"),
                                    (String) tes.get("Nama"),
                                    (String) tes.get("id"),
                                    (int) R.drawable.ic_baseline_delete_24);
                            Log.d("Nama", form.getNama());
                            allSchedule.add(form);
                        }
                        if(list.size() == 0) {
                            Toast.makeText(getApplicationContext(), "There is no schedule", Toast.LENGTH_LONG).show();
                        }

                        for(int i = 0; i<allSchedule.size(); i++){
                            if(allSchedule.get(i).getHari().equalsIgnoreCase(TanggalHariIni)){
                                jadwalPiket.add(allSchedule.get(i));
                            }
                        }
                        useAdapter();
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Fail db", "Fail to get data");
                    }
                });

        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TanggalHariIni.equals("Senin")){
                    TanggalHariIni = "Selasa";
                    date.setText(TanggalHariIni);
                    jadwalPiket.clear();
                    listSchedule();
                    useAdapter();
//                    Toast.makeText(MemberSchedule.this, "Contoh " + jadwalPiket.get(0).getNama(), Toast.LENGTH_SHORT).show();
                }
                else if(TanggalHariIni.equals("Selasa")){
                    TanggalHariIni = "Rabu";
                    date.setText(TanggalHariIni);
                    jadwalPiket.clear();
                    listSchedule();
                    useAdapter();
                }
                else if(TanggalHariIni.equals("Rabu")){
                    TanggalHariIni = "Kamis";
                    date.setText(TanggalHariIni);
                    jadwalPiket.clear();
                    listSchedule();
                    useAdapter();
                }
                else if(TanggalHariIni.equals("Kamis")){
                    TanggalHariIni = "Jumat";
                    date.setText(TanggalHariIni);
                    jadwalPiket.clear();
                    listSchedule();
                    useAdapter();
                }
                else if(TanggalHariIni.equals("Jumat")){
                    TanggalHariIni = "Sabtu";
                    date.setText(TanggalHariIni);
                    jadwalPiket.clear();
                    listSchedule();
                    useAdapter();
                }
                else if(TanggalHariIni.equals("Sabtu")){
                    TanggalHariIni = "Minggu";
                    date.setText(TanggalHariIni);
                    jadwalPiket.clear();
                    listSchedule();
                    useAdapter();
                }
                else if(TanggalHariIni.equals("Minggu")){
                    TanggalHariIni = "Senin";
                    date.setText(TanggalHariIni);
                    jadwalPiket.clear();
                    listSchedule();
                    useAdapter();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(MemberSchedule.this, HomeAdmin.class);
                startActivity(form);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

    }

    private void deleteSchedule() {
        db.collection("schedule").document(checkIntent)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                        checkIntent = "";
                        Toast.makeText(MemberSchedule.this,  "Delete Schedule Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MemberSchedule.this, MemberSchedule.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }

    void listSchedule() {
        for(int i = 0; i<allSchedule.size(); i++){
            if(allSchedule.get(i).getHari().equalsIgnoreCase(TanggalHariIni)){
                jadwalPiket.add(allSchedule.get(i));
            }
        }
    }


    void showCustomDialog(){
        final Dialog dialog = new Dialog(MemberSchedule.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_add_member);

        final EditText namaSchd = dialog.findViewById(R.id.namaSchd);
        final EditText hariSchd = dialog.findViewById(R.id.hariSchd);
        final EditText jamSchd = dialog.findViewById(R.id.jamSchd);
        Button submitButton = dialog.findViewById(R.id.btnAddSchd);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = namaSchd.getText().toString();
                String hari = hariSchd.getText().toString();
                String jam = jamSchd.getText().toString();

                DocumentReference newNoteRef = db
                        .collection("schedule")
                        .document();
                String id = newNoteRef.getId();

                if(nama.equals("") || hari.equals("") || jam.equals("")){
                    Toast.makeText(MemberSchedule.this,  "Please Input All Missing Field", Toast.LENGTH_SHORT).show();
                } else if(!hari.equalsIgnoreCase("Senin") && !hari.equalsIgnoreCase("Selasa") && !hari.equalsIgnoreCase("Rabu") &&
                        !hari.equalsIgnoreCase("Kamis") && !hari.equalsIgnoreCase("Jumat") && !hari.equalsIgnoreCase("Sabtu") &&
                        !hari.equalsIgnoreCase("Minggu") ){
                    Toast.makeText(MemberSchedule.this,  "Mohon masukkan nama hari dalam Bahasa Indonesia", Toast.LENGTH_SHORT).show();
                } else{

                    Map<String, Object> req = new HashMap<>();

                    req.put("Hari", hari);
                    req.put("Jam", jam);
                    req.put("Nama", nama);
                    req.put("id", id);

                    newNoteRef.set(req).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("Add schedule success", "DocumentSnapshot added with ID: ");
                                Toast.makeText(MemberSchedule.this,  "Add Schedule Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MemberSchedule.this, MemberSchedule.class);
                                startActivity(intent);
                            }
                            else{
                                Log.w("Error db", "Error adding document");
                            }
                        }
                    });

                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    void useAdapter(){
        mRecyclerView = findViewById(R.id.viewRequestView);
        mAdapter = new MemberScheduleAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(!checkIntent.equals("")){
            deleteSchedule();
        }
    }
}