package com.sahibinden.exception;

import java.io.Serializable;

public class GameException extends Exception implements Serializable
{
    private static final long serialVersionUID = 1L;

    public GameException() {
        super();
    }
    public GameException(String msg)   {
        super(msg);
    }
    public GameException(String msg, Exception e)  {
        super(msg, e);
    }
}