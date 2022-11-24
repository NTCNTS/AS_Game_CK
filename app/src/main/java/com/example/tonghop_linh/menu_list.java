package com.example.tonghop_linh;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class menu_list extends Fragment {



    RecyclerView recyclerView;
    GameAdapter adapter;

    FloatingActionButton floatingActionButton;

    private MenuItem menuItem;
    private SearchView searchView;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_menu_list, container, false);
       toolbar=view.findViewById(R.id.toolbar);
       AppCompatActivity activity=(AppCompatActivity) getActivity();
       activity.setSupportActionBar(toolbar);
       activity.getSupportActionBar().setTitle("");

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Game> options =
                new FirebaseRecyclerOptions.Builder<Game>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Game"), Game.class)
                        .build();

        adapter=new GameAdapter(options);
        recyclerView.setAdapter(adapter);

        floatingActionButton=(FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),Add_list.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.searchmenu,menu );
        menuItem=menu.findItem(R.id.search);
        searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconified(true);

        SearchManager searchManager= (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myseach(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                myseach(query);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void myseach(String query) {
        FirebaseRecyclerOptions<Game> options =
                new FirebaseRecyclerOptions.Builder<Game>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Game").orderByChild("game").startAt(query).endAt(query + "\uf8ff"), Game.class)
                        .build();
        adapter=new GameAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}