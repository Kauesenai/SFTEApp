package br.com.httpssftetransporte.sfte.Construtoras;

/**
 * Created by Aluno on 28/06/2018.
 */

public class MensalidadesConst {
    String id_mensalidade, NomePassageiro,Situacao,Valor,DataVenc;

    public MensalidadesConst(String id_mensalidade, String NomePassageiro, String Situacao, String Valor, String DataVenc){
        this.id_mensalidade = id_mensalidade;
        this.NomePassageiro = NomePassageiro;
        this.Situacao = Situacao;
        this.Valor = Valor;
        this.DataVenc = DataVenc;
    }
    public String getId_mensalidade(){
        return this.id_mensalidade;
    }

    public String getNomePassageiro() {
        return NomePassageiro;
    }

    public String getSituacao() {
        return Situacao;
    }

    public String getDataVenc() {
        return DataVenc;
    }

    public String getValor() {
        return Valor;
    }
}
