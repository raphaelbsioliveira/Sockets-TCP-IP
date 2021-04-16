package src;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Servidor extends Thread {
    
    private static Vector clientes;
    private Socket conexao;
    private String meuNome;
    public Servidor(Socket s){
        conexao = s;
    }
    
    public static void main(String[] args) throws IOException {
        clientes = new Vector();
        
        ServerSocket s = new ServerSocket(2000);
        while (true){
            System.out.print("Esperando conectar...");
            Socket conexao = s.accept();
            System.out.println(" Conectou!");
            Thread t = new Servidor(conexao);
            t.start();
            }
    }
    
    public void run(){
        try {
            BufferedReader entrada = null;
            try {
                entrada = new BufferedReader(new
                            InputStreamReader(conexao.getInputStream()));
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            PrintStream saida = null;
            try {
                saida = new PrintStream
                                    (conexao.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            meuNome = entrada.readLine();
            if(meuNome == null){
                return;
            }
            clientes.add(saida);
            String linha = entrada.readLine();
            
            while ((linha != null)&&(!linha.trim().equals(""))){
                sendToAll(saida," disse: ",linha);
                linha = entrada.readLine();
            }
            
            sendToAll(saida," saiu "," do Chat!");
            clientes.remove(saida);
            conexao.close();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendToAll(PrintStream saida, String acao,
    String linha) throws IOException {
        Enumeration e = clientes.elements();
        while (e.hasMoreElements()) {
            PrintStream chat = (PrintStream) e.nextElement();
            if (chat != saida) {
                chat.println(meuNome + acao + linha);
            }
            if(acao == " saiu "){
                if (chat == saida)
                    chat.println("");
            }
        }
    }
}
