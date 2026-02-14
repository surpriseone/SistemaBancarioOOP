
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.entities.SistemaBancario;
import br.com.sistemabancario.entities.Banco;
import br.com.sistemabancario.exceptions.contaNaoEncontradaException;
import br.com.sistemabancario.exceptions.valorInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SistemaBancarioTest {


    @Mock
    private Banco bancoMock;

    @Mock
    private ContaBancaria contaSaqueMock;

    @Mock
    private ContaBancaria contaDepositoMock;

    @Mock
    private ContaBancaria contaOrigemMock;

    @Mock
    private ContaBancaria contaDestinoMock;


    @InjectMocks
    private SistemaBancario sistemaBanco;


    //Testes do metodo de transferencia do sistema bancario

    @Nested
    @DisplayName("Testes de transferencia")
    class TestesDeTransferencia {

        @Test
        void testarTranferenciaValidaEntreContas() {

            when(bancoMock.buscarContaBancariaPorNumero(1))
                    .thenReturn(contaOrigemMock);

            when(bancoMock.buscarContaBancariaPorNumero(2))
                    .thenReturn(contaDestinoMock);

            sistemaBanco.tranferencia(1, 2, 300);
            verify(contaOrigemMock).tranferir(contaDestinoMock, 300);
        }

        @Test
        @DisplayName("Testar transferencia com conta origem invalida")
        void testarTransferenciaContaOrigemInvalida() {

            when(bancoMock.buscarContaBancariaPorNumero(1))
                    .thenThrow(new contaNaoEncontradaException("Conta nao encontrada"));

            Exception validacao = assertThrows(
                    contaNaoEncontradaException.class,
                    () -> sistemaBanco.tranferencia(1, 2, 200)
            );

            assertEquals(
                    "Conta nao encontrada", validacao.getMessage()
            );
        }

        @Test
        @DisplayName("Testar transferencia com conta destino invalida")
        void testarTransferenciaContaDestinoInvalida() {
            when(bancoMock.buscarContaBancariaPorNumero(1))
                    .thenReturn(contaOrigemMock);

            when(bancoMock.buscarContaBancariaPorNumero(2))
                    .thenThrow(new contaNaoEncontradaException("Conta nao encontrada"));

            Exception validacao = assertThrows(
                    contaNaoEncontradaException.class,
                    () -> sistemaBanco.tranferencia(1, 2, 200)
            );

            assertEquals("Conta nao encontrada", validacao.getMessage());

            verify(contaOrigemMock, never()).tranferir(any(ContaBancaria.class), eq(200));
        }

        @Test
        @DisplayName("Tetar tranferencia com valor de parametro invalido")
        void testarTransferenciaValorInvalido() {
            Exception validacao = assertThrows(
                    valorInvalidoException.class,
                    () -> sistemaBanco.tranferencia(1, 2, -1)
            );

            assertEquals(
                    "Valor de transferencia invalido", validacao.getMessage()
            );

            verify(bancoMock, never()).buscarContaBancariaPorNumero(1);
        }
    }


    // Testes do metodo de saque do sistema bancario

    @Nested
    class TestesDeSaque {

        @Test
        void testarSaqueValido() {

            when(bancoMock.buscarContaBancariaPorNumero(1))
                    .thenReturn(contaSaqueMock);

            sistemaBanco.sacar(1, 400);

            verify(contaSaqueMock, times(1)).sacar(400);
        }

        @Test
        void testarSaqueContaInvalida(){
            when(bancoMock.buscarContaBancariaPorNumero(1))
                    .thenThrow(new contaNaoEncontradaException("Conta nao encontrada"));

            Exception validacao = assertThrows(
                    contaNaoEncontradaException.class,
                    ()-> sistemaBanco.depositar(1, 400)
            );

            assertEquals(
                    "Conta nao encontrada", validacao.getMessage()
            );
        }

        @Test
        void testarSaqueValorInvalido(){
            Exception validacao = assertThrows(
                    valorInvalidoException.class,
                    ()-> sistemaBanco.sacar(1, -1)
            );

            assertEquals(
                    "Valor de saque invalido", validacao.getMessage()
            );

            verify(bancoMock, never()).buscarContaBancariaPorNumero(1);
        }
    }

    @Test
    @DisplayName("Testar deposito com conta e valor valido")
    void testarDepositoValido() {
        when(bancoMock.buscarContaBancariaPorNumero(1))
                .thenReturn(contaOrigemMock);

        sistemaBanco.depositar(1, 200);

        verify(contaOrigemMock, times(1)).depositar(200);
    }

    @Test
    @DisplayName("Testar deposito com conta não encontrada")
    void testarDepositoInvalido() {
        when(bancoMock.buscarContaBancariaPorNumero(20))
                .thenThrow(new contaNaoEncontradaException("Conta nao encontrada"));

        Exception validacao = assertThrows(
                contaNaoEncontradaException.class,
                () -> sistemaBanco.depositar(20, 200)
        );

        assertEquals(
                "Conta nao encontrada", validacao.getMessage()
        );
    }


    @Test
    void testarSistemaCriarConta() {

        int numeroConta;

        when(bancoMock.criarConta(anyString()))
                .thenReturn(1);

        numeroConta = sistemaBanco.sistemaCriarConta("Jonas");

        verify(bancoMock).criarConta("Jonas");

        assertEquals(
                1, numeroConta);
    }
}













