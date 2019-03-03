package com.example.vitorpereira.projetofinal_android_ufg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class CadastrarReceita extends AppCompatActivity {


    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageButton btnImageButton;

    EditText nomeReceita, descricaoReceita;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_receita);

        Button btnCadastrar = (Button)findViewById(R.id.cpBtnCadastrar);
        EditText nomeReceita = (EditText)findViewById(R.id.nomeReceita);
        EditText descricaoReceita = (EditText)findViewById(R.id.descricaoReceita);
        btnImageButton = (ImageButton) findViewById(R.id.btnImageButton);

        nomeReceita.setOnClickListener(onClickView);
        descricaoReceita.setOnClickListener(onClickView);
        btnCadastrar.setOnClickListener(onClickView);

        btnImageButton.setOnClickListener(onClickView);

        inicializeFirebase();
    }

    private void inicializeFirebase() {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private View.OnClickListener onClickView = new View.OnClickListener() {

        public void onClick(View view){

            nomeReceita = findViewById(R.id.nomeReceita);
            descricaoReceita = findViewById(R.id.descricaoReceita);

            String nome = nomeReceita.getText().toString();
            String descricao = descricaoReceita.getText().toString();

            switch (view.getId()) {
                case R.id.cpBtnCadastrar: {
                    if (nome.equals("") || descricao.equals("")) {
                        valida();
                    } else {
                        Receita receita = new Receita();

                        String uId = UUID.randomUUID().toString();

                        receita.setuId(uId);
                        receita.setNomeReceita(nome);
                        receita.setDescricaoReceita(descricao);

                        databaseReference.child("Receitas").child(receita.getuId()).setValue(receita);

                        uploadImage(uId);

                        Toast.makeText(getApplicationContext(), "Adicionado com Sucesso", Toast.LENGTH_SHORT).show();

                        limpaCampos();
                    }
                    break;
                }
                case R.id.btnImageButton:{

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                btnImageButton.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(String uid){
        if(filePath != null)
        {
            final ProgressBar simpleProgressBar=(ProgressBar) findViewById(R.id.progressBar);

            StorageReference ref = storageReference.child("imagens/"+ uid);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            simpleProgressBar.setVisibility(View.GONE);
                            Toast.makeText(CadastrarReceita.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            simpleProgressBar.setVisibility(View.GONE);
                            Toast.makeText(CadastrarReceita.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            simpleProgressBar.setVisibility(View.VISIBLE);
                        }
                    });
        }

    }

    private void limpaCampos() {
        nomeReceita.setText("");
        descricaoReceita.setText("");
    }

    private void valida() {

        String nome = nomeReceita.getText().toString();
        String descricao = descricaoReceita.getText().toString();

        if (nome.equals("")) {
            nomeReceita.setError("Campo requerido");
        }
        if (descricao.equals("")) {
            nomeReceita.setError("Campo requerido");
        }
    }
}
