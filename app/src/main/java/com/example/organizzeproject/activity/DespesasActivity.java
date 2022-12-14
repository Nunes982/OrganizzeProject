package com.example.organizzeproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.organizzeproject.R;
import com.example.organizzeproject.config.ConfifuracaoFirebase;
import com.example.organizzeproject.helper.Base64Custom;
import com.example.organizzeproject.helper.DateCustom;
import com.example.organizzeproject.model.Movimentacao;
import com.example.organizzeproject.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfifuracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfifuracaoFirebase.getFirebaseAutenticacao();
    private Double despesaTotal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoValor     = findViewById(R.id.editValor);
        campoData      = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);

        //Preencher o campo data com a date atual
        campoData.setText( DateCustom.dataAtual() );
        recuperarDespesaTotal();



    }

    public void salvarDespesa(View view){
        if ( validarCamposDespesa() ){

            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString() );

            movimentacao.setValor( valorRecuperado );
            movimentacao.setCategoria( campoCategoria.getText().toString() );
            movimentacao.setDescricao( campoDescricao.getText().toString() );
            movimentacao.setData( data);
            movimentacao.setTipo( "d" );

            Double despesaAtualizada = despesaTotal + valorRecuperado;
            atualizarDespesa( despesaAtualizada );

            movimentacao.salvar( data );

            finish();



        }

    }

    public Boolean validarCamposDespesa(){

       String textoValor = campoValor.getText().toString();
       String textoData = campoData.getText().toString();
       String textoCategoria = campoCategoria.getText().toString();
       String textoDescricao = campoDescricao.getText().toString();

       if ( !textoValor.isEmpty() ){
           if ( !textoData.isEmpty() ){
               if ( !textoCategoria.isEmpty() ){
                   if ( !textoDescricao.isEmpty() ){

                   }else {
                       Toast.makeText(DespesasActivity.this,
                               "Descri????o n??o foi preenchida!",
                               Toast.LENGTH_LONG).show();
                       return false;
                   }

               }else {
                   Toast.makeText(DespesasActivity.this,
                           "Categoria n??o foi preenchida!",
                           Toast.LENGTH_LONG).show();
                   return false;
               }

           }else {
               Toast.makeText(DespesasActivity.this,
                       "Data n??o foi preenchida!",
                       Toast.LENGTH_LONG).show();
               return false;
           }
       }else{
           Toast.makeText(DespesasActivity.this,
                   "Valor n??o foi preenchido!",
                   Toast.LENGTH_LONG).show();
           return false;
       }

        return true;
    }

    public void recuperarDespesaTotal(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUsuario );
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child( idUsuario );

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue( Usuario.class );
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void atualizarDespesa(Double despesa){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUsuario );
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child( idUsuario );

        usuarioRef.child("despesaTotal").setValue(despesa);

    }

}