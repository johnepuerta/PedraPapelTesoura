package br.com.johne.pedrapapeltesoura;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Random;

import br.com.johne.pedrapapeltesoura.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    ActivityResultLauncher<Intent> editarActivityResultLauncher;

    public static final String PARTICIPANTES = "PARTICIPANTES";
    public static final String JOGADAS = "JOGADAS";
    private int piparticipantes = 2;
    private int pijogadas = 1;
    private int ipartida = 0;
    private int ijogador1 = 0;
    private int ijogador2 = 0;
    private int ijogador3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        editarActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if(result.getResultCode() == RESULT_OK) {
                            piparticipantes = result.getData().getIntExtra(PARTICIPANTES, 2);
                            pijogadas = result.getData().getIntExtra(JOGADAS, 1);
                            activityMainBinding.jogadoresTx.setText("Participantes: " + piparticipantes);
                            activityMainBinding.jogadasTx.setText("Jogadas: " + pijogadas);
                            habilitacoes();
                        }
                    }
                }
        );
        habilitacoes();

    }

    private void habilitacoes(){

        activityMainBinding.jog1.setVisibility(pijogadas > 1 ? View.VISIBLE : View.GONE);
        activityMainBinding.jog1Vencedor.setVisibility(pijogadas > 1 ? View.VISIBLE : View.GONE);
        activityMainBinding.jog2.setVisibility(pijogadas > 1 ? View.VISIBLE : View.GONE);
        activityMainBinding.jog2Vencedor.setVisibility(pijogadas > 1 ? View.VISIBLE : View.GONE);
        activityMainBinding.jog3.setVisibility(pijogadas > 1 ? View.VISIBLE : View.GONE);
        activityMainBinding.jog3Vencedor.setVisibility(pijogadas > 1 ? View.VISIBLE : View.GONE);
        activityMainBinding.jog4.setVisibility(pijogadas > 3 ? View.VISIBLE : View.GONE);
        activityMainBinding.jog4Vencedor.setVisibility(pijogadas > 3 ? View.VISIBLE : View.GONE);
        activityMainBinding.jog5.setVisibility(pijogadas > 3 ? View.VISIBLE : View.GONE);
        activityMainBinding.jog5Vencedor.setVisibility(pijogadas > 3 ? View.VISIBLE : View.GONE);
        limpar();

    }

    public void jogar(View view){
        ipartida++;
        if(ipartida > pijogadas){
            limpar();
            ipartida = 1;
            ijogador1 = ijogador2 = ijogador3 = 0;
        }
        activityMainBinding.jogador1.setText("");
        activityMainBinding.jogador2.setText("");
        activityMainBinding.jogador3.setText("");

        JogadasEnum jogador1 = JogadasEnum.fromString((String) view.getContentDescription());
        JogadasEnum jogador2 = JogadasEnum.fromValue(new Random().nextInt(3));
        JogadasEnum jogador3 = JogadasEnum.PERDEDOR;
        if(piparticipantes == 3) {
            jogador3 = JogadasEnum.fromValue(new Random().nextInt(3));
        }
        activityMainBinding.jogador1.setText(mantaDescribe(JogadasEnum.PEDRA,   jogador1, jogador2, jogador3));
        activityMainBinding.jogador2.setText(mantaDescribe(JogadasEnum.PAPEL,   jogador1, jogador2, jogador3));
        activityMainBinding.jogador3.setText(mantaDescribe(JogadasEnum.TESOURA, jogador1, jogador2, jogador3));

        String svencedorPartida = "";

        if(JogadasEnum.isVenceJogada(jogador1, jogador2) && JogadasEnum.isVenceJogada(jogador1, jogador3)){
            svencedorPartida = "Você venceu!!!";
            ijogador1++;
        }else if(JogadasEnum.isVenceJogada(jogador2, jogador1) && JogadasEnum.isVenceJogada(jogador2, jogador3)){
            svencedorPartida = "Jogador 2 venceu!!!";
            ijogador2++;
        }else if(JogadasEnum.isVenceJogada(jogador3, jogador1) && JogadasEnum.isVenceJogada(jogador2, jogador2)){
            svencedorPartida = "Jogador 3 venceu!!!";
            ijogador3++;
        }else {
            svencedorPartida = "Empate!!!";
        }

        switch (ipartida){
            case 1:
                activityMainBinding.jog1Vencedor.setText(svencedorPartida);
                break;
            case 2:
                activityMainBinding.jog2Vencedor.setText(svencedorPartida);
                break;
            case 3:
                activityMainBinding.jog3Vencedor.setText(svencedorPartida);
                break;
            case 4:
                activityMainBinding.jog4Vencedor.setText(svencedorPartida);
                break;
            case 5:
                activityMainBinding.jog5Vencedor.setText(svencedorPartida);
                break;
        }

        if(ipartida == pijogadas ||
           ijogador1 > (pijogadas / 2) ||
           ijogador2 > (pijogadas / 2) ||
           ijogador3 > (pijogadas / 2)){
            ipartida = pijogadas;
            if(ijogador1 > ijogador2 && ijogador1 > ijogador3){
                activityMainBinding.vencedor.setText("Você venceu!!!");
            }else if(ijogador2 > ijogador1 && ijogador2 > ijogador3){
                activityMainBinding.vencedor.setText("Jogador 2 venceu!!!");
            }else if(ijogador3 > ijogador1 && ijogador3 > ijogador2){
                activityMainBinding.vencedor.setText("Jogador 3 venceu!!!");
            }else {
                activityMainBinding.vencedor.setText("Empate...");
            }
            if(pijogadas > 1) {
                activityMainBinding.fim.setVisibility(View.VISIBLE);
            }

        }


    }

    private void limpar() {
        activityMainBinding.jog1Vencedor.setText("");
        activityMainBinding.jog2Vencedor.setText("");
        activityMainBinding.jog3Vencedor.setText("");
        activityMainBinding.jog4Vencedor.setText("");
        activityMainBinding.jog5Vencedor.setText("");
        activityMainBinding.vencedor.setText("");
        activityMainBinding.fim.setVisibility(View.GONE);
        activityMainBinding.jogador1.setText("");
        activityMainBinding.jogador2.setText("");
        activityMainBinding.jogador3.setText("");
        ipartida = ijogador1 = ijogador2 = ijogador3 = 0;
    }

    private String mantaDescribe(JogadasEnum jogada, JogadasEnum j1, JogadasEnum j2, JogadasEnum j3) {
        String descricao = "";
        if(jogada.equals(j1)){
            descricao += "Você";
        }
        if(jogada.equals(j2)){
            descricao += descricao.length() > 0 ? "\nJogador 2" : "Jogador 2";
        }
        if(jogada.equals(j3)){
            descricao += descricao.length() > 0 ? "\nJogador 3" : "Jogador 3";
        }
        return descricao;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.editarMenu ){
            Intent editarIntent = new Intent(this, EditarActivity.class);
            editarIntent.putExtra(MainActivity.PARTICIPANTES, piparticipantes);
            editarIntent.putExtra(MainActivity.JOGADAS, pijogadas);
            editarActivityResultLauncher.launch(editarIntent);
            return true;
        }
        return false;
    }
}