package br.com.sistemabancario.repositories.SQL;

import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.exceptions.DataBaseException;
import br.com.sistemabancario.objectvalues.CPF;
import br.com.sistemabancario.objectvalues.Dinheiro;
import br.com.sistemabancario.repositories.Intefaces.ContaRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class ContaRepositorySQL implements ContaRepository {
    Connection connection;

    public ContaRepositorySQL(Connection conection) {
        this.connection = conection;
    }

    @Override
    public boolean salvarConta(ContaBancaria conta) {
        String sql = "INSERT INTO contas (numero_conta, CPF, titular, saldo) VALUES(?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, conta.getNumeroDaConta());
            stmt.setString(2, conta.getCPF().valor());
            stmt.setString(3, conta.getNomeTitular());
            stmt.setBigDecimal(4, conta.getSaldo().getValor());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao salvar conta!");
        }
    }

    @Override
    public int criarConta(String nomeTitular, CPF cpf) {
        ContaBancaria conta = new ContaBancaria(nomeTitular, gerarNumeroContas(), cpf);
        salvarConta(conta);
        return conta.getNumeroDaConta();
    }

    @Override
    public ContaBancaria buscarContaBancariaPorNumero(int numeroConta) {
        String sql = "SELECT * FROM contas WHERE numero_conta = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numeroConta);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    String nomeTitular = result.getString("titular");
                    String cpfSql = result.getString("CPF");
                    int numeroDaConta = result.getInt("numero_conta");
                    BigDecimal saldo = result.getBigDecimal("saldo");

                    Dinheiro saldoAtual = Dinheiro.NOVO(saldo);
                    CPF cpf = CPF.of(cpfSql);

                    return ContaBancaria.reconstituirConta(nomeTitular, numeroDaConta, cpf, saldoAtual);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao buscar conta no banco");
        }

        throw new ContaNaoEncontradaException("Conta não encontrada");
    }

    @Override
    public int quantidadeDeContas() {
        String sql = "SELECT(*) FROM contas";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao buscar a quantidade de contas!");
        }

        return 0;
    }

    @Override
    public int gerarNumeroContas() {
        int numeroGerado;
        boolean numeroExiste;

        do {
            String sql = "SELECT COUNT(*) FROM contas WHERE numero_conta = ?";
            numeroGerado = ThreadLocalRandom.current().nextInt(100000, 1000000);
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, numeroGerado);

                try (ResultSet result = stmt.executeQuery()) {
                    result.next();
                    numeroExiste = result.getInt(1) > 0;
                }

            } catch (SQLException e) {
                throw new DataBaseException("Erro ao gerar numero da conta");
            }

        } while (numeroExiste);

        return numeroGerado;
    }

    @Override
    public boolean atualizarSaldo(ContaBancaria conta){
        String sql = "UPDATE contas set saldo = ? where numero_conta = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setBigDecimal(1, conta.getSaldo().getValor());
            stmt.setInt(2, conta.getNumeroDaConta());

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas == 0){
                throw new ContaNaoEncontradaException("Não foi possivel atualizar o saldo: Conta não existe");
            }

            return true;
        } catch (SQLException e){
            throw new DataBaseException("Ocorreu um erro!");
        }
    }
}