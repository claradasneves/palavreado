import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Servidor {
  private ServerSocket serverSocket;
  private Socket[] sockets;
  private InputStream[] inputStream;
  private OutputStream[] outputStream;
  BufferedReader[] reader;

  public Servidor(int porta, int numClientes) {
    try {
      // Alocando memoria para os sockets
      sockets = new Socket[numClientes];
      inputStream = new InputStream[numClientes];
      outputStream = new OutputStream[numClientes];
      reader = new BufferedReader[numClientes];

      // Criando o servidor socket
      serverSocket = new ServerSocket(porta);
      System.out.println("Esperando conexão do cliente...");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean aceitarConexaoCliente(int numCliente) {
    try {
      sockets[numCliente] = serverSocket.accept();
      inputStream[numCliente] = sockets[numCliente].getInputStream();
      outputStream[numCliente] = sockets[numCliente].getOutputStream();
      reader[numCliente] = new BufferedReader(new InputStreamReader(inputStream[numCliente]));

      System.out.println("Cliente " + numCliente + " conectado.");

      return true;
    }

    catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public String receberMensagem (int numCliente) {
    try {
      String mensagem = reader[numCliente].readLine();
      return mensagem;
    }
    catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public boolean enviarMensagem(int numCliente, String resposta) {
    try {
      resposta = resposta + "\n";
      outputStream[numCliente].write(resposta.getBytes());
      return true;
    }

    catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }


}
