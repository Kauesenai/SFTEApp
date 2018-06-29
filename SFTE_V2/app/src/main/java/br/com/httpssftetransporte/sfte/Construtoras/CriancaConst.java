package br.com.httpssftetransporte.sfte.Construtoras;

public class CriancaConst {
    String id,Nome,Cpf;

    public CriancaConst(String id, String Nome, String Cpf) {
        this.id = id;
        this.Nome = Nome;
        this.Cpf= Cpf;
    }

    //Metodos Getters
    public String getId(){
        return id;
    }
    public String getNome() {
        return Nome;
    }

    public String getCpf() {
        return Cpf;
    }
}
