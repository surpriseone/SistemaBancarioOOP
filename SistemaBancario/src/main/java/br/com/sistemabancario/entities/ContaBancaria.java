package br.com.sistemabancario.entities;

import br.com.sistemabancario.exceptions.SaldoInsuficienteException;
import br.com.sistemabancario.exceptions.TitularEmptyException;
import br.com.sistemabancario.exceptions.TitularNullException;
import br.com.sistemabancario.exceptions.TranferirParaMesmaContaException;
import br.com.sistemabancario.exceptions.ValorInvalidoException;
import br.com.sistemabancario.objectvalues.CPF;
import br.com.sistemabancario.objectvalues.Dinheiro;

import java.util.ArrayList;
import java.util.List;

public class ContaBancaria {
    private int contaID;
    private int numeroDaConta;
    private Cliente titular;
    private Dinheiro saldo;
    private List<Transacao> extrato = new ArrayList<>();


    // Construtores
    public ContaBancaria(Cliente titular, int numeroConta) {
        this.titular = titular;
        this.numeroDaConta = numeroConta;
        this.saldo = Dinheiro.ZERO;
    }

    public int getNumeroDaConta(){
        return this.numeroDaConta;
    }
    public Dinheiro getSaldo(){
        return this.saldo;
    }

    public void adicionarNoExtrato(Transacao transacao){
        extrato.add(transacao);
    }

// Regras e validações de entrada sobre operações bancarias vindas do SistemaBancario (Depositar, sacar, transferir)

    public void depositar(Dinheiro valorDeposito){

        if(valorDeposito.comparar(Dinheiro.ZERO) <= 0){
            throw new ValorInvalidoException("O valor do deposito deve ser maior que 0");
        }

        this.saldo = this.saldo.somar(valorDeposito);
    }

    public void sacar(Dinheiro valorSaque){

        if (valorSaque.comparar(Dinheiro.ZERO) <= 0){
            throw new ValorInvalidoException("O valor do saque deve ser maior que zero");
        }

        if (valorSaque.comparar(this.saldo) > 0){
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        this.saldo = this.saldo.subtrair(valorSaque);
    }


    public void transferir(ContaBancaria conta, Dinheiro valorTranferencia){

        if (this.getNumeroDaConta() == conta.getNumeroDaConta()){
            throw new TranferirParaMesmaContaException("Você não pode transferir pra mesma conta");
        }

        this.sacar(valorTranferencia);
        conta.depositar(valorTranferencia);
    }

// ToString

    @Override
    public String toString(){
        return "Nome do titular: " + this.titular.getNome() + " | Conta: "
                + this.numeroDaConta + " | Saldo em conta: " + this.saldo + "\n";
    }

    public void imprimirExtrato() {
        for(Transacao historico: extrato){
            System.out.println(historico.formatarParaExtrato(this));
        }
    }

    //Receber dados do banco
    private ContaBancaria(int contaID, Cliente titular, int numeroDaConta, Dinheiro saldoAtual){
        this(titular, numeroDaConta);
        this.contaID = contaID;
        this.saldo = saldoAtual;
    }

    public static ContaBancaria reconstituirConta(int contaID, Cliente titular, int numeroDaConta, Dinheiro saldoAtual){
        return new ContaBancaria(contaID, titular, numeroDaConta, saldoAtual);
    }

    public Cliente getTitular() {
        return titular;
    }

    public String getNomeTitular(){
        return this.titular.getNome();
    }
}
