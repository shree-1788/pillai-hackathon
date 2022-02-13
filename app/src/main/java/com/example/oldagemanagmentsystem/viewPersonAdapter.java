package com.example.oldagemanagmentsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class viewPersonAdapter extends RecyclerView.Adapter<viewPersonAdapter.ViewHolder> {

    private ArrayList<PersonViewItems> coursesArrayList;
    private Context context;

    public viewPersonAdapter(ArrayList<PersonViewItems> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.person, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PersonViewItems courses = coursesArrayList.get(position);
        holder.fullname.setText(courses.getName());
        holder.religion.setText(courses.getReligion());
        holder.age.setText(courses.getAge());
        Picasso.get()
                .load(courses.getImage())
                .fit()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return coursesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullname;
        TextView religion;
        TextView age;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.fullname);
            religion = itemView.findViewById(R.id.religion);
            age = itemView.findViewById(R.id.age);
            imageView=itemView.findViewById(R.id.person_pic);
        }
    }
    }
