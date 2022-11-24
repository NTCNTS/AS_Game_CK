package com.example.tonghop_linh;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class menu_profile extends Fragment {

     TextView namePf,phonePf,mailPf,birthPf,locPf;
     Button btnPf;

    private String userID;
    private FirebaseUser user;
    private DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_profile,container,false);

            namePf=view.findViewById(R.id.namepf);
            phonePf= view.findViewById(R.id.phonenumberPf);
            mailPf= view.findViewById(R.id.mailPf);
            birthPf= view.findViewById(R.id.birthPf);
            locPf= view.findViewById(R.id.locationPf);
            btnPf= view.findViewById(R.id.buttonPf);


        btnPf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Login.class);
                startActivity(intent);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();

        final TextView namePF=namePf;
        final TextView phonePF=phonePf;
        final TextView mailPF=mailPf;
        final TextView birthPF=birthPf;
        final TextView LocPF=locPf;

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);

                if (userProfile!=null){
                    String name=userProfile.name;
                    String email=userProfile.mail;
                    String phone=userProfile.phone;
                    String birth=userProfile.birth;
                    String loc=userProfile.location;

                    namePF.setText(name);
                    phonePF.setText(phone);
                    mailPF.setText(email);
                    birthPF.setText(birth);
                    LocPF.setText(loc);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
