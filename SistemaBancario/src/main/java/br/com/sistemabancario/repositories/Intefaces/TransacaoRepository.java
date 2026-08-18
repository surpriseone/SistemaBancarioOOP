package br.com.sistemabancario.repositories.Intefaces;

import br.com.sistemabancario.entities.Transacao;

public interface TransacaoRepository {

    Integer gerarCodigoTransacao();
    void salvarTransacao(Transacao transacao);
    Transacao buscarTransferencia(Integer codigoTransacao);

}
