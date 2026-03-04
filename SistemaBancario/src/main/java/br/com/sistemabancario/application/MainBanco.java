package br.com.sistemabancario.application;

import br.com.sistemabancario.entities.BancoMemoria;
import br.com.sistemabancario.entities.SistemaBancario;
import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.objectvalues.Dinheiro;

import java.math.BigDecimal;
import java.util.Scanner;


public class MainBanco {
    public static void main(String[] args) {

        BancoMemoria bancoMemoriaA = new BancoMemoria("application.Banco New");
        SistemaBancario sistemaBancoA = new SistemaBancario(bancoMemoriaA);
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Jose", Dinheiro.NOVO(new BigDecimal(600)));
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Felype", Dinheiro.NOVO(new BigDecimal(200)));
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Maycon", Dinheiro.NOVO(new BigDecimal(1000)));
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Luis", Dinheiro.NOVO(new BigDecimal(2000)));
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Marta", Dinheiro.NOVO(new BigDecimal(3000)));
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Neymar", Dinheiro.NOVO(new BigDecimal(20000)));


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
                        System.out.println(bancoMemoriaA.buscarContaBancariaPorNumero(numeroContaProcurada));
                    } catch (ContaNaoEncontradaException e) {
                        System.out.println("Error 404: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println(bancoMemoriaA.getContas());
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opção invalida");
            }


        } while (option != 3);

    }
}
