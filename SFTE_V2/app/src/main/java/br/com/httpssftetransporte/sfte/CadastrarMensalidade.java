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

import br.com.httpssftetransporte.sfte.Construtoras.CriancaConst;

public class CadastrarMensalidade extends AppCompatActivity {

    private String idPassageiro;

    EditText data_vencimento, juros, valor_mensalidade;
    Spinner nome_passageiro;
    Button btn;

    ArrayAdapter<String> adapter;
    ArrayList<CriancaConst> passageiros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_mensalidade);

        nome_passageiro   = findViewById(R.id.passageiro_spinner);
        data_vencimento   = findViewById(R.id.data_vencimento);
        juros             = findViewById(R.id.juros);
        valor_mensalidade = findViewById(R.id.valor_mensalidade);

        btn               = findViewById(R.id.btn);

        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);
        passageiros = new ArrayList<CriancaConst>();
        carregarPassageiro();

        nome_passageiro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idPassageiro = passageiros.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void carregarPassageiro(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://sftetransporte.com.br/Android/lista_criancas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray passageirosArray = obj.getJSONArray("criancas");

                            for (int i = 0; i < passageirosArray.length(); i++){
                                JSONObject criancaObject = passageirosArray.getJSONObject(i);

                                CriancaConst crianca = new CriancaConst(criancaObject.getString("id_crianca"),criancaObject.getString("nome_crianca"), criancaObject.getString("cpf_passageiro"));

                                passageiros.add(crianca);
                                adapter.add(crianca.getNome());
                            }
                            nome_passageiro.setAdapter(adapter);
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

                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    public void Cadastrar(View v) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("data_venc", data_vencimento.getText().toString().trim());
        params.put("juros", juros.getText().toString().trim());
        params.put("valor_mensalidade", valor_mensalidade.getText().toString().trim());
        params.put("fk_id_crianca", idPassageiro);

        CRUD.inserir("https://sftetransporte.com.br/Android/insert_mensalidades.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try{
                    JSONObject jo = new JSONObject(response);
                    String resposta = jo.getString("resposta");
                    Toast.makeText(CadastrarMensalidade.this, resposta, Toast.LENGTH_SHORT).show();
                    Intent irTela = new Intent(CadastrarMensalidade.this, MensalidadesActivity.class);
                    startActivity(irTela);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },params,getApplicationContext());

    }
}
