/***

package br.com.sistemabancario.application;

import br.com.sistemabancario.factory.ConnectionFactory;
import br.com.sistemabancario.repositories.Intefaces.ClienteRepository;
import br.com.sistemabancario.repositories.Intefaces.TransacaoRepository;
import br.com.sistemabancario.repositories.Memory.ContaMemory;
import br.com.sistemabancario.repositories.SQL.ClienteRepositorySQL;
import br.com.sistemabancario.repositories.SQL.ContaRepositorySQL;
import br.com.sistemabancario.repositories.SQL.TransacaoRepositorySQL;
import br.com.sistemabancario.services.SistemaBancario;
import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.exceptions.SaldoInsuficienteException;
import br.com.sistemabancario.exceptions.TranferirParaMesmaContaException;
import br.com.sistemabancario.exceptions.ValorInvalidoException;
import br.com.sistemabancario.objectvalues.Dinheiro;
import br.com.sistemabancario.repositories.Intefaces.ContaRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Scanner;
import java.util.Locale;


public class Main {
    public static void main(String[] args){
        Locale.setDefault(Locale.US);


        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.recuperarConexao();
        ClienteRepository clienteRepository = new ClienteRepositorySQL();
        ContaRepository NewBank = new ContaRepositorySQL(connection, clienteRepository);
        TransacaoRepository transacaoRepository = new TransacaoRepositorySQL(connection, NewBank);
        SistemaBancario sistemaBancario = new SistemaBancario(NewBank, transacaoRepository, connection);
        sistemaBancario.sistemaCriarContaComDepositoInicial("Jose", "12345678910",Dinheiro.NOVO(new BigDecimal(600)));
        sistemaBancario.sistemaCriarContaComDepositoInicial("Felype", "12345678910",Dinheiro.NOVO(new BigDecimal(200)));
        sistemaBancario.sistemaCriarContaComDepositoInicial("Maycon", "12345678910",Dinheiro.NOVO(new BigDecimal(1000)));
        sistemaBancario.sistemaCriarContaComDepositoInicial("Luis", "12345678910",Dinheiro.NOVO(new BigDecimal(2000)));
        sistemaBancario.sistemaCriarContaComDepositoInicial("Marta", "12345678910",Dinheiro.NOVO(new BigDecimal(3000)));
        sistemaBancario.sistemaCriarContaComDepositoInicial("Neymar", "12345678910",Dinheiro.NOVO(new BigDecimal(20000)));


        Scanner sc = new Scanner(System.in);
        String nome;
        BigDecimal valorDeposito;
        char option;

        int contaUsuario;
        System.out.println("Bem vindo ao Test Bank");
        System.out.println("Qual o nome do titular da conta?: ");
        nome = sc.nextLine();
        System.out.println("Você deseja fazer um deposito inicial? S/N ");
        option = sc.next().charAt(0);
        if (Character.toUpperCase(option) == 'S'){
            System.out.println("Digite o valor do deposito: ");
            valorDeposito = sc.nextBigDecimal();
            contaUsuario = sistemaBancario.sistemaCriarContaComDepositoInicial(nome, "12345678910",Dinheiro.NOVO(valorDeposito));
        }
        else{
            contaUsuario = sistemaBancario.sistemaCriarConta(nome, "12345678910");
        }

        int execucao;
        BigDecimal valorSaque;
        BigDecimal valorTranferencia;
        int numeroContaDestino;


        do {
            System.out.printf(
                    "O que você precisa?%n" +
                            "1 - Depositar%n" +
                            "2 - Sacar%n" +
                            "3 - Transferir%n" +
                            "4 - Ver dados da conta%n" +
                            "5 - Consultar extrato%n" +
                            "6 - sair%n"
            );
            execucao = sc.nextInt();

            switch (execucao){
                case 1:
                    System.out.println("Valor do deposito: ");
                    try {
                        valorDeposito = sc.nextBigDecimal();
                        sistemaBancario.depositar(contaUsuario, Dinheiro.NOVO(valorDeposito));
                    } catch (ValorInvalidoException e) {
                        System.out.println("Error 400: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Valor do saque: ");
                    try {
                        valorSaque = sc.nextBigDecimal();
                        sistemaBancario.sacar(contaUsuario, Dinheiro.NOVO(valorSaque));
                    } catch (ValorInvalidoException e) {
                        System.out.println("Error 400: " + e.getMessage());
                    } catch (SaldoInsuficienteException e) {
                        System.out.println("Error 422: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Digite o numero da conta que você vai realizar a tranferencia");
                        numeroContaDestino = sc.nextInt();
                        System.out.println("Digite o valor da tranferencia: ");
                        valorTranferencia = sc.nextBigDecimal();
                        sistemaBancario.tranferencia(contaUsuario, numeroContaDestino, Dinheiro.NOVO(valorTranferencia));
                    } catch (ValorInvalidoException e) {
                        System.out.println("Error 400: " + e.getMessage());
                    } catch (SaldoInsuficienteException | TranferirParaMesmaContaException e) {
                        System.out.println("Error 422: " + e.getMessage());
                    } catch (ContaNaoEncontradaException e) {
                        System.out.println("Error 404: " + e.getMessage());
                    }

                    break;
                case 4:
                    System.out.println(NewBank.buscarContaBancariaPorNumero(contaUsuario));
                    break;
                case 5:
                    NewBank.buscarContaBancariaPorNumero(contaUsuario).imprimirExtrato();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opção invalida");
                    break;
            }
        } while (execucao != 6);
        sc.close();
    }
}

 ***/


