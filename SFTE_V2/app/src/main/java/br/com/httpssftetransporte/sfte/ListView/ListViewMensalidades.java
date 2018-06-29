package br.com.httpssftetransporte.sfte.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.httpssftetransporte.sfte.Construtoras.MensalidadesConst;
import br.com.httpssftetransporte.sfte.Construtoras.VansConst;
import br.com.httpssftetransporte.sfte.R;

/**
 * Created by Aluno on 28/06/2018.
 */

public class ListViewMensalidades  extends ArrayAdapter<MensalidadesConst> {

    private List<MensalidadesConst> heroList;


    private Context mCtx;

    public ListViewMensalidades(List<MensalidadesConst> heroList, Context mCtx) {
        super(mCtx, R.layout.list_view_mensalidades, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);


        View listViewItem = inflater.inflate(R.layout.list_view_mensalidades, null, true);


        TextView txtNomePassageiro = listViewItem.findViewById(R.id.txtNomePassageiro);
        TextView txtSituacao = listViewItem.findViewById(R.id.txtSituacao);
        TextView txtValor = listViewItem.findViewById(R.id.txtValor);
        TextView txtDataVenc = listViewItem.findViewById(R.id.txtDataVenc);


        MensalidadesConst hero = heroList.get(position);

        txtNomePassageiro.setText(hero.getNomePassageiro());
        txtSituacao.setText(hero.getSituacao());
        txtValor.setText(hero.getValor());
        txtDataVenc.setText(hero.getDataVenc());



        return listViewItem;
    }

}
