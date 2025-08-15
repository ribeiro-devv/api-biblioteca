# API Biblioteca

Bem-vindo ao projeto **API Biblioteca**, uma aplicação de gerenciamento de biblioteca desenvolvida com **Spring Boot**, com ênfase em segurança robusta utilizando **Spring Security** e suporte a funcionalidades modernas como documentação com **Swagger**, validação de dados com **Bean Validation**, e mais. Este projeto permite gerenciar bibliotecas, livros, usuários e pedidos de empréstimo de forma segura e escalável.

## 📋 Descrição do Projeto

A **API Biblioteca** é uma aplicação backend construída com **Spring Boot 3.0.0** e **Java 17**, projetada para gerenciar operações de uma biblioteca. O projeto utiliza **Spring Security** com suporte a **OAuth2 Resource Server** e **Authorization Server** para autenticação e autorização. Integra **Spring Data JPA** para persistência com MySQL, **Lombok** para reduzir código boilerplate, e **Springdoc OpenAPI** para documentação interativa via Swagger. A estrutura modular inclui camadas específicas para controle, exceções, modelos, e serviços.

### Funcionalidades Principais
- **Gerenciamento de Bibliotecas**: CRUD completo para bibliotecas, incluindo associação/desassociação de responsáveis.
- **Gerenciamento de Livros**: CRUD para livros dentro de uma biblioteca específica.
- **Pedidos de Empréstimo**: Criação e busca de pedidos de livros.
- **Segurança**: Autenticação e autorização via Spring Security com OAuth2.
- **Documentação**: Interface Swagger para explorar a API.
- **Validação**: Bean Validation para integridade de dados.
- **Persistência**: Banco de dados MySQL com Spring Data JPA.
- **Thymeleaf**: Suporte a templates (se aplicável).

## 🛠️ Tecnologias Utilizadas

- **Java 17**: Linguagem principal.
- **Spring Boot 3.0.0**: Framework backend.
- **Spring Security**: Segurança com OAuth2.
- **Spring Data JPA**: Persistência com MySQL.
- **Lombok**: Redução de boilerplate.
- **Springdoc OpenAPI (Swagger)**: Documentação da API.
- **Thymeleaf**: Renderização de templates.
- **Maven**: Gerenciamento de dependências.
- **MySQL**: Banco de dados relacional.

## 📚 Endpoints Principais

Consulte a documentação em `/swagger-ui.html`. Exemplos:

### Bibliotecas
- **GET /bibliotecas**: Lista todas as bibliotecas (requer `PodeConsultar`).
- **GET /bibliotecas/{bibliotecaId}**: Busca uma biblioteca específica (requer `PodeConsultar`).
- **POST /bibliotecas**: Adiciona uma nova biblioteca (requer `PodeGerenciarCadastro`).
- **PUT /bibliotecas/{livroId}**: Atualiza uma biblioteca (requer `PodeGerenciarCadastro`).
- **DELETE /bibliotecas/{livroId}**: Remove uma biblioteca (requer `PodeGerenciarCadastro`).
- **PUT /bibliotecas/{bibliotecaId}/associar/{usuarioId}**: Associa um responsável (requer `PodeGerenciarCadastro`).
- **PUT /bibliotecas/{bibliotecaId}/desassociar/{usuarioId}**: Desassocia um responsável (requer `PodeGerenciarCadastro`).

### Livros
- **GET /bibliotecas/{bibliotecaId}/livros**: Lista livros de uma biblioteca (requer `PodeConsultar`).
- **GET /bibliotecas/{bibliotecaId}/livros/{livroId}**: Busca um livro específico (requer `PodeConsultar`).
- **POST /bibliotecas/{bibliotecaId}/livros**: Adiciona um livro (requer `PodeGerenciarFuncionamento`).
- **PUT /bibliotecas/{bibliotecaId}/livros/{livroId}**: Atualiza um livro (requer `PodeGerenciarFuncionamento`).
- **DELETE /bibliotecas/{bibliotecaId}/livros/{livroId}**: Remove um livro (requer `PodeGerenciarFuncionamento`).

### Pedidos de Livros
- **GET /bibliotecas/{bibliotecaId}/pedidos/{pedidoLivroId}**: Busca um pedido específico (requer `PodeBuscar`).
- **POST /bibliotecas/{bibliotecaId}/pedidos**: Cria um novo pedido (requer `PodeCriarPedido`).

## 📦 Estrutura do Projeto

```
api-biblioteca/
├── .idea/              # Configurações do IntelliJ IDEA
├── .mvn/               # Configurações do Maven Wrapper
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.mr.demo.scurity/
│   │   │       ├── api.exceptionhandler/  # Tratamento de exceções
│   │   │       ├── controller/           # Controladores REST (ex.: BibliotecaController, LivroController, PedidoLivroController)
│   │   │       ├── dto/                  # Objetos de transferência de dados
│   │   │       ├── exception/            # Definições de exceções personalizadas
│   │   │       ├── model/                # Entidades JPA
│   │   │       ├── openai/               # Integração com OpenAI (se aplicável)
│   │   │       ├── repository/           # Repositórios JPA
│   │   │       ├── security/             # Configurações de segurança
│   │   │       ├── service/              # Lógica de negócio
│   │   │       └── DemoApplication.java  # Classe principal
│   │   └── resources/                    # Configurações e templates
├── pom.xml             # Configuração do Maven
└── README.md           # Este arquivo
```

Feito com 💻 e ☕ por [Matheus Ribeiro].
