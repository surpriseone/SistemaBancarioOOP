package br.com.sistemabancario.repositories.Memory;

import br.com.sistemabancario.entities.Cliente;
import br.com.sistemabancario.exceptions.ClienteNaoEncontradoException;
import br.com.sistemabancario.objectvalues.CPF;
import br.com.sistemabancario.objectvalues.Email;
import br.com.sistemabancario.repositories.Intefaces.ClienteRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class ClienteMemory implements ClienteRepository {
    HashSet<Integer> clientesIDs = new HashSet<>();
    HashMap<CPF, Cliente> clientesCPF = new HashMap<>();
    HashMap<Integer, Cliente> clientesID = new HashMap<>();

    @Override
    public Cliente buscarClientePorCpf(CPF cpf) {
        if(clientesCPF.containsKey(cpf)){
            return clientesCPF.get(cpf);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado");
    }

    @Override
    public Cliente buscarClientePorId(int id) {
        if(!clientesIDs.contains(id)){
            throw new ClienteNaoEncontradoException("Cliente nao encontrado");
        }
        return clientesID.get(id);
    }

    @Override
    public int salvarCliente(Cliente cliente) {
        //Simular um salvamento parecido com o banco de dados
        Cliente clienteComID = Cliente.reconstituirCliente(
                gerarIdCliente(), cliente.getNome(), cliente.getCpf(), cliente.getEmail());

        if(clientesCPF.containsKey(clienteComID.getCpf())){
            throw new IllegalArgumentException("Cliente ja cadastrado.");
        }

        clientesCPF.put(clienteComID.getCpf(), clienteComID);
        clientesID.put(clienteComID.getClienteID(), clienteComID);

        return clienteComID.getClienteID();
    }

    @Override
    public void atualizarEmail(int clienteID, Email email) {
        Cliente cliente = buscarClientePorId(clienteID);
        cliente.alterarEmail(email);
    }

    public Integer gerarIdCliente() {
        int IDGerado;
        do {
            IDGerado = ThreadLocalRandom.current().nextInt(1000, 10000);
        } while (clientesIDs.contains(IDGerado));

        clientesIDs.add(IDGerado);
        return IDGerado;
    }
}
