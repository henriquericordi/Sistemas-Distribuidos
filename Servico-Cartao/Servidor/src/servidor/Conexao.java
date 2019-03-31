/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author rafael
 */
public class Conexao implements Runnable {

    private final ServerSocket SocketServidor;
    private final Servidor servidor;

    public Conexao(ServerSocket SocketServidor, Servidor servidor) {
        this.SocketServidor = SocketServidor;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream saida;
            ObjectInputStream entrada;
            Socket conexao;
            while (true) {
                conexao = SocketServidor.accept();
                //Criar nova thread para tratar o restante da fila
                Thread nova = new Thread(new Conexao(SocketServidor, servidor));
                nova.start();
                System.out.println("Conexão estabelecida com: " + conexao.getInetAddress().getHostAddress());
                saida = new ObjectOutputStream(conexao.getOutputStream());
                entrada = new ObjectInputStream(conexao.getInputStream());
                // Informar status da conexao ao cliente
                saida.writeObject("Conexao estabelecida com sucesso...\n");
                try {
                    String mensagem = (String) entrada.readObject();
                    System.out.println("Cliente>> " + mensagem);
                    Boolean status = servidor.processarRequisicao(mensagem);
                    System.out.println("Resposta>> " + status);
                    saida.writeObject(status.toString());
                } catch (IOException iOException) {
                    System.err.println("erro: " + iOException.toString());
                }
                saida.close();
                entrada.close();
                conexao.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro: " + e.toString());
        }
    }

}
