package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditarFuncionario extends AppCompatActivity {

    private String id_funcionario;

    private static final String JSON_URL = "https://sftetransporte.com.br/Android/update_funcionario.php";

    EditText nome_func, rg, email, data_nascimento, tipo_func, cpf, data_admissao, salario, telefone, tipo_telefone, cep, rua, estado, cidade, bairro, numero, hora_entrada, hora_saida;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_funcionario);

        nome_func = findViewById(R.id.nome_funcionario);
        rg = findViewById(R.id.rg);
        email = findViewById(R.id.email);
        data_nascimento = findViewById(R.id.data_nascimento);
        tipo_func = findViewById(R.id.tipo_funcionario);
        cpf = findViewById(R.id.cpf);
        data_admissao = findViewById(R.id.data_admicao);
        salario = findViewById(R.id.salario);
        telefone = findViewById(R.id.telefone);
        tipo_telefone = findViewById(R.id.tipo_telefone);
        cep = findViewById(R.id.cep);
        rua = findViewById(R.id.rua);
        estado = findViewById(R.id.estado);
        cidade = findViewById(R.id.cidade);
        bairro = findViewById(R.id.bairro);
        numero = findViewById(R.id.numero);
        hora_entrada = findViewById(R.id.hr_entrada);
        hora_saida = findViewById(R.id.hr_saida);

        btn = findViewById(R.id.btn);

        try{
            Intent i = getIntent();
            this.id_funcionario = i.getStringExtra("id_funcionario");
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

                            JSONArray funcArray = obj.getJSONArray("funcionarios");
                            JSONObject jo = funcArray.getJSONObject(0);

                            nome_func.setText(jo.getString("nome"));
                            rg.setText(jo.getString("rg"));
                            email.setText(jo.getString("email"));
                            data_nascimento.setText(jo.getString("data_nascimento"));
                            tipo_func.setText(jo.getString("cargo"));
                            cpf.setText(jo.getString("cpf"));
                            data_admissao.setText(jo.getString("data_admicao"));
                            salario.setText(jo.getString("salario"));
                            telefone.setText(jo.getString("numero_telefone"));
                            tipo_telefone.setText(jo.getString("tipo_telefone"));
                            cep.setText(jo.getString("cep"));
                            rua.setText(jo.getString("rua"));
                            estado.setText(jo.getString("estado"));
                            cidade.setText(jo.getString("cidade"));
                            bairro.setText(jo.getString("bairro"));
                            numero.setText(jo.getString("numero"));
                            hora_entrada.setText(jo.getString("hora_entrada"));
                            hora_saida.setText(jo.getString("hora_saida"));

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
                params.put("id_funcionario", id_funcionario);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Editar(View v) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("update", "update");
        params.put("id_funcionario", id_funcionario);
        params.put("nome", nome_func.getText().toString().trim());
        params.put("rg", rg.getText().toString().trim());
        params.put("email", email.getText().toString().trim());
        params.put("data_nascimento", data_nascimento.getText().toString().trim());
        params.put("cargo", tipo_func.getText().toString().trim());
        params.put("cpf", cpf.getText().toString().trim());
        params.put("data_admicao", data_admissao.getText().toString().trim());
        params.put("salario", salario.getText().toString().trim());
        params.put("numero_telefone", telefone.getText().toString().trim());
        params.put("tipo_telefone", tipo_telefone.getText().toString().trim());
        params.put("cep", cep.getText().toString().trim());
        params.put("rua", rua.getText().toString().trim());
        params.put("estado", estado.getText().toString().trim());
        params.put("cidade", cidade.getText().toString().trim());
        params.put("bairro", bairro.getText().toString().trim());
        params.put("numero", numero.getText().toString().trim());
        params.put("hora_entrada", hora_entrada.getText().toString().trim());
        params.put("hora_saida", hora_saida.getText().toString().trim());

        StringRequest stringRequest = CRUD.editar("https://sftetransporte.com.br/Android/update_funcionario.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jo = new JSONObject(response);
                    String resposta = jo.getString("resposta");
                    Toast.makeText(EditarFuncionario.this, resposta, Toast.LENGTH_SHORT).show();
                    Intent irTela = new Intent(EditarFuncionario.this, FuncionariosActivity.class);
                    startActivity(irTela);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },params,getApplicationContext());
        RequestQueue requestQueue = Volley.newRequestQueue(EditarFuncionario.this);
        requestQueue.add(stringRequest);

    }
}
