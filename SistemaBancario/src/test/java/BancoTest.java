import br.com.sistemabancario.entities.Banco;
import br.com.sistemabancario.entities.ContaBancaria;
import br.com.sistemabancario.exceptions.contaNaoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BancoTest {


    private Banco banco;

    ContaBancaria contaTeste;


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
                contaNaoEncontradaException.class,
                () -> banco.buscarContaBancariaPorNumero(numeroNuncaCadastrado)
        );

        assertEquals(
                "Conta não encontrada", validacao.getMessage()
        );
    }

}
