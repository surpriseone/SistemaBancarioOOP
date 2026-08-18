package br.com.sistemabancario.repositories.Memory;

import br.com.sistemabancario.entities.Transacao;
import br.com.sistemabancario.exceptions.TransacaoNaoEncontradaException;
import br.com.sistemabancario.repositories.Intefaces.TransacaoRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TransacaoRepositoryMemory implements TransacaoRepository {

    private Set<Integer> IDTransacoes = new HashSet<>();
    private Map<Integer, Transacao> transacoes = new HashMap<>();

    @Override
    public Integer gerarCodigoTransacao() {
        int IDGerado;
        do {
            IDGerado = ThreadLocalRandom.current().nextInt(1000, 10000);
        } while (IDTransacoes.contains(IDGerado));

        IDTransacoes.add(IDGerado);
        return IDGerado;
    }

    @Override
    public void salvarTransacao(Transacao transacao) {
        transacoes.put(transacao.getNumeroTransacao(), transacao);
    }

    @Override
    public Transacao buscarTransferencia(Integer codigoTransacao) {
        Transacao transacaoBuscada = transacoes.get(codigoTransacao);
        if (transacaoBuscada == null) {
            throw new TransacaoNaoEncontradaException("Transação não encontrada");
        }
        return transacaoBuscada;
    }
}
