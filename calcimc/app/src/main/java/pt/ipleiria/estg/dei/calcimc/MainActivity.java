package pt.ipleiria.estg.dei.calcimc;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText userWeight;
    private EditText userHeight;
    private TextView calcResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        userWeight = findViewById(R.id.userWeight);
        userHeight = findViewById(R.id.userHeight);
        calcResult = findViewById(R.id.calcResult);

    }

    public final void handleCalcButton(View v) {
        if (userWeight.getText().toString().isEmpty() || userHeight.getText().toString().isEmpty()) {
            CharSequence text = "Please fill up all inputs!";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(getApplicationContext(), text, duration).show();
            return;
        }

        double weight = 0;
        double height = 0;
        try {
            weight = Double.parseDouble(userWeight.getText().toString());
            height = Double.parseDouble(userHeight.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid input! Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            return;
        }

        double finalResult = weight / (height * height);
        String finalText = "Your IMC: ";

        if(finalResult >= 18.5 && finalResult <= 24.99) {
            finalText += "Normal Weight";
        } else if(finalResult >= 25.0 && finalResult <= 29.99) {
            finalText += "Overweight";
        } else {
            finalText += "Go to the GYM!";
        }

        finalText += " (result: " + String.format("%.2f", finalResult) + ")";

        calcResult.setText(finalText);
        calcResult.setVisibility(View.VISIBLE);
    }
}