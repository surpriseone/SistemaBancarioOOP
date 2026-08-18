package br.com.sistemabancario.exceptions;

public class ErroDeTransferenciaException extends RuntimeException{
    public ErroDeTransferenciaException(String mensagem){
        super(mensagem);
    }
}
