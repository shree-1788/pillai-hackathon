package com.example.oldagemanagmentsystem;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Male_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Male_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView courseRV;
    private ArrayList<PersonViewItems> coursesArrayList;
    private viewPersonAdapter courseRVAdapter;

    private String mParam1;
    private String mParam2;

    RecyclerView recview;
    PersonViewAdapter adapter;

    private FirebaseFirestore db;
    ProgressBar loadingPB;

    TextView tvdisp;


    public Male_Fragment() {
        // Required empty public constructor
    }


    public static Male_Fragment newInstance(String param1, String param2) {
        Male_Fragment fragment = new Male_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_male_, container, false);
        
        /*String strtext = getArguments() != null ?
                getArguments().getString("filter_value") : "Select a category to filter";

            Toast.makeText(getContext(),strtext, Toast.LENGTH_SHORT).show();*/

        recview=(RecyclerView)view.findViewById(R.id.male_recycler);
        loadingPB = view.findViewById(R.id.idProgressBar);
        db=FirebaseFirestore.getInstance();
        tvdisp=view.findViewById(R.id.data_fetch);
        coursesArrayList = new ArrayList<>();
        recview.setHasFixedSize(true);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        courseRVAdapter = new viewPersonAdapter(coursesArrayList, getContext());

        // setting adapter to our recycler view.
        recview.setAdapter(courseRVAdapter);

        db.collection("Male Users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are
                            // hiding our progress bar and adding
                            // our data in a list.
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                PersonViewItems c = d.toObject(PersonViewItems.class);

                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                coursesArrayList.add(c);
                            }
                            // after adding the data to recycler view.
                            // we are calling recycler view notifuDataSetChanged
                            // method to notify that data has been changed in recycler view.
                            courseRVAdapter.notifyDataSetChanged();
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data
                Toast.makeText(getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });

        db.collection("Filter")
                .whereEqualTo("Filter Selected","Healthy")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("vedant", document.getId() + " => " + document.getData());

                                String doc=document.getData().toString();
                                tvdisp.setText(doc);



                                //Toast.makeText(getContext(), doc, Toast.LENGTH_SHORT).show();
                             //   Toast.makeText(getContext(), "Value Selected", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("parab", "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("Filter")
                .whereEqualTo("Filter Selected","Literate")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("vedant", document.getId() + " => " + document.getData());

                                String doc=document.getData().toString();
                                tvdisp.setText(doc);



                                //Toast.makeText(getContext(), doc, Toast.LENGTH_SHORT).show();
                                //   Toast.makeText(getContext(), "Value Selected", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("parab", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return view;
    }
}