package br.com.sistemabancario.exceptions;

public class tranferirParaMesmaContaException extends RuntimeException {
    public tranferirParaMesmaContaException(String mensagem){
        super(mensagem);
    }
}
