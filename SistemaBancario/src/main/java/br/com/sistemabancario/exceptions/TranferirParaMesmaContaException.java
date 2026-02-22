package br.com.sistemabancario.exceptions;

public class TranferirParaMesmaContaException extends RuntimeException {
    public TranferirParaMesmaContaException(String mensagem){
        super(mensagem);
    }
}
