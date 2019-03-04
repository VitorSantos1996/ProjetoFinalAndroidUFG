package com.example.vitorpereira.projetofinal_android_ufg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ComentarioActivity extends AppCompatActivity {

    DatabaseReference dataBaseReference;
    StorageReference storageReference;

    private List<Comentario> listaComentarios = new ArrayList<Comentario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_comments);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarComentario(this);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        inicializeFirebase();
        listaDados();

    }

    private void cadastrarComentario(View.OnClickListener onClickListener) {
        Intent intent = new Intent(this, CadastrarComentatio.class);
        startActivity(intent);
    }

    private void inicializeFirebase() {
        FirebaseApp.initializeApp(this);
        dataBaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    private void listaDados() {
        dataBaseReference.child("Comentarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaComentarios.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Comentario comentario = dataSnapshot1.getValue(Comentario.class);
                    listaComentarios.add(comentario);
                }

                ListView lista = (ListView) findViewById(R.id.listview_coment_id);
                ComentarioAdapter adapter = new ComentarioAdapter(ComentarioActivity.this, listaComentarios);
                lista.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
