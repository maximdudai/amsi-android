package pt.ipleiria.estg.dei.book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.Inflater;

import adaptadores.ListaLivrosAdaptador;
import pt.ipleiria.estg.dei.book.modelo.Livro;
import pt.ipleiria.estg.dei.book.modelo.SingletonGestorLivros;

public class ListaLivrosFragment extends Fragment {

    private ListView lvLivros;
    private ArrayList<Livro> livros;
    private FloatingActionButton fabLista;

    private SearchView searchView;

    public ListaLivrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_livros, container, false);

        setHasOptionsMenu(true);

        lvLivros = view.findViewById(R.id.lvLivros);
        fabLista = view.findViewById(R.id.fabLista);

        livros = SingletonGestorLivros.getInstance().listaLivro();
        lvLivros.setAdapter(new ListaLivrosAdaptador(getContext(), livros));

        lvLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID_LIVRO, (int) l);
                startActivityForResult(intent, MenuMainActivity.EDIT);
            }
        });

        fabLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(getContext(), DetalhesLivroActivity.class);
                newActivity.putExtra(DetalhesLivroActivity.ID_LIVRO, -1);
                startActivityForResult(newActivity, MenuMainActivity.ADD);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_pesquisa, menu);
        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Livro> tempLivros = new ArrayList<>();
                for (Livro l: SingletonGestorLivros.getInstance().listaLivro()) {
                    if(l.getTitulo().toLowerCase().contains(s.toLowerCase())) {
                        tempLivros.add(l);
                    }
                }
                lvLivros.setAdapter(new ListaLivrosAdaptador(getContext(), tempLivros));
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == MenuMainActivity.ADD || requestCode == MenuMainActivity.EDIT) {

                livros = SingletonGestorLivros.getInstance().listaLivro();
                lvLivros.setAdapter(new ListaLivrosAdaptador(getContext(), livros));

                switch (requestCode) {
                    case MenuMainActivity.ADD:
                        Snackbar.make(getView(), "Livro adicionado com sucesso", Snackbar.LENGTH_SHORT).show();
                        break;

                    case MenuMainActivity.EDIT:
                        if(data.getIntExtra(MenuMainActivity.OP_CODE, 0) == MenuMainActivity.DELETE) {
                            Snackbar.make(getView(), "Livro adicionado com sucesso", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(getView(), "Livro editado com sucesso", Snackbar.LENGTH_SHORT).show();
                        }
                        break;

                    default:
                        break;
                }

                //Toast.makeText(this, "Livro modificado com sucesso", Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}