package com.example.tonghop_linh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Add_list extends AppCompatActivity {

    Button btn_save,btn_back;
    EditText edt_game,edt_category,edt_price,edt_address_image;

    public static boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        Anhxa();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserData();
                clearAll();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_list.this,MainActivity.class);
                status = true;
                startActivity(intent);

            }
        });

    }

    private void Anhxa() {
        btn_save=(Button) findViewById(R.id.btn_save);
        btn_back=(Button) findViewById(R.id.btn_back);

        edt_game = findViewById(R.id.edt_game);
        edt_category = findViewById(R.id.edt_category);
        edt_price = findViewById(R.id.edt_price);
        edt_address_image = findViewById(R.id.edt_address_image);
    }

    private void inserData(){

        Map<String,Object> map = new HashMap<>();
        map.put("game",edt_game.getText().toString());
        map.put("category",edt_category.getText().toString());
        map.put("price",edt_price.getText().toString());
        map.put("image",edt_address_image.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Game").push()
                .setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Add_list.this,"Successfully completed",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_list.this,"Failure addition",Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private  void clearAll(){
        edt_game.setText("");
        edt_category.setText("");
        edt_price.setText("");
        edt_address_image.setText("");
    }

}