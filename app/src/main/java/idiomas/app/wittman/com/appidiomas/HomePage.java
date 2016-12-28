package idiomas.app.wittman.com.appidiomas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import org.json.JSONObject;


public class HomePage extends AppCompatActivity {

    TextView lblDatosSesion;
    TextView lblNombre;
    TextView lblEmail;
    TextView lblFotoData;
    TextView lblFotoUrl;
    ImageView imgImagen;

    JSONObject response, profile_pic_data, profile_pic_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        lblDatosSesion = (TextView) findViewById(R.id.lblDatosSesion);
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        lblFotoData = (TextView) findViewById(R.id.lblFotoData);
        lblFotoUrl = (TextView) findViewById(R.id.lblFotoUrl);
        imgImagen=(ImageView) findViewById(R.id.imgImagen);

        //Recibe parámetros de pantalla anterior
        Intent intent = getIntent(); //Almacena el intent
        //Bundle extras = intent.getExtras(); //Extra enviado

        String jsondata = intent.getStringExtra("jsondata"); //Extra recibido
        setPerfilUsuario(jsondata);  // call setUserProfile Method.

        //lblDatosSesion.setText(extras.getString("datos_sesion"));

        (findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(HomePage.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
       Set User Profile Information in Navigation Bar.
     */
    public void setPerfilUsuario(String jsondata){
        try {
            response = new JSONObject(jsondata);
            lblEmail.setText(response.get("email").toString());
            lblNombre.setText(response.get("name").toString());
            /*profile_pic_data = new JSONObject(response.get("picture").toString());
            profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
            Picasso.with(this).load(profile_pic_url.getString("url"))
                    .into(user_picture);*/
            profile_pic_data = new JSONObject(response.get("picture").toString());
            profile_pic_url = new JSONObject(profile_pic_data.getString("data"));


            lblFotoData.setText(profile_pic_data.toString());
            lblFotoUrl.setText(profile_pic_url.getString("url").toString());

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}