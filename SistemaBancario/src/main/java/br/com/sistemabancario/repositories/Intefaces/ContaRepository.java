package br.com.sistemabancario.repositories.Intefaces;
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.entities.Transacao;
import br.com.sistemabancario.objectvalues.CPF;


public interface ContaRepository {

    boolean salvarConta(ContaBancaria conta);
    int criarConta(String nomeTitular, CPF cpf);
    ContaBancaria buscarContaBancariaPorNumero(int numeroConta);
    int quantidadeDeContas();
    int gerarNumeroContas();
    boolean atualizarSaldo(ContaBancaria conta);
}
