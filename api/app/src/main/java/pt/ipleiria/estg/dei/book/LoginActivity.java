package pt.ipleiria.estg.dei.book;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import pt.ipleiria.estg.dei.book.listeners.LoginListener;
import pt.ipleiria.estg.dei.book.modelo.SingletonGestorLivros;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private EditText userEmail;
    private EditText userPassword;

    private LoginListener loginListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);


        SingletonGestorLivros.getInstance(getApplicationContext()).setLoginListener(this);
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

        SingletonGestorLivros.getInstance(getApplicationContext()).loginAPI(userEmail.getText().toString(), userPassword.getText().toString(), getApplicationContext());
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

    @Override
    public void onValidateLogin(String token, String email, Context context) {
        if(Objects.equals(token, "AMSI-TOKEN")) {
            //guardar dados
            SharedPreferences sharedPreferences = getSharedPreferences("userEmail",MODE_PRIVATE);

            //criar o objeto para guardar
            SharedPreferences.Editor saveData = sharedPreferences.edit();

            //guardar como chave:valor
            saveData.putString("loggedEmail", userEmail.getText().toString());
            saveData.putString("token", token);


            //confirmar e guardar dados
            saveData.apply();
            Intent toMenu = new Intent(this, MenuMainActivity.class);
            startActivity(toMenu);
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}