package br.com.sistemabancario.objectvalues;

import java.util.Objects;

public final class CPF {
    private final String cpf;

    private CPF(String cpf) {
        validacao(cpf);
        this.cpf = cpf;
    }

    private void validacao(String cpf) {
        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("Invalido");
        }
    }

    public static CPF of(String cpf){
        return new CPF(cpf);
    }

    public String valor(){
        return cpf;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CPF cpf1 = (CPF) o;
        return Objects.equals(cpf, cpf1.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }
}
