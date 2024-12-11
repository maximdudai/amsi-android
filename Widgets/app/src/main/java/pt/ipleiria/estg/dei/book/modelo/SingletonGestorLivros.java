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

        livros.add(new Livro( R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 1", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro( R.drawable.programarandroid1, 2024,  "Programar em Android AMSI - 2", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro( R.drawable.logoipl, 2024, "Programar em Android AMSI - 3", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro( R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 4", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro( R.drawable.programarandroid1, 2024, "Programar em Android AMSI - 5", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro( R.drawable.logoipl, 2024, "Programar em Android AMSI - 6", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro( R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 7", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro( R.drawable.programarandroid1, 2024, "Programar em Android AMSI - 8", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro( R.drawable.logoipl, 2024, "Programar em Android AMSI - 9", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro( R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 10", "2ª Temporada", "AMSI TEAM"));
    }

    public ArrayList<Livro> listaLivro() {
        return new ArrayList<>(livros);
    }

    public Livro getLivro(int id) {

        for(Livro l: livros) {
            if(l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }
    public void removeLivro(Livro livro) {
        Livro l = getLivro(livro.getId());

        if(l != null) {
            livros.remove(livro);
        }
    }
    public void editarLivro(Livro newLivro) {
        Livro l = getLivro(newLivro.getId());

        if(l != null) {
            l.setTitulo(newLivro.getTitulo());
            l.setAno(newLivro.getAno());
            l.setAutor(newLivro.getAutor());
            l.setSerie(newLivro.getSerie());
        }
    }
}