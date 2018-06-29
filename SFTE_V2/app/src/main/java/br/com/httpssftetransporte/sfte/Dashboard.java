package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

    }
    public void Empresas(View v){
        Intent empresas = new Intent(Dashboard.this,EmpresasActivity.class);
        startActivity(empresas);
    }
    public void Passageiros(View v){
        Intent passageiros = new Intent(Dashboard.this,CriancasActivity.class);
        startActivity(passageiros);
    }
    public void Funcionarios(View v){
        Intent funcionarios = new Intent(Dashboard.this,FuncionariosActivity.class);
        startActivity(funcionarios);
    }
    public void Van(View v){
        Intent van = new Intent(Dashboard.this,VansActivity.class);
        startActivity(van);
    }
    public void Escola(View v){
        Intent escola = new Intent(Dashboard.this,EscolaActivity.class);
        startActivity(escola);
    }
    public void Mensalidades(View v){
        Intent mensalidades = new Intent(Dashboard.this,MensalidadesActivity.class);
        startActivity(mensalidades);
    }
}
