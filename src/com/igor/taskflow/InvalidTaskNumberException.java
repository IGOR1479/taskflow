package com.igor.taskflow;

import java.io.Serial;

// Thrown when a task number is out of range
public class InvalidTaskNumberException extends Exception
{
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidTaskNumberException(String message)
    {
        super(message);
    }
}