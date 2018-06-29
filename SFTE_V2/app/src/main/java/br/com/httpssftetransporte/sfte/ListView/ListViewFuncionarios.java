package br.com.httpssftetransporte.sfte.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.httpssftetransporte.sfte.Construtoras.FuncionariosConst;
import br.com.httpssftetransporte.sfte.R;

public class ListViewFuncionarios extends ArrayAdapter<FuncionariosConst> {
    private List<FuncionariosConst> heroList;


    private Context mCtx;


    public ListViewFuncionarios(List<FuncionariosConst> heroList, Context mCtx) {
        super(mCtx, R.layout.list_view_funcionarios, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);


        View listViewItem = inflater.inflate(R.layout.list_view_funcionarios, null, true);


        TextView txtNome = listViewItem.findViewById(R.id.txtNome);
        TextView txtCargo = listViewItem.findViewById(R.id.txtCargo);

        FuncionariosConst hero = heroList.get(position);

        txtNome.setText(hero.getNome());
        txtCargo.setText(hero.getCargo());


        return listViewItem;
    }
}
