package com.nosql.controller;

import com.nosql.encryption.DESedeUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@RestController
public class TDESController {

    @Value("${TDESsecretKey}")
    String TDESsecretKey;

    @PostMapping(value = "/TDESencrypt")
    public ResponseEntity<String> encrypt(@RequestBody String text) throws Exception {
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(TDESsecretKey), "DESede");
        DESedeUtility encrypter = new DESedeUtility(secretKey);
        String encrypted = encrypter.encrypt(text);

        return new ResponseEntity<>(encrypted, HttpStatus.OK);

    }

    @PostMapping(value = "/TDESdecrypt")
    public ResponseEntity<String> dencrypt(@RequestBody String encryptedText) throws Exception {
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(TDESsecretKey), "DESede");
        DESedeUtility encrypter = new DESedeUtility(secretKey);
        String decrypted = encrypter.decrypt(encryptedText);
        return new ResponseEntity<String>(decrypted, HttpStatus.OK);

    }
}
