package br.com.sistemabancario.repositories.SQL;

import br.com.sistemabancario.entities.Cliente;
import br.com.sistemabancario.exceptions.ClienteNaoEncontradoException;
import br.com.sistemabancario.exceptions.DataBaseException;
import br.com.sistemabancario.exceptions.FalhaRecuperacaoDadosException;
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

    private static final String EMAIL_COLUNA = "email";
    private static final String CLIENTE_ID_COLUNA = "cliente_id";
    private static final String NOME_COLUNA = "nome";
    private static final String CPF_COLUNA = "cpf";

    @Override
    public Cliente buscarClientePorCpf(CPF cpf) {
        String cpfString = cpf.valor();
        String sql = "Select cliente_id, nome, email from Clientes WHERE cpf = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, cpfString);
            try(ResultSet result = stmt.executeQuery()) {
                if(result.next()){
                    int clienteID = result.getInt(CLIENTE_ID_COLUNA);
                    String nomeCliente = result.getString(NOME_COLUNA);
                    String emailCliente = result.getString(EMAIL_COLUNA);

                    Email emailVO = Email.of(emailCliente);
                    return Cliente.reconstituirCliente(clienteID, nomeCliente, cpf, emailVO);
                }
            }
        }catch (SQLException e) {
            throw new DataBaseException("Erro ao buscar cliente no banco");
        }
        
        throw new ClienteNaoEncontradoException("Cliente não encontrado");
    }

    @Override
    public Cliente buscarClientePorId(int id) {
        String sql = "Select nome, cpf, email from clientes WHERE cliente_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            try(ResultSet result = stmt.executeQuery()) {
                if(result.next()){
                    String nomeCliente = result.getString(NOME_COLUNA);
                    String cpfCliente = result.getString(CPF_COLUNA);
                    String emailCliente = result.getString(EMAIL_COLUNA);

                    CPF cpfVO = CPF.of(cpfCliente);
                    Email emailVO = Email.of(emailCliente);
                    return Cliente.reconstituirCliente(id, nomeCliente, cpfVO, emailVO);
                }
            }
        }catch (SQLException e) {
            throw new DataBaseException("Erro ao buscar cliente no banco");
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado");
    }

    @Override
    public int salvarCliente(Cliente cliente){
        String nomeCliente = cliente.getNome();
        String cpfCliente = cliente.getCpf().valor();
        String emailCliente = cliente.getEmail().valor();
        String sql = "INSERT INTO clientes (nome, cpf, email) values (?, ?, ?) RETURNING cliente_id";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, nomeCliente);
            stmt.setString(2, cpfCliente);
            stmt.setString(3, emailCliente);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt(CLIENTE_ID_COLUNA);
                }
            }

        } catch (SQLException e) {
            throw new DataBaseException("Erro ao salvar cliente no banco " + e);
        }
        throw new FalhaRecuperacaoDadosException("Nao foi possivel recuperar o ID do cliente recem salvo");
    }

    @Override
    public void atualizarEmail(int clienteID, Email email) {
        String emailString = email.valor();
        String sql = "UPDATE clientes SET email = ? WHERE cliente_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, emailString);
            stmt.setInt(2, clienteID);
            stmt.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao atualizar email no banco");
        }
    }
}
