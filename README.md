# Java POO
- Esse é meu projeto de estudos em Programação Orientada Objeto (POO), meu objetivo aqui é fixar conceitos e entender como e quando usa-los, tendo foco não so em codigo, mas fazer aplicações mais seguras, funcionais e robustas

## Sistema Bancário em Java (POO)

Este projeto foi desenvolvido com o objetivo de **praticar Programação Orientada a Objetos (POO) em Java**, simulando o funcionamento básico de um sistema bancário no terminal.

A ideia principal não foi apenas “fazer funcionar”, mas **entender profundamente como os objetos se relacionam**, onde cada responsabilidade deve ficar e como modelar um problema do mundo real usando código limpo e organizado.



Este projeto simula operações bancárias fundamentais com ênfase em:

- Encapsulamento
- Separação de responsabilidades
- Design orientado a domínio
- Testes isolados
- Evolução arquitetural progressiva

## 📌 Objetivo

Consolidar fundamentos sólidos de backend antes da introdução de frameworks como Spring Boot, garantindo domínio real sobre:

- Estruturação de camadas
- Proteção de estado
- Regras de negócio bem definidas
- Isolamento de testes
- Pensamento arquitetural

---

## 🚀 Funcionalidades

- ✔ Criação de conta bancária  
- ✔ Busca de conta por número  
- ✔ Depósito  
- ✔ Saque  
- ✔ Transferência entre contas  
- ✔ Tratamento de exceções customizadas  
- ✔ Testes unitários completos  

---

## 🧠 Arquitetura e Design

O sistema foi organizado em três responsabilidades principais:

### 🔹 ContaBancaria

Responsável por:

- Proteger o saldo
- Garantir invariantes do domínio
- Implementar regras de negócio
- Lançar exceções quando necessário

#### Regras implementadas:

- Não permite depósito com valor negativo  `valorInvalidoException`
- Não permite saque com valor negativo  `valorInvalidoException`
- Não permite saque maior que o saldo  `saldoInsuficienteException`
- Não permite transferência com valor inválido  `valorInvalidoException`
- Não permite transferência para a mesma conta  `tranferirParaMesmaContaException`

> Cada regra com sua propria exception personalizada para permitir melhor clareza


### 🔹 Banco

Responsável por:

- Armazenar contas utilizando `HashMap`
- Gerenciar criação de contas
- Buscar contas por número


### 🔹 SistemaBancario 

Camada de orquestração responsável por:

- Coordenar operações
- Realizar validações estruturais
- Atribuir regras financeiras ao dominio

Não contém lógica financeira, apenas coordenação.

---

## ⚡ Validação de Dados

O sistema aplica validação antecipada de parâmetros na camada de aplicação para:

- Evitar execução desnecessária
- Melhorar clareza de fluxo
- Tornar testes mais previsíveis

Entretanto, as regras críticas continuam protegidas na entidade de domínio.

---

## 🧪 Testes

O projeto utiliza:

- **JUnit 5**
- **Mockito** > Usando o principio de nunca mockar a classe que está sendo testada.

---

## 🧩 Princípios Aplicados

- Encapsulamento forte
- Separação de responsabilidades
- Testes isolados
- Clareza de fluxo

---

## 🛠 Tecnologias

- Java 17+
- Maven
- JUnit 5
- Mockito

---

## 🔮 Próximos Passos

- Introdução de Spring Boot
- Camada REST (Controller)
- DTOs
- Persistência com banco de dados
- Testes de integração

---

## Motivação

Este projeto faz parte do meu processo de aprendizado em Java e POO.  
Ele foi construído passo a passo, com foco em **entender o “porquê” das decisões**, e não apenas copiar soluções prontas.

A ideia é que este código sirva tanto como:

- Base para projetos futuros
- Material de revisão
- Referência para quem está começando a estudar POO e não sabe por onde iniciar

## Sobre Mim
- Desenvolvido por Felype de Moura. Estudante de Engenharia de Software, com foco nos estudos voltado pro Back-End. Gosto de entender as coisas além codigo.

- Conecte - se comigo no Linkedin: `https://www.linkedin.com/in/felypemoura/`

