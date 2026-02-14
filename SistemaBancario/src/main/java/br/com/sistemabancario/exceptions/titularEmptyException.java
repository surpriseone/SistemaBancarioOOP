package br.com.sistemabancario.exceptions;

public class titularEmptyException extends RuntimeException{
    public titularEmptyException(String mensagem) {
        super(mensagem);
    }
}
