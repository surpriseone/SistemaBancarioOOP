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
- ✔ Transações `Transferencia`, `Deposito`, `Saque`
- ✔ Salvar transações com ID unico
- ✔ Tratamento de exceções customizadas   
- ✔ Testes unitários completos  

---

## 🧠 Arquitetura e Design

Dividi o projeto em responsabilidades claras:

1. **Entities (`ContaBancaria`, `Transacao`):** Onde mora o coração do negócio. Aqui ficam as regras de quem pode ou não tirar dinheiro.
2. **Object Values (`Dinheiro`):** Um objeto que só serve para garantir que o dinheiro seja sempre válido (2 casas decimais e nunca negativo), Antes tinha varias validações no sistema para evitar chamar metodos que ja nasceram invalidos por conta de valor invalido, então o Objeto Dinheiro soluciona isso em um lugar so e lança a exceção `ValorInvalidoException` se necessario.
3. **Services (`SistemaBancario`):** Ele não faz contas, ele apenas organiza: chama o banco, pede para a conta realizar a transação e manda o BancoMemoria salvar a transação.
4. **Repositories (`BancoRepository` Interface):** Implementei a inteface pensando em entrar em banco de dados mais pra frente, para ja separar responsabilidades desde o inicio, seguindo o principio de baixo acoplamento.

## 🧪 Testes

O projeto utiliza:

- **JUnit 5**
- **Mockito** > Usando o principio de nunca mockar a classe que está sendo testada.
- **Argument Captor** > Usei para "interceptar" o que estava sendo enviado entre as classes e conferir se os valores estavam corretos.
  
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

