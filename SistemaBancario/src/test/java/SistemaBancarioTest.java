
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.entities.SistemaBancario;
import br.com.sistemabancario.entities.Banco;
import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.exceptions.ValorInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

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

    @Captor
    ArgumentCaptor<ContaBancaria> contaCaptor;

    @Captor
    ArgumentCaptor<String> nomeCaptor;

    @Captor
    ArgumentCaptor<BigDecimal> valorParametroCaptor;

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

            sistemaBanco.tranferencia(1, 2, new BigDecimal("300.00"));

            verify(contaOrigemMock, times(1)).transferir(eq(contaDestinoMock), valorParametroCaptor.capture());

            BigDecimal valorTranferido = valorParametroCaptor.getValue();
            assertEquals(
                    new BigDecimal("300.00"), valorTranferido
            );
        }

        @Test
        @DisplayName("Testar transferencia com conta origem invalida")
        void testarTransferenciaContaOrigemInvalida() {

            when(bancoMock.buscarContaBancariaPorNumero(1))
                    .thenThrow(new ContaNaoEncontradaException("Conta nao encontrada"));

            Exception validacao = assertThrows(
                    ContaNaoEncontradaException.class,
                    () -> sistemaBanco.tranferencia(1, 2, new BigDecimal(200))
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
                    .thenThrow(new ContaNaoEncontradaException("Conta nao encontrada"));

            Exception validacao = assertThrows(
                    ContaNaoEncontradaException.class,
                    () -> sistemaBanco.tranferencia(1, 2, new BigDecimal("200.00"))
            );

            assertEquals("Conta nao encontrada", validacao.getMessage());

            verify(contaOrigemMock, never()).transferir(any(ContaBancaria.class), eq(new BigDecimal("200.00")));
        }

        @Test
        @DisplayName("Tetar tranferencia com valor de parametro invalido")
        void testarTransferenciaValorInvalido() {
            Exception validacao = assertThrows(
                    ValorInvalidoException.class,
                    () -> sistemaBanco.tranferencia(1, 2, new BigDecimal("-1"))
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

            sistemaBanco.sacar(1, new BigDecimal("400.00"));

            verify(contaSaqueMock, times(1)).sacar(valorParametroCaptor.capture());

            BigDecimal valorCapturado = valorParametroCaptor.getValue();

            assertEquals(
                    new BigDecimal("400.00"), valorCapturado
            );
        }

        @Test
        void testarSaqueContaInvalida(){
            when(bancoMock.buscarContaBancariaPorNumero(1))
                    .thenThrow(new ContaNaoEncontradaException("Conta nao encontrada"));

            Exception validacao = assertThrows(
                    ContaNaoEncontradaException.class,
                    ()-> sistemaBanco.depositar(1, new BigDecimal("400.00"))
            );

            assertEquals(
                    "Conta nao encontrada", validacao.getMessage()
            );
        }

        @Test
        void testarSaqueValorInvalido(){
            Exception validacao = assertThrows(
                    ValorInvalidoException.class,
                    ()-> sistemaBanco.sacar(1, new BigDecimal("-1"))
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

        sistemaBanco.depositar(1, new BigDecimal("200.00"));

        verify(contaOrigemMock, times(1)).depositar(valorParametroCaptor.capture());

        BigDecimal valorCapturado = valorParametroCaptor.getValue();
        assertEquals(
                new BigDecimal("200.00"), valorCapturado
        );
    }

    @Test
    @DisplayName("Testar deposito com conta não encontrada")
    void testarDepositoInvalido() {
        when(bancoMock.buscarContaBancariaPorNumero(20))
                .thenThrow(new ContaNaoEncontradaException("Conta nao encontrada"));


        Exception validacao = assertThrows(
                ContaNaoEncontradaException.class,
                () -> sistemaBanco.depositar(20, new BigDecimal("200.00"))
        );

        assertEquals(
                "Conta nao encontrada", validacao.getMessage()
        );
    }


    @Test
    void testarSistemaCriarConta() {
        int numeroConta;

        when(bancoMock.criarConta("Jonas"))
                .thenReturn(1);

        numeroConta = sistemaBanco.sistemaCriarConta("Jonas");
        verify(bancoMock).criarConta(nomeCaptor.capture());

        String nomeCapturado = nomeCaptor.getValue();
        assertEquals(
                "Jonas", nomeCapturado);

        assertEquals(
                1, numeroConta);
    }
}
