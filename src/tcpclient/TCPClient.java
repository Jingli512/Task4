package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
        //if the client is sending out nothing, call the second method with two arguments instead
    	if(ToServer == null) {
            return askServer(hostname, port);
        } 

        //create client socket object
    	Socket clientSocket = new Socket(hostname,port);
    	clientSocket.setSoTimeout(20000);

        //create input and output stream
    	DataOutputStream outputtoServer = new DataOutputStream(clientSocket.getOutputStream());
    	BufferedReader inputfromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    	outputtoServer.writeBytes(ToServer+'\n');
    	
    	StringBuilder sb = new StringBuilder();
    	String st;
    	try{//continue reading when the response from server is not end 
    		while( (st = inputfromServer.readLine()) != "\n" && st != null){
    			sb.append(st+'\n');
    		}
    		clientSocket.close();
    		return sb.toString();
    	}
    	catch(IOException e){
    		clientSocket.close();
    		return sb.toString();
    	}

    }


    public static String askServer(String hostname, int port) throws  IOException {
    	Socket clientSocket = new Socket(hostname,port);
    	clientSocket.setSoTimeout(3000);

        //no output to server
    	BufferedReader inputfromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    	
    	StringBuilder sb = new StringBuilder();
    	String st;
        final int MAXI = 1024;
        int counter = 0;
    	try{
    		while( (st = inputfromServer.readLine()) != "\n" && st != null){
    			sb.append(st+'\n');
                counter++;
                if(counter >= MAXI)
                    return sb.toString();
    		}
    		clientSocket.close();
    		return sb.toString();
    	}
    	catch( IOException e){
    		clientSocket.close();
    		return sb.toString();
    	}
    }
}
