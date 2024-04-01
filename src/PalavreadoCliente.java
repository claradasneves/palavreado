import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class PalavreadoCliente {
  private Cliente cliente;
  
  public PalavreadoCliente () {
    this.cliente = new Cliente();
  }

// Usamos como base codigos do site https://wiki.sj.ifsc.edu.br/index.php/Trabalhando_com_sockets_TCP_em_Java para estruturar tanto a classe PalavreadoCliente, quanto Cliente.

// No final, separamos o método run da classe Cliente e movemos para PalavreadoCliente, pois percebemos que era mais compreensível dessa maneira.
  
  public static void main(String[] args) {
    PalavreadoCliente jogo = new PalavreadoCliente();
    jogo.run("localhost", 31384);
  }

  public void run(String host, int porta) {
    // Conectando no servidor
    cliente.conectandoServidor(host, porta);

    // Ler mensagem enviada pelo servidor até que o cliente digite ENCERRAR
    Scanner scanner = new Scanner(System.in);
    String mensagem = "";
    String enviar = "";
    String[] parametros;

    do {
      //System.out.println("Inico do loop.");
      //System.out.println("a1");
      mensagem = cliente.receberMensagem();

      //System.out.println("a2");
      //System.out.println("SERVIDOR: " + mensagem);
      parametros = mensagem.split(" ");
      //System.out.println("a3");
      switch (parametros[0]) {

        // Indica que o jogo iniciou
        case "I":
          System.out.println("Bem-vindo ao Palavreado!");
          System.out.println("O objetivo deste jogo é adivinhar a palavra secreta de " + parametros[1] + " letras em " + parametros[2] + " tentativas. Quando errar uma letra, ela sera retornada como um underline '_'. Quando você acertar uma letra que está na palavra, mas na posição errada, ela sera exibida em minusculo. E se você acertar uma letra na posição correta, ela será exibida em letras maiusculas.");
          System.out.println("Em qualquer momento, para sair do jogo, digite E.");
          break;

        // Informar que é a vez do jogador ou do outro jogador
        case "V":
          if (parametros[1].equals("1")) System.out.println("Sua vez de jogar!");
          else {
            System.out.println("Aguarde enquanto o outro jogador tenta adivinhar o Palavreado. Caso deseje sair, digite E. Caso nao, digite N. ");
            enviar = scanner.nextLine();
            System.out.println("'" + enviar + "'");
            cliente.enviarMensagem(enviar);
          } 
          break;

        // Informar o número de tentativas restantes  
        case "N":
          System.out.println("Tentativas restantes: " + parametros[1]);
          break;

        // Pedir para digitar a palavra
        case "D":
          System.out.println("Digite a palavra: ");
          enviar = scanner.nextLine();
          if (enviar.equals("E")) {
            cliente.enviarMensagem("E");
            System.out.println("Saindo do jogo...");
          }
          else cliente.enviarMensagem("T " + enviar);
          break;  

        // Feedback sobre a tentativa
        case "F":
          System.out.println(parametros[1]);
          break;

        // Mensagem informando qual o resultado do jogo: ganhou
        case "RG":
          System.out.println("Parabens, voce advinhou a palavra e ganhou do seu adversario! :D");
          break;

        // Mensagem informando qual o resultado do jogo: perdeu
        case "RP":
          System.out.println("Que pena, o seu adversario advinhou a palavra e voce perdeu. :( A palavra secreta era: " + parametros[1] + ".");
          break;

        // Mensagem informando qual o resultado do jogo: acabaram as tentativas
        case "RA":
          System.out.println("As tentativas esgotaram! Voce e seu adversario empataram. ¯\\_(ツ)_/¯ A palavra secreta era: " + parametros[1] + ".");
          break;

      // Mensagem informando que o tamanho da palavra está errado   
        case "S":
          System.out.println("Tamanho errado da palavra! >:c Digite uma palavra de " + parametros[1] + " letras.");
          break;

        // Mensagem informando que o jogo foi finalizado
        case "Q":
          System.out.println("O seu adversario desistiu. Voce ganhou... :/");
          break;

        // Caso o comando seja inválido 
        default:     
          System.out.println("Comando invalido :(");
          break;
      }
      //System.out.println("Fim do loop.");
    } while (!mensagem.equals("Q") && !(mensagem.charAt(0) == 'R') && !enviar.equals("E"));
    System.out.println("Fim de jogo.");
    // Fechando conexão
    cliente.fecharConexao();
    scanner.close();
  }  
}