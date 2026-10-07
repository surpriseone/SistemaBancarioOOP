package br.com.sistemabancario.services;
import br.com.sistemabancario.entities.Cliente;
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.entities.Transacao;
import br.com.sistemabancario.exceptions.ErroDeTransferenciaException;
import br.com.sistemabancario.objectvalues.CPF;
import br.com.sistemabancario.objectvalues.Dinheiro;
import br.com.sistemabancario.repositories.Intefaces.ContaRepository;
import br.com.sistemabancario.repositories.Intefaces.TransacaoRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class SistemaBancario {

    private ContaRepository contaRepositorio;
    private TransacaoRepository transacaoRepositorio;
    private Connection connection;

    public SistemaBancario(ContaRepository bancoRepo, TransacaoRepository transacaoRepo, Connection connection){
        this.contaRepositorio = bancoRepo;
        this.transacaoRepositorio = transacaoRepo;
        this.connection = connection;
    }

    public ContaRepository getRepositorio(){
        return this.contaRepositorio;
    }

    public void tranferencia(int numeroContaOrigem, int numeroContaDestino, Dinheiro valorTranferencia){

        try{
            prepararTransacao();

            ContaBancaria contaOrigem = contaRepositorio.buscarContaBancariaPorNumero(numeroContaOrigem);
            ContaBancaria contaDestino = contaRepositorio.buscarContaBancariaPorNumero(numeroContaDestino);

            contaOrigem.transferir(contaDestino, valorTranferencia);

            Integer IDTransacao = IDTransacao();
            Transacao transferencia = Transacao.novaTransferencia(IDTransacao, contaOrigem, contaDestino, valorTranferencia);
            contaOrigem.adicionarNoExtrato(transferencia);
            contaDestino.adicionarNoExtrato(transferencia);
            transacaoRepositorio.salvarTransacao(transferencia);
            contaRepositorio.atualizarSaldo(contaOrigem);
            contaRepositorio.atualizarSaldo(contaDestino);

            confirmarAlteracoes();
        } catch (RuntimeException e) {
            cancelarTransacao();
            throw e;
        } catch (SQLException e){
            cancelarTransacao();
        } finally {
            try {
               religarAutoCommit();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    public void sacar(int numeroDaConta, Dinheiro valorSaque){
        try{
            prepararTransacao();

            ContaBancaria contaRequestSaque = contaRepositorio.buscarContaBancariaPorNumero(numeroDaConta);
            contaRequestSaque.sacar(valorSaque);

            Integer IDSaque = IDTransacao();
            Transacao saque = Transacao.novoSaque(IDSaque, contaRequestSaque, valorSaque);
            contaRequestSaque.adicionarNoExtrato(saque);
            transacaoRepositorio.salvarTransacao(saque);
            contaRepositorio.atualizarSaldo(contaRequestSaque);

            confirmarAlteracoes();
        } catch (RuntimeException e) {
            cancelarTransacao();
            throw e;
        } catch (SQLException e){
            cancelarTransacao();
        } finally {
            try{
                religarAutoCommit();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }

    }


    public void depositar(int numeroDaConta, Dinheiro valorDeposito){
        try {
            prepararTransacao();

            ContaBancaria contaRequestDeposito = contaRepositorio.buscarContaBancariaPorNumero(numeroDaConta);
            contaRequestDeposito.depositar(valorDeposito);

            Integer IDDeposito = IDTransacao();
            Transacao deposito = Transacao.novoDeposito(IDDeposito, contaRequestDeposito, valorDeposito);
            contaRequestDeposito.adicionarNoExtrato(deposito);
            transacaoRepositorio.salvarTransacao(deposito);
            contaRepositorio.atualizarSaldo(contaRequestDeposito);

            confirmarAlteracoes();
        } catch (RuntimeException e) {
            cancelarTransacao();
            throw e;
        } catch (SQLException e){
            cancelarTransacao();
        } finally {
            try{
                religarAutoCommit();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

    }

    public int sistemaCriarConta(Cliente cliente){
        return contaRepositorio.criarConta(cliente);
    }

    public int sistemaCriarContaComDepositoInicial(Cliente cliente, Dinheiro depositoInicial) {
        int numeroConta = sistemaCriarConta(cliente);
        depositar(numeroConta, depositoInicial);

        return numeroConta;
    }

    private Integer IDTransacao(){
        return transacaoRepositorio.gerarCodigoTransacao();
    }

    private void prepararTransacao() throws SQLException{
        if(connection != null){
            connection.setAutoCommit(false);
        }
    }

    private void confirmarAlteracoes() throws SQLException{
        if(connection != null){
            connection.commit();
        }
    }

    private void cancelarTransacao() {
        try {
            if(connection != null){
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new ErroDeTransferenciaException("Algo deu errado! " + e);
        }
    }

    private void religarAutoCommit() throws SQLException{
        if(connection != null){
            connection.setAutoCommit(true);
        }
    }

}



