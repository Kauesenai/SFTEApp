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

import br.com.httpssftetransporte.sfte.Construtoras.EmpresasConst;
import br.com.httpssftetransporte.sfte.Construtoras.EscolaConst;
import br.com.httpssftetransporte.sfte.Construtoras.MensalidadesConst;
import br.com.httpssftetransporte.sfte.ListView.ListViewEscolas;
import br.com.httpssftetransporte.sfte.ListView.ListViewMensalidades;

public class MensalidadesActivity extends AppCompatActivity {

    private static final String JSON_URL = "https://sftetransporte.com.br/Android/mensalidades.php";

    ListView listView;
    List<MensalidadesConst> mensalidadesList;
    List<MensalidadesConst> mensalidadesQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensalidades);

        listView = findViewById(R.id.listView);
        mensalidadesList = new ArrayList<>();
        mensalidadesQuery = new ArrayList<>();
        listView.setTextFilterEnabled(true);
        loadMensalidadesList();
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Ações");
        menu.add(0,v.getId(),0,"Editar Mensalidade");
        menu.add(0,v.getId(),0,"Excluir Mensalidade");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Integer pos = info.position;
        MensalidadesConst mensalidades = mensalidadesQuery.get(pos);
        final String id_mensalidade = mensalidades.getId_mensalidade();
        if(item.getTitle() == "Editar Mensalidade"){
            Intent irTela = new Intent(MensalidadesActivity.this, EditarMensalidade.class);
            irTela.putExtra("id_mensalidade",id_mensalidade);
            startActivity(irTela);
        }
        else if(item.getTitle() == "Excluir Mensalidade"){
            AlertDialog.Builder builder = new AlertDialog.Builder(MensalidadesActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Deseja excluir essa mensalidade?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CRUD.excluir(JSON_URL, id_mensalidade.toString(), getApplicationContext());
                    listView.setAdapter(null);
                    loadMensalidadesList();
                }
            }).setNegativeButton("Não", null);
            builder.create().show();
        }
        return true;
    }

    private void loadMensalidadesList(){
        mensalidadesList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);

                            JSONArray mensalidadesArray = obj.getJSONArray("mensalidades");

                            for (int i = 0; i < mensalidadesArray.length(); i++){
                                JSONObject mensalidadesObject = mensalidadesArray.getJSONObject(i);

                                MensalidadesConst mensalidades = new MensalidadesConst(mensalidadesObject.getString("id_mensalidade"), mensalidadesObject.getString("nome_crianca"),"Situação: "+mensalidadesObject.getString("situacao"),"Data de Vencimento: "+mensalidadesObject.getString("data_venc"),"Valor Mensalidade: "+mensalidadesObject.getString("valor_mensalidade"));

                                mensalidadesList.add(mensalidades);
                                mensalidadesQuery.add(mensalidades);
                            }

                            ListViewMensalidades adapter = new ListViewMensalidades(mensalidadesList, getApplicationContext());

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
        Intent intent = new Intent(MensalidadesActivity.this, CadastrarMensalidade.class);
        startActivity(intent);
    }

}
