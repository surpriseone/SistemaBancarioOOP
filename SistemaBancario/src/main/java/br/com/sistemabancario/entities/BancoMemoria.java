package br.com.sistemabancario.entities;

import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.exceptions.TransacaoNaoEncontradaException;
import br.com.sistemabancario.repositories.BancoRepository;

import java.util.Collections;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class BancoMemoria implements BancoRepository {

    private String nomeBanco;
    private Map<Integer, ContaBancaria> contas = new HashMap<>();
    private Set<Integer> numerosDasContas = new HashSet<>();
    private Set<Integer> IDTransacoes = new HashSet<>();
    private Map<Integer, Transacao> transacoes = new HashMap<>();

    public BancoMemoria(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    @Override
    public void adicionarConta(ContaBancaria conta) {
        contas.put(conta.getNumeroDaConta(), conta);
    }

    // Conta sem deposito inicial
    @Override
    public int criarConta(String nomeTitular) {
        ContaBancaria conta = new ContaBancaria(nomeTitular, gerarNumeroContas());
        adicionarConta(conta);
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

    @Override
    public Collection<ContaBancaria> getContas() {
         return Collections.unmodifiableCollection(contas.values());
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
    public Integer gerarIdTransacao(){
        int IDGerado;
        do{
            IDGerado = ThreadLocalRandom.current().nextInt(1000, 10000);
        } while(IDTransacoes.contains(IDGerado));

        IDTransacoes.add(IDGerado);
        return IDGerado;
    }

    @Override
    public void salvarTransacao(Integer ID, Transacao transacao){
        transacoes.put(ID, transacao);
    }

    @Override
    public Transacao buscarTransferencia(Integer ID){
        Transacao transacaoBuscada = transacoes.get(ID);
        if(transacaoBuscada == null){
            throw new TransacaoNaoEncontradaException("Transação não encontrada");
        }
        return transacaoBuscada;
    }
}
