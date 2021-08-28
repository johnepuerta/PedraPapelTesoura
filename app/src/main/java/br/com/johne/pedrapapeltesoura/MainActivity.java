package br.com.johne.pedrapapeltesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Random;

import br.com.johne.pedrapapeltesoura.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    private static void accept(JogadasEnum jogador) {

    }

    private static void accept2(JogadasEnum valor) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    public void jogar(View view){

        activityMainBinding.resultado.setText("");
        activityMainBinding.jogador1.setText("");
        activityMainBinding.jogador2.setText("");
        activityMainBinding.jogador3.setText("");

        JogadasEnum jogador1 = JogadasEnum.fromString((String) view.getContentDescription());
        JogadasEnum jogador2 = JogadasEnum.fromValue(new Random().nextInt(3));
        JogadasEnum jogador3 = JogadasEnum.PERDEDOR;
        if(activityMainBinding.tresjogadoresRb.isChecked()) {
            jogador3 = JogadasEnum.fromValue(new Random().nextInt(3));
        }
        activityMainBinding.jogador1.setText(mantaDescribe(JogadasEnum.PEDRA,   jogador1, jogador2, jogador3));
        activityMainBinding.jogador2.setText(mantaDescribe(JogadasEnum.PAPEL,   jogador1, jogador2, jogador3));
        activityMainBinding.jogador3.setText(mantaDescribe(JogadasEnum.TESOURA, jogador1, jogador2, jogador3));

        if(JogadasEnum.isVenceJogada(jogador1, jogador2) && JogadasEnum.isVenceJogada(jogador1, jogador3)){
            activityMainBinding.resultado.setText("Você venceu!!!");
        }else if(JogadasEnum.isVenceJogada(jogador2, jogador1) && JogadasEnum.isVenceJogada(jogador2, jogador3)){
            activityMainBinding.resultado.setText("Jogador 2 venceu!!!");
        }else if(JogadasEnum.isVenceJogada(jogador3, jogador1) && JogadasEnum.isVenceJogada(jogador2, jogador2)){
            activityMainBinding.resultado.setText("Jogador 3 venceu!!!");
        }else {
            activityMainBinding.resultado.setText("Empate!!!");
        }
    }

    private String mantaDescribe(JogadasEnum jogada, JogadasEnum j1, JogadasEnum j2, JogadasEnum j3) {
        String descricao = "";
        if(jogada.equals(j1)){
            descricao += descricao.length() > 0 ? "\nVocê" : "Você";
        }
        if(jogada.equals(j2)){
            descricao += descricao.length() > 0 ? "\nJogador 2" : "Jogador 2";
        }
        if(jogada.equals(j3)){
            descricao += descricao.length() > 0 ? "\nJogador 3" : "Jogador 3";
        }
        return descricao;
    }

}