package br.com.johne.pedrapapeltesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import br.com.johne.pedrapapeltesoura.databinding.ActivityEditarBinding;

public class EditarActivity extends AppCompatActivity {

    private ActivityEditarBinding activityEditarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activityEditarBinding = ActivityEditarBinding.inflate(getLayoutInflater());
        setContentView(activityEditarBinding.getRoot());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            int iparticipantes = extras.getInt(MainActivity.PARTICIPANTES);
            int ijogadas = extras.getInt(MainActivity.JOGADAS);

            if(iparticipantes == 3){
                activityEditarBinding.participantesNrRb.check(activityEditarBinding.tresjogadoresRb.getId());
            }

            if(ijogadas == 3){
                activityEditarBinding.partidasRg.check(activityEditarBinding.tresPartidasRb.getId());
            }else if(ijogadas == 5){
                activityEditarBinding.partidasRg.check(activityEditarBinding.cincoPartidasRb.getId());
            }


        }
        setTitle("Configurações");

        activityEditarBinding.salvarBt.setOnClickListener(click -> {
            Intent retornoIntent = new Intent();
            retornoIntent.putExtra(MainActivity.PARTICIPANTES, activityEditarBinding.doisjogadoresRb.isChecked() ? 2 : 3);
            retornoIntent.putExtra(MainActivity.JOGADAS, activityEditarBinding.umaPartidaRb.isChecked() ? 1 : (activityEditarBinding.tresPartidasRb.isChecked() ? 3 : 5));
            setResult(RESULT_OK, retornoIntent);
            finish();
        });

    }
}