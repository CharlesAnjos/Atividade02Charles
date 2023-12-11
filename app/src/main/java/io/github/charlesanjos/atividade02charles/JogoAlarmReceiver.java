package io.github.charlesanjos.atividade02charles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class JogoAlarmReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    Toast.makeText(context, "Obrigado por jogar!", Toast.LENGTH_SHORT).show();
  }
}
