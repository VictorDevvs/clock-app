# ⏱️ Clock App — Timer API

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de timers, oferecendo suporte a dois tipos principais:

- **Countdown**: timer regressivo que inicia a partir de um valor definido.
- **Stopwatch**: timer progressivo que inicia em `0` e incrementa continuamente até ser pausado ou resetado.

O projeto foi construído seguindo os princípios de **Arquitetura Hexagonal (Ports and Adapters)** e **Domain-Driven Design (DDD)**, sendo totalmente desacoplado de interface gráfica e preparado para consumo por aplicações frontend.

---

## 📌 Objetivo do Projeto

Fornecer uma API robusta e extensível para:
- Criar timers.
- Controlar seu estado (start, pause, stop, reset).
- Persistir dados.
- Possibilitar futura integração com interfaces web ou mobile.

---

## 🧠 Tipos de Timer

### 1. Countdown
Timer regressivo que recebe um valor inicial (em segundos) e decrementa até zero.

Exemplo: 120 segundos 
02:00 -> 01:59 -> 01:58 -> ...


---

## ⚙️ Funcionalidades

- Criar timers do tipo `COUNTDOWN` ou `STOPWATCH`.
- Iniciar timer.
- Pausar timer.
- Parar timer.
- Resetar timer.
- Persistência em banco de dados.
- Validação de dados de entrada.
- Arquitetura desacoplada e testável.

---

## 🏗️ Arquitetura

O projeto utiliza:

### 🔹 Arquitetura Hexagonal
Separação clara entre:
- **Domínio** (regras de negócio)
- **Aplicação**
- **Infraestrutura**
- **Adaptadores (Controllers / Repositories)**

Fluxo:
Controller → Application Service → Domain → Port → Adapter (Repository)


### 🔹 Domain-Driven Design (DDD)
- Entidades ricas em comportamento.
- Serviços de domínio para regras de negócio.
- Repositórios como portas (interfaces).

---

## 🧰 Tecnologias e Dependências

- **Java 21**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **H2 Database**
- **Bean Validation**
- **Lombok**

---

## 📦 Pré-requisitos

- Java 21+
- Maven 3.9+
- IDE (IntelliJ, Eclipse, VS Code)

---

## ▶️ Como executar o projeto

### 1. Clone o repositório
```bash
git clone https://github.com/VictorDevvs/clock-app.git
cd clock-app
```
### 2. Execute a aplicação
```bash
mvn spring-boot:run
```
A API estará disponível em: http://localhost:8080

### Endpoints

### POST api/v1/timers
{
  "initialDurationInSeconds": 60,
  "timerType": "COUNTDOWN"
}

Response
{
    "id": "54c225ba-cdaa-4611-bd4e-72aeb90b28ed",
    "initialDurationInSeconds": 60,
    "currentTimeInSeconds": 60,
    "status": "STOPPED",
    "type": "COUNTDOWN",
    "createdAt": "2026-02-03T12:19:26.350720300Z",
    "updatedAt": "2026-02-03T12:19:26.350720300Z"
}

### PATCH api/v1/timers/{id}
{
    "action":"START"
}

Response
{
    "id": "54c225ba-cdaa-4611-bd4e-72aeb90b28ed",
    "initialDurationInSeconds": 60,
    "currentTimeInSeconds": 60,
    "status": "RUNNING",
    "type": "COUNTDOWN",
    "createdAt": "2026-02-03T12:19:26.350720Z",
    "updatedAt": "2026-02-03T12:21:30.108999900Z"
}

### GET api/v1/timers
Response
[
    {
        "id": "54c225ba-cdaa-4611-bd4e-72aeb90b28ed",
        "initialDurationInSeconds": 60,
        "currentTimeInSeconds": 60,
        "status": "RUNNING",
        "type": "COUNTDOWN",
        "createdAt": "2026-02-03T12:19:26.350720Z",
        "updatedAt": "2026-02-03T12:21:30.109Z"
    }
]

### GET api/v1/timers/{id}
Response
{
    "id": "54c225ba-cdaa-4611-bd4e-72aeb90b28ed",
    "initialDurationInSeconds": 60,
    "currentTimeInSeconds": 60,
    "status": "RUNNING",
    "type": "COUNTDOWN",
    "createdAt": "2026-02-03T12:19:26.350720Z",
    "updatedAt": "2026-02-03T12:21:30.108999900Z"
}

### DELETE api/v1/timers/{id}
Response
204 

🧪 Banco de Dados

Banco em memória H2 para testes e desenvolvimento.

Acesso: http://localhost:8080/h2-console

Configuração padrão:

JDBC URL: jdbc:h2:mem:clockdb
User: sa
Password: (vazio)

🔒 Validação

Utiliza Bean Validation para:

- Validar valores iniciais.
- Impedir timers com valores inválidos.
- Garantir consistência do domínio.

🚀 Possíveis Evoluções

- Autenticação e autorização.
- WebSocket para atualização em tempo real.
- Persistência em banco relacional real (PostgreSQL).
- Monitoramento de múltiplos timers simultâneos.
- Interface web.

👨‍💻 Autor

Victor Santos
Projeto desenvolvido para estudo de:

- Arquitetura Hexagonal
- DDD
- APIs REST
- Boas práticas com Spring Boot
