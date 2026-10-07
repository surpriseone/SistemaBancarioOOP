package br.com.sistemabancario.objectvalues;

import br.com.sistemabancario.exceptions.EmailException;

import java.util.Objects;

public final class Email {
    private final String email;

    private Email(String email){
        if (email == null) {
            throw new EmailException("O email não pode ser nulo");
        }

        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email não pode estar vazio");
        }

        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            throw new IllegalArgumentException("Formato de email invalido");
        }

        this.email = email.toLowerCase();
    }

    public static Email of(String email){
        return new Email(email);
    }

    public String valor() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
