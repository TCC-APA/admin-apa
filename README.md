## Passo a passo de como executar o projeto "admin-apa"

#### Banco de dados

O projeto utiliza do banco de dados **PostgreSQL** (utilizamos a versão 12 do banco, porém, outras versões podem funcionar). É necessário instalá-lo na sua máquina para a utilização do projeto.

Após a instalação, caso não possua um servidor ativo, crie um servidor em seu banco de dados.

Para se conectar ao projeto, uma *Database* (base de dados) de nome **apaWS_DB** deverá ser criada. Após a criação, certifique-se que a base de dados está conectada.

Com a base da dados criada, para criar as tabelas e suas respectivas colunas, basta executar o projeto e realizar alguma chamada aos endpoints existentes.

#### Configurando o ambiente

O projeto utiliza-se do Apache Tomcat para subir a aplicação (utilizamos a versão 9 para executar localmente). Após instalá-lo na máquina, será necessário configurar uma instância de servidor para alocar o projeto. 

Na pasta de instalação do tomcat, acesse a pasta "*conf*" e abra o arquivo "*tomcat-users.xml*" para edição. Dentro do fragmento `<tomcat-users></tomcat-users>` deverão ser inseridas, caso não existam ainda, duas *roles*: 
`<role rolename="manager-gui"/>` e  `<role rolename="manager-script"/>`. Abaixo, deverá ser inserido um usuário e senha com essas *roles* atribuídas a ele. Ex:

`<user username="apaws" password="apawspassword" roles="manager-gui,manager-script" />`

