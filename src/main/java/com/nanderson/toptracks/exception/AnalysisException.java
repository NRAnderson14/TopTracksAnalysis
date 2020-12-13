package com.nanderson.toptracks.exception;

public class AnalysisException extends Exception {

    private static final long serialVersionUID = 1L;

    public AnalysisException() {
    }

    public AnalysisException(String message) {
        super(message);
    }

    public AnalysisException(Throwable cause) {
        super(cause);
    }

    public AnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnalysisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
