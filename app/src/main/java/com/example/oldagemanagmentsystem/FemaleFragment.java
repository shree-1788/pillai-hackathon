package com.example.oldagemanagmentsystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FemaleFragment extends Fragment {

    private ArrayList<PersonViewItems> coursesArrayList;
    private viewPersonAdapter courseRVAdapter;
    RecyclerView recview;
    ImageButton imageButton;
    Toolbar toolbar;

    private FirebaseFirestore db;
    ProgressBar loadingPB;

    TextView tvdisp;

    public FemaleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_female, container, false);

        setHasOptionsMenu(true);

        imageButton=(ImageButton)view.findViewById(R.id.GoBackButton_female);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),MainDasboard.class));
            }
        });

        toolbar=view.findViewById(R.id.toolbar_records_female);
        toolbar.setTitle("Male Records");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        recview=(RecyclerView)view.findViewById(R.id.female_recycler);
        recview.setNestedScrollingEnabled(false);
        loadingPB = view.findViewById(R.id.idProgressBar_female);
        db=FirebaseFirestore.getInstance();
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
                        if (!queryDocumentSnapshots.isEmpty()) {
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                PersonViewItems c = d.toObject(PersonViewItems.class);
                                coursesArrayList.add(c);
                            }
                            courseRVAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.records_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}