package com.Se4Med.OKO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utenti extends Activity implements View.OnClickListener {

    TextView username;
    String user;
    String password;
    SharedPreferences pref;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences.Editor editor;
    ListView listBox;
    AccessoDatabase p;
    String ListaUtenti;
    String[] utenti;
    Object clickItemObj;

    Button login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pag_2);
        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editor = pref.edit();

        login = (Button) findViewById(R.id.button_login);
        listBox = findViewById(R.id.listBox);

        user = pref.getString("user",null);
        password = pref.getString("password",null);

        p = new AccessoDatabase("authenticatePatient&useremail=" + user +
                 "&idapp=" + "AcuityTest" + "&password=" + password );

        ListaUtenti = p.finale;
        utenti = ListaUtenti.substring(ListaUtenti.indexOf("{")+1,ListaUtenti.indexOf("}")).split((","));

        final List<String> user_list = new ArrayList<String>(Arrays.asList(utenti));

        // Create an ArrayAdapter from List
        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, user_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, user_list);
        // DataBind ListView with items from ArrayAdapter
        listBox.setAdapter(arrayAdapter);

        listBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                clickItemObj = adapterView.getAdapter().getItem(index);

            }
        });

        login.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        if(clickItemObj == null){
            //non è stato cliccato nessun utente
            editor.putString("username", null);
            editor.commit();
            Toast.makeText(Utenti.this, "You must select a user to proceed.", Toast.LENGTH_SHORT).show();
        }else{
            //è stato cliccato un utente, quindi inserisco in sessione lo username
            editor.putString("username", clickItemObj.toString());
            editor.putString("idPaziente", "null");
            editor.commit();
            startActivity(new Intent(Utenti.this, Home.class));
            //Toast.makeText(Utenti.this, "You clicked " + clickItemObj.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}
