/*
*
* @author Felipe Borges Oliveira
*
*/
package local.javaredes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    
    private static ServerSocket servidor;
    private static Socket conexao;
    private static DataInputStream entrada;
    private static DataOutputStream saida;
    
    public static void main(String[] args) {
        
        try {
            // especificar uma porta e aguardar conexão
            servidor = new ServerSocket(50000);
            conexao = servidor.accept();
            
            // receber dados do cliente
            String cpf;
            
            entrada = new DataInputStream(conexao.getInputStream());
            cpf = entrada.readUTF();
            
            // realizar a verificação do valor recebido
            String resultado = "";
            if (validaCpf(cpf) && calculoDigito(cpf)){
                resultado = "Este CPF é válido.";
            }
            else {
                resultado = "Este CPF é inválido.";
            }
                    
            // retornar dados ao cliente
            saida = new DataOutputStream(conexao.getOutputStream());
            saida.writeUTF(resultado);
            
            // fechar a conexão
            conexao.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public static boolean validaCpf(String cpf){
       if (cpf.length() == 11){
          return true;
       } else {
           return false;
       }
    }
    
    private static boolean calculoDigito(String cpf){
       char dig10, dig11;
       int sm, i, r, num, peso;
       
       if (cpf.equals("00000000000")){
          return false; 
       }
       
       try {
            //Primeiro Dígito
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
              num = (int)(cpf.charAt(i) - 48);
              sm = sm + (num * peso);
              peso = peso - 1;
            }
           
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)){
              dig10 = '0';  
            } else {
             dig10 = (char)(r + 48);
            }
           
            //Segundo Dígito
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
              num = (int)(cpf.charAt(i) - 48);
              sm = sm + (num * peso);
              peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)){
                dig11 = '0';
            } else {
                dig11 = (char)(r + 48);
            }

            //Compara os dígitos calculados com os últimos dígitos informados
            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                return(true);
            else {
                return(false);
            }          
       } catch(Exception e){
         return false;  
       }
    }
}
