package br.com.sistemabancario.exceptions;

public class valorInvalidoException extends RuntimeException {
    public valorInvalidoException(String mensagem) {
        super(mensagem);
    }
}

