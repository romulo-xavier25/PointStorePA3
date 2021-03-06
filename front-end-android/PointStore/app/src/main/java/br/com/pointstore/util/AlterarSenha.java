package br.com.pointstore.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.pointstore.Adapter.Menssagem;
import br.com.pointstore.Adapter.UsuarioAlterarSenha;
import br.com.pointstore.R;
import br.com.pointstore.model.Usuario;
import rest.LoginService;
import rest.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AlterarSenha extends AppCompatActivity {

    private EditText editTextEmailAtual;
    private EditText editTextNovaSenha;
    private EditText editTextConfirmarNovaSenha;
    private Usuario usuario;
    private LoginService mLoginAlterarSenhaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.3.2")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        mLoginAlterarSenhaService = retrofit.create(LoginService.class);


        editTextEmailAtual = (EditText) findViewById(R.id.editTextAlterEmailAtual);
        editTextNovaSenha = (EditText) findViewById(R.id.editTextAlterNovaSenha);
        editTextConfirmarNovaSenha = (EditText) findViewById(R.id.editTextAlterConfirmarSenha);

    }

    public void atualizarSenha (View v) {

        UsuarioAlterarSenha usuarioAlterarSenha = new UsuarioAlterarSenha();

        usuarioAlterarSenha.setEmail(editTextEmailAtual.getText().toString());
        usuarioAlterarSenha.setSenha(editTextNovaSenha.getText().toString());

        if ((editTextEmailAtual.getText().length() > 0) && (editTextNovaSenha.getText().length() > 0) && (editTextConfirmarNovaSenha.getText().length() > 0) &&
                ((editTextNovaSenha.getText().toString()).equals(editTextConfirmarNovaSenha.getText().toString())) ) {


            Call<Menssagem> userCallAtualizaSenha = mLoginAlterarSenhaService.atualizaSenha(usuarioAlterarSenha);

            userCallAtualizaSenha.enqueue(new Callback<Menssagem>() {
                @Override
                public void onResponse(Call<Menssagem> call, Response<Menssagem> response) {

                    Menssagem menssagem = response.body();

                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, " : "+menssagem.getMensagem(), Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void onFailure(Call<Menssagem> call, Throwable t) {

                }
            });

            Intent telaDeLogin = new Intent(this, Login.class);
            startActivity(telaDeLogin);

        } else {

            if (editTextEmailAtual.getText().length() <= 0){
                editTextEmailAtual.setError("Campo email é obrigatório");
            }

            if (!(editTextNovaSenha.getText().toString()).equals(editTextConfirmarNovaSenha.getText().toString())) {

                    editTextNovaSenha.setError("Senhas não conferem");
                    editTextConfirmarNovaSenha.setError("Senhas não conferem");
            }

        }
    }
}
