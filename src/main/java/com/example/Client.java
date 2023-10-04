package com.example;

    
import java.io.*;
import java.net.*;

/**
 * Hello world!
 * 
 */
public class Client 
{
    public static void main( String[] args )
    {
        Client cliente = new Client();
        cliente.connetti();
        cliente.comunica();
    }
    
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket mySocket;
    DataOutputStream outVersoServer;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    BufferedReader inDalServer;
    


    public Socket connetti(){
      System.out.println("2 CLIENT partito in esecuzione");
    try{
        //per l'input da tastiera
        tastiera = new BufferedReader(new InputStreamReader(System.in));
        //creazione socket
        mySocket = new Socket(nomeServer, portaServer); //possibile utilizzare anche 'InetAddress.getLocalHost' al posto di 'nomeServer'

        outVersoServer = new DataOutputStream(mySocket.getOutputStream());
        inDalServer = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

    }catch(UnknownHostException e){
        System.err.println("Host sconosciuto");
    }catch(Exception e){
        System.out.println(e.getMessage());
        System.out.println("Errore durante la connessione");
        System.exit(1);
    }
    return mySocket;
    }

    public void comunica(){
        for(;;){ //ciclo che termina con : "BYE"
            try{
                System.out.println("4...inserisci la stringa da trasmettere al server" + "\n");
                stringaUtente = tastiera.readLine();
                
                //controllo se la string sia diversa da BYE
                if(stringaUtente.equals("BYE")){
                    System.out.println("9..Client : termina elaborazione e chiude connessione");
                    mySocket.close(); //chiusura socket
                    break;
                }

                //spedisco al client
                System.out.println("%.. invio la stringa al server e attendo...");
                outVersoServer.writeBytes(stringaUtente + "\n");
               
                //leggo dal server
                stringaRicevutaDalServer = inDalServer.readLine();
                System.out.println("8...risposta dal server in maiuscuolo " + "\n" + stringaRicevutaDalServer);
                
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Errore durante la connessione col server");
                System.exit(1);
            }
        }
    }
}
    

