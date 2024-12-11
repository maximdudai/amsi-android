package pt.ipleiria.estg.dei.book;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adaptadores.ListaLivrosAdaptador;
import pt.ipleiria.estg.dei.book.modelo.Livro;
import pt.ipleiria.estg.dei.book.modelo.SingletonGestorLivros;

public class ListaLivrosFragment extends Fragment {

    private ListView lvLivros;
    private ArrayList<Livro> livros;

    public ListaLivrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_livros, container, false);

        lvLivros = view.findViewById(R.id.lvLivros);
        livros = SingletonGestorLivros.getInstance().listaLivro();

        lvLivros.setAdapter(new ListaLivrosAdaptador(getContext(), livros));

        lvLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID_LIVRO, (int) l);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        livros = SingletonGestorLivros.getInstance().listaLivro();

        ((ListaLivrosAdaptador) lvLivros.getAdapter()).updateData(livros);
    }
}