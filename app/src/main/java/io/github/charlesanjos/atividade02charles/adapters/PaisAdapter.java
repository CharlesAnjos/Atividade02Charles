package io.github.charlesanjos.atividade02charles.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.charlesanjos.atividade02charles.R;
import io.github.charlesanjos.atividade02charles.entidades.Pais;

public class PaisAdapter extends BaseAdapter {
  private LayoutInflater inflater;
  private ArrayList<Pais> paises;

  public PaisAdapter(Context context,
                    ArrayList<Pais> paises)
  {

    this.paises = paises;

    inflater = LayoutInflater.from(context);
  }

  public int getCount()
  {

    return paises.size();
  }


  public Pais getItem(int position)
  {

    return paises.get(position);
  }


  public long getItemId(int position)
  {

    return position;
  }

  @SuppressLint("ViewHolder")
  public View getView(int position, View view, ViewGroup parent)
  {

    Pais pais = paises.get(position);

    view = inflater.inflate(R.layout.item_lista, null);

    TextView nome = (TextView) view.findViewById(R.id.nome);
    nome.setText(pais.getNome().toString());
    TextView regiao = (TextView) view.findViewById(R.id.regiao);
    regiao.setText(pais.getRegiao().toString());
    TextView populacao = (TextView) view.findViewById(R.id.populacao);
    populacao.setText(String.valueOf(pais.getPopulacao()));

    return view;
  }
}
