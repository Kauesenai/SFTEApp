package br.com.httpssftetransporte.sfte.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.httpssftetransporte.sfte.Construtoras.VansConst;
import br.com.httpssftetransporte.sfte.R;

/**
 * Created by Aluno on 26/06/2018.
 */

public class ListViewVans extends ArrayAdapter<VansConst> {
    private List<VansConst> heroList;


    private Context mCtx;


    public ListViewVans(List<VansConst> heroList, Context mCtx) {
        super(mCtx, R.layout.list_view_vans, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);


        View listViewItem = inflater.inflate(R.layout.list_view_vans, null, true);


        TextView txtPlaca = listViewItem.findViewById(R.id.txtPlaca);
        TextView txtModelo = listViewItem.findViewById(R.id.txtModelo);

        VansConst hero = heroList.get(position);

        txtModelo.setText(hero.getModelo());
        txtPlaca.setText(hero.getPlaca());


        return listViewItem;
    }
}
