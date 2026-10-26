package br.com.sistemabancario.exceptions;

public class FalhaRecuperacaoDadosException extends RuntimeException {
    public FalhaRecuperacaoDadosException(String message) {
        super(message);
    }
}
