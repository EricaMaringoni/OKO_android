package com.Se4Med.OKO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfiloPaziente extends Activity implements View.OnClickListener {

    SharedPreferences pref;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences.Editor editor;

    ListView listBox;
    ImageView foto_profilo;
    Button test;
    Button user_informations;
    Button test_performed;
    LinearLayout temp = null;
    Button modifica;
    Button salva;

    EditText email_text;
    EditText password_text;
    EditText sesso;
    EditText luogoNascita;
    EditText num_tel;
    EditText citta;
    EditText provincia;
    EditText nazione;
    EditText diottria_dx;
    EditText diottria_sx;

    AccessoDatabase p;
    String ListaRisultati;
    String listaRisultatiTest;
    String[] risultati;
    String[] singoloRisultato;
    Object clickItemObj;

    String idPaziente;
    String username;
    String emailUser;
    String email;
    String password;

    String[] res_dx;
    String[] res_sx;
    String percentuale_dx;
    String percentuale_sx;
    String percentuale_test;

    String nome_utente;
    TextView utente;
    TableLayout table;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profilo_paziente);
        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editor = pref.edit();

        idPaziente = pref.getString("idPaziente", null);
        username = pref.getString("username", null);
        emailUser = pref.getString("emailUser", null);
        email = pref.getString("user", null);
        password = pref.getString("password", null);

        //recupero l'immagine profilo dell'utente
        p = new AccessoDatabase("getfotoprofilo&username=" + username);
        if(p.finale.contains("status:Error")){
            ImageView iv = (ImageView) findViewById(R.id.imageView);
            Bitmap foto_profilo = BitmapFactory.decodeResource(getResources(), R.drawable.user);
            iv.setImageBitmap(foto_profilo);
        }else{
            String img = p.finale.substring(p.finale.indexOf("{")+1, p.finale.indexOf("}"));
            Bitmap foto_profilo = StringToBitMap(img);
            ImageView iv = (ImageView) findViewById(R.id.imageView);
            iv.setImageBitmap(foto_profilo);
        }

        //controllo se è un paziente registrato o non registrato
        String user_type;
        //se l'id in sessione è null -> il paziente scelto è registrato
        if(idPaziente.compareTo("null")==0){
            String DOC = pref.getString("DOC", null);
            user_type = "1";
            if(DOC.compareTo("1")==0){
                p = new AccessoDatabase("getPatientInfo&patienttype=" + user_type + "&useremail=" + emailUser + "&username=" + username +
                        "&idapp=" + "AcuityTest");
            }else{
                p = new AccessoDatabase("getPatientInfo&patienttype=" + user_type + "&useremail=" + email + "&username=" + username +
                        "&idapp=" + "AcuityTest");
            }

        }else{
            user_type = "0";
            p = new AccessoDatabase("getPatientInfo&patienttype=" + user_type + "&useremail=" + emailUser + "&username=" + idPaziente +
                    "&idapp=" + "AcuityTest");
        }


        nome_utente = p.finale.substring(p.finale.indexOf("{")+1, p.finale.indexOf("}"));

        utente = findViewById(R.id.User);
        utente.setText(nome_utente);

        //creo la lista dei test eseguiti
        listBox = findViewById(R.id.listBox);

        creaLista();
        setPatientInfo();

        test = findViewById(R.id.test);
        user_informations = findViewById(R.id.User_info);
        test_performed = findViewById(R.id.Test_performed);

        test.setOnClickListener(this);
        user_informations.setOnClickListener(this);
        test_performed.setOnClickListener(this);

        modifica = findViewById(R.id.modificaDati);
        salva = findViewById(R.id.salva);

        modifica.setOnClickListener(this);
        salva.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.test:
                startActivity(new Intent(ProfiloPaziente.this, tutorial.class));
                break;

            case R.id.User_info:
                creaAreaInfo();
                break;

            case R.id.Test_performed:
                showAreaTest();
                break;

            case R.id.modificaDati:
                modificaDati();
                break;

            case R.id.salva:
                salva();
                break;

            default:
                break;
        }


    }

    public void creaLista(){

        if (idPaziente.compareTo("null")==0){
            //paziente registrato
            p = new AccessoDatabase("getResultList&docemail=" + email + "&password=" + password + "&username=" + username +
                    "&idpatient=" + idPaziente + "&idapp=" + "AcuityTest");
        }else{
            //paziente non registrato
            p = new AccessoDatabase("getResultList&docemail=" + email + "&password=" + password + "&username=" + "null" +
                    "&idpatient=" + idPaziente + "&idapp=" + "AcuityTest");
        }

        ListaRisultati = p.finale;
        if(ListaRisultati.contains("status:Error")){
            //l'utente non ha ancora eseguito test
            TextView temp = findViewById(R.id.textView6);
            temp.setText("Nessun test eseguito");
            TableLayout table = (TableLayout) findViewById(R.id.table);
            table.setVisibility(View.INVISIBLE);

        }else{
            ListaRisultati = ListaRisultati.substring(ListaRisultati.indexOf("{")+1, ListaRisultati.indexOf("}"));
            ListaRisultati = ListaRisultati.replace("[","").replace("]","");
            risultati = ListaRisultati.split((","));


            final List<String> result_list = new ArrayList<String>(Arrays.asList(risultati));

            // Create an ArrayAdapter from List
            //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, user_list);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, result_list);
            // DataBind ListView with items from ArrayAdapter
            listBox.setAdapter(arrayAdapter);

            listBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                    clickItemObj = adapterView.getAdapter().getItem(index);

                    String id_riga = clickItemObj.toString().split(" - ")[0];
                    creaTabellaRisultati(id_riga);
                }
            });
        }


    }

    public void creaTabellaRisultati(String id_riga){

        if (idPaziente.compareTo("null")==0){
            //paziente registrato
            p = new AccessoDatabase("getResult&docemail=" + email + "&password=" + password + "&username=" + username +
                    "&idpatient=" + idPaziente + "&idriga=" + id_riga.trim());
        }else{
            //paziente non registrato
            p = new AccessoDatabase("getResult&docemail=" + email + "&password=" + password + "&username=" + "null" +
                    "&idpatient=" + idPaziente + "&idriga=" + id_riga.trim());
        }


        listaRisultatiTest =  p.finale;

        listaRisultatiTest = listaRisultatiTest.substring(listaRisultatiTest.indexOf("{")+1, listaRisultatiTest.indexOf("}"));
        singoloRisultato = listaRisultatiTest.split("/");

        table = (TableLayout) findViewById(R.id.table);


        singoloRisultato[0] = singoloRisultato[0].substring(3,singoloRisultato[0].length());
        singoloRisultato[1] = singoloRisultato[1].substring(3,singoloRisultato[1].length());
        singoloRisultato[2] = singoloRisultato[2].substring(7,singoloRisultato[2].length());
        singoloRisultato[3] = singoloRisultato[3].substring(7,singoloRisultato[3].length());
        singoloRisultato[4] = singoloRisultato[4].substring(4,singoloRisultato[4].length());

        String riga;

        TableRow row_delete;
        for (int i = 0; i <= singoloRisultato[0].split("-").length-1; i++) {
            row_delete = findViewById(i);

            if(row_delete != null){

                table.removeView(row_delete);
            }

        }


        for (int i = 0; i <= singoloRisultato[0].split("-").length-1; i++) {

            if(i==0){
                riga = singoloRisultato[0].split("-")[i] + "/" + singoloRisultato[1].split("-")[i]
                        + "/" + singoloRisultato[2].split("-")[i]+ "/" + singoloRisultato[3].split("-")[i]
                        + "/" + singoloRisultato[4].split("-")[i];
            }else{
                riga = singoloRisultato[0].split("-")[i] + "/" + singoloRisultato[1].split("-")[i];
            }


            String[] singoli_dati = riga.split("/");

            final TableRow row1 = new TableRow(this);
            if(i==0){
                TextView tv0 = new TextView(this);
                TextView tv1 = new TextView(this);
                TextView tv2 = new TextView(this);
                TextView tv3 = new TextView(this);
                TextView tv4 = new TextView(this);
                if(i%2==0){
                    row1.setBackgroundColor(Color.parseColor("#f3f5f7"));
                }

                tv0.setText(singoli_dati[0]);
                tv1.setText(singoli_dati[1]);
                tv2.setText(singoli_dati[2]);
                tv3.setText(singoli_dati[3]);
                tv4.setText(singoli_dati[4]);
                row1.addView(tv0);
                row1.addView(tv1);
                row1.addView(tv2);
                row1.addView(tv3);
                row1.addView(tv4);
            }else{
                TextView tv0 = new TextView(this);
                TextView tv1 = new TextView(this);
                if(i%2==0){
                    row1.setBackgroundColor(Color.parseColor("#f3f5f7"));
                }

                tv0.setText(singoli_dati[0]);
                tv1.setText(singoli_dati[1]);
                row1.addView(tv0);
                row1.addView(tv1);
            }

            row1.setId(i);
            table.addView(row1);
        }

    }

    public void creaAreaInfo(){

        LinearLayout container_lista = findViewById(R.id.container_lista);
        LinearLayout container_info = findViewById(R.id.container_info);
        LinearLayout container_button = findViewById(R.id.button_container);
        LinearLayout container_scroll = findViewById(R.id.container_scroll);


        //ViewGroup parent = (ViewGroup) container_button.getParent();
        ViewGroup parent = (ViewGroup) container_scroll;

        String s = container_scroll.getChildAt(0).toString();

        s = s.substring(0,s.length()-1).split("/")[1];

        if(s.compareTo("container_info")==0){

        }else{
            temp = container_lista;
            parent.getChildAt(1).setVisibility(View.VISIBLE);
            parent.removeViewAt(0);
            //parent.addView(container_info,3);
            parent.addView(temp,1);
            parent.getChildAt(1).setVisibility(View.INVISIBLE);
        }

    }

    public void showAreaTest(){
        LinearLayout container_lista = findViewById(R.id.container_lista);
        LinearLayout container_info = findViewById(R.id.container_info);
        LinearLayout container_button = findViewById(R.id.button_container);
        LinearLayout container_scroll = findViewById(R.id.container_scroll);

        ViewGroup parent = (ViewGroup) container_scroll;

        String s = parent.getChildAt(0).toString();
        s = s.substring(0,s.length()-1).split("/")[1];

        if(s.compareTo("container_lista")==0){

        }else{
            temp = container_info;
            parent.getChildAt(1).setVisibility(View.VISIBLE);
            parent.removeViewAt(0);
            parent.addView(temp,1);
            parent.getChildAt(1).setVisibility(View.INVISIBLE);
        }

    }

    public void setPatientInfo(){

        p = new AccessoDatabase("getPatientAdditionalInfo&username=" + username +
                "&idapp=AcuityTest");

        String additionalInfo = p.finale;
        additionalInfo = additionalInfo.substring(additionalInfo.indexOf("{")+1, additionalInfo.indexOf("}"));

        String[] infoPatient = additionalInfo.split("-");
        email_text = findViewById(R.id.email);
        password_text = findViewById(R.id.password);
        sesso = findViewById(R.id.sesso);
        luogoNascita = findViewById(R.id.luogoNascita);
        num_tel = findViewById(R.id.num_tel);
        citta = findViewById(R.id.citta);
        provincia = findViewById(R.id.provincia);
        nazione = findViewById(R.id.nazione);
        diottria_dx = findViewById(R.id.diottria_dx);
        diottria_sx = findViewById(R.id.diottria_sx);

        disable_field();

        email_text.setText(email);
        password_text.setText(password);
        sesso.setText(ifNullValue(infoPatient[0]));
        luogoNascita.setText(ifNullValue(infoPatient[1]));
        num_tel.setText(ifNullValue(infoPatient[2]));
        citta.setText(ifNullValue(infoPatient[3]));
        provincia.setText(ifNullValue(infoPatient[4]));
        nazione.setText(ifNullValue(infoPatient[5]));
        diottria_dx.setText(ifNullValue(infoPatient[6]));
        diottria_sx.setText(ifNullValue(infoPatient[7]));
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public void modificaDati(){
        sesso.setEnabled(true);
        num_tel.setEnabled(true);
        citta.setEnabled(true);
        provincia.setEnabled(true);
        nazione.setEnabled(true);
        diottria_dx.setEnabled(true);
        diottria_sx.setEnabled(true);
        salva.setVisibility(View.VISIBLE);
        modifica.setVisibility(View.INVISIBLE);
    }

    public void salva(){

        String temp = luogoNascita.getText().toString().replace(" ","[]");
        int corretto = verificaDati(); //0 si, 1 no
        String msg_errore = "Provided erroneous or incomplete data: please check the phone number and the formal correctness of other information";


        if(corretto==0){
            p = new AccessoDatabase("updatePatientInfo&username=" + username +
                    "&idapp=AcuityTest"+"&numero_telefono_pat="+num_tel.getText()+"&luogo_di_nascita_pat="+ifEmptyValue(temp)+"&sessopat="+ifEmptyValue(sesso.getText().toString())
                    +"&citta_pat="+ifEmptyValue(citta.getText().toString())+"&provincia_pat="+ifEmptyValue(provincia.getText().toString())+
                    "&nazione_pat="+ifEmptyValue(nazione.getText().toString())+"&diottria_dx_pat="+ifEmptyValue(diottria_dx.getText().toString())
                    +"&diottria_sx_pat="+ifEmptyValue(diottria_sx.getText().toString()));

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            if(p.finale.contains("status:error_in_data_saving")){
                alertDialog.setMessage("Error_in_data_saving");
            }else{
                alertDialog.setMessage("Data saved successfully");
                disable_field();
                modifica.setVisibility(View.VISIBLE);
                salva.setVisibility(View.INVISIBLE);
            }
            alertDialog.create();
            alertDialog.show();
        }else{

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage(msg_errore);

            alertDialog.create();
            alertDialog.show();
        }

    }

    public void disable_field(){
        email_text.setEnabled(false);
        password_text.setEnabled(false);
        sesso.setEnabled(false);
        luogoNascita.setEnabled(false);
        num_tel.setEnabled(false);
        citta.setEnabled(false);
        provincia.setEnabled(false);
        nazione.setEnabled(false);
        diottria_dx.setEnabled(false);
        diottria_sx.setEnabled(false);
    }

    public String ifNullValue(String s){
        if(s.compareTo("null")==0){
            s = "";
        }
        return s;
    }

    public String ifEmptyValue(String s){
        if(s.compareTo("")==0){
            s = "null";
        }
        return s;
    }

    public int verificaDati(){

        if((num_tel.getText().length()==0 || num_tel.getText().length()==10)
        && (provincia.getText().length() == 0 ||provincia.getText().length()==2) &&
                (sesso.getText().length()==0 ||sesso.getText().length()==1)
                && (nazione.getText().length()==0 || nazione.getText().length()==3)){

            return 0;
        }else{
            return 1;
        }
    }
}