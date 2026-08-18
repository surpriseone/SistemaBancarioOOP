package br.com.sistemabancario.objectvalues;

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
}
