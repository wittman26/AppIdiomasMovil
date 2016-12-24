package idiomas.app.wittman.com.appidiomas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;


public class HomePage extends AppCompatActivity {

    TextView lblDatosSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        lblDatosSesion = (TextView) findViewById(R.id.lblDatosSesion);

        //Recibe parámetros de pantalla anterior
        Intent intent = getIntent(); //Almacena el intent
        Bundle extras = intent.getExtras(); //Extra enviado

        lblDatosSesion.setText(extras.getString("datos_sesion"));

        (findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(HomePage.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}