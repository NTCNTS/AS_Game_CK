package com.example.tonghop_linh;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameAdapter extends FirebaseRecyclerAdapter<Game,GameAdapter.myviewholder>
{

    public GameAdapter(@NonNull FirebaseRecyclerOptions<Game> options) {
        super(options);
    }

    @SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Game model) {
        holder.nametext.setText(model.getGame());
        holder.categorytext.setText(model.getCategory());
        holder.pricetext.setText(model.getPrice());
        Glide.with(holder.img1.getContext()).load(model.getImage()).into(holder.img1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity= (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Detail_Game(model.getCategory(),model.getGame(),model.getImage(),model.getPrice())).addToBackStack(null).commit();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img1.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_game))
                        .setExpanded(true, 1350)
                        .create();

                View view = dialogPlus.getHolderView();
                //anh xa
                EditText game = view.findViewById(R.id.edt_game);
                EditText category    = view.findViewById(R.id.edt_category);
                EditText price    = view.findViewById(R.id.edt_price);
                EditText imgage   = view.findViewById(R.id.edt_address_image);

                Button btn_update = view.findViewById(R.id.btn_update);
                game.setText(model.getGame());
                category.setText(model.getCategory());
                price.setText(model.getPrice());
                imgage.setText(model.getImage());

                dialogPlus.show();

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("game",game.getText().toString());
                        map.put("category",category.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("image",imgage.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Game")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nametext.getContext(),"Succesfully Updated ",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.nametext.getContext(),"Updated Failur2 ",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nametext.getContext());
                builder.setTitle("Remove");
                builder.setMessage("Deleted Data can't be recovered!");
                // xoa
                builder.setPositiveButton("Delete ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Game")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.nametext.getContext(),"Removed was not done",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_game,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img1,edit,remove;
        TextView nametext,pricetext,categorytext;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            img1=itemView.findViewById(R.id.img1);
            edit=itemView.findViewById(R.id.edit);
            remove=itemView.findViewById(R.id.remove);
            nametext=itemView.findViewById(R.id.nametext);
            pricetext=itemView.findViewById(R.id.pricetext);
            categorytext=itemView.findViewById(R.id.categorytext);
        }
    }

}
