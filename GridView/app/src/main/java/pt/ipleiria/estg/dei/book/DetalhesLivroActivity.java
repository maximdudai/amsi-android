package pt.ipleiria.estg.dei.book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pt.ipleiria.estg.dei.book.modelo.Livro;
import pt.ipleiria.estg.dei.book.modelo.SingletonGestorLivros;

public class DetalhesLivroActivity extends AppCompatActivity {

    private EditText etName, etTemporada, etTeam, etAno;
    private ImageView imgCapa;

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


        int id = getIntent().getIntExtra(ID_LIVRO, 0);
        livro = SingletonGestorLivros.getInstance().getLivro(id);


        if(livro != null) {
            carregarLivro();
        }
    }

    private void carregarLivro() {
        setTitle("Detalhes: " + livro.getTitulo());

        etName.setText(livro.getTitulo());
        etTemporada.setText(livro.getSerie());
        etTeam.setText(livro.getAutor());
        etAno.setText("" + livro.getAno());
        imgCapa.setImageResource(livro.getCapa());
    }
}