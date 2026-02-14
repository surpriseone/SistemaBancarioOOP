package br.com.sistemabancario.exceptions;

public class saldoInsuficienteException extends RuntimeException{
    public saldoInsuficienteException(String mensagem){
        super(mensagem);
    }
}

