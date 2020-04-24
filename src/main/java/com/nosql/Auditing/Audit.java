package com.nosql.Auditing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Audit {
    private static final Logger logger = LoggerFactory.getLogger(Audit.class);

    public AuditObj log(AuditObj auditObj) {

        String logOperation = auditObj.LogOperation.toUpperCase();

        switch (logOperation) {
            case "INFO":
                logger.info(auditObj.getContent());
                return auditObj;
            case "ERROR":
                logger.error(auditObj.getContent());
                return auditObj;
            case "WARN":
                logger.warn(auditObj.getContent());
                return auditObj;
        }
        return auditObj;
    }

}
