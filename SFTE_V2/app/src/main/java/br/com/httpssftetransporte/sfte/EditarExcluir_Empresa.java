package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditarExcluir_Empresa extends AppCompatActivity {

    private String id_empresa;

    private static final String JSON_URL = "https://sftetransporte.com.br/Android/updateempresas.php";

    EditText razaosocial, cnpj, inscricao, telcomercial, uf, rua, bairro, numero, cidade;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_excluir__empresa);

        razaosocial = findViewById(R.id.razao_social);
        cnpj = findViewById(R.id.cnpj);
        inscricao = findViewById(R.id.inscricao_estadual);
        telcomercial = findViewById(R.id.telefone_comercial);
        uf = findViewById(R.id.uf);
        rua = findViewById(R.id.rua);
        bairro = findViewById(R.id.bairro);
        numero = findViewById(R.id.numero);
        cidade = findViewById(R.id.cidade);

        btn = findViewById(R.id.editar);

        try{
            Intent i = getIntent();
            this.id_empresa = i.getStringExtra("id_empresa");
        }catch(NullPointerException e){
            e.printStackTrace();
        }

        carregar();
    }

    public void carregar(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray empresasArray = obj.getJSONArray("empresas");
                            JSONObject jo = empresasArray.getJSONObject(0);

                            razaosocial.setText(jo.getString("razao_social"));
                            cnpj.setText(jo.getString("cnpj"));
                            inscricao.setText(jo.getString("inscricao_estadual"));
                            telcomercial.setText(jo.getString("telefone_comercial"));
                            uf.setText(jo.getString("uf"));
                            rua.setText(jo.getString("rua"));
                            bairro.setText(jo.getString("bairro"));
                            numero.setText(jo.getString("numero"));
                            cidade.setText(jo.getString("cidade"));


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("select", "select");
                params.put("id_empresa", id_empresa);
                params.put("cnpj", cnpj.getText().toString().trim());
                params.put("inscricao_estadual", inscricao.getText().toString().trim());
                params.put("telefone_comercial",telcomercial.getText().toString().trim());
                params.put("uf", uf.getText().toString().trim());
                params.put("rua", rua.getText().toString().trim());
                params.put("bairro", bairro.getText().toString().trim());
                params.put("numero", numero.getText().toString().trim());
                params.put("cidade", cidade.getText().toString().trim());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Editar(View v) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("update", "update");
        params.put("id_empresa", id_empresa);
        params.put("razao_social", razaosocial.getText().toString().trim());
        params.put("cnpj", cnpj.getText().toString().trim());
        params.put("inscricao_estadual", inscricao.getText().toString().trim());
        params.put("telefone_comercial", telcomercial.getText().toString().trim());
        params.put("uf", uf.getText().toString().trim());
        params.put("rua", rua.getText().toString().trim());
        params.put("bairro", bairro.getText().toString().trim());
        params.put("numero", numero.getText().toString().trim());
        params.put("cidade", cidade.getText().toString().trim());


        StringRequest stringRequest = CRUD.editar("https://sftetransporte.com.br/Android/updateempresas.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jo = new JSONObject(response);
                    String resposta = jo.getString("resposta");
                    Toast.makeText(EditarExcluir_Empresa.this, resposta, Toast.LENGTH_SHORT).show();
                    Intent irTela = new Intent(EditarExcluir_Empresa.this, EmpresasActivity.class);
                    startActivity(irTela);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },params,getApplicationContext());
        RequestQueue requestQueue = Volley.newRequestQueue(EditarExcluir_Empresa.this);
        requestQueue.add(stringRequest);

    }

}
