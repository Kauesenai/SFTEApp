package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText email;
    EditText senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestQueue = Volley.newRequestQueue(this);
        email = findViewById(R.id.et_email_address);
        senha = findViewById(R.id.et_password);


    }

    public void mudar_tela(View v){
        final String email = this.email.getText().toString().trim();
        final String senha = this.senha.getText().toString().trim();

        StringRequest sr = new StringRequest(Request.Method.POST, "https://sftetransporte.com.br/Android/logar.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    boolean valido = jo.getBoolean("validar");
                    if (valido) {
                        JSONObject quei = jo.getJSONObject("resposta");
                        String id = quei.getString("id");
                        String email = quei.getString("email");
                        String nome = quei.getString("nome");
                        String tipo_usuario = quei.getString("tipo_usuario");

                        Intent irTela = new Intent(HomeActivity.this, Dashboard.class);
                        startActivity(irTela);

                    }else{
                        Toast.makeText(HomeActivity.this, "Login incorreto", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams() throws com.android.volley.AuthFailureError{
                Map<String,String> params = new HashMap<String,String>();
                params.put("loginPadrao", "loginPadrao");
                params.put("email",email);
                params.put("senha",senha);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(sr);

    }

}
