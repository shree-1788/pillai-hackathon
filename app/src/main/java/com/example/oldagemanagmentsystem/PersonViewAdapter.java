package com.example.oldagemanagmentsystem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class PersonViewAdapter  extends FirestoreRecyclerAdapter<PersonViewItems, PersonViewAdapter.PersonHolder> {

    @Override
    protected void onBindViewHolder(@NonNull PersonHolder holder, int position, @NonNull PersonViewItems model) {
        holder.fullname.setText(model.getName());
        holder.fullname.setText(model.getReligion());
        holder.fullname.setText(model.getAge());
        Picasso.get()
                .load(model.getImage())
                .fit()
                .into(holder.imageView);
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    class PersonHolder extends RecyclerView.ViewHolder {
        TextView fullname;
        TextView religion;
        TextView age;
        ImageView imageView;

        public PersonHolder(View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.fullname);
            religion = itemView.findViewById(R.id.religion);
            age = itemView.findViewById(R.id.age);
            imageView=itemView.findViewById(R.id.person_pic);
        }
    }

    public PersonViewAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

}
