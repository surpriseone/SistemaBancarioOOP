

import br.com.sistemabancario.entities.BancoMemoria;
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.objectvalues.Dinheiro;
import br.com.sistemabancario.repositories.BancoRepository;
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



    private BancoRepository bancoMemoria;

    ContaBancaria contaTeste;

    @Captor
    ArgumentCaptor<ContaBancaria> contaCaptor;

    @BeforeEach
    public void setUp(){
        bancoMemoria = new BancoMemoria("Banco Teste");
        contaTeste = new ContaBancaria("Jose", 10);
        bancoMemoria.adicionarConta(contaTeste);
    }


    @Test
    void testarBuscarContaValida(){

        int numeroDaConta = contaTeste.getNumeroDaConta();
        ContaBancaria contaRetornada = bancoMemoria.buscarContaBancariaPorNumero(numeroDaConta);
        assertEquals(contaTeste, contaRetornada);
        assertEquals(contaTeste.getNumeroDaConta(), contaRetornada.getNumeroDaConta());
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
        int numeroConta = bancoMemoria.criarConta("João");
        ContaBancaria conta = bancoMemoria.buscarContaBancariaPorNumero(numeroConta);

        assertEquals(
                "João", conta.getNomeTitular()
        );

        assertEquals(
                Dinheiro.ZERO, conta.getSaldo()
        );

        assertEquals(
                numeroConta, conta.getNumeroDaConta()
        );

    }
}
