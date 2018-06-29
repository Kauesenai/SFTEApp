package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.List;

import br.com.httpssftetransporte.sfte.Construtoras.FuncionariosConst;
import br.com.httpssftetransporte.sfte.ListView.ListViewFuncionarios;

public class FuncionariosActivity extends AppCompatActivity {


    private static final String JSON_URL = "https://sftetransporte.com.br/Android/lista_funcionarios.php";


    ListView listView;

    SearchView searchView;
    List<FuncionariosConst> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);

        listView = (ListView) findViewById(R.id.listView);
        heroList = new ArrayList<>();

        loadHeroList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FuncionariosConst funcionarios = heroList.get(i);
                //Toast.makeText(VansActivity.this, "Foi o Toast", Toast.LENGTH_SHORT).show();
                if(i==0){
                    Intent puxarDados= new Intent(view.getContext(), MainActivity.class);
                    puxarDados.putExtra("id_func",funcionarios.getId());
                    startActivityForResult(puxarDados,0);
                }
                if(i==1){
                    Intent puxarDados= new Intent(view.getContext(), MainActivity.class);
                    puxarDados.putExtra("id_func",funcionarios.getId());
                    startActivityForResult(puxarDados,0);
                }
                if(i==2){
                    Intent puxarDados= new Intent(view.getContext(), MainActivity.class);
                    puxarDados.putExtra("id_func",funcionarios.getId());
                    startActivityForResult(puxarDados,0);
                }
                if(i==3){
                    Intent puxarDados= new Intent(view.getContext(), MainActivity.class);
                    puxarDados.putExtra("id_func",funcionarios.getId());
                    startActivityForResult(puxarDados,0);
                }
                if(i==4){
                    Intent puxarDados= new Intent(view.getContext(), MainActivity.class);
                    puxarDados.putExtra("id_func",funcionarios.getId());
                    startActivityForResult(puxarDados,0);
                }
                if(i==5){
                    Intent puxarDados= new Intent(view.getContext(), MainActivity.class);
                    puxarDados.putExtra("id_func",funcionarios.getId());
                    startActivityForResult(puxarDados,0);
                }
                if(i==6){
                    Intent puxarDados= new Intent(view.getContext(), MainActivity.class);
                    puxarDados.putExtra("id_func",funcionarios.getId());
                    startActivityForResult(puxarDados,0);
                }
                if(i==7){
                    Intent puxarDados= new Intent(view.getContext(), MainActivity.class);
                    puxarDados.putExtra("id_func",funcionarios.getId());
                    startActivityForResult(puxarDados,0);
                }
                if(i==8){
                    Intent puxarDados= new Intent(view.getContext(), MainActivity.class);
                    puxarDados.putExtra("id_func",funcionarios.getId());
                    startActivityForResult(puxarDados,0);
                }
                if(i==9){
                    Intent puxarDados= new Intent(view.getContext(), MainActivity.class);
                    puxarDados.putExtra("id_func",funcionarios.getId());
                    startActivityForResult(puxarDados,0);
                }

            }
        });
    }

    public void fab(View v){
        Intent fab= new Intent(FuncionariosActivity.this,MainActivity.class);
        startActivity(fab);
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


                            JSONArray heroArray = obj.getJSONArray("funcionarios");


                            for (int i = 0; i < heroArray.length(); i++) {

                                JSONObject heroObject = heroArray.getJSONObject(i);


                                FuncionariosConst hero = new FuncionariosConst(heroObject.getString("id_func"), heroObject.getString("nome"), heroObject.getString("cargo"));


                                heroList.add(hero);
                            }


                            ListViewFuncionarios adapter = new ListViewFuncionarios(heroList, getApplicationContext());


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


}
