package pt.ipleiria.estg.dei.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private TextView guessResult;
    private TextView totalTentativas;

    private static final Random randomNumber = new Random();
    private static int randomGuessNumber;
    private static int tentativas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        guessResult = findViewById(R.id.guessResult);
        totalTentativas = findViewById(R.id.totalTentativas);

        randomGuessNumber = GetRandomNumber(1, 10);
        tentativas = 5;
    }

    public void onClickGuess(View view) {
        if(etNumber.getText().toString().trim().isEmpty()) {
            System.out.println("tamanho: " + etNumber.getText().toString().length());
            return;
        }
        int insertedValue = Integer.parseInt(etNumber.getText().toString());

        if(tentativas <= 1) {
            guessResult.setText("Game Over!");
            resetGame();
            totalTentativas.setText("Let's try again xD");
            return;
        }

        if(randomGuessNumber == insertedValue) {
            guessResult.setText("You are the GOAT!");
            resetGame();
            return;

        } else if(randomGuessNumber > insertedValue) {
            guessResult.setText("You are not the GOAT - too small!");
        } else {
            guessResult.setText("You are not the GOAT - too high!");
        }

        tentativas --;
        totalTentativas.setText("Tentativas: " + tentativas);
    }
    private void resetGame() {
        randomGuessNumber = GetRandomNumber(1, 10);
        tentativas = 5;
        totalTentativas.setText("Tentativas: " + tentativas);
    }
    private int GetRandomNumber(int min, int max) {
        int valor = randomNumber.nextInt(max - min + 1) + min;
        return valor;
    }
}
