# 📅 Habit Tracker API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-brightgreen)
![License](https://img.shields.io/badge/License-Apache_2.0-blue)

> API RESTful desenvolvida para gerenciamento e rastreamento de hábitos diários, com cálculo de "streak" (dias consecutivos) e autenticação segura via JWT.

---

## 💻 Sobre o Projeto

Este projeto é uma API robusta desenvolvida como parte de um portfólio pessoal e para estudo prático de arquitetura de software. O objetivo é permitir que usuários cadastrem seus hábitos, marquem como concluídos diariamente e acompanhem seu progresso através de sequências de dias (streaks).

A arquitetura segue os padrões de mercado, focando em **Clean Code**, separação de responsabilidades e segurança.

### ✨ Principais Funcionalidades

-   **Autenticação e Segurança:** Login e Cadastro com JWT (JSON Web Token) e senhas criptografadas (BCrypt).
-   **Gestão de Hábitos:** Criar, listar, deletar e marcar hábitos como "feitos".
-   **Lógica de Streak:** Cálculo automático de dias consecutivos de realização do hábito.
-   **Health Check:** Endpoint `/ping` para monitoramento de uptime (compatível com UptimeRobot).
-   **Documentação:** API totalmente documentada com Swagger UI.

---

## 🛠 Tecnologias Utilizadas

-   **Linguagem:** Java 17
-   **Framework:** Spring Boot 3.5.7
-   **Banco de Dados:** PostgreSQL (Produção: Neon.tech / Dev: Local)
-   **Segurança:** Spring Security + Auth0 JWT
-   **Documentação:** SpringDoc OpenAPI (Swagger)
-   **Validação:** Hibernate Validator (Bean Validation)

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

Certifique-se de ter instalado em sua máquina:
-   Java JDK 17+
-   Maven (ou usar o wrapper `./mvnw` incluso)
-   PostgreSQL (ou Docker para subir um container)

### 1. Clonar o repositório

```bash
git clone [https://github.com/SEU-USUARIO/habit-tracker-api.git](https://github.com/SEU-USUARIO/habit-tracker-api.git)
cd habit-tracker-api/api

2. Configurar Variáveis de Ambiente
Por segurança, este projeto utiliza variáveis de ambiente para dados sensíveis. Crie as variáveis no seu sistema ou na sua IDE (IntelliJ/Eclipse):
Variável
Descrição
Exemplo
DB_URL
URL de conexão JDBC do Postgres
jdbc:postgresql://localhost:5432/habit_tracker
DB_USERNAME
Usuário do Banco
postgres
DB_PASSWORD
Senha do Banco
minha_senha
JWT.SECRET
Chave secreta para assinatura do Token
uma_string_aleatoria_e_segura

3. Executar a aplicação
Com as variáveis configuradas, execute via terminal:

Bash


./mvnw spring-boot:run


A aplicação iniciará na porta 8080.
📚 Documentação da API
Com a aplicação rodando, acesse a documentação interativa completa (Swagger UI) em:



http://localhost:8080/swagger-ui/index.html


Dica: Para testar rotas protegidas no Swagger, faça login na rota /auth/login, copie o token gerado e cole no botão Authorize no topo da página.
📂 Estrutura do Projeto
O projeto segue uma estrutura baseada em domínios e camadas:



src/main/java/lab/lp/api
├── controller       # Camada de entrada (REST Controllers)
├── domain           # Regras de Negócio Core
│   ├── model        # Entidades JPA (User, Habit)
│   ├── repository   # Interfaces de acesso a dados
│   └── service      # Lógica de negócios (UserService, HabitService)
├── dto              # Objetos de Transferência de Dados (Records)
└── infra            # Configurações e Implementações Técnicas
    ├── config       # Configs globais (Swagger, etc)
    ├── exception    # Tratamento global de erros
    └── security     # Configuração de Segurança e Filtros JWT


🤝 Contribuição
Contribuições são bem-vindas! Se você tiver sugestões de melhoria ou novas features:
Faça um Fork do projeto.
Crie uma Branch para sua Feature (git checkout -b feature/MinhaFeature).
Faça o Commit (git commit -m 'Adicionando funcionalidade X').
Faça o Push (git push origin feature/MinhaFeature).
Abra um Pull Request.
📝 Licença
Este projeto está sob a licença Apache 2.0.
<p align="center">
Desenvolvido por <strong>Pedro Lucas Maia</strong>
</p>
