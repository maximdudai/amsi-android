package pt.ipleiria.estg.dei.book.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.book.modelo.Livro;

public class LivroJsonParser {

    public static ArrayList<Livro> parserJsonLivros(JSONArray response) {
        ArrayList<Livro> livros = new ArrayList<>();

        try {

            for(int i = 0; i < response.length(); i++) {
                JSONObject livro = (JSONObject) response.get(i);

                int idLivro = livro.getInt("id");
                String titulo = livro.getString("titulo");
                String serie = livro.getString("serie");
                String autor = livro.getString("autor");
                int ano = livro.getInt("ano");
                String capa = livro.getString("capa");

                Livro auxLivro = new Livro(idLivro, capa, ano, titulo, serie, autor);
                livros.add(auxLivro);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return livros;
    }

    public static Livro parserJsonLivro(String response) {

        Livro auxLivro = null;

        try {

            JSONObject livro = new JSONObject(response);

            int idLivro = livro.getInt("id");
            String titulo = livro.getString("titulo");
            String serie = livro.getString("serie");
            String autor = livro.getString("autor");
            int ano = livro.getInt("ano");
            String capa = livro.getString("capa");

            auxLivro = new Livro(idLivro, capa, ano, titulo, serie, autor);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return auxLivro;
    }

    // TODO: ParserJsonLogin

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
