import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.exceptions.SaldoInsuficienteException;
import br.com.sistemabancario.exceptions.TranferirParaMesmaContaException;
import br.com.sistemabancario.exceptions.ValorInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ContaBancariaTest {

     protected ContaBancaria contaUsuario1;
     protected ContaBancaria contaUsuario2;

    @BeforeEach
        public void criarConta() {
            contaUsuario1 = new ContaBancaria("Usuario1", 12, new BigDecimal("600.0"));
            contaUsuario2 = new ContaBancaria("Usuario2", 22, new BigDecimal("800.0"));
    }

    @Test
    public void depositoValido(){
        contaUsuario1.depositar(new BigDecimal(600));

        assertTrue(
                contaUsuario1.getSaldo().compareTo(new BigDecimal("1200")) == 0
        );
    }

    @Test
    public void depositoInvalido(){
        Exception validacao = assertThrows(
                ValorInvalidoException.class,
                () -> contaUsuario1.depositar(new BigDecimal(-20))
        );

        assertEquals(
                "O valor do deposito deve ser maior que 0", validacao.getMessage()
        );
    }

    @Test
    public void saqueValido(){
        BigDecimal saldoCompleto = contaUsuario1.getSaldo();
        contaUsuario1.sacar(saldoCompleto);

        assertEquals(
                0, contaUsuario1.getSaldo().compareTo(BigDecimal.ZERO)
        );
    }

    @Test
    public void saqueInvalido(){

        BigDecimal valorMaiorQueSaldo = contaUsuario1.getSaldo().add(BigDecimal.ONE);

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
        BigDecimal saldoConta1 = contaUsuario1.getSaldo();
        contaUsuario1.transferir(contaUsuario2, saldoConta1);

        assertEquals(
                0, contaUsuario1.getSaldo().compareTo(BigDecimal.ZERO)
        );
        assertEquals(
                0, contaUsuario2.getSaldo().compareTo(new BigDecimal(1400))
        );
    }

    @Test
    public void tranferenciaInvalida(){

        Exception validacao = assertThrows(
                TranferirParaMesmaContaException.class,
                () -> contaUsuario1.transferir(contaUsuario1, new BigDecimal(500))
        );
        assertEquals(
                "Você não pode transferir pra mesma conta", validacao.getMessage()
        );
    }
}


