package br.com.sistemabancario.entities;


import br.com.sistemabancario.exceptions.ValorInvalidoException;

import java.math.BigDecimal;

public class SistemaBancario {

    private Banco banco;

    public SistemaBancario(Banco nomeBanco){
        this.banco = nomeBanco;
    }

    public Banco getBanco(){
        return this.banco;
    }

    public void tranferencia(int numeroContaOrigem, int numeroContaDestino, BigDecimal valorTranferencia){
        //validação para evitar chamar metodos, em um cenario onde a tranferencia ja nasceu invalida.
        if (valorTranferencia.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorInvalidoException("Valor de transferencia invalido");
        }

        ContaBancaria contaOrigem = banco.buscarContaBancariaPorNumero(numeroContaOrigem);
        ContaBancaria contaDestino = banco.buscarContaBancariaPorNumero(numeroContaDestino);

        contaOrigem.transferir(contaDestino, valorTranferencia);
    }

    public void sacar(int numeroDaConta, BigDecimal valorSaque){

        if (valorSaque.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorInvalidoException("Valor de saque invalido");
        }

        ContaBancaria contaRequestSaque = banco.buscarContaBancariaPorNumero(numeroDaConta);
        contaRequestSaque.sacar(valorSaque);
    }


    public void depositar(int numeroDaConta, BigDecimal valorDeposito){

        if (valorDeposito.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorInvalidoException("Valor de deposito invalido");
        }

        ContaBancaria contaRequestDeposito = banco.buscarContaBancariaPorNumero(numeroDaConta);
        contaRequestDeposito.depositar(valorDeposito);
    }

    public int sistemaCriarConta(String nomeTitular){
        return banco.criarConta(nomeTitular);
    }

    public int sistemaCriarContaComDepositoInicial(String nomeTitular, BigDecimal depositoInicial) {
        return banco.criarConta(nomeTitular, depositoInicial);
    }
}

