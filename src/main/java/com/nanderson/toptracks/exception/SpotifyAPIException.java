package com.nanderson.toptracks.exception;

public class SpotifyAPIException extends Exception {

    private static final long serialVersionUID = 1L;

    public SpotifyAPIException() {
    }

    public SpotifyAPIException(String message) {
        super(message);
    }

    public SpotifyAPIException(Throwable cause) {
        super(cause);
    }

    public SpotifyAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpotifyAPIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
