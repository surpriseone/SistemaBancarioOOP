package br.com.sistemabancario.application;




import br.com.sistemabancario.entities.Banco;
import br.com.sistemabancario.entities.SistemaBancario;
import br.com.sistemabancario.exceptions.contaNaoEncontradaException;
import br.com.sistemabancario.exceptions.saldoInsuficienteException;
import br.com.sistemabancario.exceptions.tranferirParaMesmaContaException;
import br.com.sistemabancario.exceptions.valorInvalidoException;
import java.util.Scanner;



public class Main {
    public static void main(String[] args){

        Banco NewBank = new Banco("New Bank");
        SistemaBancario sistemaNewBank = new SistemaBancario(NewBank);

        sistemaNewBank.sistemaCriarContaComDepositoInicial("Jose", 600);
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Felype", 200);
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Maycon", 1000);
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Luis", 2000);
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Marta", 60000);
        sistemaNewBank.sistemaCriarContaComDepositoInicial("Neymar", 20000);


        Scanner sc = new Scanner(System.in);
        String nome;
        double valorDeposito;
        char option;

        int contaUsuario;
        System.out.println("Bem vindo ao Test Bank");
        System.out.println("Qual o nome do titular da conta?: ");
        nome = sc.nextLine();
        System.out.println("Você deseja fazer um deposito inicial? S/N ");
        option = sc.next().charAt(0);
        if (Character.toUpperCase(option) == 'S'){
            System.out.println("Digite o valor do deposito: ");
            valorDeposito = sc.nextDouble();
            contaUsuario = sistemaNewBank.sistemaCriarContaComDepositoInicial(nome, valorDeposito);
        }
        else{
            contaUsuario = sistemaNewBank.sistemaCriarConta(nome);
        }

        int execucao;
        double valorSaque;
        double valorTranferencia;
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
                        valorDeposito = sc.nextDouble();
                        sistemaNewBank.depositar(contaUsuario, valorDeposito);
                    } catch (valorInvalidoException e) {
                        System.out.println("Error 400: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Valor do saque: ");
                    try {
                        valorSaque = sc.nextDouble();
                        sistemaNewBank.sacar(contaUsuario, valorSaque);
                    } catch (valorInvalidoException e) {
                        System.out.println("Error 400: " + e.getMessage());
                    } catch (saldoInsuficienteException e) {
                        System.out.println("Error 422: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Digite o numero da conta que você vai realizar a tranferencia");
                        numeroContaDestino = sc.nextInt();
                        System.out.println("Digite o valor da tranferencia: ");
                        valorTranferencia = sc.nextDouble();
                        sistemaNewBank.tranferencia(contaUsuario, numeroContaDestino, valorTranferencia);
                    } catch (valorInvalidoException e) {
                        System.out.println("Error 400: " + e.getMessage());
                    } catch (saldoInsuficienteException | tranferirParaMesmaContaException e) {
                        System.out.println("Error 422: " + e.getMessage());
                    } catch (contaNaoEncontradaException e) {
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


