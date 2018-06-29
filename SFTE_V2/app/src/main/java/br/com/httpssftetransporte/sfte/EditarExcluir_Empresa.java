package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    RequestQueue requestQueue;
    String HttpUrl = "https://sftetransporte.com.br/Android/lista_empresa_id.php";
    String editar = "https://sftetransporte.com.br/Android/update_empresa.php";
    String excluir = "https://sftetransporte.com.br/Android/delete_empresa.php";

    TextView txtView;
    EditText razao_social;
    EditText cnpj;
    EditText inscricao_estadual;
    EditText telefone_comercial;
    EditText uf;
    EditText rua;
    EditText bairro;
    EditText numero;
    EditText cidade;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_excluir__empresa);
        requestQueue = Volley.newRequestQueue(this);

        razao_social = findViewById(R.id.razao_social);
        cnpj = findViewById(R.id.cnpj);
        inscricao_estadual = findViewById(R.id.inscricao_estadual);
        telefone_comercial = findViewById(R.id.telefone_comercial);
        uf = findViewById(R.id.uf);
        rua = findViewById(R.id.rua);
        bairro = findViewById(R.id.bairro);
        numero = findViewById(R.id.numero);
        cidade = findViewById(R.id.cidade);

        txtView = (TextView)findViewById(R.id.textView1);
        try{
            Intent i = getIntent();
            String getId = i.getStringExtra("id_empresa");
            txtView.setText(getId);
            //Toast.makeText(this, getId, Toast.LENGTH_SHORT).show();

        }catch(NullPointerException e){
            e.printStackTrace();
        }
        PuxarDados();
    }

    public void PuxarDados() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        System.out.println(ServerResponse);

                        try{
                            JSONObject obj = new JSONObject(ServerResponse);

                            JSONArray servicocompraArray = obj.getJSONArray("empresas");
                            JSONObject jo = servicocompraArray.getJSONObject(0);

                            razao_social.setText(jo.getString("razao_social"));
                            cnpj.setText(jo.getString("cnpj"));
                            inscricao_estadual.setText(jo.getString("inscricao_estadual"));
                            telefone_comercial.setText(jo.getString("telefone_comercial"));
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
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(EditarExcluir_Empresa.this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                //     $_POST['nome']
                params.put("id_empresa", txtView.getText().toString());
                return params;
            }

        };

        requestQueue.getCache().clear();//Limpando o cache
        requestQueue.add(stringRequest);

    }


    public void Editar(View view) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, editar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Toast.makeText(EditarExcluir_Empresa.this, "Editado com Sucesso!", Toast.LENGTH_SHORT).show();
                        Intent lista = new Intent(EditarExcluir_Empresa.this, EmpresasActivity.class);
                        startActivity(lista);
                        //Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        //System.out.println(ServerResponse);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(EditarExcluir_Empresa.this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                //     $_POST['nome']
                params.put("id_empresa", txtView.getText().toString());
                params.put("razao_social", razao_social.getText().toString());
                params.put("cnpj", cnpj.getText().toString());
                params.put("inscricao_estadual", inscricao_estadual.getText().toString());
                params.put("telefone_comercial", telefone_comercial.getText().toString());
                params.put("uf", uf.getText().toString());
                params.put("rua", rua.getText().toString());
                params.put("bairro", bairro.getText().toString());
                params.put("numero", numero.getText().toString());
                params.put("cidade", cidade.getText().toString());


                return params;
            }

        };


        requestQueue.getCache().clear();//Limpando o cache
        requestQueue.add(stringRequest);
    }
}
