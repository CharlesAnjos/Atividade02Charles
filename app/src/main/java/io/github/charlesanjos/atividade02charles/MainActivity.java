package io.github.charlesanjos.atividade02charles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
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
import io.github.charlesanjos.atividade02charles.auxiliadores.DBManager;
import io.github.charlesanjos.atividade02charles.entidades.Pais;

public class MainActivity extends AppCompatActivity {

  private ListView listView;
  private PaisAdapter paisAdapter;
  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());
  ArrayList<Pais> paises = null;
  private static final String CHANNEL_ID = "3";
  private final int NOTIFICATION_ID = 1;
  private static final int CODIGO_SOLICITACAO = 11;
  private Notification builder;
  private NotificationManagerCompat nm;
  private static final String PERMISSAO =
      Manifest.permission.POST_NOTIFICATIONS;

  private DBManager dbManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    criarCanalNotificacao();

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
          popularDB(paises);
          listView.setAdapter(paisAdapter);
        });
      }
    });
  }

  private void criarCanalNotificacao() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence nome = "canal 2";
      String descricao = "descrição do canal 2";
      int importancia = NotificationManager.IMPORTANCE_DEFAULT;
      NotificationChannel canal = new NotificationChannel(CHANNEL_ID
          , nome, importancia);
      canal.setDescription(descricao);


      NotificationManager nm = getSystemService(NotificationManager.class);
      nm.createNotificationChannel(canal);
    }//if
  }//method

  private void popularDB(ArrayList<Pais> paises) {
    dbManager = new DBManager(this);
    dbManager.open();
    for(Pais pais: paises){
      dbManager.insert(
          pais.getNome(),
          pais.getRegiao(),
          String.valueOf(pais.getPopulacao()),
          pais.getBandeira()
      );
    }
    dbManager.close();
  }
}