package com.example.vitorpereira.projetofinal_android_ufg;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewReceitaAdapter extends RecyclerView.Adapter<RecyclerViewReceitaAdapter.MyViewHolder> {

    private Context context;
    private List<Receita> receitas;

    public RecyclerViewReceitaAdapter(Context context, List<Receita> receitas) {
        this.context = context;
        this.receitas = receitas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.cardview_item_receita, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.nomeReceita.setText(receitas.get(position).getNome());
        // TODO: 02/03/19 converter para idImage
        holder.imgReceita.setImageResource(R.drawable.img_teste);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReceitaActivity.class);
                intent.putExtra("receita", receitas.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeReceita;
        ImageView imgReceita;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            nomeReceita = (TextView) itemView.findViewById(R.id.txt_titulo_card_item);
            imgReceita = (ImageView) itemView.findViewById(R.id.imageview_card_item);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);

        }
    }
}
