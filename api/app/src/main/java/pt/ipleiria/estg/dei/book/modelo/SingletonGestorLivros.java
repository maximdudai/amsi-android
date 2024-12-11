package pt.ipleiria.estg.dei.book.modelo;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.book.DetalhesLivroActivity;
import pt.ipleiria.estg.dei.book.MenuMainActivity;
import pt.ipleiria.estg.dei.book.R;
import pt.ipleiria.estg.dei.book.listeners.LivroListener;
import pt.ipleiria.estg.dei.book.listeners.LivrosListener;
import pt.ipleiria.estg.dei.book.listeners.LoginListener;
import pt.ipleiria.estg.dei.book.utils.LivroJsonParser;
import pt.ipleiria.estg.dei.book.utils.LoginParser;

public class SingletonGestorLivros {

    private static SingletonGestorLivros instance = null;
    private ArrayList<Livro> livros;
    private ArrayList<String> dadosApi;

    private LivroBDHelper livroBDHelper = null;

    //api
    private static RequestQueue volleyQueue = null;

    private LivrosListener livrosListener;
    private LivroListener livroListener;
    private LoginListener loginListener;

    public static synchronized SingletonGestorLivros getInstance(Context context) {
        if(instance == null) {
            instance = new SingletonGestorLivros(context);
            //api
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private static String mUrlAPILivros = "http://amsi.dei.estg.ipleiria.pt/api/livros";

    //end point for login
    private static final String murlAPILogin = "http://amsi.dei.estg.ipleiria.pt/api/auth/login";

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
        livroBDHelper.adicionarLivroBD(livro);
    }
    public void removeLivro(Livro livro) {
        Livro l = getLivroBD(livro.getId());

        if(l != null) {
            livroBDHelper.removerLivroBd(l.getId());
        }
    }

    public void adicionarLivrosBD(ArrayList<Livro> livros) {
        livroBDHelper.apagarLivros();

        for(Livro l: livros) {
            adicionarLivroBD(l);
        }
    }

    public void editarLivroBD(Livro newLivro) {
        Livro l = getLivroBD(newLivro.getId());
        if(l != null) {
            livroBDHelper.editarLivroBD(newLivro);
        }
    }

    //region # MÉTODOS - API

    //TODO: FALTA COLOCAR O PARAMETRO DE TOKEN
    public void adicionarLivroAPI(final Livro livro, final Context context) {
        if(!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, context.getString(R.string.no_internet_access), Toast.LENGTH_SHORT).show();
        } else {

            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPILivros, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    adicionarLivroBD(LivroJsonParser.parserJsonLivro(response));

                    //TODO: IMPLEMENTAR LISTENER
                    if(livroListener != null) {
                        livroListener.onRefreshDetalhes(MenuMainActivity.ADD);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token", "AMSI-TOKEN");
                    params.put("titulo", livro.getTitulo());
                    params.put("serie", livro.getSerie());
                    params.put("autor", livro.getAutor());
                    params.put("ano", ""+livro.getAno());
                    params.put("capa", livro.getCapa() == null ? DetalhesLivroActivity.DEFAULT_IMG : livro.getCapa());

                    return params;
                }
            };
            volleyQueue.add(request);

        }
    }
    
    public void getAllLivrosAPI(final Context context) {
        if(!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, context.getString(R.string.no_internet_access), Toast.LENGTH_SHORT).show();

            //TODO: Carregar livros da DB atraves do metodo getAllLivrosDB()
            if(livrosListener != null)
                livrosListener.onRefreshListaLivros(livroBDHelper.getAllLivrosBD());
        } else {

            //pedido ao API

            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPILivros, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("-----> GETAPI: " + response);

                    livros = LivroJsonParser.parserJsonLivros(response);
                    adicionarLivrosBD(livros);

                    //TODO: IMPLEMENTAR LISTENERS
                    if(livrosListener != null)
                        livrosListener.onRefreshListaLivros(livros);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            volleyQueue.add(req);
        }

    }

    public void editarLivroAPI(final Livro livro, final Context context) {
        if(!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, context.getString(R.string.no_internet_access), Toast.LENGTH_SHORT).show();
        } else {

            StringRequest request = new StringRequest(Request.Method.PUT, mUrlAPILivros + "/" + livro.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    adicionarLivroBD(LivroJsonParser.parserJsonLivro(response));

                    //TODO: IMPLEMENTAR LISTENER
                    if(livroListener != null) {
                        livroListener.onRefreshDetalhes(MenuMainActivity.EDIT);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token", "AMSI-TOKEN");
                    params.put("titulo", livro.getTitulo());
                    params.put("serie", livro.getSerie());
                    params.put("autor", livro.getAutor());
                    params.put("ano", ""+livro.getAno());
                    params.put("capa", livro.getCapa() == null ? DetalhesLivroActivity.DEFAULT_IMG : livro.getCapa());

                    return params;
                }
            };
            volleyQueue.add(request);

        }

    }

    public void removerLivroAPI(final Livro livro, final Context context) {
        if(!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, context.getString(R.string.no_internet_access), Toast.LENGTH_SHORT).show();
        } else {

            StringRequest request = new StringRequest(Request.Method.PUT, mUrlAPILivros + "/" + livro.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    removeLivro(livro);
                    //TODO: IMPLEMENTAR LISTENER

                    if(livroListener != null) {
                        livroListener.onRefreshDetalhes(MenuMainActivity.DELETE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(request);

        }
    }

    public void loginAPI(String email, String password, final Context context) {
        StringRequest request = new StringRequest(Request.Method.POST, murlAPILogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String token = String.valueOf(LoginParser.parseLoginData(response));

                System.out.println("---- token " + token);

                if(loginListener != null)
                    loginListener.onValidateLogin(token, "something", context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        volleyQueue.add(request);
    }


    //registo listeners
    public void setLivrosListener(LivrosListener livrosListener) {
        this.livrosListener = livrosListener;
    }

    public void setLivroListener(LivroListener livroListener) {
        this.livroListener = livroListener;
    }
    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
    //endregion
}