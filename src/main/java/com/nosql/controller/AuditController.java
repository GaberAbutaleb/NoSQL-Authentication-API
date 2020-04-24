package com.nosql.controller;

import com.nosql.Auditing.Audit;
import com.nosql.Auditing.AuditObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuditController {

    @Autowired
    Audit audit;

    @PostMapping(value = "/Audit")
    public ResponseEntity<AuditObj> Audit(@RequestBody AuditObj auditobj) throws Exception {
        AuditObj returnAuditObj = audit.log(auditobj);
        return new ResponseEntity<>(returnAuditObj, HttpStatus.OK);

    }
}
