package br.com.sistemabancario.entities;

import br.com.sistemabancario.exceptions.TitularEmptyException;
import br.com.sistemabancario.exceptions.TitularNullException;
import br.com.sistemabancario.objectvalues.CPF;
import br.com.sistemabancario.objectvalues.Email;

public class Cliente {
    private int clienteID;
    private String nome;
    private CPF cpf;
    private Email email;

    public Cliente(String nome, CPF cpf, Email email){
        this.nome = validarNome(nome);
        this.cpf = cpf;
        this.email = email;
    }

    private String validarNome(String nomeTitular){
        if (nomeTitular == null) {
            throw new TitularNullException("O titular precisa ter um nome");
        }

        if (nomeTitular.trim().isEmpty()) {
            throw new TitularEmptyException("O Titular não pode ter nome vazio");
        }

        return nomeTitular.toUpperCase();
    }

    public String getNome() {
        return nome;
    }

    public CPF getCpf() {
        return cpf;
    }

    public Email getEmail() {
        return email;
    }

    //Reconstituir do banco
    private Cliente(int clienteID, String nome, CPF cpf, Email email){
        this(nome, cpf, email);
        this.clienteID = clienteID;
    }

    public static Cliente reconstituirCliente(int clienteID, String nome, CPF cpf, Email email){
        return new Cliente(clienteID, nome, cpf, email);
    }
}
