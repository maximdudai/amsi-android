package pt.ipleiria.estg.dei.book.modelo;

import android.content.Context;

import java.util.ArrayList;

public class SingletonGestorLivros {

    private static SingletonGestorLivros instance = null;
    private ArrayList<Livro> livros;

    private LivroBDHelper livroBDHelper = null;

    public static synchronized SingletonGestorLivros getInstance(Context context) {
        if(instance == null) {
            instance = new SingletonGestorLivros(context);
        }
        return instance;
    }

    private SingletonGestorLivros(Context context) {
        livros = new ArrayList<>();
        livroBDHelper = new LivroBDHelper(context);
    }

    public ArrayList<Livro> listaLivro() {
        livros = livroBDHelper.getAllLivrosBD();
        return new ArrayList<>(livros);
    }

    public Livro getLivroBD(int id) {

        for(Livro l: livros) {
            if(l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    public void adicionarLivroBD(Livro livro) {
        Livro auxLivro = livroBDHelper.adicionarLivroBD(livro);
        if(auxLivro != null) {
            livros.add(livro);
        }
    }
    public void removeLivro(Livro livro) {
        Livro l = getLivroBD(livro.getId());

        if(l != null) {
            if(livroBDHelper.removerLivroBd(l.getId())) {
                livros.remove(l);
            }
        }
    }
    public void editarLivroBD(Livro newLivro) {
        Livro l = getLivroBD(newLivro.getId());
        if(l != null) {
            livroBDHelper.editarLivroBD(newLivro);
        }
    }
}