package umn.ac.id.uasbangaumedic;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestForm extends AppCompatActivity {
    private EditText inputNama, inputNIM, inputlineId, inputOrganisasi, inputJabatan, inputAcara, inputTanggalPelaksanaan, inputWaktuTempatPelaksaan, inputTanggalPengembalian, inputJumlahMedis, inputDeskripsi, inputKeterangan;
    private Spinner inputTipe;
    private Button cancelButton, requestButton, takeButton, chooseButton;
    private ImageView fotoValidasi;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static String imageFileName, imagePath = "default";
    private byte bb[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);
        Toolbar tb = findViewById(R.id.toolbar);
        tb.setTitle("Request Form");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        inputNama = findViewById(R.id.inputNama);
        inputNIM = findViewById(R.id.inputNIM);
        inputlineId = findViewById(R.id.inputlineId);
        inputOrganisasi = findViewById(R.id.inputOrganisasi);
        inputJabatan = findViewById(R.id.inputJabatan);
        inputAcara = findViewById(R.id.inputAcara);
        inputTanggalPelaksanaan = findViewById(R.id.inputTanggalPelaksanaan);
        inputWaktuTempatPelaksaan = findViewById(R.id.inputWaktuTempatPelaksaan);
        inputTanggalPengembalian = findViewById(R.id.inputTanggalPengembalian);
        inputJumlahMedis = findViewById(R.id.inputJumlahMedis);
        inputDeskripsi = findViewById(R.id.inputDeskripsi);
        inputKeterangan = findViewById(R.id.inputKeterangan);
        cancelButton = findViewById(R.id.cancelButton);
        requestButton = findViewById(R.id.requestButton);
        fotoValidasi = findViewById(R.id.fotoKTM);
        takeButton = findViewById(R.id.btnFoto);
        inputNama.setText(MainActivity.nama);
        inputNIM.setText(MainActivity.nim);

        inputTipe = findViewById(R.id.inputTipe);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.tipe_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputTipe.setAdapter(spinnerAdapter);

        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(RequestForm.this, HomeUser.class);
                startActivity(home);
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference newNoteRef = db
                        .collection("form")
                        .document();
                String id = newNoteRef.getId();
                String nama = inputNama.getText().toString();
                String nim = inputNIM.getText().toString();
                String lineId = inputlineId.getText().toString();
                String organisasi = inputOrganisasi.getText().toString();
                String jabatan = inputJabatan.getText().toString();
                boolean request;
                if(inputTipe.getSelectedItem().toString().equals("Jasa")) {
                    request = true;
                } else {
                    request = false;
                }
                String acara = inputAcara.getText().toString();
                String tanggalPelaksanaan = inputTanggalPelaksanaan.getText().toString();
                String waktuTempatPelaksanaan = inputWaktuTempatPelaksaan.getText().toString();
                int jumlahMedis = jumlahMedis = Integer.parseInt(inputJumlahMedis.getText().toString());;
                String tanggalPengembalian = inputTanggalPengembalian.getText().toString();
                if(request == true) {
                    jumlahMedis = Integer.parseInt(inputJumlahMedis.getText().toString());
                    tanggalPengembalian = "";
                } else {
                    tanggalPengembalian = inputTanggalPengembalian.getText().toString();
                    jumlahMedis = 0;
                }
                String deskripsi = inputDeskripsi.getText().toString();
                String keterangan = inputKeterangan.getText().toString();

                if(nama.equals("") || nim.equals("") || lineId.equals("") || organisasi.equals("") || jabatan.equals("") || acara.equals("") ||
                        tanggalPelaksanaan.equals("") || waktuTempatPelaksanaan.equals("") || deskripsi.equals("")) {
                    Toast.makeText(getApplicationContext(), "Mohon isi seluruh informasi penting", Toast.LENGTH_SHORT).show();
                } else {
                    if(request == false && tanggalPengembalian.equals("")) {
                        Toast.makeText(getApplicationContext(), "Mohon isi seluruh informasi penting", Toast.LENGTH_SHORT).show();
                    } else if(request == true && jumlahMedis == 0) {
                        Toast.makeText(getApplicationContext(), "Mohon isi seluruh informasi penting", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        FormMedic newForm = new FormMedic(MainActivity.email, nama, nim, lineId, organisasi, jabatan, acara, tanggalPelaksanaan,
                                waktuTempatPelaksanaan, deskripsi, jumlahMedis, keterangan, tanggalPengembalian, request, 0, id, imagePath, imageFileName);
                        addToDb(newForm, newNoteRef);
                        Intent home = new Intent(RequestForm.this, HomeUser.class);
                        home.putExtra("Status", true);
                        startActivity(home);

                    }

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                imageFileName = "JPEG_" + timeStamp;
                onCaptureImageResult(data, imageFileName);
            }
        }
    }

    private void onCaptureImageResult(Intent data, String namaFile) {
        Bitmap image = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        bb = bytes.toByteArray();
        fotoValidasi.setImageBitmap(image);
        uploadImagetoDB(bb, namaFile);
    }

    private void uploadImagetoDB(byte[] bb, String namaFile){

        StorageReference sr = storageReference.child("images/"+namaFile);
        sr.putBytes(bb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        imagePath = task.getResult().toString();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RequestForm.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addToDb(FormMedic newForm, DocumentReference newNoteRef) {

        newNoteRef.set(newForm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("Success db", "DocumentSnapshot added with ID: ");
                }
                else{
                    Log.w("Error db", "Error adding document");
                }
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