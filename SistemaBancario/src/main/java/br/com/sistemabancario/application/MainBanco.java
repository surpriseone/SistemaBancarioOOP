/***

package br.com.sistemabancario.application;

import br.com.sistemabancario.repositories.Intefaces.TransacaoRepository;
import br.com.sistemabancario.repositories.Memory.ContaMemory;
import br.com.sistemabancario.repositories.Memory.TransacaoRepositoryMemory;
import br.com.sistemabancario.services.SistemaBancario;
import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.objectvalues.Dinheiro;

import java.math.BigDecimal;
import java.util.Scanner;


public class MainBanco {
    public static void main(String[] args) {

        ContaMemory bancoMemoriaA = new ContaMemory();
        TransacaoRepositoryMemory transacaoRepositoryMemory = new TransacaoRepositoryMemory();
        SistemaBancario sistemaBancoA = new SistemaBancario(bancoMemoriaA, transacaoRepositoryMemory, null);
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Jose", "12345678910", Dinheiro.NOVO(new BigDecimal(600)));
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Felype", "12345678910",Dinheiro.NOVO(new BigDecimal(200)));
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Maycon", "12345678910",Dinheiro.NOVO(new BigDecimal(1000)));
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Luis", "12345678910",Dinheiro.NOVO(new BigDecimal(2000)));
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Marta", "12345678910",Dinheiro.NOVO(new BigDecimal(3000)));
        sistemaBancoA.sistemaCriarContaComDepositoInicial("Neymar", "12345678910",Dinheiro.NOVO(new BigDecimal(20000)));


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
                case 3:
                    break;
                default:
                    System.out.println("Opção invalida");
            }


        } while (option != 3);

    }
}
***/