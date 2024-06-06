package umn.ac.id.uasbangaumedic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class HomeAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        Toolbar tb = findViewById(R.id.toolbar);
        tb.setTitle("Hello, " + MainActivity.nama);
        setSupportActionBar(tb);


        ImageView manageRequest = findViewById(R.id.requestManager);
        ImageView memberSchedule = findViewById(R.id.memberSchedule);


        manageRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(HomeAdmin.this, ViewJob.class);
                startActivity(form);
            }
        });

        memberSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewRequest = new Intent(HomeAdmin.this, MemberSchedule.class);
                startActivity(viewRequest);
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
            Intent intent = new Intent(HomeAdmin.this, Profile.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}