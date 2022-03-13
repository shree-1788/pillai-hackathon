package com.example.oldagemanagmentsystem;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class Records_Activity_Main extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton imageButton;
    String filterString="0";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Male_Fragment()).commit();

//        db=FirebaseFirestore.getInstance();


//        imageButton=(ImageButton)findViewById(R.id.GoBackButton);
//
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),MainDasboard.class));
//            }
//        });

//        toolbar=findViewById(R.id.toolbar_records);
//        toolbar.setTitle("Welcome To Records");
//        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Male_Fragment()).commit();
        }


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.records_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_male:
                            selectedFragment = new Male_Fragment();
                            break;
                        case R.id.nav_female:s:
                            selectedFragment = new FemaleFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if (item.getItemId()==R.id.healthy){
//
//           Toast.makeText(this, "You selected Healthy", Toast.LENGTH_SHORT).show();
//            db.collection("Filter")
//                    .document("Filter Selected")
//                    .update("Selection","Healthy");
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
//        }
//        if (item.getItemId()==R.id.lit){
//
//            Toast.makeText(this, "You selected Literate", Toast.LENGTH_SHORT).show();
//            db.collection("Filter")
//                    .document("Filter Selected")
//                    .update("Selection","Literate");
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}