package umn.ac.id.uasbangaumedic;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailRequest extends AppCompatActivity {
    private int index;
    private TextView nama, nim, lineId, organisasi, jabatan, tipe, acara, tanggalPelaksanaan, waktuTempatPelaksanaan, alatJasa, alatJasaValue, deskripsi, keterangan, status;
    private Button backToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);
        Intent intent = getIntent();
        index = intent.getIntExtra("Index", 0);

        Toolbar tb = findViewById(R.id.toolbar);
        tb.setTitle("Request Detail");
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
        status = findViewById(R.id.status);
        backToHome = findViewById(R.id.backToHome);

        FormMedic detail = ViewRequest.request.get(index);

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
        if(detail.getStatus() == 0) {
            status.setText("Pending");
            status.setBackgroundTintList(this.getResources().getColorStateList(R.color.white));
        } else if(detail.getStatus() == 1) {
            status.setText("Ditolak");
            status.setBackgroundTintList(this.getResources().getColorStateList(R.color.red));
        } else {
            status.setText("Diterima");
            status.setBackgroundTintList(this.getResources().getColorStateList(R.color.green));
        }

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(DetailRequest.this, HomeUser.class);
                startActivity(home);
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