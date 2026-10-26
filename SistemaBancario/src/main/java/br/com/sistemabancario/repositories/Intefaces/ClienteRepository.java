package br.com.sistemabancario.repositories.Intefaces;

import br.com.sistemabancario.entities.Cliente;
import br.com.sistemabancario.objectvalues.CPF;
import br.com.sistemabancario.objectvalues.Email;

public interface ClienteRepository {
    Cliente buscarClientePorCpf(CPF cpf);
    Cliente buscarClientePorId(int id);
    int salvarCliente(Cliente cliente);
    void atualizarEmail(int clienteID, Email email);
}
