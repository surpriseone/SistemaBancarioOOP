package br.com.sistemabancario.repositories;
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.entities.Transacao;

import java.util.Collection;

public interface BancoRepository {

    //Metodos da conta
    void adicionarConta(ContaBancaria conta);
    int criarConta(String nomeTitular);
    ContaBancaria buscarContaBancariaPorNumero(int numeroConta);
    int quantidadeDeContas();
    Collection<ContaBancaria> getContas();
    int gerarNumeroContas();

    //Metodos de transação
    Integer gerarIdTransacao();
    void salvarTransacao(Integer ID, Transacao transacao);
    Transacao buscarTransferencia(Integer ID);
}
