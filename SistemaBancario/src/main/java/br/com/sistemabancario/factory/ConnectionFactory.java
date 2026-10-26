package br.com.sistemabancario.factory;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final Dotenv dotenv = Dotenv.load();

    //inicia uma unica vez
    private static final String host = dotenv.get("DB_HOST");
    private static final String port = dotenv.get("DB_PORT");
    private static final String name = dotenv.get("DB_NAME");
    private static final String user = dotenv.get("DB_USER");
    private static final String pass = dotenv.get("DB_PASS");

    private static final String url = String.format(
            "jdbc:postgresql://%s:%s/%s", host, port, name
    );

    public Connection recuperarConexao() {
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco!", e);
        }
    }
}
