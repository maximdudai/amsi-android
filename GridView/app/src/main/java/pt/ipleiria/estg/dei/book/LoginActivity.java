package pt.ipleiria.estg.dei.book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
    }

    public final void handleLogin(View view) {

        if(!isEmailValid(userEmail.getText().toString()))
        {
            Toast.makeText(this, "Invalid email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isPasswordValid())
        {
            Toast.makeText(this, "Invalid password (min. 4 chars)", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent sendIntent = new Intent(this, MenuMainActivity.class);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra("userEmail", userEmail.getText().toString());
        sendIntent.setType("text/plain");

        //guardar dados
        SharedPreferences sharedPreferences = getSharedPreferences("userEmail",MODE_PRIVATE);

        //criar o objeto para guardar
        SharedPreferences.Editor saveData = sharedPreferences.edit();

        //guardar como chave:valor
        saveData.putString("loggedEmail", userEmail.getText().toString());

        //confirmar e guardar dados
        saveData.apply();

        startActivity(sendIntent);
    }

    private boolean isEmailValid(String email) {
        if(email == null)
            return false;

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isPasswordValid()
    {
        return userPassword.getText().length() >= 4;
    }
}