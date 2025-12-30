# AsMoney API

## 📌 Visão Geral

**AsMoney** é uma API REST desenvolvida em **Java 17 com Spring Boot 4** para gerenciamento financeiro pessoal. A aplicação permite o controle de transações financeiras, oferecendo autenticação segura, validação de dados, documentação automática e suporte a containers Docker.

O projeto foi pensado com foco em **boas práticas de arquitetura**, **manutenibilidade**, **testabilidade** e **segurança**, sendo ideal tanto para uso real quanto para fins de estudo e portfólio.

---

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 4**
* **Spring Web**
* **Spring Data JPA**
* **PostgreSQL**
* **Spring Security**
* **JWT (JSON Web Token)**
* **Auth0** (autenticação e autorização)
* **Bean Validation**
* **Lombok**
* **Docker**
* **Docker Compose**
* **Swagger UI (OpenAPI 3)**
* **JUnit + Mockito** (testes unitários)

---

## 🧱 Arquitetura

A aplicação segue uma **arquitetura em camadas**, promovendo separação de responsabilidades, baixo acoplamento e facilidade de manutenção.

Além disso, o projeto faz uso extensivo de **Injeção de Dependência (Dependency Injection - DI)** fornecida pelo **Spring Framework**, permitindo maior flexibilidade, testabilidade e organização do código.

### Estrutura geral:

```
modules
 ├── user
 │   ├── controller
 │   ├── usecase
 │   ├── entity
 │   ├── repository
 │   └── dto
 ├── transaction
 │   ├── controller
 │   ├── usecase
 │   ├── entity
 │   ├── repository
 │   └── dto
 └── auth
     ├── controller
     └── security
```

### Camadas

* **Controller**: recebe e responde requisições HTTP
* **UseCase / Service**: contém a regra de negócio
* **Repository**: acesso a dados (JPA)
* **Entity**: mapeamento das tabelas do banco
* **DTOs**: objetos de entrada e saída da API

---

## 🔁 Injeção de Dependência

O projeto utiliza **Injeção de Dependência nativa do Spring**, permitindo que os componentes sejam desacoplados e facilmente substituídos em testes.

### Principais anotações utilizadas:

* `@RestController`
* `@Service`
* `@Repository`
* `@Component`
* `@RequiredArgsConstructor` (Lombok)

A injeção é feita preferencialmente via **construtor**, seguindo boas práticas recomendadas pelo Spring:

```java
@Service
@RequiredArgsConstructor
public class UpdateTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public Transaction execute(...) {
        // regra de negócio
    }
}
```

Essa abordagem facilita:

* Escrita de testes unitários
* Substituição de dependências com mocks
* Evolução da aplicação sem alto acoplamento

---

## 🔐 Autenticação e Segurança

A segurança da aplicação é baseada em **JWT** com integração ao **Auth0**.

* Autenticação via token Bearer
* Endpoints protegidos por Spring Security
* Validação de token em cada requisição

### Exemplo de Header:

```http
Authorization: Bearer <seu_token_jwt>
```

---

## 📄 Validação de Dados

A API utiliza **Bean Validation** para validação de entradas, garantindo integridade dos dados e respostas padronizadas em caso de erro.

Exemplos:

* Campos obrigatórios
* Tamanhos mínimos/máximos
* Valores inválidos

---

## 📘 Documentação da API

A documentação é gerada automaticamente com **Swagger UI (OpenAPI 3)**.

Após subir a aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
```

Nela é possível:

* Visualizar todos os endpoints
* Ver modelos de request/response
* Testar a API diretamente pelo navegador

---

## 🧪 Testes

O projeto possui **testes unitários**, garantindo a confiabilidade das regras de negócio.

Ferramentas utilizadas:

* **JUnit 5**
* **Mockito**

Os testes focam principalmente na camada de **UseCases**, isolando dependências externas.

---

## 🐳 Docker e Docker Compose

A aplicação pode ser executada facilmente via **Docker Compose**, incluindo o banco de dados PostgreSQL.

### Subir a aplicação:

```bash
docker-compose up -d
```

Isso irá subir:

* API Spring Boot
* Banco de dados PostgreSQL

---

## ▶️ Executando localmente (sem Docker)

### Pré-requisitos:

* Java 17
* PostgreSQL

### Passos:

```bash
./mvnw spring-boot:run
```

Configure as variáveis de ambiente no `application.yml` ou `application.properties`.

---

## ⚙️ Configuração da Aplicação (application.yml)

A aplicação utiliza o arquivo `application.yml` para centralizar as configurações de ambiente, banco de dados, JPA, Flyway e segurança JWT.

### Exemplo de `application.yml`:

```yml
spring:
  application:
    name: AsMoney

  datasource:
    url: jdbc:postgresql://localhost:5432/db_name
    username: 
    password: 
    driver-class-name: org.postgresql.Driver

  jpa:
    show-sql: true

  flyway:
    enabled: true

  jwt:
    secret-key: "secret-key-example"
```



---



## 🎯 Objetivos do Projeto

* Aplicar boas práticas com Spring Boot
* Implementar autenticação moderna com JWT
* Organizar código com arquitetura em camadas
* Garantir qualidade com testes unitários
* Facilitar execução com Docker

---

## 👤 Autor

Desenvolvido por **Dinho Torres**

---

## 📄 Licença

Este projeto está sob a licença **MIT**.
