package io.github.charlesanjos.atividade02charles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.charlesanjos.atividade02charles.adapters.PaisAdapter;
import io.github.charlesanjos.atividade02charles.auxiliadores.Auxiliador;
import io.github.charlesanjos.atividade02charles.auxiliadores.Conexao;
import io.github.charlesanjos.atividade02charles.entidades.Pais;

public class MainActivity extends AppCompatActivity {

  private ListView listView;
  private PaisAdapter paisAdapter;
  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());
  ArrayList<Pais> paises = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    listView = findViewById(R.id.list_view);

    executor.execute(() -> {
      Conexao conexao = new Conexao();
      String URL = "https://restcountries.com/v3.1/all?fields=name,region,population,flags,numericCode";
      InputStream inputStream = conexao.obterRespostaHTTP(URL);
      Auxiliador auxiliador = new Auxiliador();
      String textoJSON = auxiliador.converter(inputStream);

      Gson gson = new Gson();

      if (textoJSON != null) {
        Type type = new TypeToken<ArrayList<Pais>>() {
        }.getType();
        paises = gson.fromJson(textoJSON, type);

        handler.post(() -> {
          paisAdapter = new PaisAdapter(this, paises);
          listView.setAdapter(paisAdapter);
        });
      }
    });
  }
}
