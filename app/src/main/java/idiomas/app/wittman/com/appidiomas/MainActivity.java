package idiomas.app.wittman.com.appidiomas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String datos_sesion ="";
    private TextView lblIngresar;
    private LoginButton btnFacebook;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());

        // Establecer las devoluciones de llamada
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        //Inicializa los componentes de la vista
        lblIngresar = (TextView)findViewById(R.id.lblIngresar);
        btnFacebook = (LoginButton)findViewById(R.id.btnFacebook);
        btnFacebook.setReadPermissions("email");

        // Registrar las devoluciones de llamada
        getLoginDetails(btnFacebook);

    }

    /*
     Register a callback function with LoginButton to respond to the login result.
    */
    protected void getLoginDetails(LoginButton login_button){
        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {
                Intent home = new Intent(MainActivity.this,HomePage.class);

                datos_sesion =
                                "User ID: "
                                + login_result.getAccessToken().getUserId();
//                                + "\n" +
//                                "Auth Token: "
//                                + login_result.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        login_result.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                try {
                                    datos_sesion = datos_sesion + "\n" +
                                            "Nombre: "
                                            + object.getString("name").toString();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();


                home.putExtra("datos_sesion", datos_sesion);

                startActivity(home);

            }
            @Override
            public void onCancel() {
                // code for cancellation
                lblIngresar.setText("Login attempt canceled.");
            }
            @Override
            public void onError(FacebookException exception) {
                //  code to handle error
                lblIngresar.setText("Login attempt failed.");
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e("data",data.toString());
    }

}
