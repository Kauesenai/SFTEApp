package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.httpssftetransporte.sfte.Construtoras.VansConst;

public class EditarPassageiroActivity extends AppCompatActivity {

    private String id_crianca;

    private static final String JSON_URL = "https://sftetransporte.com.br/Android/updatepassageiro.php";

    EditText nome_crianca,data_nascimento,cpf_passageiro,rg_passageiro,cep,rua,estado,cidade,bairro,numero,hr_entrada,hr_saida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_passageiro);

        nome_crianca = findViewById(R.id.nome_crianca);
        data_nascimento= findViewById(R.id.data_nascimento);
        cpf_passageiro = findViewById(R.id.cpf_passageiro);
        rg_passageiro = findViewById(R.id.rg_passageiro);
        cep = findViewById(R.id.cep);
        rua = findViewById(R.id.rua);
        estado = findViewById(R.id.estado);
        cidade = findViewById(R.id.cidade);
        bairro = findViewById(R.id.bairro);
        numero = findViewById(R.id.numero);
        hr_entrada  = findViewById(R.id.hr_entrada);
        hr_saida = findViewById(R.id.hr_saida);

        try{
            Intent i = getIntent();
            this.id_crianca = i.getStringExtra("id_crianca");
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

                            JSONArray funcArray = obj.getJSONArray("criancas");
                            JSONObject jo = funcArray.getJSONObject(0);

                            nome_crianca.setText(jo.getString("nome_crianca"));
                            data_nascimento.setText(jo.getString("data_nascimento"));
                            cpf_passageiro.setText(jo.getString("cpf_passageiro"));
                            rg_passageiro.setText(jo.getString("rg"));
                            cep.setText(jo.getString("cep"));
                            rua.setText(jo.getString("rua"));
                            estado.setText(jo.getString("estado"));
                            cidade.setText(jo.getString("cidade"));
                            bairro.setText(jo.getString("bairro"));
                            numero.setText(jo.getString("numero"));
                            hr_entrada.setText(jo.getString("hora_entrada"));
                            hr_saida.setText(jo.getString("hora_saida"));

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
                params.put("id_crianca", id_crianca);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Editar(View v) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("update", "update");
        params.put("id_crianca", id_crianca);
        params.put("nome_crianca", nome_crianca.getText().toString().trim());
        params.put("data_nascimento", data_nascimento.getText().toString().trim());
        params.put("cpf_passageiro", cpf_passageiro.getText().toString().trim());
        params.put("rg", rg_passageiro.getText().toString().trim());
        params.put("cep", cep.getText().toString().trim());
        params.put("rua", rua.getText().toString().trim());
        params.put("estado", estado.getText().toString().trim());
        params.put("cidade", cidade.getText().toString().trim());
        params.put("bairro", bairro.getText().toString().trim());
        params.put("numero", numero.getText().toString().trim());
        params.put("hora_entrada", hr_entrada.getText().toString().trim());
        params.put("hora_saida", hr_saida.getText().toString().trim());

        StringRequest stringRequest = CRUD.editar("https://sftetransporte.com.br/Android/updatepassageiro.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jo = new JSONObject(response);
                    String resposta = jo.getString("resposta");
                    Toast.makeText(EditarPassageiroActivity.this, resposta, Toast.LENGTH_SHORT).show();
                    Intent irTela = new Intent(EditarPassageiroActivity.this, CriancasActivity.class);
                    startActivity(irTela);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },params,getApplicationContext());
        RequestQueue requestQueue = Volley.newRequestQueue(EditarPassageiroActivity.this);
        requestQueue.add(stringRequest);

    }

}
