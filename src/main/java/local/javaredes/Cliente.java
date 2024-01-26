/*
*
* @author Felipe Borges Oliveira
*
*/
package local.javaredes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
 
    private static Socket conexao;
    private static DataInputStream entrada;
    private static DataOutputStream saida;
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        
        try {
            // conectar ao servidor
            conexao = new Socket("127.0.0.1",50000);
            
            // enviar um numero inteiro
            saida = new DataOutputStream(conexao.getOutputStream());
            System.out.println("Digite um CPF para verificação:");
            String cpf = sc.nextLine();
            saida.writeUTF(cpf);
            
            // receber resposta do servidor
            entrada = new DataInputStream(conexao.getInputStream());
            String resposta = entrada.readUTF();
            System.out.println("Resposta do servidor: " + resposta);
            
            // fechar conexão
            conexao.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
      sc.close();  
    }
}
