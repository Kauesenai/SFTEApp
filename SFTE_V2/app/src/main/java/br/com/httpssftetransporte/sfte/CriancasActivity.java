package br.com.httpssftetransporte.sfte;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import br.com.httpssftetransporte.sfte.Construtoras.CriancaConst;
import br.com.httpssftetransporte.sfte.Construtoras.FuncionariosConst;
import br.com.httpssftetransporte.sfte.ListView.ListViewCriancas;
import br.com.httpssftetransporte.sfte.ListView.ListViewFuncionarios;

public class CriancasActivity extends AppCompatActivity {

    private static final String JSON_URL = "https://sftetransporte.com.br/Android/lista_criancas.php";

    ListView listView;
    List<CriancaConst> criancasList;
    List<CriancaConst> criancasQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criancas);

        listView = findViewById(R.id.listView);
        criancasList = new ArrayList<>();
        criancasQuery = new ArrayList<>();
        listView.setTextFilterEnabled(true);
        loadCriancaList();
        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Ações");
        menu.add(0,v.getId(),0,"Editar Passageiro");
        menu.add(0,v.getId(),0,"Excluir Passageiro");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Integer pos = info.position;
        CriancaConst crianca = criancasQuery.get(pos);
        final String id_crianca = crianca.getId();
        if(item.getTitle() == "Editar Passageiro"){
            Intent irTela = new Intent(CriancasActivity.this, EditarPassageiroActivity.class);
            irTela.putExtra("id_crianca",id_crianca);
            startActivity(irTela);
        }
        else if(item.getTitle() == "Excluir Passageiro"){
            AlertDialog.Builder builder = new AlertDialog.Builder(CriancasActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Deseja excluir esse passageiro?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CRUD.excluir(JSON_URL, id_crianca.toString(), getApplicationContext());
                    listView.setAdapter(null);
                    loadCriancaList();
                }
            }).setNegativeButton("Não", null);
            builder.create().show();
        }
        return true;
    }

    private void loadCriancaList() {
        criancasList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray criancaArray = obj.getJSONArray("criancas");

                            for (int i = 0; i < criancaArray.length(); i++){
                                JSONObject criancaObject = criancaArray.getJSONObject(i);

                                CriancaConst crianca = new CriancaConst(criancaObject.getString("id_crianca"),criancaObject.getString("nome_crianca"), criancaObject.getString("cpf_passageiro"));

                                criancasList.add(crianca);
                                criancasQuery.add(crianca);
                            }

                            ListViewCriancas adapter = new ListViewCriancas(criancasList, getApplicationContext());

                            listView.setAdapter(adapter);

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void ds (View v){
        Intent intent = new Intent(CriancasActivity.this, CadastrarPassageiro.class);
        startActivity(intent);
    }
}
