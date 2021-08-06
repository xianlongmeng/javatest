package com.ly.test;

import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

public class SealedObjectTest {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, ClassNotFoundException, NoSuchProviderException, BadPaddingException {
        char[] cs="asdf".toCharArray();
        KeyGenerator kgen=KeyGenerator.getInstance("AES");
        SecretKey aesKey=kgen.generateKey();
        Cipher cipher=Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,aesKey);
        SealedObject sealedObject=new SealedObject(cs,cipher);
        Cipher cipher1=Cipher.getInstance("AES");
        cipher1.init(Cipher.DECRYPT_MODE,aesKey);
        Arrays.fill(cs,'\0');
        System.out.println("sealedObject-" + sealedObject);
        //Object a=sealedObject.getObject(aesKey);
        System.out.println("sealedObject Data-" + String.valueOf((char[])(sealedObject.getObject(aesKey))));
        System.out.println("sealedObject Data-" + String.valueOf((char[])(sealedObject.getObject(cipher1))));
        System.out.println("sealedObject Data-" + String.valueOf(cs));
    }
}
