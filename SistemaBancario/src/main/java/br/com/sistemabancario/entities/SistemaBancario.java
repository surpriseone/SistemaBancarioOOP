package br.com.sistemabancario.entities;


import br.com.sistemabancario.exceptions.valorInvalidoException;

public class SistemaBancario {

    private Banco banco;

    public SistemaBancario(Banco nomeBanco){
        this.banco = nomeBanco;
    }

    public Banco getBanco(){
        return this.banco;
    }

    public void tranferencia(int numeroContaOrigem, int numeroContaDestino, double valorTranferencia){
        //validação para evitar chamar metodos, em um cenario onde a tranferencia ja nasceu invalida.
        if (valorTranferencia <= 0){
            throw new valorInvalidoException("Valor de transferencia invalido");
        }

        ContaBancaria contaOrigem = banco.buscarContaBancariaPorNumero(numeroContaOrigem);
        ContaBancaria contaDestino = banco.buscarContaBancariaPorNumero(numeroContaDestino);

        contaOrigem.tranferir(contaDestino, valorTranferencia);
    }

    public void sacar(int numeroDaConta, double valorSaque){

        if (valorSaque <= 0){
            throw new valorInvalidoException("Valor de saque invalido");
        }

        ContaBancaria contaRequestSaque = banco.buscarContaBancariaPorNumero(numeroDaConta);
        contaRequestSaque.sacar(valorSaque);
    }


    public void depositar(int numeroDaConta, double valorDeposito){

        if (valorDeposito <= 0){
            throw new valorInvalidoException("Valor de deposito invalido");
        }

        ContaBancaria contaRequestDeposito = banco.buscarContaBancariaPorNumero(numeroDaConta);
        contaRequestDeposito.depositar(valorDeposito);
    }

    public int sistemaCriarConta(String nomeTitular){
        return banco.criarConta(nomeTitular);
    }

    public int sistemaCriarContaComDepositoInicial(String nomeTitular, double depositoInicial){
        return banco.criarConta(nomeTitular, depositoInicial);
    }
}

