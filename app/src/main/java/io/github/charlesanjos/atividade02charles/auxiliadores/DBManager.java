package io.github.charlesanjos.atividade02charles.auxiliadores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

  private DatabaseHelper dbHelper;

  private Context context;

  private SQLiteDatabase database;

  public DBManager(Context c) {
    context = c;
  }

  public DBManager open() throws SQLException {
    dbHelper = new DatabaseHelper(context);
    database = dbHelper.getWritableDatabase();
    return this;
  }

  public void close() {
    dbHelper.close();
  }

  public void insert(String nome, String regiao, String populacao, String bandeira) {
    ContentValues contentValue = new ContentValues();
    contentValue.put(DatabaseHelper.NOME, nome);
    contentValue.put(DatabaseHelper.REGIAO, regiao);
    contentValue.put(DatabaseHelper.POPULACAO, populacao);
    contentValue.put(DatabaseHelper.BANDEIRA, bandeira);
    database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
  }

  public Cursor fetch() {
    String[] columns = new String[] {
        DatabaseHelper._ID,
        DatabaseHelper.NOME,
        DatabaseHelper.REGIAO,
        DatabaseHelper.POPULACAO,
        DatabaseHelper.BANDEIRA
    };
    Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
    }
    return cursor;
  }

  public int update(long _id, String nome, String regiao, String populacao, String bandeira) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(DatabaseHelper.NOME, nome);
    contentValues.put(DatabaseHelper.REGIAO, regiao);
    contentValues.put(DatabaseHelper.POPULACAO, populacao);
    contentValues.put(DatabaseHelper.BANDEIRA, bandeira);
    int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
    return i;
  }

  public void delete(long _id) {
    database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
  }
}