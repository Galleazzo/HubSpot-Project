# Integração com a Plataforma de CRM HubSpot

Este projeto tem como objetivo integrar uma aplicação Java com a plataforma de CRM **HubSpot**, permitindo a criação de contatos e o recebimento de eventos via Webhook.

## Requisitos

Antes de começar, certifique-se de ter os seguintes itens instalados no seu ambiente:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=windows) (ou sua IDE favorita)
- [Ngrok](https://dashboard.ngrok.com/get-started/setup/windows)
- Conta no [HubSpot Developer](https://developers.hubspot.com/)
- Conta no [Ngrok](https://ngrok.com)

> Obs: Esperamos que você já tenha conta no GitHub para clonar o projeto ;)
Sem Banco de Dados:
Esse projeto não utiliza nenhum banco de dados. A decisão foi intencional, uma vez que o foco está na integração com a API do HubSpot e no fluxo OAuth + Webhook. Como não havia a necessidade de armazenar informações localmente, não foi necessário complicar o projeto com uma camada de persistência. Isso ajuda a manter o código mais enxuto e direto ao ponto, facilitando o entendimento e o aprendizado.
---

## Etapas de Configuração

### 1. Criando seu aplicativo no HubSpot

- Acesse o portal de desenvolvedor da HubSpot e crie um novo aplicativo.
- Anote o **Client ID** e o **Client Secret**.
- Em **Redirect URLs**, adicione: `http://localhost:8080/oauth/callback`
- Em **Escopos (Scopes)**, adicione:
  - `crm.objects.contacts.read`
  - `crm.objects.contacts.write`
  - `oauth`
- Crie uma **conta de teste** do desenvolvedor para testar sua aplicação.

### 2. Criando sua conta e instalando o Ngrok

- Acesse o site do [Ngrok](https://ngrok.com) e crie uma conta.
- Siga os passos para instalar e configurar o ngrok no seu ambiente local.

### 3. Clonando o projeto e configurando as variáveis de ambiente

```bash
git clone https://github.com/seu-usuario/seu-projeto.git
cd seu-projeto
```

Adicione as seguintes variáveis de ambiente (ou configure no `.env` ou diretamente na sua IDE):

```
HUBSPOT_CLIENT_ID=seuClientId
HUBSPOT_CLIENT_SECRET=seuClientSecret
```

### 4. Executando o projeto

Rode o projeto pela sua IDE ou via terminal:

```bash
mvn spring-boot:run
```

Verifique se o log mostra algo como:

```
Started HubSpotProjectApplication in X seconds
```

### 5. Iniciando o Ngrok

No terminal, execute:

```bash
ngrok http 8080
```

Copie a URL gerada, algo como:

```
https://seu-endereco.ngrok-free.app
```

### 6. Configurando o Webhook no HubSpot

No seu app do HubSpot, adicione a URL do webhook:

```
https://seu-endereco.ngrok-free.app/webhook/contact
```

Com as seguintes opções:

- **Tipo de objeto**: Contato
- **Assinatura**: Criado

---

## Fluxo de Autenticação e Uso

O fluxo do projeto é o seguinte:

1. Geração da URL de autorização
2. Usuário acessa a URL e autoriza o aplicativo
3. Callback da autorização retorna um access token
4. Access token é usado para criar contatos
5. Ao criar um contato, o HubSpot dispara um webhook para nossa aplicação

### Requisições CURL de exemplo

**1. Gerar URL de autorização**
```bash
curl --location 'http://localhost:8080/oauth/authorize'
```

**2. Callback de autorização (após logar e aprovar)**
```bash
curl --location --request POST 'http://localhost:8080/oauth/callback?code=SEU_CODIGO_AQUI'
```

**3. Criar um contato**
```bash
curl --location 'http://localhost:8080/hubspot/contacts' \
--header 'Authorization: Bearer SEU_TOKEN_AQUI' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Teste",
    "lastName": "Silva",
    "email": "teste@exemplo.com"
}'
```

---

## Exemplos Visuais

**Tela de autorização:**
![image](https://github.com/user-attachments/assets/8a957f2d-1c56-409d-a612-d03c278309b9)

**Retorno do access token:**
![image](https://github.com/user-attachments/assets/e92df1d2-68b9-4a3f-92d0-03e3a21c1f13)

**Resposta da criação de contato:**
![image](https://github.com/user-attachments/assets/bdc71e75-1879-4610-9cd8-88c12560b7a5)

**Webhook recebido no log:**
![eventos-hubspot](https://github.com/user-attachments/assets/cd003e07-8102-427b-9a99-1cb59ea550c2)

---

## Estrutura do Projeto

O projeto é simples e direto, com uma arquitetura limpa e legível, seguindo os princípios de **Clean Code** e **SOLID**. Pensado para facilitar a curva de aprendizado de outros desenvolvedores.

### Dependências principais:
- `spring-boot-starter-web`
- `jackson-databind`
- `spring-boot-devtools`
- `spring-boot-starter-test`

Evitei usar bibliotecas externas desnecessárias para manter a simplicidade e foco na integração com a API do HubSpot.

---

## Links úteis e Referências

- https://developers.hubspot.com/docs/guides/apps/authentication/oauth-quickstart-guide
- https://developers.hubspot.com/docs/guides/api/app-management/oauth-tokens
- https://developers.hubspot.com/docs/guides/apps/private-apps/overview
- https://github.com/HubSpot/oauth-quickstart-nodejs/blob/master/index.js
- https://community.hubspot.com/t5/APIs-Integrations/Cannot-find-the-Client-Id-and-Client-Secret/m-p/721186
- https://developers.hubspot.com/docs/reference/api/crm/objects/contacts/v3
- https://developers.hubspot.com/docs/guides/api/overview
- https://developers.hubspot.com/docs/guides/api/app-management/webhooks#webhook-payloads
- https://dashboard.ngrok.com/get-started/setup/windows

---

Obrigado!

