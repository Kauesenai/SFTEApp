package br.com.httpssftetransporte.sfte;

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
        carregarMotorista();

        //Motorista
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
    //Carregar Motorista
    public void carregarMotorista(){
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
        public void Cadastrar(View view) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Toast.makeText(CadastrarPassageiro.this, "Enviado ao servidor", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        //System.out.println(ServerResponse);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(CadastrarPassageiro.this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                //     $_POST['nome']
                params.put("nome", nome_responsavel.getText().toString());
                params.put("data_nascimento", data_nascimento_responsavel.getText().toString());
                params.put("rg", rg_responsavel.getText().toString());
                params.put("email", email.getText().toString());
                params.put("senha", senha.getText().toString());
                params.put("cpf", cpf_responsavel.getText().toString());
                params.put("telefone", telefone.getText().toString());
                params.put("tipo_telefone", tipo_telefone.getText().toString());
                params.put("parentesco", parentesco.getText().toString());
                params.put("escola", fk_id_escola.getText().toString());
                params.put("nome_crianca", nome_crianca.getText().toString());
                params.put("cpf_passageiro", cpf_passageiro.getText().toString());
                params.put("rg", rg_passageiro.getText().toString());
                params.put("data_nascimento", data_nascimento.getText().toString());
                params.put("rua", rua.getText().toString());
                params.put("numero", numero.getText().toString());
                params.put("cep", cep.getText().toString());
                params.put("cidade", cidade.getText().toString());
                params.put("bairro", bairro.getText().toString());
                params.put("estado", estado.getText().toString());
                params.put("van_embarque", fk_id_van.getSelectedItem().toString());
                params.put("hora_saida", hr_saida.getText().toString());
                params.put("hora_entrada", hr_entrada.getText().toString());



                return params;
            }

        };


        requestQueue.getCache().clear();//Limpando o cache
        requestQueue.add(stringRequest);
    }

}