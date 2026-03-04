package br.com.sistemabancario.entities;
import br.com.sistemabancario.objectvalues.Dinheiro;
import br.com.sistemabancario.repositories.BancoRepository;

public class SistemaBancario {

    private BancoRepository repositorio;

    public SistemaBancario(BancoRepository nomeBanco){
        this.repositorio = nomeBanco;
    }

    public BancoRepository getRepositorio(){
        return this.repositorio;
    }

    public void tranferencia(int numeroContaOrigem, int numeroContaDestino, Dinheiro valorTranferencia){
        ContaBancaria contaOrigem = repositorio.buscarContaBancariaPorNumero(numeroContaOrigem);
        ContaBancaria contaDestino = repositorio.buscarContaBancariaPorNumero(numeroContaDestino);

        contaOrigem.transferir(contaDestino, valorTranferencia);

        Integer IDTransacao = IDTransacao();
        Transacao transferencia = Transacao.novaTransferencia(IDTransacao, contaOrigem, contaDestino, valorTranferencia);
        contaOrigem.adicionarNoExtrato(transferencia);
        contaDestino.adicionarNoExtrato(transferencia);
        repositorio.salvarTransacao(IDTransacao, transferencia);
    }

    public void sacar(int numeroDaConta, Dinheiro valorSaque){
        ContaBancaria contaRequestSaque = repositorio.buscarContaBancariaPorNumero(numeroDaConta);
        contaRequestSaque.sacar(valorSaque);

        Integer IDSaque = IDTransacao();
        Transacao saque = Transacao.novoSaque(IDSaque, contaRequestSaque, valorSaque);
        contaRequestSaque.adicionarNoExtrato(saque);
        repositorio.salvarTransacao(IDSaque, saque);
    }


    public void depositar(int numeroDaConta, Dinheiro valorDeposito){
        ContaBancaria contaRequestDeposito = repositorio.buscarContaBancariaPorNumero(numeroDaConta);
        contaRequestDeposito.depositar(valorDeposito);

        Integer IDDeposito = IDTransacao();
        Transacao deposito = Transacao.novoDeposito(IDDeposito, contaRequestDeposito, valorDeposito);
        contaRequestDeposito.adicionarNoExtrato(deposito);
        repositorio.salvarTransacao(IDDeposito, deposito);
    }

    public int sistemaCriarConta(String nomeTitular){
        return repositorio.criarConta(nomeTitular);
    }

    public int sistemaCriarContaComDepositoInicial(String nomeTitular, Dinheiro depositoInicial) {
        int numeroConta = sistemaCriarConta(nomeTitular);
        depositar(numeroConta, depositoInicial);

        return numeroConta;
    }

    private Integer IDTransacao(){
        return repositorio.gerarIdTransacao();
    }
}

