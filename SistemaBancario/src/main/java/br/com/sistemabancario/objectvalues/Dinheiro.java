package br.com.sistemabancario.objectvalues;

import br.com.sistemabancario.exceptions.ValorInvalidoException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Dinheiro {
    private final BigDecimal valor;

    private Dinheiro(BigDecimal valor){
        validacao(valor);
        this.valor = valor.setScale(2, RoundingMode.HALF_EVEN);
    }

    private static void validacao(BigDecimal valor){
        if (valor == null){
            throw new ValorInvalidoException("Dinheiro não pode ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0){
            throw new ValorInvalidoException("Dinheiro não pode ser negativo");
        }
    }

    public static Dinheiro NOVO(BigDecimal valor){
        return new Dinheiro(valor);
    }

    public Dinheiro somar(Dinheiro valorDaSoma){
        return new Dinheiro(this.valor.add(valorDaSoma.valor));
    }

    public  Dinheiro subtrair(Dinheiro valorDaSubtracao){
        return new Dinheiro(this.valor.subtract(valorDaSubtracao.valor));
    }

    public int comparar(Dinheiro valorComparado){
        return this.valor.compareTo(valorComparado.valor);
    }

    public BigDecimal getValor() {
        return valor;
    }

    //constantes
    public static final Dinheiro ZERO = new Dinheiro(new BigDecimal(0));
    public static final Dinheiro ONE = new Dinheiro(new BigDecimal(1));

    @Override
    public String toString(){
        return "R$ " + this.valor;
    }
}
