package io.github.charlesanjos.atividade02charles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
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
          popularDB(paises, findViewById(android.R.id.content));
          listView.setAdapter(paisAdapter);
        });
      }
    });
  }

  public void gerar(View v) {
    Intent i = new Intent(MainActivity.this, DBExploreActivity.class);
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, i, PendingIntent.FLAG_IMMUTABLE);

    Bitmap bitmap = BitmapFactory.decodeResource(
        this.getResources(), R.drawable.ic_launcher_background
    );

    builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Banco de dados de Países")
        .setContentText("O armazenamento do banco de dados está pronto.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pi)
        .setLargeIcon(bitmap)
        .setStyle(new NotificationCompat.BigTextStyle().bigText("O armazenamento do banco de dados está pronto.")).build();
    nm = NotificationManagerCompat.from(MainActivity.this);
    int temPermissao = ContextCompat.checkSelfPermission(MainActivity.this, PERMISSAO);
    if(temPermissao != PackageManager.PERMISSION_GRANTED){
      ActivityCompat.requestPermissions(this, new String[]{PERMISSAO}, CODIGO_SOLICITACAO);
    } else {
      nm.notify(NOTIFICATION_ID, builder);
    }
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

  private void popularDB(ArrayList<Pais> paises, View v) {
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
    gerar(v);
    dbManager.close();
  }
}