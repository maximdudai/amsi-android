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

        livros.add(new Livro(1, R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 1", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2, R.drawable.programarandroid1, 2024,  "Programar em Android AMSI - 2", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(3, R.drawable.logoipl, 2024, "Programar em Android AMSI - 3", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(4, R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 4", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(5, R.drawable.programarandroid1, 2024, "Programar em Android AMSI - 5", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(6, R.drawable.logoipl, 2024, "Programar em Android AMSI - 6", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(7, R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 7", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(8, R.drawable.programarandroid1, 2024, "Programar em Android AMSI - 8", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(9, R.drawable.logoipl, 2024, "Programar em Android AMSI - 9", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(10, R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 10", "2ª Temporada", "AMSI TEAM"));
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


}