package io.github.charlesanjos.atividade02charles.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.github.charlesanjos.atividade02charles.R;
import io.github.charlesanjos.atividade02charles.auxiliadores.DBManager;
import io.github.charlesanjos.atividade02charles.auxiliadores.DatabaseHelper;
import io.github.charlesanjos.atividade02charles.entidades.Pais;

public class CursorAdapter extends BaseAdapter {
  private final LayoutInflater inflater;
  private final Cursor cursor;

  public CursorAdapter(Context context,
                     Cursor cursor) {

    this.cursor = cursor;

    inflater = LayoutInflater.from(context);
  }

  public int getCount() {
    return cursor.getCount();
  }

  @SuppressLint("Range")
  @Override
  public Pais getItem(int position) {
    Pais pais;
    cursor.moveToPosition(position);
    String nome = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NOME));
    String regiao = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REGIAO));
    String populacao = cursor.getString(cursor.getColumnIndex(DatabaseHelper.POPULACAO));
    String bandeira = cursor.getString(cursor.getColumnIndex(DatabaseHelper.BANDEIRA));
    pais = new Pais(nome,regiao,Integer.parseInt(populacao),bandeira);
    return pais;
  }

  @SuppressLint("Range")
  @Override
  public long getItemId(int position) {
    cursor.moveToPosition(position);
    String paisId = cursor.getString(cursor.getColumnIndex(DatabaseHelper._ID));
    return Integer.parseInt(paisId);
  }

  @SuppressLint({"ViewHolder", "InflateParams"})
  public View getView(int position, View view, ViewGroup parent) {

    Pais pais = getItem(position);

    view = inflater.inflate(R.layout.item_lista, null);

    TextView nome = view.findViewById(R.id.nome);
    nome.setText(pais.getNome());
    TextView regiao = view.findViewById(R.id.regiao);
    regiao.setText(pais.getRegiao());
    TextView populacao = view.findViewById(R.id.populacao);
    populacao.setText(String.valueOf(pais.getPopulacao()));

    ImageView bandeira = view.findViewById(R.id.bandeira);
    Picasso.get()
        .load(pais.getBandeira())
        .fit()
        .centerInside()
        .into(bandeira);

    return view;
  }
}
