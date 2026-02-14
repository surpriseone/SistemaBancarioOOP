package br.com.sistemabancario.application;

import br.com.sistemabancario.entities.Banco;
import br.com.sistemabancario.entities.SistemaBancario;
import br.com.sistemabancario.exceptions.contaNaoEncontradaException;
import java.util.Scanner;


public class MainBanco {
    public static void main(String[] args) {

        Banco bancoA = new Banco("application.Banco New");
        SistemaBancario sistemaBancoA = new SistemaBancario(bancoA);
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Jose", 600);
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Felype", 200);
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Maycon", 1000);
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Luis", 2000);
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Marta", 60000);
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Nicolas", 20000);


        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.printf(
                    "Sistema BancoA : Qual operação ira ser realizada?%n" +
                            "1 - Buscar conta Bancaria por numero%n" +
                            "2 - Listar contas cadastradas no banco%n" +
                            "3 - Sair%n"
            );
            option = sc.nextInt();

            switch (option){
                case 1:
                    System.out.println("Informe o numero da conta procurada: ");

                    try {
                        int numeroContaProcurada = sc.nextInt();
                        System.out.println(bancoA.buscarContaBancariaPorNumero(numeroContaProcurada));
                    } catch (contaNaoEncontradaException e) {
                        System.out.println("Error 404: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println(bancoA.getContas());
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opção invalida");
            }


        } while (option != 3);

    }
}
