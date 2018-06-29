package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Map;

import br.com.httpssftetransporte.sfte.Construtoras.EmpresasConst;
import br.com.httpssftetransporte.sfte.Construtoras.FuncionariosConst;

public class CadastrarVan extends AppCompatActivity {
    //Inserir
    EditText txtModelo;
    EditText txtMarca;
    EditText txtCapacidade;
    EditText txtPlaca;
    EditText txtAno;
    EditText txtRenavam;
    RequestQueue requestQueue;
    private String idMotorista, idMonitora;
    String inserir = "https://sftetransporte.com.br/Android/insert_van.php";

    Spinner motorista, monitora;

    EditText placa;

    //Spinner Motorista
    ArrayAdapter<String> adapter;
    ArrayList<FuncionariosConst> motoristaa;

    //Spinner Monitora
    ArrayAdapter<String> adapter2;
    ArrayList<FuncionariosConst> monitoraa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_van);
        requestQueue = Volley.newRequestQueue(this);
        monitora = findViewById(R.id.monitora);
        motorista = findViewById(R.id.motorista);
        txtModelo = findViewById(R.id.txtModelo);
        txtCapacidade = findViewById(R.id.txtCapacidade);
        txtMarca = findViewById(R.id.txtMarca);
        txtAno = findViewById(R.id.txtAno);
        txtRenavam = findViewById(R.id.txtRenavam);
        placa = findViewById(R.id.placa);

        //Motorista
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);
        motoristaa = new ArrayList<FuncionariosConst>();
        carregarMotorista();

        //Motorista
        motorista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idMotorista = motoristaa.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Monitora
        adapter2 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);
        monitoraa = new ArrayList<FuncionariosConst>();
        carregarMonitora();

        //Monitora
        monitora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idMonitora = monitoraa.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void Inserir(View view) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, inserir,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Toast.makeText(CadastrarVan.this, "Cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();
                       //Intent lista = new Intent(CadastrarVan.this, VansActivity.class);
                       //startActivity(lista);
                        //Toast.makeText(InserirVan.this, ServerResponse, Toast.LENGTH_LONG).show();
                        System.out.println(ServerResponse);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(CadastrarVan.this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {



                Map<String, String> params = new HashMap<>();
                //     $_POST['nome']
                params.put("nome_monitora", monitora.getSelectedItem().toString());
                params.put("nome_motorista", motorista.getSelectedItem().toString());
                params.put("modelo", txtModelo.getText().toString());
                params.put("marca", txtMarca.getText().toString());
                params.put("capacidade", txtCapacidade.getText().toString());
                params.put("placa", placa.getText().toString());
                params.put("ano", txtAno.getText().toString());
                params.put("renavam", txtRenavam.getText().toString());
                return params;
            }

        };
        requestQueue.getCache().clear();//Limpando o cache
        requestQueue.add(stringRequest);
    }
    //Carregar Motorista
    public void carregarMotorista(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://sftetransporte.com.br/Android/lista_motorista_spinner.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray forncompraArray = obj.getJSONArray("motorista");

                            for (int i = 0; i < forncompraArray.length(); i++){
                                JSONObject forncompraObject = forncompraArray.getJSONObject(i);

                                FuncionariosConst forneCompra = new FuncionariosConst(forncompraObject.getString("id_func"), forncompraObject.getString("nome"), forncompraObject.getString("cargo"));

                                motoristaa.add(forneCompra);
                                adapter.add(forneCompra.getNome());
                            }
                            motorista.setAdapter(adapter);
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

    //Carregar Monitora
    public void carregarMonitora(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://sftetransporte.com.br/Android/lista_monitora_spinner.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray forncompraArray = obj.getJSONArray("monitora");

                            for (int i = 0; i < forncompraArray.length(); i++){
                                JSONObject forncompraObject = forncompraArray.getJSONObject(i);

                                FuncionariosConst forneCompra = new FuncionariosConst(forncompraObject.getString("id_func"), forncompraObject.getString("nome"), forncompraObject.getString("cargo"));

                                monitoraa.add(forneCompra);
                                adapter2.add(forneCompra.getNome());
                            }
                            monitora.setAdapter(adapter2);
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

}
