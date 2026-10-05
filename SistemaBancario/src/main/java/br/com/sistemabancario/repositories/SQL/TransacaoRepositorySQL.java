package br.com.sistemabancario.repositories.SQL;

import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.entities.TipoTransacao;
import br.com.sistemabancario.entities.Transacao;
import br.com.sistemabancario.exceptions.DataBaseException;
import br.com.sistemabancario.exceptions.TransacaoNaoEncontradaException;
import br.com.sistemabancario.objectvalues.Dinheiro;
import br.com.sistemabancario.repositories.Intefaces.ContaRepository;
import br.com.sistemabancario.repositories.Intefaces.TransacaoRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class TransacaoRepositorySQL implements TransacaoRepository {

    private ContaRepository contaRepositorio;
    private Connection conection;

    public TransacaoRepositorySQL(Connection connection, ContaRepository contaRepositorio) {
        this.conection = connection;
        this.contaRepositorio = contaRepositorio;
    }

    @Override
    public Integer gerarCodigoTransacao(){
        int numeroGerado;
        boolean numeroExiste;

        do{
            String sql = "SELECT COUNT(*) FROM transacoes WHERE codigo_transacao = ?";
            numeroGerado = ThreadLocalRandom.current().nextInt(100000, 1000000);
            try(PreparedStatement stmt = conection.prepareStatement(sql)){
                stmt.setInt(1, numeroGerado);

                try(ResultSet result = stmt.executeQuery()){
                    result.next();
                    numeroExiste = result.getInt(1) > 0;
                }

            }catch (SQLException e){
                throw new DataBaseException("Erro ao gerar numero da transação");
            }

        }while(numeroExiste);

        return numeroGerado;
    }

    @Override
    public void salvarTransacao(Transacao transacao){

        String sql = "INSERT INTO transacoes (codigo_transacao, tipo, valor, " +
                "conta_origem_id, conta_destino_id) VALUES(?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = conection.prepareStatement(sql)){
            stmt.setInt(1, transacao.getNumeroTransacao());
            stmt.setString(2, transacao.getTipoDeTransacao().name());
            stmt.setBigDecimal(3, transacao.getValorTransacao().getValor());
            stmt.setInt(4, transacao.getContaOrigem().getNumeroDaConta());

            if(transacao.getContaDestino() != null){
                stmt.setInt(5, transacao.getContaDestino().getNumeroDaConta());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();

        }catch (SQLException e){
            throw new DataBaseException("Erro ao salvar Transação");
        }
    }

    @Override
    public Transacao buscarTransferencia(Integer codigoTransacao){
        String sql = "SELECT * from transacoes where codigo_transacao = ?";

        try(PreparedStatement stmt = conection.prepareStatement(sql)){
            stmt.setInt(1, codigoTransacao);

            try(ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    Integer codigo_transacao = result.getInt("codigo_transacao");
                    String tipoBanco = result.getString("tipo");
                    BigDecimal valor = result.getBigDecimal("valor");
                    LocalDateTime data = result.getObject("data_hora", LocalDateTime.class);
                    int contaOrigemNumero = result.getInt("conta_origem_id");
                    ContaBancaria contaOrigem = contaRepositorio.buscarContaBancariaPorNumero(contaOrigemNumero);

                    int contaDestinoNumero = result.getInt("conta_destino_id");
                    ContaBancaria contaDestino = null;

                    if (!result.wasNull()) {
                        contaDestino = contaRepositorio.buscarContaBancariaPorNumero(contaDestinoNumero);
                    }

                    Dinheiro valorDinheiro = Dinheiro.NOVO(valor);
                    TipoTransacao tipo = TipoTransacao.valueOf(tipoBanco);

                    Transacao transacao = Transacao.reconstituirTransacao(
                            codigo_transacao, tipo, valorDinheiro, contaOrigem, contaDestino, data
                    );

                    return transacao;
                }
            }
        }catch (SQLException e){
            throw new DataBaseException("Erro ao buscar transação");
        }
        throw new TransacaoNaoEncontradaException("Transação não encontrada");
    }

}


