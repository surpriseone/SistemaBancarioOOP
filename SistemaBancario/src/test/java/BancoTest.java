

import br.com.sistemabancario.objectvalues.CPF;
import br.com.sistemabancario.repositories.Memory.ContaMemory;
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.objectvalues.Dinheiro;
import br.com.sistemabancario.repositories.Intefaces.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BancoTest {



    private ContaRepository bancoMemoria;

    ContaBancaria contaTeste;

    @Captor
    ArgumentCaptor<ContaBancaria> contaCaptor;

    @BeforeEach
    public void setUp(){
        bancoMemoria = new ContaMemory();
        contaTeste = new ContaBancaria("Jose", 10, CPF.of("91120080012"));
        bancoMemoria.salvarConta(contaTeste);
    }


    @Test
    void testarBuscarContaValida(){

        int numeroDaConta = contaTeste.getNumeroDaConta();
        ContaBancaria contaRetornada = bancoMemoria.buscarContaBancariaPorNumero(numeroDaConta);
        assertEquals(contaTeste, contaRetornada);
        assertEquals(contaTeste.getNumeroDaConta(), contaRetornada.getNumeroDaConta());
        assertEquals(contaTeste.getCPF(), contaRetornada.getCPF());
        assertEquals(contaTeste.getSaldo(), contaRetornada.getSaldo());
        assertEquals(contaTeste.getNomeTitular(), contaRetornada.getNomeTitular());
    }


    @Test
    void testarBuscarContaNaoCadastrada(){
        int numeroNuncaCadastrado = 100;
        Exception validacao = assertThrows(
                ContaNaoEncontradaException.class,
                () -> bancoMemoria.buscarContaBancariaPorNumero(numeroNuncaCadastrado)
        );

        assertEquals(
                "Conta não encontrada", validacao.getMessage()
        );
    }

    @Test
    void testarCriacaoContaComDeposito(){
        int numeroConta = bancoMemoria.criarConta("João", CPF.of("12345678911"));
        ContaBancaria conta = bancoMemoria.buscarContaBancariaPorNumero(numeroConta);

        assertEquals(
                "João", conta.getNomeTitular()
        );

        assertEquals("12345678911", conta.getCPF().valor());

        assertEquals(
                Dinheiro.ZERO, conta.getSaldo()
        );

        assertEquals(
                numeroConta, conta.getNumeroDaConta()
        );

    }
}
