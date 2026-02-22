package br.com.sistemabancario.exceptions;

public class ContaNaoEncontradaException extends RuntimeException{
    public ContaNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
