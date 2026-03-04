
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.entities.SistemaBancario;
import br.com.sistemabancario.entities.BancoMemoria;
import br.com.sistemabancario.exceptions.ContaNaoEncontradaException;
import br.com.sistemabancario.exceptions.ValorInvalidoException;
import br.com.sistemabancario.objectvalues.Dinheiro;
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
    private BancoMemoria bancoMemoriaMock;

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
    ArgumentCaptor<Dinheiro> valorParametroCaptor;

    //Testes do metodo de transferencia do sistema bancario

    @Nested
    @DisplayName("Testes de transferencia")
    class TestesDeTransferencia {

        @Test
        void testarTranferenciaValidaEntreContas() {
            when(bancoMemoriaMock.buscarContaBancariaPorNumero(1))
                    .thenReturn(contaOrigemMock);

            when(bancoMemoriaMock.buscarContaBancariaPorNumero(2))
                    .thenReturn(contaDestinoMock);

            sistemaBanco.tranferencia(1, 2, Dinheiro.NOVO(new BigDecimal(300)));

            verify(contaOrigemMock, times(1)).transferir(eq(contaDestinoMock), valorParametroCaptor.capture());

            Dinheiro valorTranferido = valorParametroCaptor.getValue();
            Dinheiro expected = Dinheiro.NOVO(new BigDecimal(300));

            assertEquals(
                    expected.getValor(), valorTranferido.getValor()
            );
        }

        @Test
        @DisplayName("Testar transferencia com conta origem invalida")
        void testarTransferenciaContaOrigemInvalida() {

            when(bancoMemoriaMock.buscarContaBancariaPorNumero(1))
                    .thenThrow(new ContaNaoEncontradaException("Conta nao encontrada"));

            Exception validacao = assertThrows(
                    ContaNaoEncontradaException.class,
                    () -> sistemaBanco.tranferencia(1, 2, Dinheiro.NOVO(new BigDecimal(300))
            ));

            assertEquals(
                    "Conta nao encontrada", validacao.getMessage()
            );
        }

        @Test
        @DisplayName("Testar transferencia com conta destino invalida")
        void testarTransferenciaContaDestinoInvalida() {
            when(bancoMemoriaMock.buscarContaBancariaPorNumero(1))
                    .thenReturn(contaOrigemMock);

            when(bancoMemoriaMock.buscarContaBancariaPorNumero(2))
                    .thenThrow(new ContaNaoEncontradaException("Conta nao encontrada"));

            Exception validacao = assertThrows(
                    ContaNaoEncontradaException.class,
                    () -> sistemaBanco.tranferencia(1, 2, Dinheiro.NOVO(new BigDecimal(200)))
            );

            assertEquals("Conta nao encontrada", validacao.getMessage());
        }

        @Test
        @DisplayName("Tetar tranferencia com valor de parametro invalido")
        void testarTransferenciaValorInvalido() {
            Exception validacao = assertThrows(
                    ValorInvalidoException.class,
                    () -> sistemaBanco.tranferencia(1, 2, Dinheiro.NOVO(new BigDecimal(-1))
            ));

            assertEquals(
                    "Dinheiro não pode ser negativo", validacao.getMessage()
            );

            verify(bancoMemoriaMock, never()).buscarContaBancariaPorNumero(1);
        }
    }


    // Testes do metodo de saque do sistema bancario

    @Nested
    class TestesDeSaque {

        @Test
        void testarSaqueValido() {

            Dinheiro valorSaque = Dinheiro.NOVO(new BigDecimal(400));

            when(bancoMemoriaMock.buscarContaBancariaPorNumero(1))
                    .thenReturn(contaSaqueMock);

            sistemaBanco.sacar(1, valorSaque);

            verify(contaSaqueMock, times(1)).sacar(valorParametroCaptor.capture());

            Dinheiro valorCapturado = valorParametroCaptor.getValue();

            assertEquals(
                    valorSaque, valorCapturado
            );
        }

        @Test
        void testarSaqueContaInvalida(){
            Dinheiro valorDeposito = Dinheiro.NOVO(new BigDecimal(400));

            when(bancoMemoriaMock.buscarContaBancariaPorNumero(1))
                    .thenThrow(new ContaNaoEncontradaException("Conta nao encontrada"));

            Exception validacao = assertThrows(
                    ContaNaoEncontradaException.class,
                    ()-> sistemaBanco.depositar(1, valorDeposito)
            );

            assertEquals(
                    "Conta nao encontrada", validacao.getMessage()
            );
        }

        @Test
        void testarSaqueValorInvalido(){

            Exception validacao = assertThrows(
                    ValorInvalidoException.class,
                    ()-> sistemaBanco.sacar(1, Dinheiro.NOVO(new BigDecimal(-1)))
            );

            assertEquals(
                    "Dinheiro não pode ser negativo", validacao.getMessage()
            );

            verify(bancoMemoriaMock, never()).buscarContaBancariaPorNumero(1);
        }
    }

    @Test
    @DisplayName("Testar deposito com conta e valor valido")
    void testarDepositoValido() {
        Dinheiro valorDeposito = Dinheiro.NOVO(new BigDecimal(200));

        when(bancoMemoriaMock.buscarContaBancariaPorNumero(1))
                .thenReturn(contaOrigemMock);

        sistemaBanco.depositar(1, valorDeposito);

        verify(contaOrigemMock, times(1)).depositar(valorParametroCaptor.capture());

        Dinheiro valorCapturado = valorParametroCaptor.getValue();
        assertEquals(
                valorDeposito, valorCapturado
        );
    }

    @Test
    @DisplayName("Testar deposito com conta não encontrada")
    void testarDepositoInvalido() {
        Dinheiro valorDeposito = Dinheiro.NOVO(new BigDecimal(200));

        when(bancoMemoriaMock.buscarContaBancariaPorNumero(20))
                .thenThrow(new ContaNaoEncontradaException("Conta nao encontrada"));


        Exception validacao = assertThrows(
                ContaNaoEncontradaException.class,
                () -> sistemaBanco.depositar(20, valorDeposito)
        );

        assertEquals(
                "Conta nao encontrada", validacao.getMessage()
        );
    }


    @Test
    void testarSistemaCriarConta() {
        int numeroConta;

        when(bancoMemoriaMock.criarConta("Jonas"))
                .thenReturn(1);

        numeroConta = sistemaBanco.sistemaCriarConta("Jonas");
        verify(bancoMemoriaMock).criarConta(nomeCaptor.capture());

        String nomeCapturado = nomeCaptor.getValue();
        assertEquals(
                "Jonas", nomeCapturado);

        assertEquals(
                1, numeroConta);
    }
}
