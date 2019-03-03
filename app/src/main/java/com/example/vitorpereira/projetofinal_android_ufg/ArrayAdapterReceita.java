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

public class ArrayAdapterReceita extends ArrayAdapter<Receita> {

    private final Context context;
    private final List<Receita> elementos;

    private StorageReference storageReference;

    public ArrayAdapterReceita(Context context, List<Receita> elementos) {
        super(context,R.layout.list_receitas,elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_receitas, parent, false);

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageStorage = storageReference.child("imagens/"+elementos.get(position).getuId());

        ImageView imagem = (ImageView) rowView.findViewById(R.id.imageHome);
        TextView nomeReceita = (TextView) rowView.findViewById(R.id.nomeReceitaHome);
        TextView descricaoReceita = (TextView) rowView.findViewById(R.id.descricaoReceitaHome);

        GlideApp.with(getContext()).load(imageStorage).into(imagem);

        imagem.setImageResource(elementos.get(position).getImagem());
        nomeReceita.setText(elementos.get(position).getNomeReceita());
        descricaoReceita.setText(elementos.get(position).getDescricaoReceita());

        return rowView;
    }
}
