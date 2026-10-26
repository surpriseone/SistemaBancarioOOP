

 package br.com.sistemabancario.application;

 import br.com.sistemabancario.entities.Cliente;
 import br.com.sistemabancario.factory.ConnectionFactory;
 import br.com.sistemabancario.objectvalues.CPF;
 import br.com.sistemabancario.objectvalues.Email;
 import br.com.sistemabancario.repositories.Intefaces.ClienteRepository;
 import br.com.sistemabancario.repositories.Intefaces.ContaRepository;
import br.com.sistemabancario.repositories.Intefaces.TransacaoRepository;
 import br.com.sistemabancario.repositories.SQL.ClienteRepositorySQL;
 import br.com.sistemabancario.repositories.SQL.ContaRepositorySQL;
import br.com.sistemabancario.repositories.SQL.TransacaoRepositorySQL;
import br.com.sistemabancario.services.SistemaBancario;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteSql {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        try(Connection connection = factory.recuperarConexao()){
            ClienteRepository clienteSql = new ClienteRepositorySQL(connection);
            ContaRepository contaSql = new ContaRepositorySQL(connection, clienteSql);
            TransacaoRepository transacaoSql = new TransacaoRepositorySQL(connection, contaSql);
            SistemaBancario sistema = new SistemaBancario(contaSql, transacaoSql, connection, clienteSql);

            String nome = "Joana";
            CPF cpf = CPF.of("22233344456");
            Email email = Email.of("joana@gmail.com");
            Cliente cliente = new Cliente(nome, cpf, email);
            int id_gerado = sistema.cadastrarCliente(cliente);
            System.out.println(id_gerado);
            sistema.sistemaCriarConta(Cliente.reconstituirCliente(id_gerado, nome, cpf, email));
            System.out.println();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}