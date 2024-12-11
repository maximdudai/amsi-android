package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.book.R;
import pt.ipleiria.estg.dei.book.modelo.Livro;

public class ListaLivrosAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> livros;

    public ListaLivrosAdaptador(Context context, ArrayList<Livro> livros) {
        this.context = context;
        this.livros = livros;
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int i) {
        return livros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return livros.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null) {
            view = inflater.inflate(R.layout.item_lista_livro, null);
        }

        ViewHolderLista viewHolderLista = (ViewHolderLista) view.getTag();
        if(viewHolderLista == null) {
            viewHolderLista = new ViewHolderLista(view);
            view.setTag(viewHolderLista);
        }

        viewHolderLista.update(livros.get(i));

        return view;
    }

    private class ViewHolderLista {
        private TextView tvTitulo, tvSerie, tvAno, tvAutor;
        private ImageView imgCapa;

        public ViewHolderLista(View view) {
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvSerie = view.findViewById(R.id.tvSerie);
            tvAno = view.findViewById(R.id.tvAno);
            tvAutor = view.findViewById(R.id.tvAutor);
            imgCapa = view.findViewById(R.id.capa);
        }

        public void update(Livro livro) {
            tvTitulo.setText(livro.getTitulo());
            tvSerie.setText(livro.getSerie());
            tvAno.setText("" + livro.getAno());
            tvAutor.setText(livro.getAutor());
            imgCapa.setImageResource(livro.getCapa());
        }

    }
}
