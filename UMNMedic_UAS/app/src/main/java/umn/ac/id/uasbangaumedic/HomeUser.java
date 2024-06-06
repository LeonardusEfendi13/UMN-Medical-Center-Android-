package umn.ac.id.uasbangaumedic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class HomeUser extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        Toolbar tb = findViewById(R.id.toolbar);
        tb.setTitle("Hello, " + MainActivity.nama);
        setSupportActionBar(tb);

        ImageView emergencyCall =findViewById(R.id.emergencyCall);
        emergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:(021)542208087000"));
                startActivity(i);
            }
        });

        ImageView requestForm = findViewById(R.id.requestHelp);
        ImageView viewRequest = findViewById(R.id.viewRequest);
        ImageView snk = findViewById(R.id.SnK);
        ImageView aboutus = findViewById(R.id.aboutUs);


        requestForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(HomeUser.this, RequestForm.class);
                startActivity(form);
            }
        });

        viewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewRequest = new Intent(HomeUser.this, ViewRequest.class);
                startActivity(viewRequest);
            }
        });
        snk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snk = new Intent(HomeUser.this, SyaratDanKetentuan.class);
                startActivity(snk);
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutus = new Intent(HomeUser.this, AboutUs.class);
                startActivity(aboutus);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tb_icon, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profile) {
            Intent intent = new Intent(HomeUser.this, Profile.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}