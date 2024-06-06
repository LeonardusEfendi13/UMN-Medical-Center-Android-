package umn.ac.id.uasbangaumedic;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DetailJob extends AppCompatActivity {
    private int index;
    private TextView nama, nim, lineId, organisasi, jabatan, tipe, acara, tanggalPelaksanaan, waktuTempatPelaksanaan, alatJasa, alatJasaValue, deskripsi, keterangan;
    private Button accept, decline;
    private ImageView fotoValidasi;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);
        Intent intent = getIntent();
        index = intent.getIntExtra("Index", 0);
        Toolbar tb = findViewById(R.id.toolbar);
        tb.setTitle("Job Request Detail");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama = findViewById(R.id.nama);
        nim = findViewById(R.id.nim);
        lineId = findViewById(R.id.lineId);
        organisasi = findViewById(R.id.organisasi);
        jabatan = findViewById(R.id.jabatan);
        tipe = findViewById(R.id.request);
        acara = findViewById(R.id.acara);
        tanggalPelaksanaan = findViewById(R.id.tanggalPelaksanaan);
        waktuTempatPelaksanaan = findViewById(R.id.waktuTempatPelaksaan);
        alatJasa = findViewById(R.id.alatJasa);
        alatJasaValue = findViewById(R.id.alatJasaValue);
        deskripsi = findViewById(R.id.deskripsi);
        keterangan = findViewById(R.id.keterangan);
        accept = findViewById(R.id.tindakanAccept);
        decline = findViewById(R.id.tindakanDecline);
        fotoValidasi = findViewById(R.id.fotoValidasi);

        FormMedic detail = ViewJob.job.get(index);

        nama.setText(detail.getNama());
        nim.setText(detail.getNim());
        lineId.setText(detail.getLineId());
        organisasi.setText(detail.getOrganisasi());
        jabatan.setText(detail.getJabatan());
        if(detail.getRequest()) {
            tipe.setText("Jasa");
            alatJasa.setText("Jumlah medis :");
            alatJasaValue.setText(""+detail.getJumlahMedis());
        } else {
            tipe.setText("Peralatan");
            alatJasa.setText("Tanggal pengembalian :");
            alatJasaValue.setText(detail.getTanggalPengembalian());
        }
        acara.setText(detail.getAcara());
        tanggalPelaksanaan.setText(detail.getTanggalPelaksanaan());
        waktuTempatPelaksanaan.setText(detail.getWaktuTempatPelaksanaan());
        deskripsi.setText((detail.getDeskripsi()));
        keterangan.setText((detail.getKeterangan()));
        String url = detail.getImagePath();
        Glide.with(this).load(url).into(fotoValidasi);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> req = new HashMap<>();
                req.put("acara", detail.getAcara());
                req.put("deskripsi", detail.getDeskripsi());
                req.put("email", detail.getEmail());
                req.put("jabatan", detail.getJabatan());
                req.put("jumlahMedis", detail.getJumlahMedis());
                req.put("keterangan", detail.getKeterangan());
                req.put("lineId", detail.getLineId());
                req.put("nama", detail.getNama());
                req.put("nim", detail.getNim());
                req.put("organisasi", detail.getOrganisasi());
                req.put("request", detail.getRequest());
                req.put("status", 2);
                req.put("tanggalPelaksanaan", detail.getTanggalPelaksanaan());
                req.put("tanggalPengembalian", detail.getTanggalPengembalian());
                req.put("waktuTempatPelaksanaan", detail.getWaktuTempatPelaksanaan());
                req.put("id", detail.getId());


                db.collection("form").document(detail.getId())
                        .set(req)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error writing document", e);
                            }
                        });

                detail.setStatus(2);
                Intent back = new Intent(DetailJob.this, ViewJob.class);
                startActivity(back);
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> req = new HashMap<>();
                req.put("acara", detail.getAcara());
                req.put("deskripsi", detail.getDeskripsi());
                req.put("email", detail.getEmail());
                req.put("jabatan", detail.getJabatan());
                req.put("jumlahMedis", detail.getJumlahMedis());
                req.put("keterangan", detail.getKeterangan());
                req.put("lineId", detail.getLineId());
                req.put("nama", detail.getNama());
                req.put("nim", detail.getNim());
                req.put("organisasi", detail.getOrganisasi());
                req.put("request", detail.getRequest());
                req.put("status", 1);
                req.put("tanggalPelaksanaan", detail.getTanggalPelaksanaan());
                req.put("tanggalPengembalian", detail.getTanggalPengembalian());
                req.put("waktuTempatPelaksanaan", detail.getWaktuTempatPelaksanaan());
                req.put("id", detail.getId());
                db.collection("form").document(detail.getId())
                        .set(req)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error writing document", e);
                            }
                        });
                detail.setStatus(1);
                Intent back = new Intent(DetailJob.this, ViewJob.class);
                startActivity(back);
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