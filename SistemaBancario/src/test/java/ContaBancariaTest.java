

import br.com.sistemabancario.entities.Cliente;
import br.com.sistemabancario.objectvalues.CPF;
import br.com.sistemabancario.objectvalues.Email;
import br.com.sistemabancario.repositories.Memory.ContaMemory;
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.services.SistemaBancario;
import br.com.sistemabancario.exceptions.SaldoInsuficienteException;
import br.com.sistemabancario.exceptions.TranferirParaMesmaContaException;
import br.com.sistemabancario.exceptions.ValorInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.sistemabancario.objectvalues.Dinheiro;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ContaBancariaTest {
    private SistemaBancario sistema;
    private ContaMemory bancoMemoria;
    protected ContaBancaria contaUsuario1;
    protected ContaBancaria contaUsuario2;

    @BeforeEach
        public void criarConta() {
            Cliente cliente1 = new Cliente("Cliente 1", CPF.of("12345678910"), Email.of("Cliente1@gmail.com"));
            Cliente cliente2 = new Cliente("Cliente 2", CPF.of("11122233344"), Email.of("Cliente2@gmail.com"));
            contaUsuario1 = new ContaBancaria(cliente1, 12);
            contaUsuario2 = new ContaBancaria(cliente2, 22);
            contaUsuario1.depositar(Dinheiro.NOVO(new BigDecimal(10)));
    }

    @Test
    public void depositoValido(){
        Dinheiro valorDeposito = Dinheiro.NOVO(new BigDecimal(600));
        contaUsuario1.depositar(valorDeposito);

        assertTrue(
                contaUsuario1.getSaldo().comparar(valorDeposito) > 0
        );
    }

    @Test
    public void depositoInvalido(){
        Exception validacao = assertThrows(
                ValorInvalidoException.class,
                () -> contaUsuario1.depositar(Dinheiro.NOVO((new BigDecimal(-20))))
        );

        assertEquals(
                "Valor não pode ser negativo", validacao.getMessage()
        );
    }

    @Test
    public void saqueValido(){
        Dinheiro saldoCompleto = contaUsuario1.getSaldo();
        contaUsuario1.sacar(saldoCompleto);

        assertEquals(
                0, contaUsuario1.getSaldo().comparar(Dinheiro.ZERO)
        );
    }

    @Test
    public void saqueInvalido(){

        Dinheiro valorMaiorQueSaldo = contaUsuario1.getSaldo().somar(Dinheiro.ONE);

        Exception validacao = assertThrows(
                SaldoInsuficienteException.class,
                () ->  contaUsuario1.sacar(valorMaiorQueSaldo)
        );
        assertEquals(
                "Saldo insuficiente", validacao.getMessage()
        );
    }

    @Test
    public void tranferenciaValida(){
        Dinheiro saldoConta1 = contaUsuario1.getSaldo();
        Dinheiro expexted = Dinheiro.NOVO(new BigDecimal(10));
        contaUsuario1.transferir(contaUsuario2, saldoConta1);

        assertEquals(
                0, contaUsuario1.getSaldo().comparar(Dinheiro.ZERO)
        );
        assertEquals(
                expexted.getValor(), contaUsuario2.getSaldo().getValor()
        );
    }

    @Test
    public void tranferenciaInvalida(){

        Exception validacao = assertThrows(
                TranferirParaMesmaContaException.class,
                () -> contaUsuario1.transferir(contaUsuario1, Dinheiro.NOVO(new BigDecimal(500)))
        );
        assertEquals(
                "Você não pode transferir pra mesma conta", validacao.getMessage()
        );
    }
}


