package io.github.charlesanjos.atividade02charles;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class FinalActivity extends AppCompatActivity {

  private TextView parabens;
  private String partidaDataPath;
  private int pontosPartida;
  private String partidaNome;
  private Task<DataSnapshot> myRef;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_final);
    parabens = findViewById(R.id.parabens);
    partidaDataPath = getIntent().getStringExtra("partidaDataPath");
    pontosPartida = getIntent().getIntExtra("partidaPontos",0);
    partidaNome = getIntent().getStringExtra("partidaNome");


    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle("Fim da Partida!!!");
    }

    parabens.setText("Parabéns, " +partidaNome+ "\n");
    parabens.append("Você fez "+pontosPartida+" pontos!");
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