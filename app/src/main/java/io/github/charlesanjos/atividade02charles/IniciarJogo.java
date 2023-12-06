package io.github.charlesanjos.atividade02charles;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Random;

import io.github.charlesanjos.atividade02charles.auxiliadores.DBManager;
import io.github.charlesanjos.atividade02charles.entidades.Pais;
import io.github.charlesanjos.atividade02charles.entidades.Partida;

public class IniciarJogo extends AppCompatActivity {
  private DBManager dbManager;
  private Partida partida = new Partida();
  private EditText nomeUsuarioEditText;
  private EditText numeroPaisesEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_iniciar_jogo);
    if(getSupportActionBar() != null){
      getSupportActionBar().setTitle("Iniciar Jogo!");
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void jogar(View view) {
    // pegar os dados da partida
    nomeUsuarioEditText = findViewById(R.id.nome_usuario);
    numeroPaisesEditText = findViewById(R.id.numero_paises);
    String nomeUsuario = nomeUsuarioEditText.getText().toString();
    int numeroPaises = Integer.parseInt(numeroPaisesEditText.getText().toString());

    partida.setNome(nomeUsuario);

    // acessar bd local
    dbManager = new DBManager(this);
    dbManager.open();
    int maxidpais = dbManager.fetch().getCount(), i = 0;
    Random r = new Random();
    ArrayList<Pais> paisesPartida = new ArrayList<>();

    // selecionar o numero de países desejado
    while (i < numeroPaises){
      int idpais = r.nextInt(maxidpais)+12501;
      Pais paisPartida = dbManager.getPaisfromDatabase(idpais);
      paisesPartida.add(paisPartida);
      i++;
    }

    partida.setPaises(paisesPartida);

    // conectar no banco firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String partidaDataPath = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + "/" +partida.getNome();
    partida.setDataPath(partidaDataPath);

    // armazenar dados do jogo (nome do jogador, paises selecionados) no firebase
    DatabaseReference myRef = database.getReference("partidas/" + partidaDataPath);
    myRef.setValue(partida);
  }
}