package com.Se4Med.OKO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;

import Common.Resources.ImageShape;


public class Login extends AppCompatActivity implements View.OnClickListener {

    TextView username;
    TextView password;
    String user;
    String password_hash;
    ImageView icona;
    SharedPreferences pref;
    public static final String MyPREFERENCES = "MyPrefs" ;

    Button login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        login = (Button) findViewById(R.id.button_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        password_hash = getPassword_hash(password.getText().toString());

        user = username.getText().toString();



        AccessoDatabase p = new AccessoDatabase("authenticate&useremail=" + user +
                "&password=" + password_hash );

        String result =  p.finale;
        SharedPreferences.Editor editor = pref.edit();
        if(result.endsWith("LOGIN OK}")){

            //l'utente è presente nel DB quindi carico i dati in sessione
            editor.putString("user", user);
            editor.putString("password", password_hash);
            editor.commit();

            //controllo se è un paziente o un dottore
            p = new AccessoDatabase("authenticateDoctorNameSurname&useremail=" + user +
                    "&password=" + password_hash + "&idapp=" + "AcuityTest" );


            if(p.finale.contains("login_ok")){
                //è un dottore, vado alla Pazienti
                editor.putString("DOC", "1");
                editor.commit();
                startActivity(new Intent(Login.this, Pazienti.class));
            }else{
                //è un paziente reg, vado a pag 2
                editor.putString("DOC", "0");
                editor.commit();
                startActivity(new Intent(Login.this, Utenti.class));
            }
        }else{
            editor.putString("user", null);
            editor.putString("password", null);
            editor.commit();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Login failed. " + "Check your credentials and try again.");
            alertDialog.create();
            alertDialog.show();
        }

    }


    public String getPassword_hash(String password){
        String pass_hash = "";
        try{
            pass_hash = HashFunction.toSha256(password);
        }catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pass_hash;
    }
}