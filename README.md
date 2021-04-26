## Passo a passo de como executar o projeto "admin-apa"

#### Banco de dados

O projeto utiliza do banco de dados **PostgreSQL** (utilizamos a versão 12 do banco, porém, outras versões podem funcionar). É necessário instalá-lo na sua máquina para a utilização do projeto.

Após a instalação, caso não possua um servidor ativo, crie um servidor em seu banco de dados.

Para se conectar ao projeto, uma *Database* (base de dados) de nome **apaWS_DB** deverá ser criada. Após a criação, certifique-se que a base de dados está conectada.

Com a base da dados criada, para criar as tabelas e suas respectivas colunas, basta executar o projeto e realizar alguma chamada aos endpoints existentes.

#### Configurando o ambiente

Para buildar o projeto e baixar as dependências, utilizamos do Maven. Ele deve ser instalado na máquina que rodará o projeto. [Este tutorial pode ser seguido para a instalação](https://maven.apache.org/install.html)

O projeto utiliza-se do Apache Tomcat para subir a aplicação (utilizamos a versão 9 para executar localmente). [Este tutorial] pode ser seguido para a instalação no Ubuntu](https://phoenixnap.com/kb/how-to-install-tomcat-ubuntu) e [neste tutorial](https://www.liquidweb.com/kb/installing-tomcat-9-on-windows/) se encontra o guia de instalação no windows. Após instalá-lo na máquina, será necessário configurar uma instância de servidor para alocar o projeto. 

Na pasta de instalação do tomcat, acesse a pasta "*conf*" e abra o arquivo "*tomcat-users.xml*" para edição. Dentro do fragmento `<tomcat-users></tomcat-users>` deverão ser inseridas, caso não existam ainda, duas *roles*: 
`<role rolename="manager-gui"/>` e  `<role rolename="manager-script"/>`. Abaixo, deverá ser inserido um usuário e senha com essas *roles* atribuídas a ele. Exemplo:

```
<tomcat-users xmlns="http://tomcat.apache.org/xml"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
              version="1.0">
  <role rolename="manager-gui"/>
  <role rolename="manager-script"/>
  <user username="apaws" password="apawspassword" roles="manager-gui,manager-script" />
</tomcat-users>
```
Este mesmo usuário e senha deverá ser utilizado para a configuração do Maven a seguir.

Para configurar o Maven, após a instalação utilizando o tutorial acima, siga para a pasta onde foi instalado o mesmo. Na pasta "*conf*", o arquivo "*settings.xml*" poderá ser encontrado. nele, deverá ser adicionado dentro do fragmento `<servers></servers>` um servidor para que possa ser deployado o projeto. O servidor deverá ter o id "**APAServer**" e deverá utilizar o mesmo usuário e senha designados anteriormente ao Tomcat. Exemplo: 
```
<server>
      <id>APAServer</id>
      <username>apaws</username>
      <password>apawspassword</password>
</server>
```

Para clonar o projeto na máquina desejada, utilizaremos o git, que pode ser instalado [seguindo este tutorial](https://git-scm.com/book/pt-br/v2/Come%C3%A7ando-Instalando-o-Git). Caso a pasta destino não tenha sido inicializada pelo git, utilize nela o comando em um terminal:

`git init`

Após inicializado o git, utilize o comando `git clone` seguido do link disponibilizado na página do github do projeto para cloná-lo.

`git clone https://github.com/TCC-APA/admin-apa.git`

### Inicializando o projeto

Após configurado o ambiente e com o banco de dados inicializado, para inicializar o projeto, deve-se levantar uma instância do Tomcat. Para isto, deverá ser rodado o script "**startup.bat**" no caso do Windows, ou "**startup.sh**" no caso do Ubuntu. Ambos podem ser encontrados na pasta *bin* localizada no diretório de instalação do Tomcat na máquina. Ao executar o script, uma nova janela do **terminal** será aberta, e ela deverá permanecer para que o projeto consiga funcionar. 

Com o tomcat inicializado, basta levantar o projeto em si. Para isto, no projeto clonado abra um **terminal** e digite o seguinte comando:

`mvn clean install tomcat7:redeploy`

Certifique-se de que esta mensagem apareceu no terminal: 

```
[INFO] tomcatManager status code:200, ReasonPhrase:
[INFO] OK - Instalada aplicaÃ§Ã£o no path de contexto [/apa]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  16.417 s
[INFO] Finished at: 2021-03-23T02:09:48-03:00
[INFO] ------------------------------------------------------------------------
```

Caso tenha aparecido, o projeto se encontra deployado.

### Produção (Dockerfile)

Pré-requisito: Docker [https://www.digitalocean.com/community/tutorials/como-instalar-e-usar-o-docker-no-ubuntu-18-04-pt]

`mvn clean install`

`docker-compose up -d`

`http://localhost:8080/apa/monitoracao`


#### Exemplo de requisição: 

```
curl --location --request POST 'http://localhost:8080/apa/aluno' \
--header 'Content-Type: application/json' \
--data-raw '{
"idade": 23,
"matricula": "12342352BCC",
"nome": "Fernando Godoyy",
"senha": "123456",
"genero": "M"
}'
```