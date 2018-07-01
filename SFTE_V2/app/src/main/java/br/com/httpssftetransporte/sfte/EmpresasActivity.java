package br.com.httpssftetransporte.sfte;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import br.com.httpssftetransporte.sfte.Construtoras.EmpresasConst;
import br.com.httpssftetransporte.sfte.ListView.ListViewEmpresas;

public class EmpresasActivity extends AppCompatActivity {

    private static final String JSON_URL = "https://sftetransporte.com.br/Android/lista_emp.php";

    ListView listView;
    List<EmpresasConst> empresasList;
    List<EmpresasConst> empresasQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);
        listView = findViewById(R.id.listView);
        empresasList = new ArrayList<>();
        empresasQuery = new ArrayList<>();
        listView.setTextFilterEnabled(true);
        loadEmpresasList();
        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Ações");
        menu.add(0,v.getId(),0,"Editar Empresa");
        menu.add(0,v.getId(),0,"Excluir Empresa");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Integer pos = info.position;
        EmpresasConst empresas = empresasQuery.get(pos);
        final String id_empresa = empresas.getId();
        if(item.getTitle() == "Editar Empresa"){
            Intent irTela = new Intent(EmpresasActivity.this, EditarExcluir_Empresa.class);
            irTela.putExtra("id_empresa",id_empresa);
            startActivity(irTela);
        }
        else if(item.getTitle() == "Excluir Empresa"){
            AlertDialog.Builder builder = new AlertDialog.Builder(EmpresasActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Deseja excluir essa empresa?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CRUD.excluir(JSON_URL, id_empresa.toString(), getApplicationContext());
                    listView.setAdapter(null);
                    loadEmpresasList();
                }
            }).setNegativeButton("Não", null);
            builder.create().show();
        }
        return true;
    }

    private void loadEmpresasList(){
        empresasList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray empresasArray = obj.getJSONArray("empresas");

                            for (int i = 0; i < empresasArray.length(); i++){
                                JSONObject empresasObject = empresasArray.getJSONObject(i);

                                EmpresasConst empresas = new EmpresasConst(empresasObject.getString("id_empresa"),empresasObject.getString("razao_social"), empresasObject.getString("telefone_comercial"));

                                empresasList.add(empresas);
                                empresasQuery.add(empresas);
                            }

                            ListViewEmpresas adapter = new ListViewEmpresas(empresasList, getApplicationContext());

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

}

