package com.nosql.controller;

import com.nosql.encryption.AESUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AESController {

    @Value("${AESsecretKey}")
    String AESsecretKey;


    @PostMapping(value = "/AESencrypt")
    public ResponseEntity<String> encrypt(@RequestBody String text) throws Exception {
        String encryptedText = AESUtility.encrypt(text, AESsecretKey);
        return new ResponseEntity<>(encryptedText, HttpStatus.OK);
    }

    @PostMapping(value = "/AESdecrypt")
    public ResponseEntity<String> dencrypt(@RequestBody String encryptedText) throws Exception {
        String decryptedString = AESUtility.decrypt(encryptedText, AESsecretKey);
        return new ResponseEntity<String>(decryptedString, HttpStatus.OK);

    }
}
