package io.github.charlesanjos.atividade02charles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import io.github.charlesanjos.atividade02charles.adapters.CursorAdapter;
import io.github.charlesanjos.atividade02charles.adapters.PaisAdapter;
import io.github.charlesanjos.atividade02charles.auxiliadores.DBManager;
import io.github.charlesanjos.atividade02charles.auxiliadores.DatabaseHelper;
import io.github.charlesanjos.atividade02charles.entidades.Pais;

public class DBExploreActivity extends AppCompatActivity {
  private DBManager dbManager;
  private ListView listView;
  private CursorAdapter cursorAdapter;
  private ArrayList<Pais> paises;

  final String[] from = new String[] {
      DatabaseHelper.NOME,
      DatabaseHelper.REGIAO,
      DatabaseHelper.POPULACAO,
      DatabaseHelper.BANDEIRA
  };

  final int[] to = new int[] {
      R.id.nome,
      R.id.regiao,
      R.id.populacao,
      R.id.bandeira
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dbexplore);
    if(getSupportActionBar() != null){
      getSupportActionBar().setTitle("Países BD");
      getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }
    dbManager = new DBManager(this);
    dbManager.open();
    Cursor cursor = dbManager.fetch();
    cursor.moveToFirst();

    listView = (ListView) findViewById(R.id.list_view);
    listView.setEmptyView(findViewById(R.id.empty));

    cursorAdapter = new CursorAdapter(this, cursor);
    listView.setAdapter(cursorAdapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.toolbar_menu, menu);
    menu.getItem(0).setTitle("Consultar API");
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    //identificar a ação de voltar a tela
    if (item.getItemId() == android.R.id.home) {//encerra a activity
      Intent intent = new Intent(this,MainActivity.class);
      startActivity(intent);
    }

    return super.onOptionsItemSelected( item );
  }

  public void acessar_bd_local(MenuItem item) {
    Intent intent = new Intent(this,MainActivity.class);
    startActivity(intent);
  }

  public void iniciar_jogo(MenuItem item) {
    Intent intent = new Intent(this,IniciarJogo.class);
    startActivity(intent);
  }
}