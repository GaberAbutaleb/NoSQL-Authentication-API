package com.nosql.controller;

import com.nosql.encryption.RSAUtil;
import com.nosql.encryption.RSAVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@RestController
public class RSAController {

    @GetMapping(value = "/getRsaKeyPair")
    public ResponseEntity<RSAVO> getRsaKeyPair() throws NoSuchAlgorithmException {
        RSAVO vo = new RSAVO();
        RSAUtil keyPairGenerator = new RSAUtil();
        vo.setPrivatekey(Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded()));
        vo.setPublicKey(Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded()));
        return new ResponseEntity<>(vo, HttpStatus.OK);

    }

    @PostMapping(value = "/encrypt")
    public ResponseEntity<RSAVO> encrypt(@RequestBody RSAVO vo) throws Exception {

        String pubkey = vo.getPublicKey().replaceAll("\\s+", "").trim();
        RSAVO voobj = new RSAVO();
        String dec = RSAUtil.encryptMessage(vo.getText(), pubkey);
        voobj.setEncryptedvalue(dec);

        return new ResponseEntity<>(voobj, HttpStatus.OK);

    }

    @PostMapping(value = "/dencrypt")
    public ResponseEntity<RSAVO> dencrypt(@RequestBody RSAVO vo) throws Exception {

        String pvtkey = vo.getPrivatekey().replaceAll("\\s+", "").trim();
        RSAVO voobj = new RSAVO();
        String dec = RSAUtil.decryptMessage(vo.getEncryptedvalue(), pvtkey);
        voobj.setText(dec);

        return new ResponseEntity<>(voobj, HttpStatus.OK);

    }

}
