import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
  private Socket socket;
  private InputStream inputStream;
  private OutputStream outputStream;
  BufferedReader reader;

  public void conectandoServidor(String host, int porta) {
    try {
      socket = new Socket(host, porta);
      inputStream = socket.getInputStream();
      outputStream = socket.getOutputStream();
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      
      System.out.println("Conectado ao servidor.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String receberMensagem() {
    try {
      String mensagem = reader.readLine();
      return mensagem;
    }

    catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean enviarMensagem(String mensagem) {
    try {
      mensagem = mensagem + "\n";
      outputStream.write(mensagem.getBytes());
      return true;
    }

    catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void fecharConexao() {
    try {
      socket.close();
      inputStream.close();
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}