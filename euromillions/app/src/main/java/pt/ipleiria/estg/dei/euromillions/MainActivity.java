package pt.ipleiria.estg.dei.euromillions;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // 0 - 50 up
    // 0 - 12 down

    private int[] generatedNumbers = new int[5];
    private int[] generatedStars = new int[2];

    private int[] numberElements = new int[5];
    private int[] starsElements = new int[2];
    private LinearLayout linearNumbersLayout;
    private LinearLayout linearStarsLayout;

    private Random randomNumberGenerate = new Random();

    private TextView tvNumbers;
    private TextView tvStars;
    private TextView tvAcertou;

    private int totalRightNumbers;
    private int totalStarNumbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //linear layout element
        linearNumbersLayout = findViewById(R.id.linearNumbersLayout);
        linearStarsLayout = findViewById(R.id.linearStarsLayout);

        //generated keys and stars
        tvNumbers = findViewById(R.id.tvNumbers);
        tvStars = findViewById(R.id.tvStars);

        //
        tvAcertou = findViewById(R.id.tvAcertou);
    }

    public void handleGenerateKey(View view) {
        boolean isDataEmpty = isFieldEmpty();

        System.out.println("debug: isFieldEmpty " + isDataEmpty);

        if (isDataEmpty) {
            tvAcertou.setText("Please fill up all input fields!");
            return;
        }

        // reset data;
        resetData();

        //generate new numbers
        generateNumbers();
        generateStars();


        int equalKeys = 0;
        int equalStars = 0;

        for (int i = 0; i < 5; i++) {
            if(generatedNumbers[i] == numberElements[i])
                equalKeys ++;
        }

        for(int x = 0; x < 2; x++) {
            if(generatedStars[x] == starsElements[x])
                equalStars ++;
        }

        tvAcertou.setText("Acertou em " + equalKeys + " numeros e " + equalStars + " estrelas");

    }

    public void resetData() {
        generatedNumbers = new int[5];
        generatedStars = new int[2];

        tvNumbers.setText("");
        tvStars.setText("");
    }

    public void generateNumbers() {
        for (int i = 0; i < 5; i++) {
            int generatedNumber = randomNumberGenerate.nextInt(50 - 1 + 1) + 1;
            generatedNumbers[i] = generatedNumber;
            tvNumbers.setText(tvNumbers.getText() + " " + generatedNumber);
        }

    }

    public void generateStars() {
        for (int i = 0; i < 2; i++) {
            int generatedNumber = randomNumberGenerate.nextInt(12 - 1 + 1) + 1;
            generatedStars[i] = generatedNumber;
            tvStars.setText(tvStars.getText() + " " + generatedNumber);
        }
    }

    private boolean isFieldEmpty() {
        int availableKeys = linearNumbersLayout.getChildCount();
        int availableStars = linearStarsLayout.getChildCount();

        EditText[] clientKeys = new EditText[availableKeys];
        EditText[] clientStars = new EditText[availableStars];

        // filled EditText's elements
        int filledNumbers = 0;
        int filledStars = 0;

        // numbers
        for (int i = 0; i < availableKeys; i++) {
            // Get linearLayout child's
            clientKeys[i] = (EditText) linearNumbersLayout.getChildAt(i);

            if (!clientKeys[i].getText().toString().trim().isEmpty()) {
                numberElements[i] = Integer.parseInt(clientKeys[i].getText().toString());
                filledNumbers ++;
            }
        }

        //stars
        for (int x = 0; x < availableStars; x++) {
            // Get linearLayout child's
            clientStars[x] = (EditText) linearStarsLayout.getChildAt(x);

            if (!clientStars[x].getText().toString().trim().isEmpty()) {
                starsElements[x] = Integer.parseInt(clientStars[x].getText().toString());
                filledStars ++;
            }
        }

        System.out.println("debug: stars: " + starsElements.length + " keys: " + numberElements.length);

        return filledStars < 2 && filledNumbers < 5;
    }
}