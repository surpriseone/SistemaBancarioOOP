package br.com.sistemabancario.entities;

import br.com.sistemabancario.objectvalues.Dinheiro;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Transacao {

    private final Integer CodigoTransacao;
    private final TipoTransacao tipoDeTransacao;
    private final Dinheiro valor;
    private final ContaBancaria contaOrigem;
    private final ContaBancaria contaDestino;
    private final LocalDateTime dataHora;

   private Transacao(Integer codigoTransacao,
                     TipoTransacao tipo,
                     Dinheiro valorParametro,
                     ContaBancaria contaOrigem,
                     ContaBancaria contaDestino) {

        this.CodigoTransacao = codigoTransacao;
        this.tipoDeTransacao = tipo;
        this.valor = valorParametro;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.dataHora = LocalDateTime.now();
    }

    public Integer getNumeroTransacao(){
       return this.CodigoTransacao;
    }
    public TipoTransacao getTipoDeTransacao() {
        return tipoDeTransacao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Dinheiro getValorTransacao() {
        return valor;
    }

    public ContaBancaria getContaDestino() {
        return contaDestino;
    }

    public ContaBancaria getContaOrigem() {
        return contaOrigem;
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

    public static Transacao novoDeposito(Integer ID, ContaBancaria contaOrigem, Dinheiro valorParametro) {
        return new Transacao(ID, TipoTransacao.DEPOSITO, valorParametro, contaOrigem, null);
    }

    public static Transacao novoSaque(Integer ID, ContaBancaria contaOrigem, Dinheiro valorParametro) {
        return new Transacao(ID, TipoTransacao.SAQUE, valorParametro, contaOrigem, null);
    }

//Reconstituir transação vinda do banco
    private Transacao(Integer codigoTransacao,
                     TipoTransacao tipo,
                     Dinheiro valorParametro,
                     ContaBancaria contaOrigem,
                     ContaBancaria contaDestino,
                     LocalDateTime data) {

        this.CodigoTransacao = codigoTransacao;
        this.tipoDeTransacao = tipo;
        this.valor = valorParametro;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.dataHora = data;
    }

    public static Transacao reconstituirTransacao(Integer codigoTransacao,
                                                  TipoTransacao tipo,
                                                  Dinheiro valorParametro,
                                                  ContaBancaria contaOrigem,
                                                  ContaBancaria contaDestino,
                                                  LocalDateTime data) {

       return new Transacao(codigoTransacao, tipo, valorParametro, contaOrigem, contaDestino, data);
    }
}