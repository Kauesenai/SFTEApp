package br.com.httpssftetransporte.sfte.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.httpssftetransporte.sfte.Construtoras.CriancaConst;
import br.com.httpssftetransporte.sfte.R;

public class ListViewCriancas extends ArrayAdapter<CriancaConst> {
    private List<CriancaConst> heroList;
    private List<CriancaConst> orig;

    private Context mCtx;

    public ListViewCriancas(List<CriancaConst> heroList, Context mCtx) {
        super(mCtx, R.layout.list_view_criancas, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);


        View listViewItem = inflater.inflate(R.layout.list_view_criancas, null, true);



        TextView txtCpf = listViewItem.findViewById(R.id.txtCpf);
        TextView txtNome = listViewItem.findViewById(R.id.txtNome);

        CriancaConst hero = heroList.get(position);

        txtNome.setText(hero.getNome());
        txtCpf.setText(hero.getCpf());


        return listViewItem;
    }
}
