package br.com.httpssftetransporte.sfte.Construtoras;

/**
 * Created by Aluno on 28/06/2018.
 */

public class EscolaConst {

    String id, NomeEscola, Telefone;

    public EscolaConst(String id, String NomeEscola, String Telefone) {
        this.NomeEscola = NomeEscola;
        this.Telefone = Telefone;
        this.id= id;
    }
    public String getId(){
        return id;
    }
    public String getNomeEscola() {
        return NomeEscola;
    }

    public String getTelefone() {
        return Telefone;
    }
}
