package pt.ipleiria.estg.dei.book.modelo;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.book.R;

public class SingletonGestorLivros {

    private static SingletonGestorLivros instance = null;
    private ArrayList<Livro> livros;


    public static synchronized SingletonGestorLivros getInstance() {
        if(instance == null) {
            instance = new SingletonGestorLivros();
        }
        return instance;
    }

    private SingletonGestorLivros() {
         gerarDadosDinamico();
    }

    private void gerarDadosDinamico() {
        livros = new ArrayList<>();

        Livro livro = new Livro(1, 2002, "Hello World", "2 temp", "AMSI team", R.drawable.programarandroid1);
        livros.add(livro);
    }

    public ArrayList<Livro> listaLivro() {
        return new ArrayList<>(livros);
    }
}
