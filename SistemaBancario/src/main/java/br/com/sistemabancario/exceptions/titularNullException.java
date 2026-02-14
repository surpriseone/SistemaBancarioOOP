package br.com.sistemabancario.exceptions;

public class titularNullException extends RuntimeException{
    public titularNullException(String mensagem){
        super(mensagem);
    }
}
