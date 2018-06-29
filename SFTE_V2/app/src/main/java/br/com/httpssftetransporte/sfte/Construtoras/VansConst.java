package br.com.httpssftetransporte.sfte.Construtoras;

/**
 * Created by Aluno on 26/06/2018.
 */

public class VansConst {
    String id_van, Modelo,Placa;

    //Nosso metodo construtor, que é usado na Activity para fazer a listagem.
    public VansConst(String id_van, String Modelo, String Placa) {
        this.id_van = id_van;
        this.Modelo = Modelo;
        this.Placa = Placa;
    }

    //Metodos Getters
    public String getId(){
        return id_van;
    }
    public String getModelo() {
        return Modelo;
    }

    public String getPlaca() {
        return Placa;
    }
}