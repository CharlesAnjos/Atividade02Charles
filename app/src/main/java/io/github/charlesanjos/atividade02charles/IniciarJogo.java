package io.github.charlesanjos.atividade02charles;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Random;

import io.github.charlesanjos.atividade02charles.auxiliadores.DBManager;
import io.github.charlesanjos.atividade02charles.entidades.Pais;
import io.github.charlesanjos.atividade02charles.entidades.Partida;

public class IniciarJogo extends AppCompatActivity {
  private DBManager dbManager;
  private Partida partida = new Partida();
  private EditText nomeUsuarioEditText;
  private NumberPicker numeroPaises;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_iniciar_jogo);
    if(getSupportActionBar() != null){
      getSupportActionBar().setTitle("Iniciar Jogo!");
    }
    nomeUsuarioEditText = findViewById(R.id.nome_usuario);
    numeroPaises = findViewById(R.id.numero_paises);
    numeroPaises.setMinValue(4);
    numeroPaises.setMaxValue(250);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void jogar(View view) {
    // pegar os dados da partida
    String nomeUsuario = nomeUsuarioEditText.getText().toString();
    int numeroPaises = this.numeroPaises.getValue();

    partida.setNome(nomeUsuario);

    // acessar bd local
    dbManager = new DBManager(this);
    dbManager.open();
    int maxidpais = dbManager.fetch().getCount(), i = 0;
    Random r = new Random();
    ArrayList<Pais> paisesPartida = new ArrayList<>();

    // selecionar o numero de países desejado
    while (i < numeroPaises){
      int idpais = r.nextInt(maxidpais)+1;
      Pais paisPartida = dbManager.getPaisfromDatabase(idpais);
      paisesPartida.add(paisPartida);
      i++;
    }
    dbManager.close();

    partida.setPaises(paisesPartida);

    // conectar no banco firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String partidaDataPath = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + "/" +partida.getNome();
    partida.setDataPath(partidaDataPath);

    // armazenar dados do jogo (nome do jogador, paises selecionados) no firebase
    DatabaseReference myRef = database.getReference("partidas/" + partidaDataPath);
    myRef.setValue(partida);
    Intent partidaIntent = new Intent(this, PartidaActivity.class);
    partidaIntent.putExtra("partidaDataPath", partidaDataPath);
    startActivity(partidaIntent);
  }
}