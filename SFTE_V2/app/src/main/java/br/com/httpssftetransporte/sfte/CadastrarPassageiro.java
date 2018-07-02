package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;
import java.util.Map;

import br.com.httpssftetransporte.sfte.Construtoras.FuncionariosConst;
import br.com.httpssftetransporte.sfte.Construtoras.VansConst;


public class CadastrarPassageiro extends AppCompatActivity {
    RequestQueue requestQueue;
    String HttpUrl = "https://sftetransporte.com.br/Android/insert_passageiro.php";
    EditText nome_responsavel,rg_responsavel,data_nascimento_responsavel,email,senha,cpf_responsavel,telefone,tipo_telefone,parentesco,fk_id_escola,nome_crianca,data_nascimento,cpf_passageiro,rg_passageiro,cep,rua,estado,cidade,bairro,numero,hr_entrada,hr_saida;
    Spinner fk_id_van;
    ArrayAdapter<String> adapter;
    ArrayList<VansConst> vans;
    private String idVan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_passageiro);

        nome_responsavel = findViewById(R.id.nome_responsavel);
        rg_responsavel = findViewById(R.id.rg_responsavel);
        data_nascimento_responsavel = findViewById(R.id.data_nascimento_responsavel);
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        cpf_responsavel = findViewById(R.id.cpf_responsavel);
        tipo_telefone = findViewById(R.id.tipo_telefone);
        telefone = findViewById(R.id.telefone);
        parentesco = findViewById(R.id.parentesco);
        fk_id_escola = findViewById(R.id.fk_id_escola);
        fk_id_van = findViewById(R.id.fk_id_van);
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

        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);
        vans = new ArrayList<VansConst>();
        carregarVan();

        fk_id_van.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idVan = vans.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void carregarVan(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://sftetransporte.com.br/Android/lista_van.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray forncompraArray = obj.getJSONArray("vans");

                            for (int i = 0; i < forncompraArray.length(); i++){
                                JSONObject forncompraObject = forncompraArray.getJSONObject(i);

                                VansConst forneCompra = new VansConst(forncompraObject.getString("id_van"), forncompraObject.getString("modelo"), forncompraObject.getString("placa"));

                                vans.add(forneCompra);
                                adapter.add(forneCompra.getModelo());
                            }
                            fk_id_van.setAdapter(adapter);
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

        params.put("nome", nome_responsavel.getText().toString().trim());
        params.put("rg", rg_responsavel.getText().toString().trim());
        params.put("data_nascimento", data_nascimento.getText().toString().trim());
        params.put("email", email.getText().toString().trim());
        params.put("senha", senha.getText().toString().trim());
        params.put("cpf", cpf_responsavel.getText().toString().trim());
        params.put("telefone", telefone.getText().toString().trim());
        params.put("tipo_telefone", tipo_telefone.getText().toString().trim());
        params.put("parentesco", parentesco.getText().toString().trim());
        params.put("fk_id_escola", fk_id_escola.getText().toString().trim());

        params.put("fk_id_van", idVan);

       //Dados Criança
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

        CRUD.inserir("https://sftetransporte.com.br/Android/insert_mensalidades.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try{
                    Toast.makeText(CadastrarPassageiro.this, response, Toast.LENGTH_SHORT).show();
                    JSONObject jo = new JSONObject(response);
                    String resposta = jo.getString("resposta");
                    Toast.makeText(CadastrarPassageiro.this, resposta, Toast.LENGTH_SHORT).show();
                    Intent irTela = new Intent(CadastrarPassageiro.this, CriancasActivity.class);
                    startActivity(irTela);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },params,getApplicationContext());

    }

}