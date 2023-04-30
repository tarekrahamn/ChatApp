package com.example.messenger;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serversocket ) {
        try {
            this.serverSocket = serversocket;
            this.socket = serversocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error Creating Server ...");
            e.printStackTrace();
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void sendMessageToClient(String messageToClient){
        try {
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to the clint ...");
            closeEverything(socket,bufferedReader,bufferedWriter);

        }
    }
    public void receiveMessageFromClint(VBox vbox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String messageFromClient = bufferedReader.readLine();
                        Controller.addLabel(messageFromClient,vbox);
                    }catch (IOException e ){
                        e.printStackTrace();
                        System.out.println("Error receving message from the client...");
                        closeEverything(socket,bufferedReader,bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        try {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(socket!=null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
