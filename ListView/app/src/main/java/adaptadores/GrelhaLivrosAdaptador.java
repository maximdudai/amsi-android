package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.book.R;
import pt.ipleiria.estg.dei.book.modelo.Livro;

public class GrelhaLivrosAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> livros;

    public GrelhaLivrosAdaptador(Context context, ArrayList<Livro> livros) {
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
            view = inflater.inflate(R.layout.item_grelha_livro, null);
        }

        ViewHolderGrelha viewGrelha = (ViewHolderGrelha) view.getTag();
        if(viewGrelha == null) {
            viewGrelha = new ViewHolderGrelha(view);
            view.setTag(viewGrelha);
        }

        viewGrelha.update(livros.get(i));

        return view;
    }

    private class ViewHolderGrelha {
        private ImageView imgCapa;

        public ViewHolderGrelha(View view) {
            imgCapa = view.findViewById(R.id.imgGrelha);
        }

        public void update(Livro livro) {
            imgCapa.setImageResource(livro.getCapa());
        }

    }

}
