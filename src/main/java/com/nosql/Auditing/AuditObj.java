package com.nosql.Auditing;


public class AuditObj {
    String Content;
    String LogOperation;


    public AuditObj() {
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getLogOperation() {
        return LogOperation;
    }

    public void setLogOperation(String logOperation) {
        LogOperation = logOperation;
    }
}
