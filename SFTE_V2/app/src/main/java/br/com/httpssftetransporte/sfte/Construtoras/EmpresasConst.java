package br.com.httpssftetransporte.sfte.Construtoras;

public class EmpresasConst {
    String id,RazaoSocial,Telefone;

    //Nosso metodo construtor, que é usado na mainActivity para fazer a listagem.
    public EmpresasConst(String id, String RazaoSocial, String Telefone) {
        this.RazaoSocial = RazaoSocial;
        this.Telefone = Telefone;
        this.id= id;
    }

    //Metodos Getters
    public String getId(){
        return id;
    }
    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public String getTelefone() {
        return Telefone;
    }
}
