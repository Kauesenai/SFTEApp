package br.com.httpssftetransporte.sfte.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.httpssftetransporte.sfte.Construtoras.EmpresasConst;
import br.com.httpssftetransporte.sfte.Construtoras.EscolaConst;
import br.com.httpssftetransporte.sfte.R;

/**
 * Created by Aluno on 28/06/2018.
 */

public class ListViewEscolas  extends ArrayAdapter<EscolaConst> {
    private List<EscolaConst> heroList;
    private List<EscolaConst> orig;

    private Context mCtx;


    public ListViewEscolas(List<EscolaConst> heroList, Context mCtx) {
        super(mCtx, R.layout.list_view_escolas, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<EscolaConst> results = new ArrayList<EscolaConst>();
                if (orig == null) {//verificando se a
                    orig = heroList;
                }
                if (constraint != null) {
                    constraint = constraint.toString().toLowerCase();
                    //verifica se temos algo na lista original
                    if (orig != null && orig.size() > 0) {
                        //para cada obj do UsuarioConst dentro lista
                        for (final EscolaConst g : orig) {//Ocorerá para cada usuario que está na lista
                            /*
                             * Transformando o nome, sobrenome e email do usuario em minuscula
                             * e depois conferindo se ele contem na nossa restrição (constraint)
                             * */
                            if ((g.getNomeEscola().toLowerCase().contains(constraint.toString())) ||
                                    (g.getTelefone().toLowerCase().contains(constraint.toString())))  {
                                results.add(g);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                heroList = (ArrayList<EscolaConst>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);


        View listViewItem = inflater.inflate(R.layout.list_view_escolas, null, true);


        TextView txtNomeEscola = listViewItem.findViewById(R.id.txtNomeEscola);
        TextView txtTelefone = listViewItem.findViewById(R.id.txtTelefone);

        EscolaConst hero = heroList.get(position);

        txtNomeEscola.setText(hero.getNomeEscola());
        txtTelefone.setText(hero.getTelefone());


        return listViewItem;
    }
}
