import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.exceptions.saldoInsuficienteException;
import br.com.sistemabancario.exceptions.tranferirParaMesmaContaException;
import br.com.sistemabancario.exceptions.valorInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContaBancariaTest {

     protected ContaBancaria contaUsuario1;
     protected ContaBancaria contaUsuario2;

    @BeforeEach
        public void criarConta() {
            contaUsuario1 = new ContaBancaria("Usuario1", 12, 600.0);
            contaUsuario2 = new ContaBancaria("Usuario2", 22, 800.0);
    }

    @Test
    public void depositoValido(){
        contaUsuario1.depositar(600);
        assertEquals(
                1200, contaUsuario1.getSaldo()
        );
    }

    @Test
    public void depositoInvalido(){
        Exception validacao = assertThrows(
                valorInvalidoException.class,
                () -> contaUsuario1.depositar(-20)
        );

        assertEquals(
                "O valor do deposito deve ser maior que 0", validacao.getMessage()
        );
    }

    @Test
    public void saqueValido(){
        double saldoCompleto = contaUsuario1.getSaldo();
        contaUsuario1.sacar(saldoCompleto);

        assertEquals(
                0, contaUsuario1.getSaldo()
        );
    }

    @Test
    public void saqueInvalido(){

        double valorMaiorQueSaldo = contaUsuario1.getSaldo() + 1;

        Exception validacao = assertThrows(
                saldoInsuficienteException.class,
                () ->  contaUsuario1.sacar(valorMaiorQueSaldo)
        );
        assertEquals(
                "Saldo insuficiente", validacao.getMessage()
        );
    }

    @Test
    public void tranferenciaValida(){
        double saldoConta1 = contaUsuario1.getSaldo();
        contaUsuario1.tranferir(contaUsuario2, saldoConta1);

        assertEquals(
                0.0, contaUsuario1.getSaldo()
        );
        assertEquals(
                1400.0, contaUsuario2.getSaldo()
        );
    }

    @Test
    public void tranferenciaInvalida(){

        Exception validacao = assertThrows(
                tranferirParaMesmaContaException.class,
                () -> contaUsuario1.tranferir(contaUsuario1, 500)
        );
        assertEquals(
                "Você não pode tranferir pra mesma conta", validacao.getMessage()
        );
    }
}


