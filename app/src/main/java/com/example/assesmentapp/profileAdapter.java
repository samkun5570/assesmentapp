package com.example.assesmentapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class profileAdapter extends FirebaseRecyclerAdapter<ProfileModel,profileAdapter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private Context context;
    public profileAdapter(@NonNull FirebaseRecyclerOptions<ProfileModel> options, Context context) {
        super(options);
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,address,phone,hobbies;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.namelist);
            email = itemView.findViewById(R.id.emaillist);
            address = itemView.findViewById(R.id.addresslist);
            phone = itemView.findViewById(R.id.phonelist);
            hobbies = itemView.findViewById(R.id.hobbieslist);
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if (context instanceof ProfileViewActivity) {
            ((ProfileViewActivity)context).disable();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_resorce, parent, false);
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull ProfileModel profileModel) {
        holder.name.setText(profileModel.getName());
        System.out.println(" name "+profileModel.getName());
        holder.email.setText(profileModel.getEmail());
        System.out.println(" email "+profileModel.getEmail());
        holder.address.setText(profileModel.getAddress());
        System.out.println(" address "+profileModel.getAddress());
        holder.phone.setText(profileModel.getPhone());
        System.out.println(" phone "+profileModel.getPhone());
        holder.hobbies.setText(profileModel.getHobbies());
        System.out.println(" hobbies "+profileModel.getHobbies());

//        holder.root.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
//                System.out.println(String.valueOf(position));
//            }
//        });
    }
}
