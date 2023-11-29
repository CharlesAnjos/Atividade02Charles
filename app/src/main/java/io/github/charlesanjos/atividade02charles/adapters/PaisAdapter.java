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
  private final LayoutInflater inflater;
  private final ArrayList<Pais> paises;

  public PaisAdapter(Context context,
                     ArrayList<Pais> paises) {

    this.paises = paises;

    inflater = LayoutInflater.from(context);
  }

  public int getCount() {

    return paises.size();
  }


  public Pais getItem(int position) {

    return paises.get(position);
  }


  public long getItemId(int position) {

    return position;
  }

  @SuppressLint({"ViewHolder", "InflateParams"})
  public View getView(int position, View view, ViewGroup parent) {

    Pais pais = paises.get(position);

    view = inflater.inflate(R.layout.item_lista, null);

    TextView nome = view.findViewById(R.id.nome);
    nome.setText(pais.getNome());
    TextView regiao = view.findViewById(R.id.regiao);
    regiao.setText(pais.getRegiao());
    TextView populacao = view.findViewById(R.id.populacao);
    populacao.setText(String.valueOf(pais.getPopulacao()));

    System.out.println(pais.getBandeira());
    ImageView bandeira = view.findViewById(R.id.bandeira);
    //bandeira.setImageBitmap();

    return view;
  }
}
