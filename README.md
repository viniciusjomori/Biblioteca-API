# API da Biblioteca

Esta é uma API de uma biblioteca que utiliza o PostgreSQL como banco de dados. Os usuários podem realizar operações com livros, usuários, cargos, empréstimos e reservas

## Instalação e Configuração

### Pré-requisitos

1.  Instalar o arquivo LibraryAPP.jar, em "releases"
2.  Java
4.  PostgreSQL

### Configuração do Banco de Dados

Crie um banco de dados PostgreSQL com o nome "Library", usuário "postgres" e senha "senha".

### Uso da API

Na pasta contendo o aquivo jar, execute o seguinte comando:
```shell
java -jar LibraryAPP.jar
```

A API usará o endereço `localhost:8080`

## Cargos
Um usuário pode ter um dentre três cargos (CLIENT, EMPLOYEE e ADMINISTRATOR) ou nenhum. Esse cargo determinará quais métodos estarão acessíveis ao usuário. Sem autenticação, o usuário é capaz de consultar livros e criar uma conta de cliente. Um cliente pode consultar seus dados, reservar livros e cancelar suas reservas. Um funcionário pode registrar, atualizar e excluir livros, realizar empréstimos e consultar dados de todas as tabelas do banco de dados. Um administrador pode realizar todos os métodos de um funcionário, além de registrar, atualizar e deletar funcionários.

Para autenticação, esta API usa Token JWT, o qual é adquirido no método de login e inserido no campo "Authentication" (tipo: Bearer Token) no cabeçalho da requisição. Cada token corresponde a um usuário, portanto se este for autenticado, a API autenticará o autor da requisição.

## Usuários de Teste

Ao iniciar a API, é registrado automaticamente três usuários, cada um com um cargo diferente. Seus dados de login estão listados abaixo:
### Cliente
```json
{
	"username": "client",
	"password": "senha"
}
``` 
### Employee
```json
{
	"username": "employee",
	"password": "senha"
}
``` 
### Administrator
```json
{
	"username": "adm",
	"password": "senha"
}
``` 

## Métodos Disponíveis

### Usuários

- **Consulta de Livros:**
	-   **Método:** GET
	-   **Endpoint:** `/book`
	-   **Descrição:** Permite que o usuário consulte a lista de livros disponíveis na biblioteca.

- **Consulta de Livros por ID:**
	-   **Método:** GET
	-   **Endpoint:** `/book/{ID DO LIVRO`
	-   **Descrição:** Permite que o usuário consulte um livro específico a partir de sua identificação.

-   **Criação de Conta de Cliente:**
    -   **Método:** POST
    -   **Endpoint:** `/client`
    -   **Descrição:** Permite que o usuário crie uma nova conta de cliente.
    -   **Corpo da Requisição (JSON):**
```json
{
	"username": "Nome do usuário",
	"password": "Senha do usuário"
}
``` 
- **Autenticação e Geração de Token JWT:**
	-   **Método:** POST
	-   **Endpoint:** `/user/login`
	-   **Descrição:** Permite que um usuário faça login e obtenha um token JWT para autenticação nas próximas requisições. A autenticação é essencial para que o usuário tenha acesso aos dados de sua conta e aos métodos que exigem um cargo específico.
	-   **Corpo da Requisição (JSON):**
```json
{
	"username": "Nome do usuário",
	"password": "Senha do usuário"
}
```
Esse método retorna um token JWT que deve ser copiado e colado no campo "Authorization" (tipo: Bearer Token) do cabeçalho das próximas requisições.

- **Consultar Informações Básicas do Usuário Autenticado:**
	-   **Método:** GET
	-   **Endpoint:** `/user/online`
	-   **Descrição:** Permite ao usuário autenticado consultar seus dados como ID, nome e cargo.

### Cliente
-   **Consulta dos Dados da Conta Online:**
    
    -   **Método:** GET
    -   **Endpoint:** `/client/online`
    -   **Descrição:** Permite que o cliente autenticado consulte os dados da sua conta, incluindo o ID, nome, empréstimos e reservas.

-   **Realização de Reserva de Livro:**
    -   **Método:** POST
    -   **Endpoint:** `/client/online/reserve/book/{ID DO LIVRO}`
    -   **Descrição:** Permite que o cliente autenticado realize a reserva de um livro específico.
        
-   **Cancelamento de Reserva:**
    -   **Método:** POST
    -   **Endpoint:** `/client/online/reserve/{ID DA RESERVA}/cancel`
    -   **Descrição:** Permite que o cliente autenticado cancele uma de suas reservas.

-   **Consulta de Reservas do Empréstimos:**
    -   **Método:** GET
    -   **Endpoint:** `/client/online/loan`
    -   **Descrição:** Permite que o cliente autenticado consulte seus empréstimos.

-   **Consulta de Reservas do Cliente :**
    -   **Método:** GET
    -   **Endpoint:** `/client/online/reserve`
    -   **Descrição:** Permite que o cliente autenticado consulte suas reservas.

-   **Atualização da Conta do Cliente :**
    -   **Método:** PUT
    -   **Endpoint:** `/client/online`
    -   **Descrição:** Permite que o cliente autenticado altere seu nome de usuário e senha.
	-   **Corpo da Requisição (JSON):**
```json
{
	"username": "Novo nome do usuário",
	"password": "Nova senha do usuário"
}
```
Esse método retorna o novo token JWT do cliente.

-   **Exclusão da Conta do Cliente :**
    -   **Método:** DELETE
    -   **Endpoint:** `/client/online`
    -   **Descrição:** Permite que o cliente autenticado delete sua conta.

### Funcionário

### `/book`
-   **Criação de Novo Livro:**
    -   **Método:** POST
    -   **Endpoint:** `/book`
    -   **Descrição:** Permite que um funcionário salve um novo livro no banco de dados.
    -   **Corpo da Requisição (JSON):**
```json
{
	"title": "Título do livro",
	"author": "Autor do livro",
	"publishingCompany": "Editora do livro",
	"releaseDate": "2001-01-01",
	"totalCopies": 10
}
``` 
-   **Atualização de Livro Existente:**
    -   **Método:** PUT
    -   **Endpoint:** `/book/{ID DO LIVRO}`
    -   **Descrição:** Permite que um funcionário atualize as informações de um livro existente.
    -   **Corpo da Requisição (JSON):**
```json
{
	"title": "Novo título do livro",
	"author": "Novo autor do livro",
	"publishingCompany": "Nova editora do livro",
	"releaseDate": "2002-02-02",
	"totalCopies": 11
}
``` 
        
-   **Exclusão de Livro:**
    -   **Método:** DELETE
    -   **Endpoint:** `/book/{ID DO LIVRO}`
    -   **Descrição:** Permite que um funcionário exclua um livro do banco de dados.

### `/loan`

-   **Realização de Empréstimo:**
    -   **Método:** POST
    -   **Endpoint:** `/loan`
    -   **Descrição:** Permite que um funcionário realize um empréstimo de um livro para um cliente.
    -   **Corpo da Requisição (JSON):**
```json
{
	"clientId": "ID do cliente",
	"bookId": "ID do livro"
}
``` 
        
-   **Devolução de Empréstimo:**
    -   **Método:** POST
    -   **Endpoint:** `/loan/deliver/{ID DO EMPRESTIMO}`
    -   **Descrição:** Permite que um funcionário registre a devolução de um livro emprestado.
        
-   **Realização de Empréstimo a partir de Reserva:**
    -   **Método:** POST
    -   **Endpoint:** `/loan/from-reserve/{ID DA RESERVA}`
    -   **Descrição:** Permite que um funcionário realize um empréstimo a partir de uma reserva existente.

-   **Consulta de Empréstimos:**
    -   **Método:** GET
    -   **Endpoint:** `/loan`
    -   **Descrição:** Permite que um funcionário consulte todos os empréstimos salvos no banco de dados.

-   **Consulta de Empréstimos por Cliente:**
    -   **Método:** GET
    -   **Endpoint:** `/loan/client/{ID DO CLIENTE}`
    -   **Descrição:** Permite que um funcionário consulte todos os empréstimos salvos no banco de dados de um cliente.

-   **Consulta de Empréstimos por Livro:**
    -   **Método:** GET
    -   **Endpoint:** `/loan/book/{ID DO LIVRO}`
    -   **Descrição:** Permite que um funcionário consulte todos os empréstimos salvos no banco de dados de um livro.

-   **Consulta de Empréstimos por Status:**
    -   **Método:** GET
    -   **Endpoint:** `/loan/status/{STATUS}`
    -   **Descrição:** Permite que um funcionário consulte todos os empréstimos salvos no banco de dados com um determinado status. Esses status podem ser "active", "late" e "delivered".

### `/reserve`

-   **Consulta de Reservas:**
    -   **Método:** GET
    -   **Endpoint:** `/reserve`
    -   **Descrição:** Permite que um funcionário consulte todas as reservas salvas no banco de dados

-   **Consulta de Reservas por Cliente:**
    -   **Método:** GET
    -   **Endpoint:** `/reserve/client/{ID DO CLIENTE}`
    -   **Descrição:** Permite que um funcionário consulte todas as reservas salvas no banco de dados de um cliente.

-   **Consulta de Reservas por Livro:**
    -   **Método:** GET
    -   **Endpoint:** `/reserve/book/{ID DO LIVRO}`
    -   **Descrição:** Permite que um funcionário consulte todas as reservas no banco de dados de um livro salvas.

-   **Consulta de Reservas por Status:**
    -   **Método:** GET
    -   **Endpoint:** `/reserve/status/{STATUS}`
    -   **Descrição:** Permite que um funcionário consulte todas as reservas salvas no banco de dados com um determinado status. Esses status podem ser "active", "canceled", "done" e "expired".

### `/user`

-   **Consulta de Usuários:**
    -   **Método:** GET
    -   **Endpoint:** `/user`
    -   **Descrição:** Permite que um funcionário consulte todos os usuários salvos no banco de dados

### `/role`

-   **Consulta de Cargos:**
    -   **Método:** GET
    -   **Endpoint:** `/role`
    -   **Descrição:** Permite que um funcionário consulte todos os cargos salvos no banco de dados e os usuários pertencentes a cada um deles.

-   **Consulta de Cargo por Nome:**
    -   **Método:** GET
    -   **Endpoint:** `/role/{NOME DO CARGO}`
    -   **Descrição:** Permite que um funcionário consulte um cargo salvo no banco de dados com base no seu nome e seus usuários. O nome do cargo pode ser "client", "employee" e "administrator".

-   **Consulta de um Cargo ou Superiores:**
    -   **Método:** GET
    -   **Endpoint:** `/role/{NOME DO CARGO}/or-above`
    -   **Descrição:** Permite que um funcionário consulte um cargo e seus superiores salvos no banco de dados. Por exemplo, `/role/employee/or-above` resultará na consulta de funcionários e administradores.

-   **Consulta de um Cargo ou Inferiores:**
    -   **Método:** GET
    -   **Endpoint:** `/role/{NOME DO CARGO}/or-below`
    -   **Descrição:** Permite que um funcionário consulte um cargo e seus inferiores salvos no banco de dados. Por exemplo, `/role/administrator/or-below` resultará na consulta de funcionários e administradores.

### Administrador
Um administrador pode realizar todos os métodos de um funcionário e os métodos listados abaixo.

-   **Criação de Novo Funcionário:**
    -   **Método:** POST
    -   **Endpoint:** `/employee`
    -   **Descrição:** Permite que um administrador adicione um novo funcionário ao banco de dados.
    -   **Corpo da Requisição (JSON):**
```json
{
	"username": "Nome de usuário do funcionário",
	"password": "Senha do funcionário",
	"role": "employee ou administrator"
}
``` 

-   **Atualização de Dados do Funcionário:**
    -   **Método:** PUT
    -   **Endpoint:** `/employee/{ID DO FUNCIONÁRIO}`
    -   **Descrição:** Permite que um administrador atualize as informações de um funcionário existente.
    -   **Corpo da Requisição (JSON):**
```json
{
	"username": "Novo nome de usuário do funcionário",
	"password": "Nova senha do funcionário",
	"role": "employee ou administrator"
}
```
-   **Exclusão de Funcionário:**
    
    -   **Método:** DELETE
    -   **Endpoint:** `/employee/{ID DO FUNCIONÁRIO}`
    -   **Descrição:** Permite que um administrador exclua um funcionário do banco de dados.

## Licença

Este projeto está licenciado sob a  [MIT License](https://chat.openai.com/LICENSE).