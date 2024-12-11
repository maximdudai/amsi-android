package pt.ipleiria.estg.dei.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pt.ipleiria.estg.dei.book.modelo.Livro;
import pt.ipleiria.estg.dei.book.modelo.SingletonGestorLivros;

public class DetalhesLivroActivity extends AppCompatActivity {

    private EditText etName, etTemporada, etTeam, etAno;
    private ImageView imgCapa;
    private FloatingActionButton fabGuardar;

    public static final String ID_LIVRO = "ID_LIVRO";
    private Livro livro;

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
        livro = SingletonGestorLivros.getInstance().getLivro(id);

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

                    SingletonGestorLivros.getInstance().editarLivro(livro);

                    Intent intent = new Intent();
                    intent.putExtra(MenuMainActivity.OP_CODE, MenuMainActivity.EDIT);
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    Livro l = new Livro(R.drawable.logoipl, Integer.parseInt(etAno.getText().toString()), etName.getText().toString(), etTemporada.getText().toString(), etTeam.getText().toString());
                    SingletonGestorLivros.getInstance().adicionarLivro(l);

                    Intent intent = new Intent();
                    intent.putExtra(MenuMainActivity.OP_CODE, MenuMainActivity.ADD);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void carregarLivro() {
        setTitle("Detalhes: " + livro.getTitulo());

        etName.setText(livro.getTitulo());
        etTemporada.setText(livro.getSerie());
        etTeam.setText(livro.getAutor());
        etAno.setText("" + livro.getAno());
        imgCapa.setImageResource(livro.getCapa());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(livro != null) {
            getMenuInflater().inflate(R.menu.menu_remover, menu);
            return super.onCreateOptionsMenu(menu);

        }
        return false;
    }
}