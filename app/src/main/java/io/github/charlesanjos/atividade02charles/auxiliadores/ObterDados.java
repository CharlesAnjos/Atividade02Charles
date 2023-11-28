package io.github.charlesanjos.atividade02charles.auxiliadores;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import io.github.charlesanjos.atividade02charles.entidades.Pais;

public class ObterDados extends AsyncTask<Void,Void,Void> {
  StringBuilder builder;

  @SuppressLint("StaticFieldLeak")
  TextView textField;

  @SuppressLint("StaticFieldLeak")
  FragmentActivity fragment;

  public ObterDados(StringBuilder builder, TextView textField, FragmentActivity fragment){
    this.builder = builder;
    this.textField = textField;
    this.fragment = fragment;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    System.out.println("iniciando download");
  }

  @Override
  protected Void doInBackground(Void... voids) {
    System.out.println("background");
    Conexao conexao = new Conexao();
    String URL = "https://restcountries.com/v2/all?fields=name,region,population,flag";
    InputStream inputStream = conexao.obterRespostaHTTP(URL);
    Auxiliador auxiliador = new Auxiliador();
    String textoJSON = auxiliador.converter(inputStream);
    Log.i("JSON","doInBackground "+textoJSON);

    Gson gson = new Gson();

    if(textoJSON != null){
      Type type = new TypeToken<List<Pais>>(){}.getType();
      List<Pais> paises = gson.fromJson(textoJSON,type);
    }
    else {
      fragment.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          System.out.println("deu ruim");
        }
      });
    } //else
    return null;
  }//doInBackground

  @Override
  protected void onPostExecute(Void unused) {
    super.onPostExecute(unused);
    textField.setText(builder.toString());
  }
}
