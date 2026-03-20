# realtime-chat-service

Projeto de estudo e prática com Spring Boot, focado na implementação de um serviço de chat em tempo real com autenticação JWT e comunicação via WebSocket/STOMP.

## Tecnologias

- **Java 25** + **Spring Boot 4.0.3**
- **Spring Security** — autenticação e autorização
- **Spring WebSocket** + **STOMP** — comunicação em tempo real
- **JJWT 0.11.5** — geração e validação de tokens JWT
- **Argon2** + **pepper** — hashing seguro de senhas
- **H2** — banco de dados em memória *(uso em desenvolvimento/testes)*
- **Spring Data JPA** + **Hibernate**
- **Springdoc OpenAPI 3** — documentação Swagger
- **Lombok**

## Estrutura do projeto

```
src/main/java/realtime/chat/
├── config/
│   ├── DataInitializer.java        # Cria o usuário admin na inicialização
│   ├── JwtAuthFilter.java          # Filtro de autenticação JWT por requisição
│   ├── PepperPasswordEncoder.java  # Wrapper Argon2 + pepper
│   ├── SecurityConfig.java         # Configuração do Spring Security
│   ├── WebSocketConfig.java        # Endpoints e broker STOMP
│   └── WebSocketSecurityConfig.java
├── controller/
│   ├── AuthController.java         # Login e registro
│   └── ChatController.java         # Mensagens via WebSocket
├── dto/
├── model/
│   ├── UserEntity.java
│   ├── MessageEntity.java
│   └── enums/
│       └── Role.java               # USER, ADMIN
├── repository/
├── service/
│   ├── JwtService.java
│   ├── UserDetailsService.java
│   └── ChatService.java
└── RealtimeChatApplication.java
```

## Segurança

- Senhas armazenadas com **Argon2** (padrões Spring Security v5.8: 16 bytes salt, 32 bytes hash, 3 iterações, 64MB memória)
- **Pepper** aplicado antes do hash — mesmo que o banco vaze, o hash é inútil sem o pepper
- Autenticação stateless via **JWT Bearer token**
- Token propagado no header STOMP para autenticar conexões WebSocket

## Variáveis de ambiente

| Variável               | Descrição                          | Padrão (dev)         |
|------------------------|------------------------------------|----------------------|
| `JWT_SECRET`           | Chave de assinatura do JWT         | valor embutido       |
| `JWT_REFRESH_EXPIRATION` | Tempo de expiração do token (ISO 8601) | `P7D` (7 dias) |
| `JWT_PEPPER`           | Pepper adicionado ao hash da senha | valor embutido       |

> Em produção, **nunca** use os valores padrão. Defina as variáveis de ambiente com valores gerados de forma segura.

## Como executar

```bash
# Com Maven Wrapper
./mvnw spring-boot:run

# Definindo variáveis de ambiente (exemplo)
JWT_SECRET=<sua_chave> JWT_PEPPER=<seu_pepper> ./mvnw spring-boot:run
```

## Endpoints REST

| Método | Rota             | Acesso      | Descrição              |
|--------|------------------|-------------|------------------------|
| POST   | `/api/auth/login`    | Público     | Autentica e retorna JWT |
| POST   | `/api/auth/register` | Público     | Cadastra novo usuário (role `USER`) |

## WebSocket

- Endpoint de conexão: `ws://localhost:8080/api/ws-chat` (SockJS)
- Enviar mensagem: `/app/chat.sendMessage`
- Receber mensagens: `/topic/public`
- Autenticação: header `Authorization: Bearer <token>` na conexão STOMP

## Frontend

O projeto inclui um cliente web em `/api/index.html` para testes e validação da implementação. Ele oferece:

- Tela de login e cadastro de conta
- Chat em tempo real após autenticação
- Mensagens próprias destacadas visualmente
- Logout com desconexão do WebSocket

## Banco de dados (H2)

Por ser um projeto de estudo, o banco de dados é **em memória** — os dados são perdidos ao reiniciar a aplicação.

- Console H2: `http://localhost:8080/api/h2-console`
- JDBC URL: `jdbc:h2:mem:chatdb`
- Usuário: `sa` / Senha: *(vazia)*

Um usuário `admin` com senha `admin123` é criado automaticamente na inicialização via `DataInitializer`.

## Documentação da API

Swagger UI disponível em: `http://localhost:8080/api/swagger-ui.html`
