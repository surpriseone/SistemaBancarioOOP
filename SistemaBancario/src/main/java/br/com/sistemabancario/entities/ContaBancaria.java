package br.com.sistemabancario.entities;

import br.com.sistemabancario.exceptions.saldoInsuficienteException;
import br.com.sistemabancario.exceptions.titularEmptyException;
import br.com.sistemabancario.exceptions.titularNullException;
import br.com.sistemabancario.exceptions.tranferirParaMesmaContaException;
import br.com.sistemabancario.exceptions.valorInvalidoException;
import java.util.ArrayList;

public class ContaBancaria {

    private int numeroDaConta;
    private String nomeTitular;
    private double saldo;
    private ArrayList<String> extrato = new ArrayList<>();


    // Construtores
    public ContaBancaria(String nomeTitular, int numeroConta) {

        if (nomeTitular == null) {
            throw new titularNullException("O titular precisa ter um nome");
        }

        if (nomeTitular.trim().isEmpty()) {
            throw new titularEmptyException("O Titular não pode ter nome vazio");
        }

        this.numeroDaConta = numeroConta;
        this.nomeTitular = nomeTitular;
        this.saldo = 0.0;
    }

    public ContaBancaria(String nomeTitular, int numeroConta, double DepositoInicial){
        this(nomeTitular, numeroConta);
        this.depositar(DepositoInicial);
    }

// Metodos Get

    public int getNumeroDaConta(){
        return this.numeroDaConta;
    }

    public String getNomeTitular(){
        return this.nomeTitular;
    }

    public double getSaldo(){
        return this.saldo;
    }

//Metodo set

    public void setNomeTitular(String novoNomeTitular){
        if (novoNomeTitular == null) {
            throw new titularNullException("O Titular tem que ter um nome");
        }

        if (novoNomeTitular.trim().isEmpty()) {
            throw new titularEmptyException("Nome do titular não pode estar vazio");
        }

        this.nomeTitular = novoNomeTitular;
    }



// Regras e validações de entrada sobre operações bancarias vindas do SistemaBancario (Depositar, sacar, transferir)

    public void depositar(double valorDeposito){
        if (valorDeposito <= 0){
            throw new valorInvalidoException("O valor do deposito deve ser maior que 0");
        }

        this.saldo = this.saldo + valorDeposito;
        this.extrato(1, valorDeposito, null);  //1 deposito
    }


    public void sacar(double valorSaque){

        if (valorSaque <= 0){
            throw new valorInvalidoException("O valor do saque deve ser maior que zero");
        }

        if (valorSaque > this.saldo){
            throw new saldoInsuficienteException("Saldo insuficiente");
        }

        this.saldo -= valorSaque;
        this.extrato(2, valorSaque, null); //2 saque
    }



    public void tranferir(ContaBancaria conta, double valorTranferencia){

        if (this.equals(conta)){
            throw new tranferirParaMesmaContaException("Você não pode tranferir pra mesma conta");
        }
        if(valorTranferencia <= 0){
            throw new valorInvalidoException("Valor da tranferencia deve ser maior que zero");
        }
        if (valorTranferencia > this.saldo){
            throw new saldoInsuficienteException("Saldo insuficiente");
        }

        this.enviarTranferencia(conta, valorTranferencia);
        conta.receberTranferencia(this, valorTranferencia);
    }

    private void receberTranferencia(ContaBancaria remetente, double valorRecebido){
        this.saldo += valorRecebido;
        this.extrato(3, valorRecebido, remetente); //3 Tranferencia recebida
    }

    private void enviarTranferencia(ContaBancaria destinatario, double valorEnviado){
        this.saldo -= valorEnviado;
        this.extrato(4, valorEnviado, destinatario); //4 Tranferencia enviada
    }


// ToString

    @Override
    public String toString(){
        return "Nome do titular: " + this.nomeTitular + " | Conta: "
                + this.numeroDaConta + " | Saldo em conta: " + this.saldo + "\n";
    }

// Extrato bancario

    private void extrato(int tipoOperacao, double valor, ContaBancaria conta){
        String historico;
        int numeroContaParametro = 0;
        String nomeContaParametro = null;

        if (conta != null){
            numeroContaParametro = conta.getNumeroDaConta();
            nomeContaParametro = conta.getNomeTitular();
        }

        switch (tipoOperacao) {
            case 1:
                historico = String.format("Deposito: +R$%.2f%n", valor);
                extrato.add(historico);
                break;
            case 2:
                historico = String.format("Saque: -R$%.2f%n", valor);
                extrato.add(historico);
                break;
            case 3:
                historico = String.format("Tranferencia recebida de: %s, conta: %d +R$%.2f%n",
                        nomeContaParametro, numeroContaParametro, valor);
                extrato.add(historico);
                break;
            case 4:
                historico = String.format("Tranferencia enviada para: %s, conta: %d -R$%.2f%n",
                        nomeContaParametro, numeroContaParametro, valor);
                extrato.add(historico);
                break;
            default:
                throw new AssertionError();
        }

    }

    public void imprimirExtrato(){
        System.out.println("Extrato da conta : ");
        for (String historico : extrato){
            System.out.print(historico);
        }
        System.out.println("Saldo da atual da conta: " + this.saldo);
    }
}
