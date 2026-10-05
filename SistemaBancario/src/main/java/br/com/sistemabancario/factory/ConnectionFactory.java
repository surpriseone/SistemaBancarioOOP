package br.com.sistemabancario.factory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private String url = "";

    private String usuario = "";

    private String senha = "";

    public Connection recuperarConexao() {
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco!", e);
        }
    }
}
