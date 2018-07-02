package br.com.httpssftetransporte.sfte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CadastrarFuncionarios extends AppCompatActivity {

    EditText nome_func, rg, email, data_nascimento, senha, tipo_func, cpf, data_admissao, cnh, validade_cnh, primeira_cnh, categoria_cnh, sexo, salario, telefone, tipo_telefone, cep, rua, estado, cidade, bairro, numero, hora_entrada, hora_saida;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_funcionarios);

        nome_func = findViewById(R.id.nome_funcionario);
        rg = findViewById(R.id.rg);
        email = findViewById(R.id.email);
        data_nascimento = findViewById(R.id.data_nascimento);
        senha = findViewById(R.id.senha);
        tipo_func = findViewById(R.id.tipo_funcionario);
        cpf = findViewById(R.id.cpf);
        data_admissao = findViewById(R.id.data_admicao);
        cnh = findViewById(R.id.cnh);
        validade_cnh = findViewById(R.id.validade_cnh);
        primeira_cnh = findViewById(R.id.primeira_cnh);
        categoria_cnh = findViewById(R.id.categoria_cnh);
        sexo = findViewById(R.id.sexo);
        salario = findViewById(R.id.salario);
        telefone = findViewById(R.id.telefone);
        tipo_telefone = findViewById(R.id.tipo_telefone);
        cep = findViewById(R.id.cep);
        rua = findViewById(R.id.rua);
        estado = findViewById(R.id.estado);
        cidade = findViewById(R.id.cidade);
        bairro = findViewById(R.id.bairro);
        numero = findViewById(R.id.numero);
        hora_entrada = findViewById(R.id.hr_entrada);
        hora_saida = findViewById(R.id.hr_saida);

        btn = findViewById(R.id.btn);

    }

    public void Cadastrar(View v) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("nome", nome_func.getText().toString().trim());
        params.put("rg", rg.getText().toString().trim());
        params.put("email", email.getText().toString().trim());
        params.put("data_nascimento", data_nascimento.getText().toString().trim());
        params.put("senha", senha.getText().toString().trim());
        params.put("cargo", tipo_func.getText().toString().trim());
        params.put("cpf", cpf.getText().toString().trim());
        params.put("data_admicao", data_admissao.getText().toString().trim());
        params.put("cnh", cnh.getText().toString().trim());
        params.put("validade_cnh", validade_cnh.getText().toString().trim());
        params.put("primeira_cnh", primeira_cnh.getText().toString().trim());
        params.put("categoria_cnh", categoria_cnh.getText().toString().trim());
        params.put("sexo", sexo.getText().toString().trim());
        params.put("salario", salario.getText().toString().trim());
        params.put("numero_telefone", telefone.getText().toString().trim());
        params.put("tipo_telefone", tipo_telefone.getText().toString().trim());
        params.put("cep", cep.getText().toString().trim());
        params.put("rua", rua.getText().toString().trim());
        params.put("estado", estado.getText().toString().trim());
        params.put("cidade", cidade.getText().toString().trim());
        params.put("bairro", bairro.getText().toString().trim());
        params.put("numero", numero.getText().toString().trim());
        params.put("hora_entrada", hora_entrada.getText().toString().trim());
        params.put("hora_saida", hora_saida.getText().toString().trim());

        CRUD.inserir("https://sftetransporte.com.br/Android/insert_funcionario.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Toast.makeText(CadastrarFuncionarios.this, response, Toast.LENGTH_SHORT).show();
                    JSONObject jo = new JSONObject(response);
                    String resposta = jo.getString("resposta");
                    Toast.makeText(CadastrarFuncionarios.this, resposta, Toast.LENGTH_SHORT).show();
                    Intent irTela = new Intent(CadastrarFuncionarios.this, FuncionariosActivity.class);
                    startActivity(irTela);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },params,getApplicationContext());

    }
}
