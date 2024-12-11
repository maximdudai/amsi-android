package pt.ipleiria.estg.dei.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pt.ipleiria.estg.dei.book.listeners.LivroListener;
import pt.ipleiria.estg.dei.book.modelo.Livro;
import pt.ipleiria.estg.dei.book.modelo.SingletonGestorLivros;

public class DetalhesLivroActivity extends AppCompatActivity implements LivroListener {

    private EditText etName, etTemporada, etTeam, etAno;
    private ImageView imgCapa;
    private FloatingActionButton fabGuardar;

    public static final String ID_LIVRO = "ID_LIVRO";
    private Livro livro;

    public static final String DEFAULT_IMG =
            "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_livro);

        etName = findViewById(R.id.etName);
        etTemporada = findViewById(R.id.etTemporada);
        etTeam = findViewById(R.id.etTeam);
        etAno = findViewById(R.id.etAno);
        imgCapa = findViewById(R.id.imgCapa);

        fabGuardar = findViewById(R.id.fabGuardar);

        int id = getIntent().getIntExtra(ID_LIVRO, 0);
        livro = SingletonGestorLivros.getInstance(getApplicationContext()).getLivroBD(id);

        if(livro != null) {
            carregarLivro();
            fabGuardar.setImageResource(R.drawable.ic_action_save);
        } else {
            setTitle(getString(R.string.txtAddBook));
            fabGuardar.setImageResource(R.drawable.ic_action_add);
        }

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(livro != null) {
                    boolean emptyFields = livro.getTitulo().isEmpty();

                    livro.setTitulo(etName.getText().toString());
                    livro.setSerie(etTemporada.getText().toString());
                    livro.setAutor(etTeam.getText().toString());
                    livro.setAno(Integer.parseInt(etAno.getText().toString()));

                    SingletonGestorLivros.getInstance(getApplicationContext()).editarLivroAPI(livro, getApplicationContext());

                } else {
                    Livro l = new Livro(0, DEFAULT_IMG, Integer.parseInt(etAno.getText().toString()), etName.getText().toString(), etTemporada.getText().toString(), etTeam.getText().toString());
                    SingletonGestorLivros.getInstance(getApplicationContext()).adicionarLivroAPI(l, getApplicationContext());
                }
            }
        });

        SingletonGestorLivros.getInstance(getApplicationContext()).setLivroListener(this);
    }

    private void carregarLivro() {
        setTitle("Detalhes: " + livro.getTitulo());

        etName.setText(livro.getTitulo());
        etTemporada.setText(livro.getSerie());
        etTeam.setText(livro.getAutor());
        etAno.setText("" + livro.getAno());


        //or GetApplicationContext()
        Glide.with(this)
                .load(livro.getCapa())
                .placeholder(R.drawable.logoipl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(livro != null) {
            getMenuInflater().inflate(R.menu.menu_remover, menu);
            return super.onCreateOptionsMenu(menu);

        }
        return false;
    }

    @Override
    public void onRefreshDetalhes(int op) {
        Intent intent = new Intent();
        intent.putExtra(MenuMainActivity.OP_CODE, op);
        setResult(RESULT_OK, intent);
        finish();
    }
}