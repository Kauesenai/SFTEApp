package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.List;

import br.com.httpssftetransporte.sfte.Construtoras.EmpresasConst;
import br.com.httpssftetransporte.sfte.ListView.ListViewEmpresas;

public class EmpresasActivity extends AppCompatActivity {

    private static final String JSON_URL = "https://sftetransporte.com.br/Android/lista_emp.php";


    ListView listView;
    List<EmpresasConst> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);
        listView = (ListView) findViewById(R.id.listView);
        heroList = new ArrayList<>();
        listView.setTextFilterEnabled(true);//filtro pré-definido
        loadHeroList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EmpresasConst van = heroList.get(i);
                //Toast.makeText(VansActivity.this, "Foi o Toast", Toast.LENGTH_SHORT).show();
                if(i==0){
                    Intent puxarDados= new Intent(view.getContext(), EditarExcluir_Empresa.class);
                    puxarDados.putExtra("id_empresa",van.getId());
                    startActivityForResult(puxarDados,0);
                }
            }
        });


    }

    private void loadHeroList() {

        //final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);


        //progressBar.setVisibility(View.VISIBLE);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressBar.setVisibility(View.INVISIBLE);


                        try {

                            JSONObject obj = new JSONObject(response);


                            JSONArray heroArray = obj.getJSONArray("empresas");


                            for (int i = 0; i < heroArray.length(); i++) {

                                JSONObject heroObject = heroArray.getJSONObject(i);


                                EmpresasConst hero = new EmpresasConst(heroObject.getString("id_empresa"), heroObject.getString("razao_social"), heroObject.getString("cnpj"));


                                heroList.add(hero);
                            }


                            ListViewEmpresas adapter = new ListViewEmpresas(heroList, getApplicationContext());


                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(stringRequest);
    }

    public boolean onQueryTextChange(String newText){//Semelhante ao onkeyup do javascript
        if (TextUtils.isEmpty(newText)) {//verifica se o texto digitado é nulo
            /*Como o texto digitado é nulo, limpamos todos os filtros
              Assim mostramos todos os registos  */
            listView.clearTextFilter();
        } else {
            //Caso o texto nao é seja vazio, é definido ele como nosso filto
            listView.setFilterText(newText);
        }
        return true;
    }
    public boolean onQueryTextSubmit(String query){
        /*
         * Este metodo é usado para o botao de submit,
         * em nosso caso nao ultilizamos esse botao,
         * assim retornamos ele como false, já que o metodo
         * é obrigatorio por conta do implements.
         *
         * Para poder usar ele defina  searchView.setSubmitButtonEnabled(true); em seu onCreate
         * */
        return false;
    }


}

