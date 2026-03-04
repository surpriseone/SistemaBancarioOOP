package br.com.sistemabancario.entities;

import br.com.sistemabancario.objectvalues.Dinheiro;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Transacao {

    private final Integer ID;
    private final TipoTransacao tipoDeTransacao;
    private final Dinheiro valor;
    private final ContaBancaria contaOrigem;
    private final ContaBancaria contaDestino;
    private final LocalDateTime dataHora;

   private Transacao(Integer ID,
                     TipoTransacao tipo,
                     Dinheiro valorParametro,
                     ContaBancaria contaOrigem,
                     ContaBancaria contaDestino) {

        this.ID = ID;
        this.tipoDeTransacao = tipo;
        this.valor = valorParametro;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.dataHora = LocalDateTime.now();
    }

    public TipoTransacao getTipoDeTransacao() {
        return tipoDeTransacao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Dinheiro getValor() {
        return valor;
    }

    public String formatarParaExtrato(ContaBancaria contaQuePuxouExtrato) {

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = dataHora.format(formatador);

        switch (this.tipoDeTransacao) {
            case DEPOSITO:
                return dataFormatada + " | Deposito de + " + valor;

            case SAQUE:
                return dataFormatada + " | Saque de - " + valor;

            case TRANSFERENCIA:
                if (contaQuePuxouExtrato.getNumeroDaConta() == contaOrigem.getNumeroDaConta()) {
                    return dataFormatada + " | Transferência de - " + valor + " enviada para " + contaDestino.getNomeTitular();
                }
                if (contaQuePuxouExtrato.getNumeroDaConta() == contaDestino.getNumeroDaConta()) {
                    return dataFormatada + " | Transferência de + " + valor + " recebida de " + contaOrigem.getNomeTitular();
                }
            default:
                return "Transação invalida";
        }
    }


    public static Transacao novaTransferencia(Integer ID, ContaBancaria contaOrigem, ContaBancaria contaDestino, Dinheiro valorParametro) {
        return new Transacao(ID, TipoTransacao.TRANSFERENCIA, valorParametro, contaOrigem, contaDestino);
    }

    public static Transacao novoDeposito(Integer ID, ContaBancaria contaDestino, Dinheiro valorParametro) {
        return new Transacao(ID, TipoTransacao.DEPOSITO, valorParametro, null, contaDestino);
    }

    public static Transacao novoSaque(Integer ID, ContaBancaria contaOrigem, Dinheiro valorParametro) {
        return new Transacao(ID, TipoTransacao.SAQUE, valorParametro, contaOrigem, null);
    }
}