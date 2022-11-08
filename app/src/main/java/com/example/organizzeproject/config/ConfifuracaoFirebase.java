package com.example.organizzeproject.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfifuracaoFirebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;

    //retorma a instacia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao(){
        if ( autenticacao == null ){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

    //Retorna a instancia do FirebaseDatabase
    public static DatabaseReference getFirebaseDatabase(){
        if ( firebase == null ){
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }


}
