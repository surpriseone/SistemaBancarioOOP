package br.com.sistemabancario.repositories.SQL;

import br.com.sistemabancario.entities.Cliente;
import br.com.sistemabancario.exceptions.ClienteNaoEncontradoException;
import br.com.sistemabancario.exceptions.DataBaseException;
import br.com.sistemabancario.objectvalues.CPF;
import br.com.sistemabancario.objectvalues.Email;
import br.com.sistemabancario.repositories.Intefaces.ClienteRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRepositorySQL implements ClienteRepository {

    Connection connection;

    public ClienteRepositorySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Cliente buscarClientePorCpf(CPF cpf) {
        String cpfString = cpf.valor();
        String sql = "Select cliente_id, nome_cliente, email_cliente from Clientes WHERE cpf_cliente = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, cpfString);
            try(ResultSet result = stmt.executeQuery()) {
                if(result.next()){
                    int clienteID = result.getInt("cliente_id");
                    String nomeCliente = result.getString("nome_cliente");
                    String emailCliente = result.getString("email_cliente");

                    Email emailVO = Email.of(emailCliente);
                    return Cliente.reconstituirCliente(clienteID, nomeCliente, cpf, emailVO);
                }
            }
        }catch (SQLException e) {
            throw new DataBaseException("Erro ao buscar cliente no banco");
        };

        throw new ClienteNaoEncontradoException("Cliente não encontrado");
    }

    public Cliente buscarClientePorId(int id) {
        String sql = "Select nome_cliente, cpf_cliente, email_cliente from Clientes WHERE cliente_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            try(ResultSet result = stmt.executeQuery()) {
                if(result.next()){
                    String nomeCliente = result.getString("nome_cliente");
                    String cpfCliente = result.getString("cpf_cliente");
                    String emailCliente = result.getString("email_cliente");

                    CPF cpfVO = CPF.of(cpfCliente);
                    Email emailVO = Email.of(emailCliente);
                    Cliente.reconstituirCliente(id, nomeCliente, cpfVO, emailVO);
                }
            }
        }catch (SQLException e) {
            throw new DataBaseException("Erro ao buscar cliente no banco");
        };

        throw new ClienteNaoEncontradoException("Cliente não encontrado");
    }
}
