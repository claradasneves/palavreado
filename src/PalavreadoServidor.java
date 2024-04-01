import java.util.Random;

public class PalavreadoServidor {
  private Servidor servidor;
  private static String palavraOculta; // Palavra a ser adivinhada
  private static int tamanhoPalavra; // Tamanho da palavra a ser adivinhada
  private static int maxTentativas; // Número máximo de tentativas

// Utilizamos como referência o projeto https://github.com/zonete/JogoDaForcaNetbeansFacul/tree/master e a página https://pt.wikiversity.org/wiki/Introdu%C3%A7%C3%A3o_%C3%A0s_Redes_de_Computadores/Jogo_da_Forca para uma lógica similar do jogo e para estruturar as classes PalavreadoServidor e Servidor. Além dos códigos fonecidos em https://wiki.sj.ifsc.edu.br/index.php/Trabalhando_com_sockets_TCP_em_Java durante todo o trabalho.
  
  public PalavreadoServidor() {
    palavraOculta = geraPalavraOculta();
    tamanhoPalavra = palavraOculta.length();
    maxTentativas = tamanhoPalavra + 1;
    servidor = new Servidor(31384, 2);
  }

  public static void main(String[] args) {
      PalavreadoServidor jogo = new PalavreadoServidor();
    jogo.run();
  }

  public void run() {
    servidor.aceitarConexaoCliente(0);
    servidor.aceitarConexaoCliente(1);
    servidor.enviarMensagem(0, "I " + tamanhoPalavra + " " + maxTentativas);
    servidor.enviarMensagem(1, "I " + tamanhoPalavra + " " + maxTentativas);

    int tentativasRestantes = maxTentativas; // Numero de tentativas dos dois jogadores juntos
    int jogadorAtual = 0;
    String tentativa = "";
    boolean desistiu = false;
    String mensagemA, mensagemB;
    String[] parametros;

    while (tentativasRestantes > 0 && desistiu == false) {
      // Informa ao outro jogador que nao eh a vez dele
      servidor.enviarMensagem(numOutroJogador(jogadorAtual), "V 0");

      // Infoma o jogador atual que eh vez dele
      servidor.enviarMensagem(jogadorAtual, "V 1");
      System.out.println("Vez do jogador " + jogadorAtual + ".");
      // Informa quantas tentativas restam
      servidor.enviarMensagem(jogadorAtual, "N " + tentativasRestantes);
      System.out.println("Restam " + tentativasRestantes + " para o jogador " + jogadorAtual + ".");
      // Requere que o jogador digite sua tentativa
      servidor.enviarMensagem(jogadorAtual, "D");
      System.out.println("Recebendo tentativa do jogador " + jogadorAtual + ".");
      // Recebe a tentativa
      mensagemA = servidor.receberMensagem(jogadorAtual);

      // Da a chance do jogador desistir do jogo
      mensagemB = servidor.receberMensagem(numOutroJogador(jogadorAtual));

      if (mensagemB.equals("E")) {
        desistiu = true;
        System.out.println("Jogador " + jogadorAtual + " desistiu do jogo.");
        servidor.enviarMensagem(jogadorAtual, "Q");
        break;
      } else {

        if (mensagemA.equals("E")) {
          desistiu = true;
          System.out.println("Jogador " + numOutroJogador(jogadorAtual) + " desistiu do jogo.");
          servidor.enviarMensagem(numOutroJogador(jogadorAtual), "Q");
          break;
        } else {
          parametros = mensagemA.split(" ");
          if (parametros[0].equals("T")) {
            tentativa = parametros[1].toLowerCase();

            System.out.println("Jogador " + jogadorAtual + " digitou " + tentativa + ".");
          }
        }

        // Valida a tentativa do jogador
        while (tentativa.length() != tamanhoPalavra) {
           System.out.println("A tentativa do jogador foi inválida. Aguardando nova tentativa.");
          // Informa que o numero de letras esta errado e qual o tamanho correto
          servidor.enviarMensagem(jogadorAtual, "S " + tamanhoPalavra);
          // Requere mais uma tentativa
          servidor.enviarMensagem(jogadorAtual, "D");
          // Recebe a tentativa
          mensagemA = servidor.receberMensagem(jogadorAtual);

          if (mensagemA.equals("E")) {
            desistiu = true;
            servidor.enviarMensagem(numOutroJogador(jogadorAtual), "Q");
            System.out.println("Jogador " + jogadorAtual + " desistiu do jogo.");
            break;
          } else {
            parametros = mensagemA.split(" ");
            if (parametros[0].equals("T")) {
              tentativa = parametros[1].toLowerCase();
            }
          }
        }
        System.out.println("Tentativa do " + jogadorAtual + " é valida.");

        // Verifica se a tentativa está correta
        if (tentativa.equals(palavraOculta)) {
          servidor.enviarMensagem(jogadorAtual, "F " + tentativa.toUpperCase());
          servidor.enviarMensagem(jogadorAtual, "RG");
          servidor.enviarMensagem(numOutroJogador(jogadorAtual), "RP " + palavraOculta);
          System.out.println("O jogador " + jogadorAtual + " acertou a palavra oculta: " + palavraOculta + ".");
          break;
        } else {
          // Enviando o feedback da tentativa
          servidor.enviarMensagem(jogadorAtual, "F " + exibirTentativa(tentativa));

          if (jogadorAtual == 1)
            tentativasRestantes--;
          jogadorAtual = numOutroJogador(jogadorAtual);
        }
      }

      // Mostra a palavra correta se o jogador perdeu
      if (tentativasRestantes == 0) {
        System.out.println("O jogador " + jogadorAtual + "perdeu. Mostrando a palavra correta: " + palavraOculta + ".");
        servidor.enviarMensagem(0, "RA " + palavraOculta);
        servidor.enviarMensagem(1, "RA " + palavraOculta);
      }
    }
  }

  private static String exibirTentativa(String tentativa) {
    String retorno = "";

    for (int i = 0; i < tamanhoPalavra; i++) {
      if (palavraOculta.charAt(i) == tentativa.charAt(i)) {
        retorno += tentativa.toUpperCase().charAt(i);

      } else if (palavraOculta.toLowerCase().contains(String.valueOf(tentativa.toLowerCase().charAt(i)))) {
        retorno += tentativa.toLowerCase().charAt(i);
      } else {
        retorno += "_";
      }
    }
    return retorno;
  }

  public String geraPalavraOculta() {
    String[] palavras = { "nariz", "grego", "ideia", "ileso", "odiar", "lhama", "foca", "autor", "fraude", "falir", "media", "marra", "almondega", "sobremesa", "amigas", "picanha", "querida", "querido", "galinha", "confiar" };
    Random random = new Random();
    return palavras[random.nextInt(palavras.length)];
  }

  // Funcao que quando recebe 1 retorna 0, e quando recebe 0 retorna 1
  public static int numOutroJogador(int n) {
    return ((n + 1) % 2);
  }
}
