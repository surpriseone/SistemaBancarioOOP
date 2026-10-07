package br.com.sistemabancario.repositories.Memory;

import br.com.sistemabancario.entities.Cliente;
import br.com.sistemabancario.entities.ContaBancaria;

import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.objectvalues.CPF;
import br.com.sistemabancario.repositories.Intefaces.ContaRepository;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class ContaMemory implements ContaRepository {

    private Map<Integer, ContaBancaria> contas = new HashMap<>();
    private Set<Integer> numerosDasContas = new HashSet<>();

    @Override
    public boolean salvarConta(ContaBancaria conta) {
        contas.put(conta.getNumeroDaConta(), conta);
        return true;
    }

    @Override
    public int criarConta(Cliente titular) {
        ContaBancaria conta = new ContaBancaria(titular, gerarNumeroContas());
        salvarConta(conta);
        return conta.getNumeroDaConta();
    }

    //Buscar conta
    @Override
    public ContaBancaria buscarContaBancariaPorNumero(int numeroConta) {
        ContaBancaria contaBuscada = contas.get(numeroConta);

        if (contaBuscada == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada");
        }

        return contaBuscada;
    }

    @Override
    public int quantidadeDeContas() {
        return contas.size();
    }

// Gerar numero aleatorio para cada conta
    @Override
    public int gerarNumeroContas() {
        int numeroGerado;
        do {
            numeroGerado = ThreadLocalRandom.current().nextInt(100000, 1000000);
        } while (numerosDasContas.contains(numeroGerado));

        numerosDasContas.add(numeroGerado);
        return numeroGerado;
    }

    @Override
    public boolean atualizarSaldo(ContaBancaria conta){
        return true;
    }
}

