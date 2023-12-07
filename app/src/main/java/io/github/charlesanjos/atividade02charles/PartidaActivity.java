package io.github.charlesanjos.atividade02charles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import io.github.charlesanjos.atividade02charles.entidades.Pais;
import io.github.charlesanjos.atividade02charles.entidades.Partida;

public class PartidaActivity extends AppCompatActivity {
  private final ArrayList<Integer> paisesUsados = new ArrayList<>();
  private ImageView imagemBandeira;
  private Button opcao1;
  private Button opcao2;
  private Button opcao3;
  private Button opcao4;
  private TextView resultado;
  private TextView resposta;
  private Button continuar;
  private int rodadasCompletas = 0;
  private ArrayList<Integer> opcoesRodada = new ArrayList<>();
  private Pais paisRodada;
  private Partida partida = null;
  private Task<DataSnapshot> myRef;
  private String partidaDataPath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_partida);
    partidaDataPath = getIntent().getStringExtra("partidaDataPath");
    imagemBandeira = findViewById(R.id.imagemBandeira);
    opcao1 = findViewById(R.id.opcao1);
    opcao2 = findViewById(R.id.opcao2);
    opcao3 = findViewById(R.id.opcao3);
    opcao4 = findViewById(R.id.opcao4);
    resultado = findViewById(R.id.resultado);
    resposta = findViewById(R.id.resposta);
    continuar = findViewById(R.id.continuar);
    if (partida == null) {
      inicializarPartida();
    } else {
      rodada();
    }
  }

  private void inicializarPartida() {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    myRef = database.getReference("partidas/").child(partidaDataPath).get().addOnCompleteListener(
        task -> {
          if (!task.isSuccessful()) {
            Log.e("firebase", "Error getting data", task.getException());
          } else {
            String partidaNome = null, partidaDataPath = null;
            ArrayList<Pais> partidaPaises = new ArrayList<>();
            DataSnapshot partidaSnapshot = task.getResult();
            Log.i("partida snapshot", Objects.requireNonNull(partidaSnapshot.getValue()).toString());
            partidaNome = (String) partidaSnapshot.child("nome").getValue();
            partidaDataPath = (String) partidaSnapshot.child("dataPath").getValue();
            for (DataSnapshot paisSnapshot : partidaSnapshot.child("paises").getChildren()) {
              AtomicReference<String> paisNome = new AtomicReference<>();
              AtomicReference<String> paisBandeira = new AtomicReference<>();
              AtomicReference<String> paisRegiao = new AtomicReference<>();
              AtomicReference<Long> paisPopulacao = new AtomicReference<>();
              paisSnapshot.getChildren().forEach(paisChildrenSnapshot -> {
                String paisChildrenSnapshotKey = Objects.requireNonNull(paisChildrenSnapshot.getKey());
                String paisChildrenSnapshotValue = Objects.requireNonNull(paisChildrenSnapshot.getValue()).toString();
                switch (paisChildrenSnapshotKey) {
                  case "bandeira":
                    paisBandeira.set((String) paisChildrenSnapshot.getValue());
                    break;
                  case "nome":
                    paisNome.set((String) paisChildrenSnapshot.getValue());
                    break;
                  case "populacao":
                    paisPopulacao.set((Long) Objects.requireNonNull(paisChildrenSnapshot.getValue()));
                    break;
                  case "regiao":
                    paisRegiao.set((String) paisChildrenSnapshot.getValue());
                    break;
                }
              });
              partidaPaises.add(new Pais(paisNome.get(), paisRegiao.get(), paisPopulacao.get(), paisBandeira.get()));
            }
            partida = new Partida(partidaNome, partidaPaises, partidaDataPath);
            rodada();
          }
        }
    );
  }

  private void rodada() {
    iniciarRodada();
    opcoesRodada = new ArrayList<>();
    if (rodadasCompletas < partida.getPaises().size()) {
      if (getSupportActionBar() != null) {
        getSupportActionBar().setTitle(
            partida.getNome() + ", você tem " + partida.getPontos() + " pontos");
        getSupportActionBar().setSubtitle(
            "País " + (rodadasCompletas + 1) + " de " + partida.getTotalPontos()
        );
      }
      //pegar pais randomicamente
      int paisCorretoIndex = getRandomWithExclusion(0, partida.getPaises().size(), paisesUsados);
      paisesUsados.add(paisCorretoIndex);
      opcoesRodada.add(paisCorretoIndex);
      Log.i("opcoes depoisd de inserir o país correto", opcoesRodada.toString());
      //pegar +3 opções randomicamente
      int i = 0;
      while (i < 3) {
        int opcaoId = getRandomWithExclusion(0, partida.getPaises().size(), opcoesRodada);
        opcoesRodada.add(opcaoId);
        i++;
      }
      Log.i("opcoes antes do shuffle", opcoesRodada.toString());
      Collections.shuffle(opcoesRodada, new Random());
      Log.i("opcoes depois do shuffle", opcoesRodada.toString());
      paisRodada = partida.getPaises().get(paisCorretoIndex);
      Picasso.get()
          .load(paisRodada.getBandeira())
          .centerInside()
          .fit()
          .into(imagemBandeira);
      Pais paisOpcao1 = partida.getPaises().get(opcoesRodada.get(0));
      opcao1.setText(paisOpcao1.getNome());
      Pais paisOpcao2 = partida.getPaises().get(opcoesRodada.get(1));
      opcao2.setText(paisOpcao2.getNome());
      Pais paisOpcao3 = partida.getPaises().get(opcoesRodada.get(2));
      opcao3.setText(paisOpcao3.getNome());
      Pais paisOpcao4 = partida.getPaises().get(opcoesRodada.get(3));
      opcao4.setText(paisOpcao4.getNome());

      opcao1.setOnClickListener(v -> {
        checarResposta(opcoesRodada.get(0), paisCorretoIndex);
      });

      opcao2.setOnClickListener(v -> {
        checarResposta(opcoesRodada.get(1), paisCorretoIndex);
      });

      opcao3.setOnClickListener(v -> {
        checarResposta(opcoesRodada.get(2), paisCorretoIndex);
      });

      opcao4.setOnClickListener(v -> {
        checarResposta(opcoesRodada.get(3), paisCorretoIndex);
      });

      continuar.setOnClickListener(v -> {
        Log.i("continuar", String.valueOf(rodadasCompletas));
        Log.i("continuar", String.valueOf(partida.getPaises().size()));
        if(rodadasCompletas < partida.getPaises().size()){
          rodada();
        } else {
          encerrarPartida();
        }
      });
    }
  }

  private void encerrarPartida() {
    Intent finalIntent = new Intent(this, FinalActivity.class);
    finalIntent.putExtra("partidaDataPath", partidaDataPath);
    finalIntent.putExtra("partidaPontos", partida.getPontos());
    finalIntent.putExtra("partidaNome", partida.getNome());
    startActivity(finalIntent);
  }

  @SuppressLint("SetTextI18n")
  void checarResposta(int opcao, int paisCorreto) {
    encerrarRodada();
    if (opcao == paisCorreto) {
      partida.setPontos(partida.getPontos() + 1);
      resultado.setText("✅✅✅✅");
      resultado.setBackgroundColor(Color.GREEN);
      resposta.setText("ISSO!");
    } else {
      resultado.setText("❌❌❌❌");
      resultado.setBackgroundColor(Color.RED);
      resposta.setText("Não, o correto é "+ partida.getPaises().get(paisCorreto).getNome() +"!");
    }
    rodadasCompletas++;
  }

  void encerrarRodada() {
    opcao1.setEnabled(false);
    opcao2.setEnabled(false);
    opcao3.setEnabled(false);
    opcao4.setEnabled(false);
    resultado.setVisibility(View.VISIBLE);
    resultado.setEnabled(true);
    resposta.setVisibility(View.VISIBLE);
    resposta.setEnabled(true);
    continuar.setVisibility(View.VISIBLE);
    continuar.setEnabled(true);
  }

  void iniciarRodada() {
    opcao1.setEnabled(true);
    opcao2.setEnabled(true);
    opcao3.setEnabled(true);
    opcao4.setEnabled(true);
    resultado.setVisibility(View.INVISIBLE);
    resultado.setEnabled(false);
    resposta.setVisibility(View.INVISIBLE);
    resposta.setEnabled(false);
    continuar.setVisibility(View.INVISIBLE);
    continuar.setEnabled(false);
  }

  public int getRandomWithExclusion(int start, int end, ArrayList<Integer> exclude) {
    int bound = end - start;
    int random = new Random().nextInt(bound);
    boolean isRepeated = false;
      for (int ex : exclude) {
        if (random == ex) {
          isRepeated = true;
        }
    }
    if(isRepeated){
      random = getRandomWithExclusion(start, end, exclude);
    }
    return random;
  }
}