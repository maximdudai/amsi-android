package pt.ipleiria.estg.dei.book;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import adaptadores.GrelhaLivrosAdaptador;
import adaptadores.ListaLivrosAdaptador;
import pt.ipleiria.estg.dei.book.modelo.Livro;
import pt.ipleiria.estg.dei.book.modelo.SingletonGestorLivros;

public class GrelhaLivrosFragment extends Fragment {

    private GridView gridView;
    private ArrayList<Livro> livros;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grelha_livros, container, false);

        gridView = view.findViewById(R.id.gridView);
        livros = SingletonGestorLivros.getInstance().listaLivro();

        gridView.setAdapter(new GrelhaLivrosAdaptador(getContext(), livros));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID_LIVRO, (int) l);
                startActivity(intent);
            }
        });

        return view;
    }
}