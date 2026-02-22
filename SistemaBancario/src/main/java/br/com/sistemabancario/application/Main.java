package br.com.sistemabancario.application;




import br.com.sistemabancario.entities.Banco;
import br.com.sistemabancario.entities.SistemaBancario;
import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.exceptions.SaldoInsuficienteException;
import br.com.sistemabancario.exceptions.TranferirParaMesmaContaException;
import br.com.sistemabancario.exceptions.ValorInvalidoException;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Locale;


public class Main {
    public static void main(String[] args){
        Locale.setDefault(Locale.US);
        Banco NewBank = new Banco("New Bank");
        SistemaBancario sistemaNewBank = new SistemaBancario(NewBank);
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Jose", new BigDecimal(600));
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Felype", new BigDecimal(200));
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Maycon", new BigDecimal(1000));
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Luis", new BigDecimal(2000));
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Marta", new BigDecimal(3000));
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Neymar", new BigDecimal(20000));


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
            contaUsuario = sistemaNewBank.sistemaCriarContaComDepositoInicial(nome, valorDeposito);
        }
        else{
            contaUsuario = sistemaNewBank.sistemaCriarConta(nome);
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
                        sistemaNewBank.depositar(contaUsuario, valorDeposito);
                    } catch (ValorInvalidoException e) {
                        System.out.println("Error 400: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Valor do saque: ");
                    try {
                        valorSaque = sc.nextBigDecimal();
                        sistemaNewBank.sacar(contaUsuario, valorSaque);
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
                        sistemaNewBank.tranferencia(contaUsuario, numeroContaDestino, valorTranferencia);
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


