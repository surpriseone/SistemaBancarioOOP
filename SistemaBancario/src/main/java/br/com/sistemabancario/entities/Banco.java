package br.com.sistemabancario.entities;

import br.com.sistemabancario.exceptions.contaNaoEncontradaException;
import java.util.Collections;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Banco {

    private Map<Integer, ContaBancaria> contas = new HashMap<>();
    private String nomeBanco;
    private Set<Integer> numerosDasContas = new HashSet<>();


    public Banco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public void adiocionarConta(ContaBancaria conta) {
        contas.put(conta.getNumeroDaConta(), conta);
    }

    // Conta sem deposito inicial
    public int criarConta(String nomeTitular) {
        ContaBancaria conta = new ContaBancaria(nomeTitular, gerarNumero());
        this.adiocionarConta(conta);
        return conta.getNumeroDaConta();
    }


    //conta com deposito inicial
    public int criarConta(String nomeTitular, double valorDeposito) {
        ContaBancaria conta = new ContaBancaria(nomeTitular, gerarNumero(), valorDeposito);
        this.adiocionarConta(conta);
        return conta.getNumeroDaConta();
    }


    //Buscar conta
    public ContaBancaria buscarContaBancariaPorNumero(int numeroConta) {
        ContaBancaria contaBuscada = contas.get(numeroConta);

        if (contaBuscada == null) {
            throw new contaNaoEncontradaException("Conta não encontrada");
        }

        return contaBuscada;
    }

    public int quantidadeDeContas() {
        return contas.size();
    }

    public Collection<ContaBancaria> getContas() {
         return Collections.unmodifiableCollection(contas.values());
    }

// Gerar numero aleatorio para cada conta

    public int gerarNumero() {
        int numeroGerado;
        do {
            numeroGerado = ThreadLocalRandom.current().nextInt(100000, 1000000);
        } while (numerosDasContas.contains(numeroGerado));

        numerosDasContas.add(numeroGerado);
        return numeroGerado;
    }

}
