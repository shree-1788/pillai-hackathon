package com.example.oldagemanagmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ManageData extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5,et6,et7;
    String age,bed,health,literate,name,pension,religion,userid,uniqueId;
    Button add_male,add_female;
    ImageView add_image;
    FirebaseFirestore firestore;
    FirebaseAuth fAuth;
    String img_url="null";
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        uniqueId = ref.push().getKey();
        et1=(EditText) findViewById(R.id.age_et);
        et2=(EditText) findViewById(R.id.bed_et);
        et3=(EditText) findViewById(R.id.health_con_et);
        et4=(EditText) findViewById(R.id.literacy_et);
        et5=(EditText) findViewById(R.id.name_et);
        et6=(EditText) findViewById(R.id.pension_et);
        et7=(EditText) findViewById(R.id.religion_et);

        add_male=(Button)findViewById(R.id.add_male);
        add_female=(Button)findViewById(R.id.add_female);

        fAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        add_image=(ImageView)findViewById(R.id.add_image_folder);

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFileChooser();
            }
        });



        add_male.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            age = et1.getText().toString();
                                            bed = et2.getText().toString();
                                            health = et3.getText().toString();
                                            literate = et4.getText().toString();
                                            name = et5.getText().toString();
                                            pension = et6.getText().toString();
                                            religion = et7.getText().toString();
                                           // userid = fAuth.getCurrentUser().getUid();
                                            DocumentReference documentReference = firestore.collection("Male Users").document(uniqueId);
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("Age", age);
                                            user.put("Bed Written", bed);
                                            user.put("Health Condition", health);
                                            user.put("Literacy", literate);
                                            user.put("Name", name);
                                            user.put("Pension", pension);
                                            user.put("Religion", religion);
                                            user.put("Image",img_url);
                                            //user.put("userType",spin_val);
                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(ManageData.this, "Successful", Toast.LENGTH_SHORT).show();
                                                    Intent intent = getIntent();
                                                    finish();
                                                    startActivity(intent);

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(ManageData.this, "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            startstorageprocessimage();
                                        }
                                    });

        add_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                age=et1.getText().toString();
                bed=et2.getText().toString();
                health=et3.getText().toString();
                literate=et4.getText().toString();
                name=et5.getText().toString();
                pension=et6.getText().toString();
                religion=et7.getText().toString();
            }
        });
    }

    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void startstorageprocessimage() {
        if(mImageUri==null){
            Toast.makeText(ManageData.this, "PLS UPLOAD AN IMAGE", Toast.LENGTH_SHORT).show();
        }

        final StorageReference reference=storageReference.child("Profile Pics").child("Male/"+name+System.currentTimeMillis()+".jpeg");
        reference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Male Users").document(uniqueId);
                        Map<String,Object> user= new HashMap<>();
                        user.put("Image",uri.toString());
                        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ManageData.this, "Photo Updated", Toast.LENGTH_SHORT).show();
                                //preogressbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(ManageData.this, "Data Updated, Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainDasboard.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ManageData.this, "Photo Updating Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                    exception.printStackTrace();
                }
            });
            builder.build().load(mImageUri).into(add_image);
        }
    }
}