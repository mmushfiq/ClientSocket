
package az.mm.clientsocket.Beans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author MM
 */
@Named(value = "clientBean")
@SessionScoped
public class ClientSocketBean implements Serializable {

    Socket socket;
    private String inputText;
    private String encryptedKey;

    public String getInputText() {
        return inputText;
    }
    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getEncryptedKey() {
        return encryptedKey;
    }
    
    public void ajaxGetEncryptedKey() throws Exception{
      try {
            Thread.sleep(2000);
            String host = "localhost";
            int port = 2014;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
            
             //Server Sockete deyishenin deyerini gonderir  
             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
             out.println(inputText);
             
             //Server Socketden response olunan deyeri goturur 
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             encryptedKey = in.readLine();
        
        } catch (Exception ex) {
             encryptedKey = "Server Socket ilə bağlantı yoxdu";
             System.err.println("ClientSocketBean ajaxGetEncryptedKey() chatch--> "+ex);
        } finally {
            try {
                if(socket!=null){
                   socket.close();
                }        
            } catch (IOException ex) {
                System.err.println("ClientSocketBean ajaxGetEncryptedKey() finally chatch--> "+ex);
            }
        }
    }//end ajax method
    
}//end Bean
