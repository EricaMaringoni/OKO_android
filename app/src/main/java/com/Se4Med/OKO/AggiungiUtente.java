package com.Se4Med.OKO;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import pub.devrel.easypermissions.EasyPermissions;


public class AggiungiUtente extends AppCompatActivity implements View.OnClickListener {

    TextView title;
    EditText name;
    EditText surname;
    EditText username;
    EditText luogoDiNascita;
    EditText dataDiNaascita;
    EditText numero_telefono;
    EditText provincia;
    EditText paese;
    EditText nazione;
    EditText sesso;
    EditText diottrie_dx;
    EditText diottrie_sx;
    ImageView foto;
    AccessoDatabase p;
    Bitmap bitmap;
    Button upload;
    Button add;

    SharedPreferences pref;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences.Editor editor;

    public static final String KEY_User_Document1 = "doc1";
    private String Document_img1="";

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiungi_utente);

        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();

        title = findViewById(R.id.title_1);

        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        username = (EditText) findViewById(R.id.username);
        luogoDiNascita = (EditText) findViewById(R.id.luogonascita);
        dataDiNaascita = (EditText) findViewById(R.id.date);
        provincia = (EditText) findViewById(R.id.pv);
        paese = (EditText) findViewById(R.id.citta_R);
        nazione = (EditText) findViewById(R.id.paese);
        sesso = (EditText) findViewById(R.id.sesso_R);
        numero_telefono = (EditText) findViewById(R.id.numTelefono);
        diottrie_dx = (EditText) findViewById(R.id.diott_dx);
        diottrie_sx = (EditText) findViewById(R.id.diott_sx);

        foto = (ImageView) findViewById(R.id.profile);
        upload =  findViewById(R.id.upload);
        add =  findViewById(R.id.add_R);



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK","UPLOAD");
                upload_foto();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                Log.d("CLICK","ADD");
                add_user();


            }
        });


    }

    @Override
    public void onClick(View view) {

    }


    public void add_user(){

        String nome = ifNullValue(""+name.getText());
        String cognome = ifNullValue(""+surname.getText());
        String usernam = ifNullValue(""+username.getText());
        String birth_place = ifNullValue(""+luogoDiNascita.getText());
        String pv = ifNullValue(""+provincia.getText());
        String city = ifNullValue(""+paese.getText());
        String nation = ifNullValue(""+nazione.getText());
        String num = ifNullValue(""+numero_telefono.getText());
        String d_dx = ifNullValue(""+diottrie_dx.getText());
        String d_sx = ifNullValue(""+diottrie_sx.getText());
        String sess = ifNullValue(""+sesso.getText());
        String doc = pref.getString("DOC",null);
        String email = pref.getString("user",null);

        String password = pref.getString("password",null);
        String data = ifNullValue(""+dataDiNaascita.getText());
        //Log.d("FOTO=",""+Document_img1);

        int i,y;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        if(usernam != ""){

            //Verifico username
            p=new AccessoDatabase("verifyusername&username="+usernam);

            if(p.finale.contains("Error")){
                alertDialog.setMessage("Username not available");
                alertDialog.create();
                alertDialog.show();
            } else {
                String v = "createpatdoc&idapp="+ "AcuityTest"
                        +"&namepat=" + nome + "&surnamepat=" + cognome +"&useremail=" + email + "&password="+
                        password + "&username=" + usernam + "&sessopat=" + sess + "&luogo_di_nascita_pat=" +
                        birth_place + "&data_di_nascita_pat="+ data +"&numero_telefono_pat=" + num + "&citta_pat=" +
                        city + "&provincia_pat=" + pv + "&nazione_pat="+ nation +
                        "&diottria_dx_pat=" + d_dx + "&diottria_sx_pat=" + d_sx + "&foto_pat=" + Document_img1;

                Log.d("URL",""+v);
                //DOC==0
                if(!nome.equals("") && !cognome.equals("") && !dataDiNaascita.equals("") ){

                    if((numero_telefono.length()==0 || numero_telefono.length()==10) &&
                            (provincia.length()==0 || provincia.length()==2) &&
                            (sesso.length()==0 || sesso.length()==1) &&
                            (nazione.length()==0 ||nazione.length()==3)){

                        if(doc.equals("0")) {
                            Log.d("DOC","0");
                            p = new AccessoDatabase("createpatientuser&idapp="+ "AcuityTest"
                                    +"&namepat=" + nome + "&surnamepat=" + cognome +"&useremail=" + email + "&password="+
                                    password + "&username=" + usernam + "&sessopat=" + sess + "&luogo_di_nascita_pat=" +
                                    birth_place + "&data_di_nascita_pat="+ data +"&numero_telefono_pat=" + num + "&citta_pat=" +
                                    city + "&provincia_pat=" + pv + "&nazione_pat="+ nation +
                                    "&diottria_dx_pat=" + d_dx + "&diottria_sx_pat=" + d_sx + "&foto_pat=" + Document_img1);

                            //VERIFICA SALVATAGGIO
                        } else {
                            //DOC==1
                            Log.d("DOC","1");
                            Log.d("DATA",""+data);
                            p = new AccessoDatabase("createpatdoc&idapp="+ "AcuityTest"
                                    +"&namepat=" + nome + "&surnamepat=" + cognome +"&docemail=" + email + "&password="+
                                    password + "&username=" + usernam + "&sessopat=" + sess + "&luogo_di_nascita_pat=" +
                                    birth_place + "&data_di_nascita_pat="+ data +"&numero_telefono_pat=" + num + "&citta_pat=" +
                                    city + "&provincia_pat=" + pv + "&nazione_pat="+ nation +
                                    "&diottria_dx_pat=" + d_dx + "&diottria_sx_pat=" + d_sx + "&foto_pat=" + Document_img1);

                        }

                        if(p.finale.contains("error_in_data_saving")){
                            alertDialog.setMessage("Error_in_data_saving");
                        }else if (p.finale.contains("Data updated")){
                            alertDialog.setMessage("Data saved successfully");
                        }
                        alertDialog.create();
                        alertDialog.show();
                        if(doc.equals("1")) {
                            startActivity(new Intent(this, Pazienti.class));
                        }else{
                            startActivity(new Intent(this, Home.class));
                        }

                    }else{
                        alertDialog.setMessage("Provided erroneous or incomplete data: please check the phone number and the formal correctness of other information");
                        alertDialog.create();
                        alertDialog.show();
                    }
                }else{
                    alertDialog.setMessage("You must insert: name, surname and date of birth");
                    alertDialog.create();
                    alertDialog.show();
                }

            }
        }else{
            alertDialog.setMessage("You must insert an username");
            alertDialog.create();
            alertDialog.show();
        }
    }


    public void upload_foto(){

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        // Log.d("FOTO=",""+Document_img1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)){
                {
                    if (requestCode == 1) {
                        File f = new File(Environment.getExternalStorageDirectory().toString());
                        for (File temp : f.listFiles()) {
                            if (temp.getName().equals("temp.jpg")) {
                                f = temp;
                                break;
                            }
                        }
                        try {

                            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                            bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                            bitmap = getResizedBitmap(bitmap, 400);
                            foto.setImageBitmap(bitmap);
                            BitMapToString(bitmap);
                            String path = Environment
                                    .getExternalStorageDirectory()
                                    + File.separator
                                    + "Phoenix" + File.separator + "default";
                            f.delete();
                            OutputStream outFile = null;
                            File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                            try {
                                outFile = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                                outFile.flush();
                                outFile.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {


                    }
                }
            } else if (requestCode == 2) {
                if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    thumbnail = getResizedBitmap(thumbnail, 800);
                    Log.w("path of ", picturePath + "");
                    foto.setImageBitmap(thumbnail);

                    try {
                        uploadPictureToServer(picturePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    BitMapToString(thumbnail);

                } else {

                    EasyPermissions.requestPermissions(this, "Access for storage",
                            101, galleryPermissions);
                }
            }
        }
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;


    }


    public static void uploadPictureToServer(String i_file) throws IOException {
        // TODO Auto-generated method stub
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);


        HttpPost httppost = new HttpPost("http://10.0.2.2:9997//se4medservice");
        File file = new File(i_file);
        Log.d("EDIT USER PROFILE", "UPLOAD: file length = " + file.length());
        Log.d("EDIT USER PROFILE", "UPLOAD: file exist = " + file.exists());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        //MultipartEntity mpEntity = new MultipartEntity();
        ContentBody cbFile = new FileBody(file, "image/jpg");
        //mpEntity.addPart("userfile", cbFile);
        builder.addPart("userfile",cbFile);

        httppost.setEntity(builder.build());
        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            System.out.println(EntityUtils.toString(resEntity));
        }
        if (resEntity != null) {
            resEntity.consumeContent();
        }

        httpclient.getConnectionManager().shutdown();

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String ifNullValue(String s){

        if(s.equals(null)){
            s = "";
        }
        return s;
    }

}
