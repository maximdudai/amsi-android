package pt.ipleiria.estg.dei.book;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import pt.ipleiria.estg.dei.book.listeners.LoginListener;

public class MenuMainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, LoginListener {

    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "OPCODE";

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView loggedInAccount;
    private String email = "";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.ndOpen, R.string.ndClose);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        carregarCabecalho();

        fragmentManager = getSupportFragmentManager();

        navigationView.setNavigationItemSelectedListener(this);

        this.carregarFragmentoInicial();

    }
    private void carregarCabecalho() {
        email = getIntent().getStringExtra("userEmail");

        SharedPreferences sharedPreferanceUser = getSharedPreferences("userEmail", Context.MODE_PRIVATE);

        if(email != null) {
            SharedPreferences.Editor editorUser = sharedPreferanceUser.edit();
            editorUser.putString("loggedEmail", email);
            editorUser.apply();
        } else {
            email = sharedPreferanceUser.getString("userEmail", "Sem Email");

        }

        View view = navigationView.getHeaderView(0);
        TextView nav_txtEmail = view.findViewById(R.id.userAccount);
        nav_txtEmail.setText(email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        if(item.getItemId() == R.id.navEstatico) {
            fragment = new ListaLivrosFragment();
            setTitle(item.getTitle());
        }

        if(fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void handleSendEmail() {
        SharedPreferences getSavedData = getSharedPreferences("userEmail", Context.MODE_PRIVATE);
        String savedEmail = getSavedData.getString("loggedEmail", "");

        String subject = "AMSI 2024/25";
        String message = "Ola " + savedEmail + " isto e uma mensagem de teste!";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc882");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{savedEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
        else
            Toast.makeText(this, "Nao tem email config", Toast.LENGTH_SHORT).show();
    }

    private void carregarFragmentoInicial() {
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0);

        item.setCheckable(true);
        onNavigationItemSelected(item);
    }

    @Override
    public void onValidateLogin(String token, String email, Context context) {

    }
}