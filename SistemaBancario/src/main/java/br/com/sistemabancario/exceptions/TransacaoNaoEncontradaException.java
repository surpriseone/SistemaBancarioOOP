package br.com.sistemabancario.exceptions;

public class TransacaoNaoEncontradaException extends RuntimeException {
    public  TransacaoNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
