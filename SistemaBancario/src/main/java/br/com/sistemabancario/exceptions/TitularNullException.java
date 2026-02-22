package br.com.sistemabancario.exceptions;

public class TitularNullException extends RuntimeException{
    public TitularNullException(String mensagem){
        super(mensagem);
    }
}
