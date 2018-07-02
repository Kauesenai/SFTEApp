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
import android.widget.SearchView;
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
import br.com.httpssftetransporte.sfte.Construtoras.FuncionariosConst;
import br.com.httpssftetransporte.sfte.ListView.ListViewEmpresas;
import br.com.httpssftetransporte.sfte.ListView.ListViewFuncionarios;

public class FuncionariosActivity extends AppCompatActivity {

    private static final String JSON_URL = "https://sftetransporte.com.br/Android/lista_funcionarios.php";

    ListView listView;
    List<FuncionariosConst> funcionariosList;
    List<FuncionariosConst> funcionariosQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);
        listView = findViewById(R.id.listView);
        funcionariosList = new ArrayList<>();
        funcionariosQuery = new ArrayList<>();
        listView.setTextFilterEnabled(true);
        loadFuncionariosList();
        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Ações");
        menu.add(0,v.getId(),0,"Editar Funcionário");
        menu.add(0,v.getId(),0,"Excluir Funcionário");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Integer pos = info.position;
        FuncionariosConst funcionarios = funcionariosQuery.get(pos);
        final String id_funcionario = funcionarios.getId();
        if(item.getTitle() == "Editar Funcionário"){
            Intent irTela = new Intent(FuncionariosActivity.this, EditarFuncionario.class);
            irTela.putExtra("id_funcionario",id_funcionario);
            startActivity(irTela);
        }
        else if(item.getTitle() == "Excluir Funcionário"){
            AlertDialog.Builder builder = new AlertDialog.Builder(FuncionariosActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Deseja excluir esse funcionário?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CRUD.excluir(JSON_URL, id_funcionario.toString(), getApplicationContext());
                    listView.setAdapter(null);
                    loadFuncionariosList();
                }
            }).setNegativeButton("Não", null);
            builder.create().show();
        }
        return true;
    }


    private void loadFuncionariosList() {
        funcionariosList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray funcArray = obj.getJSONArray("funcionarios");

                            for (int i = 0; i < funcArray.length(); i++){
                                JSONObject funcObject = funcArray.getJSONObject(i);

                                FuncionariosConst funcionarios = new FuncionariosConst(funcObject.getString("id_func"),funcObject.getString("nome"), funcObject.getString("cargo"));

                                funcionariosList.add(funcionarios);
                                funcionariosQuery.add(funcionarios);
                            }

                            ListViewFuncionarios adapter = new ListViewFuncionarios(funcionariosList, getApplicationContext());

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
        Intent intent = new Intent(FuncionariosActivity.this, CadastrarFuncionarios.class);
        startActivity(intent);
    }

}
