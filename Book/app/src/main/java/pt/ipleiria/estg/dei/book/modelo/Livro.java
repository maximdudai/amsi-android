package pt.ipleiria.estg.dei.book.modelo;

public class Livro {
    private int id, ano, capa;
    private String titulo, serie, autor;

    public Livro(int id, int ano, String titulo, String serie, String autor, int capa) {
        this.id = id;
        this.ano = ano;
        this.titulo = titulo;
        this.serie = serie;
        this.autor = autor;
        this.capa = capa;
    }



    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getCapa() {
        return capa;
    }

    public void setCapa(int capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
