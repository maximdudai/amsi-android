package pt.ipleiria.estg.dei.book;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.book.modelo.Livro;
import pt.ipleiria.estg.dei.book.modelo.SingletonGestorLivros;

public class DinamicoFragment extends Fragment {

    private EditText Titulo, Serie, Autor, Ano;
    private ImageView imgCapa;

    public DinamicoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dinamico, container, false);

        Titulo = view.findViewById(R.id.titulo);
        Serie = view.findViewById(R.id.serie);
        Autor = view.findViewById(R.id.autor);
        Ano = view.findViewById(R.id.ano);
        imgCapa = view.findViewById(R.id.capa);

        this.carregarLivros();

        return view;
    }

    private void carregarLivros() {
        ArrayList<Livro> livros = SingletonGestorLivros.getInstance().listaLivro();

        if(livros.isEmpty())
            return;

        Livro livro = livros.get(0);
        Titulo.setText(livro.getTitulo());
        Serie.setText(livro.getSerie());
        Autor.setText(livro.getAutor());
        Ano.setText("" + livro.getAno());
        imgCapa.setImageResource(livro.getCapa());
    }

}