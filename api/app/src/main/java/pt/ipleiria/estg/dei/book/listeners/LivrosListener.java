package pt.ipleiria.estg.dei.book.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.book.modelo.Livro;

public interface LivrosListener {
    void onRefreshListaLivros(ArrayList<Livro> listaLivros);
}