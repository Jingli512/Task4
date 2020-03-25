import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class ConcHTTPAsk {
    public static void main( String[] args) throws IOException {
        //take the port number as the first argument ans set up a socket
        ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));
        try{
			while(true){
				Socket conSocket = welcomeSocket.accept();
				new Thread(new MyRunnable(conSocket)).start();
			}

		}
		catch(Exception e){
			System.out.println("Exception!!");
			e.printStackTrace();
		}
	}
}
