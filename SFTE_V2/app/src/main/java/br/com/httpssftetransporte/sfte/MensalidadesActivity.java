package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import br.com.httpssftetransporte.sfte.Construtoras.EscolaConst;
import br.com.httpssftetransporte.sfte.Construtoras.MensalidadesConst;
import br.com.httpssftetransporte.sfte.ListView.ListViewEscolas;
import br.com.httpssftetransporte.sfte.ListView.ListViewMensalidades;

public class MensalidadesActivity extends AppCompatActivity {

    private static final String JSON_URL = "https://sftetransporte.com.br/Android/mensalidades.php";


    ListView listView;


    List<MensalidadesConst> heroList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensalidades);

        listView = (ListView) findViewById(R.id.listView);
        heroList = new ArrayList<>();

        loadHeroList();
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


                            JSONArray heroArray = obj.getJSONArray("mensalidades");


                            for (int i = 0; i < heroArray.length(); i++) {

                                JSONObject heroObject = heroArray.getJSONObject(i);


                                MensalidadesConst hero = new MensalidadesConst(heroObject.getString("id_mensalidade"), heroObject.getString("nome_crianca"),"Situação: "+heroObject.getString("situacao"),"Data de Vencimento: "+heroObject.getString("data_venc"),"Valor Mensalidade: "+heroObject.getString("valor_mensalidade"));


                                heroList.add(hero);
                            }


                            ListViewMensalidades adapter = new ListViewMensalidades(heroList, getApplicationContext());


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
