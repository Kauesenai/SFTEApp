package br.com.httpssftetransporte.sfte.Construtoras;

public class FuncionariosConst {
    String id_func, Nome,Cargo;

    //Nosso metodo construtor, que é usado na Activity para fazer a listagem.
    public FuncionariosConst(String id_func, String Nome, String Cargo) {
        this.id_func = id_func;
        this.Nome = Nome;
        this.Cargo = Cargo;
    }

    //Metodos Getters
    public String getId(){
        return this.id_func;
    }
    public String getNome() {
        return Nome;
    }

    public String getCargo() {
        return Cargo;
    }
}