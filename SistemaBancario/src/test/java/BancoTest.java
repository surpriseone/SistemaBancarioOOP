import br.com.sistemabancario.entities.Banco;
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BancoTest {



    private Banco banco;

    ContaBancaria contaTeste;

    @Captor
    ArgumentCaptor<ContaBancaria> contaCaptor;

    @BeforeEach
    public void setUp(){
        banco = new Banco("Banco Teste");
        contaTeste = new ContaBancaria("Jose", 10);
        banco.adiocionarConta(contaTeste);
    }


    @Test
    void testarBuscarContaValida(){

        int numeroDaConta = contaTeste.getNumeroDaConta();
        ContaBancaria contaRetornada = banco.buscarContaBancariaPorNumero(numeroDaConta);
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
                () -> banco.buscarContaBancariaPorNumero(numeroNuncaCadastrado)
        );

        assertEquals(
                "Conta não encontrada", validacao.getMessage()
        );
    }

    @Test
    void testarCriacaoContaComDeposito(){
        int numeroConta = banco.criarConta("João", new BigDecimal(600));
        ContaBancaria conta = banco.buscarContaBancariaPorNumero(numeroConta);

        assertEquals(
                "João", conta.getNomeTitular()
        );

        assertEquals(
                new BigDecimal("600.00"), conta.getSaldo()
        );

        assertEquals(
                numeroConta, conta.getNumeroDaConta()
        );

    }
}
