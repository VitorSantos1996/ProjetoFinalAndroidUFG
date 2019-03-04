package com.example.vitorpereira.projetofinal_android_ufg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ReceitaActivity extends AppCompatActivity {

    private static DatabaseReference dataBaseReference;
    private static StorageReference storageReference;

    private static List<Comentario> listaComentarios = new ArrayList<Comentario>();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    static Receita receita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        receita = (Receita) getIntent().getSerializableExtra("receita");

        inicializeFirebase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_receita_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putSerializable("receita", receita);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View viewReceitaDetail = inflater.inflate(R.layout.fragment_activity_detalhe_receita, container, false);
            final View viewComment = inflater.inflate(R.layout.fragment_activity_comments, container, false);

            Receita receitaDetalhe = new Receita();

            if (getArguments() != null) {
                receitaDetalhe = (Receita) getArguments().getSerializable("receita");
            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

                TextView nomeReceita = (TextView) viewReceitaDetail.findViewById(R.id.txt_receita_nome_id);
                TextView ingredientes = (TextView) viewReceitaDetail.findViewById(R.id.txt_ingredientes_id);
                ImageView imgReceita = (ImageView) viewReceitaDetail.findViewById(R.id.img_receita_id);

                nomeReceita.setText(receitaDetalhe.getNome());
                ingredientes.setText(receitaDetalhe.getIngredientes());
                imgReceita.setImageResource(R.drawable.img_teste);

                return viewReceitaDetail;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                FloatingActionButton fab = (FloatingActionButton) viewComment.findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(viewComment.getContext(), CadastrarComentatio.class);
                        startActivity(intent);
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                listaDados(viewComment);
                return viewComment;
            }
            return null;
        }

    }

    private void inicializeFirebase() {
        FirebaseApp.initializeApp(this);
        dataBaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    private static void listaDados(final View view) {
        dataBaseReference.child("Comentarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaComentarios.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Comentario comentario = dataSnapshot1.getValue(Comentario.class);
                    listaComentarios.add(comentario);
                }

                ListView lista = (ListView) view.findViewById(R.id.listview_coment_id);
                ComentarioAdapter adapter = new ComentarioAdapter(view.getContext(), listaComentarios);
                lista.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
