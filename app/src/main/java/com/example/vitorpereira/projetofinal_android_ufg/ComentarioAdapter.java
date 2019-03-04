package com.example.vitorpereira.projetofinal_android_ufg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ComentarioAdapter extends ArrayAdapter<Comentario> {

    private final Context context;
    private final List<Comentario> elementos;

    private StorageReference storageReference;

    public ComentarioAdapter(Context context, List<Comentario> elementos) {
        super(context, R.layout.item_comentario, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_comentario, parent, false);

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageStorage = storageReference.child("imagens/"+elementos.get(position).getuId());

        ImageView imagem = (ImageView) rowView.findViewById(R.id.imageComment);
        TextView textoComentario = (TextView) rowView.findViewById(R.id.txt_nomeusuario_id);

        GlideApp.with(getContext()).load(imageStorage).into(imagem);

        imagem.setImageResource(elementos.get(position).getImagem());
        textoComentario.setText(elementos.get(position).getTextoComentario());

        return rowView;
    }
}
