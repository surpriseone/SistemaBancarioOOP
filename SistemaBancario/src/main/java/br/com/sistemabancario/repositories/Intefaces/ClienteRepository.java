package br.com.sistemabancario.repositories.Intefaces;

import br.com.sistemabancario.entities.Cliente;
import br.com.sistemabancario.objectvalues.CPF;

public interface ClienteRepository {
    Cliente buscarClientePorCpf(CPF cpf);
    Cliente buscarClientePorId(int id);
}
