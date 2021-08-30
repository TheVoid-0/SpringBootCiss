# SpringBootCiss


A aplicação foi criada utilizando a ferramenta Spring initalzr com as dependências básicas, outras foram sendo adicionadas, porém uma que talvez deva ser destacada é o Lombok
se o Lombok não estiver instalado, dependendo da IDE pode aparecer alguns erros equivocados no código fonte.
<!---Estes são apenas requisitos de exemplo. Adicionar, duplicar ou remover conforme necessário--->
* Java release target 16
* Desenvolvido utilizando a JDK 16
* Projeto montado utilizando o maven
* Durante o desenvolvimento foi usada o Eclipse IDE
* Utilizado o MSQL Server 

## 🚀 Instalando

Para instalar o projeto basta importar em uma IDE com suporte ao maven e rodar os comandos de lifecycle padrão, deve se atentar quanto a geração dos recursos
(generated-sources) essa etapa é necessária devido o uso do QueryDsl

Após rodar o projeto o liquibase deve criar as tabelas no banco e popular um usuário root cujo login e senha são respectivamente: root@root.com, root;
através desse usuário é possível criar os outros, a princípio eu enviaria a senha gerada por e-mail, mas por questões de simplicidade deixei fixa no código como root,
então qualquer usuário criado terá a senha root.

## observações

Eu gostaria de ter feito uma documentação mais detalhada explicando o motivo das decisões de implementação mas infelizmente demorei mais do que eu esperava para realizar o projeto
e não quero postergar a entrega.

O repositório foi deixado como público porque não me parece conter nenhum tipo de informação sensível no projeto, mas se necessário, basta me solicitar que deixarei privado.


