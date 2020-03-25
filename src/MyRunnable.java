import java.net.*;
import java.io.*;

import tcpclient.TCPClient;

public class MyRunnable implements Runnable {

    Socket conSocket;

    //static boolean first = true;
    public MyRunnable(Socket socket) {
        conSocket = socket;
    }

    public void run() {
        /*if (first) {
            first = !first;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        String hostname = null;
        String portnumber = null;
        String string = null;
        String HTTP200 = "HTTP/1.1 200 OK\r\n\r\n";
        String HTTP400 = "HTTP/1.1 400 Bad request\r\n\r\n";
        String HTTP404 = "HTTP/1.1 404 Not found\r\n\r\n";


        try {
        	//get input and output stream
			BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(conSocket.getInputStream()));
			DataOutputStream outputToClient = new DataOutputStream(conSocket.getOutputStream());

            String requestFinal = inputFromClient.readLine();
            String[] splitRequest = requestFinal.split("[ ?&=]");


            for (int i = 0; i < splitRequest.length; i++) {
                if (splitRequest[i].equals("hostname")) {
                    hostname = splitRequest[i + 1];
                } else if (splitRequest[i].equals("port")) {
                    portnumber = splitRequest[i + 1];
                } else if (splitRequest[i].equals("string")) {
                    string = splitRequest[i + 1];
                }
            }
            StringBuilder response = new StringBuilder();
            if (hostname != null && portnumber != null && splitRequest[1].equals("/ask")) {
                try {
                    //get response from the server by calling TCPclient
                    String res = TCPClient.askServer(hostname, Integer.parseInt(portnumber), string);
                    response.append(HTTP200);
                    response.append(res);


                } catch (Exception e) {
                    //both hostname and port number are okay, but cannot connect to the server
                    response.append(HTTP404);
                    e.printStackTrace();
                }
            } else {
                //Bad request is the hostname/portname is empty
                response.append(HTTP400);
            }
            outputToClient.writeBytes(response.toString());
            conSocket.close();
            inputFromClient.close();
            outputToClient.close();
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }


    }


}