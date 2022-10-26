
# Jogo da Memória FACOM

Jogo da Memória FACOM é uma aplicação desenvolvida em sala de aula no contexto da disciplina Programação para Web oferecida pela FACOM/UFMS e ministrada pelo prof. Hudson Silva Borges.

## Requisitos
- [Java JRE e JDK](https://www.oracle.com/java/technologies/downloads/)
- [Eclipse IDE for Web Developers](https://www.eclipse.org/downloads/)
- [Apache Tomcat EE 8](https://tomee.apache.org/)

## Preparação do ambiente

Para começar a usar essa aplicação, você deve:

1. Faça o download deste projeto usando o `git` ou diretamente por meio da opção `Download ZIP`.

2. Execute o Eclipse IDE e crie um workspace para trabalho (ou abrar se já houver um).

3. No *workspace* criado, mova o projeto clonado ou extraia os arquivos. Os arquivos deverão estar dentro de uma pasta, e não diretamente espalhados no workspace.

4. Em seguida, você deve abrir o projeto dentro do Eclipse  em `File > Import... > General > Existing Projects into Workspace`. Na janela aberta, selecione o diretório extraído no *workspace* e clique em `Finish`.

5. Após o processo de import, atualize o Maven do projeto clicando com o botão direito no projeto e `Maven > Update Project...`. Neste momento você já terá a aplicação instalada e compilada (se tudo ocorreu normalmente).

6.  Agora devemos configurar o servidor para a aplicação com o Apache Tomcat EE. Para isso, acesse a aba `Servers` na parte inferior da IDE e selecione `No servers are available. Click this link to create....`. Neste ponto siga o processo assim como visto em sala de aula.

7. Feito isso, basta clicar com o botão direito sobre o projeto e selecionar `Run As > Run on Server` e selecionar o servidor configurado no passo acima.

8. Após iniciar o servidor você pode acessar o endereço [http://localhost:8080/Jogo_da_Memoria/home](http://localhost:8080/Jogo_da_Memoria/home) que é a página inicial da nossa aplicação.

9. (BÔNUS) Se você desejar remover o contexto `Jogo_da_Memoria` da URL, você pode acessar a opção `Properties > Web Project Settings` (clicando com o botão direito n projeto) e alterar para o root (`/`) ou outro qualquer.
