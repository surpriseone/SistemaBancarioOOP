package br.com.sistemabancario.exceptions;

public class contaNaoEncontradaException extends RuntimeException{
    public contaNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
