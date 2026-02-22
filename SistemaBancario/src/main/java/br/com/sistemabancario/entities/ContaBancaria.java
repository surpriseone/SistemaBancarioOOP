package br.com.sistemabancario.entities;

import br.com.sistemabancario.exceptions.SaldoInsuficienteException;
import br.com.sistemabancario.exceptions.TitularEmptyException;
import br.com.sistemabancario.exceptions.TitularNullException;
import br.com.sistemabancario.exceptions.TranferirParaMesmaContaException;
import br.com.sistemabancario.exceptions.ValorInvalidoException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ContaBancaria {

    private int numeroDaConta;
    private String nomeTitular;
    private BigDecimal saldo;
    private ArrayList<String> extrato = new ArrayList<>();


    // Construtores
    public ContaBancaria(String nomeTitular, int numeroConta) {

        if (nomeTitular == null) {
            throw new TitularNullException("O titular precisa ter um nome");
        }

        if (nomeTitular.trim().isEmpty()) {
            throw new TitularEmptyException("O Titular não pode ter nome vazio");
        }

        this.numeroDaConta = numeroConta;
        this.nomeTitular = nomeTitular;
        this.saldo = BigDecimal.ZERO;
    }

    public ContaBancaria(String nomeTitular, int numeroConta, BigDecimal DepositoInicial){
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

    public BigDecimal getSaldo(){
        return this.saldo;
    }

//Metodo set

    public void setNomeTitular(String novoNomeTitular){
        if (novoNomeTitular == null) {
            throw new TitularNullException("O Titular tem que ter um nome");
        }

        if (novoNomeTitular.trim().isEmpty()) {
            throw new TitularEmptyException("Nome do titular não pode estar vazio");
        }

        this.nomeTitular = novoNomeTitular;
    }



// Regras e validações de entrada sobre operações bancarias vindas do SistemaBancario (Depositar, sacar, transferir)

    public void depositar(BigDecimal valorDeposito){
        if (valorDeposito == null){
            throw new ValorInvalidoException("Valor não pode ser nulo");
        }

        if(valorDeposito.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorInvalidoException("O valor do deposito deve ser maior que 0");
        }

        this.saldo = this.saldo.add(setarPontoFlutuante(valorDeposito));
        this.extrato(1, valorDeposito, null);  //1 deposito
    }


    public void sacar(BigDecimal valorSaque){

        if (valorSaque.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorInvalidoException("O valor do saque deve ser maior que zero");
        }

        if (valorSaque.compareTo(this.saldo) > 0){
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        this.saldo = this.saldo.subtract(setarPontoFlutuante(valorSaque));
        this.extrato(2, valorSaque, null); //2 saque
    }



    public void transferir(ContaBancaria conta, BigDecimal valorTranferencia){

        if (this.getNumeroDaConta() == conta.getNumeroDaConta()){
            throw new TranferirParaMesmaContaException("Você não pode transferir pra mesma conta");
        }
        if(valorTranferencia.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorInvalidoException("Valor da tranferencia deve ser maior que zero");
        }
        if (valorTranferencia.compareTo(this.saldo) > 0){
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        this.enviarTranferencia(conta, valorTranferencia);
        conta.receberTranferencia(this, valorTranferencia);
    }

    private void receberTranferencia(ContaBancaria remetente, BigDecimal valorRecebido){
        this.saldo = this.saldo.add(setarPontoFlutuante(valorRecebido));
        this.extrato(3, valorRecebido, remetente); //3 Tranferencia recebida
    }

    private void enviarTranferencia(ContaBancaria destinatario, BigDecimal valorEnviado){
        this.saldo = this.saldo.subtract(setarPontoFlutuante(valorEnviado));
        this.extrato(4, valorEnviado, destinatario); //4 Tranferencia enviada
    }


// ToString

    @Override
    public String toString(){
        return "Nome do titular: " + this.nomeTitular + " | Conta: "
                + this.numeroDaConta + " | Saldo em conta: " + this.saldo + "\n";
    }

// Extrato bancario

    private void extrato(int tipoOperacao, BigDecimal valor, ContaBancaria conta){
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


    private BigDecimal setarPontoFlutuante(BigDecimal valorParaPadronizar){
        return valorParaPadronizar.setScale(2, RoundingMode.HALF_UP); // valor formatado
    }
}
