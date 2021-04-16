package src;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread{
    
    private static boolean done = false;
    private Socket conexao;
    
    public Cliente(Socket s){
        conexao = s;
    }
    public static void main(String[] args) throws IOException{
        Socket conexao = new Socket("localhost",2000);
        PrintStream saida = new PrintStream
            (conexao.getOutputStream());
        BufferedReader teclado = new BufferedReader(new
            InputStreamReader(System.in));
        System.out.print("Entre com o seu nome: ");
        String meuNome = teclado.readLine();
        saida.println(meuNome);
        Thread t = new Cliente(conexao);
        t.start();
        String linha;
        while (true){
            if (done){
                break;
                }
        System.out.println("> ");
        linha = teclado.readLine();
        saida.println(linha);
        }
    }
    
    public void run(){
        BufferedReader entrada = null;
        try {
            entrada = new BufferedReader(new
                        InputStreamReader(conexao.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        String linha = null;
        while (true){
            try {
                linha = entrada.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        if (linha.trim().equals("")){
            System.out.println("Conexao encerrada!!!");
            break;
        }
        System.out.println();
        System.out.println(linha);
        System.out.print("...> ");
            }
        done = true;
        }
}
