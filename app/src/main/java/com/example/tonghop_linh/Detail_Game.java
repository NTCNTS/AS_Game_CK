package com.example.tonghop_linh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class Detail_Game extends Fragment {

    String category,game,image,price;

    public Detail_Game(String category, String game, String image, String price) {
        this.category = category;
        this.game = game;
        this.image = image;
        this.price = price;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.activity_detail, container, false);
        ImageView imageDetail = view.findViewById(R.id.imageviewDetail);
        TextView textGame = view.findViewById(R.id.textviewGame);
        TextView txtCategory = view.findViewById(R.id.textviewCategory);
        TextView txtPrice = view.findViewById(R.id.textviewPrice);

        textGame.setText(game);
        txtCategory.setText(category);
        txtPrice.setText(price);
        Glide.with(getContext()).load(image).into(imageDetail);

        return view;
    }
    public void onBackPressed(){
        AppCompatActivity activity= (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new menu_list()).addToBackStack(null).commit();

    }

}
