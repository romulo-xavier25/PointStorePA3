package br.com.pointstore.util;

/**
 * Created by Juan on 05/05/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;

import br.com.pointstore.ListarAnunciosActivity;
import br.com.pointstore.R;
import rest.LoginService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FinalizarCompra extends Activity {

    private Spinner spn1;
    private List<String> nomes = new ArrayList<String>();
    private String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_compra);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        //Adicionando Nomes no ArrayList
        nomes.add("Cartao de Crédito");
        nomes.add("Boleto");


        //Identifica o Spinner no layout
        spn1 = (Spinner) findViewById(R.id.spinner);
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nomes);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spn1.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                nome = parent.getItemAtPosition(posicao).toString();
                //imprime um Toast na tela com o nome que foi selecionado
                //Toast.makeText(FinalizarCompra.this, nome + " Selecionado", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.button2), nome + " Selecionado", Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void paginainicial (View view){
        Intent paginainicial = new Intent(FinalizarCompra.this, ListarAnunciosActivity.class);
        startActivity(paginainicial);
    }



}
