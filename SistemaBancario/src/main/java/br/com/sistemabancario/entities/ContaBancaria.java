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

    private int numeroDaConta;
    private String nomeTitular;
    private Dinheiro saldo;
    private CPF cpf;
    private List<Transacao> extrato = new ArrayList<>();


    // Construtores
    public ContaBancaria(String nomeTitular, int numeroConta, CPF cpfEntrada) {

        if (nomeTitular == null) {
            throw new TitularNullException("O titular precisa ter um nome");
        }

        if (nomeTitular.trim().isEmpty()) {
            throw new TitularEmptyException("O Titular não pode ter nome vazio");
        }
        this.cpf = cpfEntrada;
        this.numeroDaConta = numeroConta;
        this.nomeTitular = nomeTitular;
        this.saldo = Dinheiro.ZERO;
    }


// Metodos Get
    public int getNumeroDaConta(){
        return this.numeroDaConta;
    }
    public String getNomeTitular(){
        return this.nomeTitular;
    }
    public Dinheiro getSaldo(){
        return this.saldo;
    }
    public CPF getCPF(){
        return this.cpf;
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
        return "Nome do titular: " + this.nomeTitular + " | Conta: "
                + this.numeroDaConta + " | Saldo em conta: " + this.saldo + "\n";
    }

    public void imprimirExtrato() {
        for(Transacao historico: extrato){
            System.out.println(historico.formatarParaExtrato(this));
        }
    }

    //Receber dados do banco
    private ContaBancaria(String nomeTitular, int numeroDaConta, CPF cpf, Dinheiro saldoAtual){
        this(nomeTitular, numeroDaConta, cpf);
        this.saldo = saldoAtual;
    }

    public static ContaBancaria reconstituirConta(String nomeTitular, int numeroDaConta, CPF cpf, Dinheiro saldoAtual){
        return new ContaBancaria(nomeTitular, numeroDaConta, cpf, saldoAtual);
    }
}
