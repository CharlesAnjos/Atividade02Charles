package io.github.charlesanjos.atividade02charles;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import io.github.charlesanjos.atividade02charles.entidades.Pais;
import io.github.charlesanjos.atividade02charles.entidades.Partida;

public class FinalActivity extends AppCompatActivity {

  private TextView parabens;
  private ListView rankingList;
  private String partidaDataPath;
  private int pontosPartida;
  private String partidaNome;
  private DatabaseReference myRef;
  private ArrayList<Partida> partidas = new ArrayList<>();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_final);
    parabens = findViewById(R.id.parabens);
    rankingList = findViewById(R.id.ranking_list);
    partidaDataPath = getIntent().getStringExtra("partidaDataPath");
    pontosPartida = getIntent().getIntExtra("partidaPontos",0);
    partidaNome = getIntent().getStringExtra("partidaNome");


    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle("Fim da Partida!!!");
    }

    parabens.setText("Parabéns, " +partidaNome+ "\n");
    parabens.append("Você fez "+pontosPartida+" pontos!"+ "\n");

    acessarFirebase();

    AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(this, JogoAlarmReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    Calendar time = Calendar.getInstance();
    time.setTimeInMillis(System.currentTimeMillis());
    time.add(Calendar.SECOND, 5);
    alarmMgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
  }

  private void acessarFirebase() {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference partidaRef = database.getReference("partidas/").child(partidaDataPath);
    partidaRef.child("pontos").setValue(pontosPartida);
    DatabaseReference partidasRef = database.getReference("partidas/");
    partidasRef.get().addOnCompleteListener(
      task -> {
        if (!task.isSuccessful()) {
          Log.e("firebase", "Error getting data", task.getException());
        } else {
          DataSnapshot partidasSnapshot = task.getResult();
          for(DataSnapshot partidaSnapshot: partidasSnapshot.getChildren()){
            String nomePartida = null;
            int pontosPartida = 0;
            String dataPath = null;
            int totalPaises = 0;
            for(DataSnapshot dadoPartida: partidaSnapshot.getChildren()){
              if(Objects.equals(dadoPartida.getKey(), "nome")) nomePartida = dadoPartida.getValue().toString();
              if(dadoPartida.getKey().equals("pontos")) pontosPartida = Integer.parseInt(dadoPartida.getValue().toString());
              if(dadoPartida.getKey().equals("dataPath")) dataPath = dadoPartida.getValue().toString();
              if(dadoPartida.getKey().equals("totalPaises")) totalPaises = Integer.parseInt(dadoPartida.getValue().toString());
            }
            Partida partida = new Partida(nomePartida, null,dataPath);
            partida.setPontos(pontosPartida);
            partida.setTotalPaises(totalPaises);
            Log.i("partida",partida.toString());
            partidas.add(partida);
          }
          preencherLista();
        }
      }
    );
  }

  private void preencherLista() {
    ArrayList<String> pontuacoes_ranking = new ArrayList<>();
    for(Partida partida: partidas){
      pontuacoes_ranking.add(partida.getNome() +" "+partida.getPontos()+"/"+partida.getTotalPaises());
    }
    ArrayAdapter<String> rankingsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pontuacoes_ranking);
    rankingList.setAdapter(rankingsAdapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.toolbar_menu, menu);
    return true;
  }

  public void acessar_bd_local(MenuItem item) {
    Intent intent = new Intent(this,DBExploreActivity.class);
    startActivity(intent);
  }

  public void iniciar_jogo(MenuItem item) {
    Intent intent = new Intent(this,IniciarJogo.class);
    startActivity(intent);
  }
}