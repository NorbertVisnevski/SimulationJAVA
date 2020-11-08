package com.NV.simulation.exceptions;

public class UnknownAnimalException extends Exception{
    public UnknownAnimalException() {
        super();
    }

    public UnknownAnimalException(String message) {
        super(message);
    }
}
