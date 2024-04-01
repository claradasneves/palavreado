# palavreado
![Linguagem mais utilizada](https://img.shields.io/github/languages/top/claradasneves/palavreado)
![Último commit](https://img.shields.io/github/last-commit/claradasneves/palavreado)
![README](https://img.shields.io/badge/readme-8A2BE2)

> O Palavreado é um jogo no estilo wordle (ou termo.oo, versão brasileira) onde foi utilizado TCP para conectar dois jogadores e fazer uma partida online. O projeto foi desenvolvido para o trabalho da disciplina Redes de Computadores.

## 🎯 Funcionalidades

- Esse projeto usa TCP para fazer a conexão entre cliente e servidor
- Permite com que dois usuários joguem "Palavreado"
- Usado para entender melhor comandos TCP e sockets de uma forma clara e simples

## 🖱 Como instalar e rodar
  Para compilar o código pela parte do servidor, é necessário digitar na prompt de comando javac Servidor.java e javac PalavreadoServidor.java. Assim, o servidor já poderá ser inicializado.
  
  Já compilar o código sendo o cliente, é preciso escrever javac Cliente.java e javac PalavreadoCliente.java no prompt de comando. Assim, você já estará pronto para poder ser um jogador. Não é necessário usar nenhum SO em específico ou API para compilar.
  
  Para executar o código, é preciso executar as classes no prompt de comando. Como não há um arquivo executável (um jar ou batch/shell script), é necessário seguir a etapa anterior de compilar o código e digitar:
• java PalavreadoServidor, para criar um servidor.
• java PalavreadoCliente, para ser um jogador.

## ⬇ Como usar o programa
  O jogo Palavreado é jogado em turnos pelos dois jogadores. Após ambos entrarem no jogo, as regras iniciais e instruções são enviadas e, é importante ressaltar, que mesmo que não seja a vez do jogador, é necessário que ele digite e envie a letra ’N’ para que a rodada seja contabilizada, enquanto o outro jogador precisa tentar acertar a palavra.
  
  O servidor aceitará apenas que o cliente envie palavras do mesmo tamanho que a palavra secreta, caso contrário, ele enviará uma mensagem dizendo para o jogador tentar novamente.
  
  Caso a palavra que o jogador enviou esteja correta, o jogo acaba e é enviada uma mensagem para o outro jogador. Caso na palavra enviada pelo jogador, uma determinada letra esteja na posição correta em comparação com a palavra secreta, ela fica em letra maiúscula, caso a letra esteja na palavra secreta mas não naquela posição, ela aparece apenas como minúscula, e caso haja uma letra que não esteja na palavra secreta, ela é substituida por um underline. Por exemplo, digamos que a palavra secreta seja "corda"e o jogador, em uma das tentativas, digitou "caldo", o servidor retornará "Ca D ".
  
  Caso um dos jogadores queira sair do jogo, basta digitar a letra ’E’ e o servidor dará a vitória para o adversário.
