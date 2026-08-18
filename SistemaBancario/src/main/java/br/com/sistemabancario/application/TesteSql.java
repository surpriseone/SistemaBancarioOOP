package br.com.sistemabancario.application;

import br.com.sistemabancario.factory.ConnectionFactory;
import br.com.sistemabancario.repositories.Intefaces.ContaRepository;
import br.com.sistemabancario.repositories.Intefaces.TransacaoRepository;
import br.com.sistemabancario.repositories.SQL.ContaRepositorySQL;
import br.com.sistemabancario.repositories.SQL.TransacaoRepositorySQL;
import br.com.sistemabancario.services.SistemaBancario;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteSql {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        try(Connection connection = factory.recuperarConexao()){
            ContaRepository contaSql = new ContaRepositorySQL(connection);
            TransacaoRepository transacaoSql = new TransacaoRepositorySQL(connection, contaSql);
            SistemaBancario sistema = new SistemaBancario(contaSql, transacaoSql, connection);

            sistema.sistemaCriarConta("Felype", "12345678911");
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
