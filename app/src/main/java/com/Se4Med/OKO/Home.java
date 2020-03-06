package com.Se4Med.OKO;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Home extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences pref;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences.Editor editor;
    String doc;
    TextView testo;
    TextView title;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editor = pref.edit();
        doc = pref.getString("DOC", null);

        testo = findViewById(R.id.testo);
        testo.setText("These tests have no diagnostic value."
                + " In case of difficulties, only an eye care professional "
                + "can carry out a complete eye examination to detect any eventual "
                + "visual problems. No personal health information is collected or retained "
                + "as the result of taking these tests.");
        title = findViewById(R.id.title);
        b = findViewById(R.id.guidelines);
        b.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home) {
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            TypedValue tv = new TypedValue();
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());


            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            PopupWindow pw;
            if (doc.compareTo("1")==0){
                //è un dottore quindi menu_doc
                 pw = new PopupWindow(inflater.inflate(R.layout.nav_doctor, null, false),width,height, true);

            }else{
                //è un paziente
                 pw = new PopupWindow(inflater.inflate(R.layout.nav_patient, null, false),width,height, true);

            }

            pw.showAtLocation(this.findViewById(R.id.main), 0,0, 0);


            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (doc.compareTo("1") == 0) {
            //è un dottore quindi menu_doc
            switch(id)
            {
                case R.id.menu_aggiungi_utente:
                    startActivity(new Intent(this, AggiungiUtente.class));
                    break;

                case R.id.menu_home:
                    startActivity(new Intent(this, Pazienti.class));
                    break;

                case R.id.menu_logout:
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(this, Login.class));
                    break;
            }

        } else {
            //è un paziente
            switch(id)
            {
                case R.id.menu_aggiungi_utente:
                    startActivity(new Intent(this, AggiungiUtente.class));
                    break;

                case R.id.menu_cambia_utente:
                    editor.remove("username");
                    editor.commit();
                    startActivity(new Intent(this, Utenti.class));
                    break;

                case R.id.menu_home:
                    startActivity(new Intent(this, Home.class));
                    break;

                case R.id.menu_profilo:
                    startActivity(new Intent(this, ProfiloPaziente.class));
                    break;

                case R.id.menu_logout:
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(this, Login.class));
                    break;
                case R.id.guidelines:
                    startActivity(new Intent(this, tutorial.class));
                    break;
            }
        }
    }
}
