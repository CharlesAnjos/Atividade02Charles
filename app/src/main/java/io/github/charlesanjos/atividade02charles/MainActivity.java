package io.github.charlesanjos.atividade02charles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.charlesanjos.atividade02charles.auxiliadores.Auxiliador;
import io.github.charlesanjos.atividade02charles.auxiliadores.Conexao;
import io.github.charlesanjos.atividade02charles.entidades.Pais;

public class MainActivity extends AppCompatActivity {
  private StringBuilder builder = null;
  ExecutorService executor = Executors.newSingleThreadExecutor();

  private TextView textPaises;
  Handler handler = new Handler(Looper.getMainLooper());

  List<Pais> paises = null;

  public List<Pais> getPaises() {
    return paises;
  }

  public void setPaises(List<Pais> paises) {
    this.paises = paises;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textPaises = findViewById(R.id.textPaises);
    textPaises.setMovementMethod(new ScrollingMovementMethod());

    executor.execute(() -> {
      Conexao conexao = new Conexao();
      String URL = "https://restcountries.com/v2/all?fields=name,region,population,flag,numericCode";
      InputStream inputStream = conexao.obterRespostaHTTP(URL);
      Auxiliador auxiliador = new Auxiliador();
      String textoJSON = auxiliador.converter(inputStream);

      Gson gson = new Gson();
      builder = new StringBuilder();

      if (textoJSON != null) {
        Type type = new TypeToken<List<Pais>>() {}.getType();
        paises = gson.fromJson(textoJSON,type);
        builder.append("Países:").append("\n\n");
        for (int i = 0; i < paises.size(); i++) {
          builder
              .append(i+1)
              .append(" - ")
              .append(paises.get(i).getNome())
              .append(" - ")
              .append(paises.get(i).getPopulacao())
              .append(" - ")
              .append(paises.get(i).getRegiao())
              .append("\n\n");
        }
      } else {
        System.out.println("deu ruim");
      }

      handler.post(() -> textPaises.setText(builder.toString()));
    });
  }
}