package br.com.sistemabancario.factory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private String url = "jdbc:mysql://172.18.158.87:3306/sistema_bancario";

    private String usuario = "felype_java";

    private String senha = "Susu003!";

    public Connection recuperarConexao() {
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco!", e);
        }
    }
}
