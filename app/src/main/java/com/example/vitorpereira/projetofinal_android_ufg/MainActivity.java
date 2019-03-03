package com.example.vitorpereira.projetofinal_android_ufg;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;



import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    DatabaseReference dataBaseReference;
    StorageReference storageReference;
    
    private List<Receita> listaReceitas = new ArrayList<Receita>();
    ArrayAdapter<Receita> ArrayAdapterReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializeFirebase();

        listaDados();

        firebaseAuth = FirebaseAuth.getInstance();

        Button deleteAccount = findViewById(R.id.delete_account_button);

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().delete(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Your account has been deleted. ",Toast.LENGTH_SHORT).show();
                            signOut();
                        }
                    }
                });
            }
        });

    }

    private void listaDados() {
        dataBaseReference.child("Receitas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaReceitas.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Receita receita = dataSnapshot1.getValue(Receita.class);
                    listaReceitas.add(receita);
                }

                ListView lista = (ListView) findViewById(R.id.lvMain);

                ArrayAdapterReceita adapter = new ArrayAdapterReceita(MainActivity.this, listaReceitas);
                lista.setAdapter(adapter);;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializeFirebase() {
        FirebaseApp.initializeApp(this);
        dataBaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.icon_logout){
            AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Sucessfully Signed out.",Toast.LENGTH_SHORT).show();
                        signOut();
                    }
                }
            });
        }
        if (menuItem.getItemId()==R.id.icon_add){
            Intent intent = new Intent(this,CadastrarReceita.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null){
            signOut();
        }
    }

    private void signOut() {
            Intent loginIntent = new Intent(this,LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
    }
}
