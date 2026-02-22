package br.com.sistemabancario.exceptions;

public class TitularEmptyException extends RuntimeException{
    public TitularEmptyException(String mensagem) {
        super(mensagem);
    }
}
