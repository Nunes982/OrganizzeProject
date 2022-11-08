package com.example.organizzeproject.helper;

import android.util.Base64;

import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;

import java.nio.charset.StandardCharsets;

public class Base64Custom {

    public static String codificarBase64 (String texto){
        return Base64.encodeToString(texto.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
   }

    public static String decodificarBase64 (String textoCodificado){
        return  new String( Base64.decode(textoCodificado, Base64.DEFAULT) );
    }

}
