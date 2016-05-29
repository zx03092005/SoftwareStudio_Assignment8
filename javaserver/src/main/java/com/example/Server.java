package com.example;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * Created by Gary on 16/5/28.
 */
public class Server extends JFrame implements Runnable{
    private ServerSocket servSock;
    private JTextArea textArea = new JTextArea();
    public Server(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setResizable(false);
        this.setVisible(true);

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.textArea.setEditable(false);
        this.textArea.setSize(400, 300);
        JScrollPane jScrollPane = new JScrollPane(this.textArea);
        this.add(jScrollPane);
        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            textArea.append("IP of my system is := "+IP.getHostAddress() + "\n");
            textArea.append("Waitting to connect......\n");
            System.out.print("IP of my system is := "+IP.getHostAddress() + "\n");
            System.out.print("Waitting to connect......\n");

            // Create server socket
            servSock = new ServerSocket(2000);

            // Create socket thread
            Thread thread;
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket start error !");
            System.out.println("IOException :" + e.toString());
        }
    }

    @Override
    public void run(){
        BufferedReader reader;
        // Running for waitting multiple client
        while(true){
            try{
                // After client connected, create client socket connect with client
                Socket clntSock = servSock.accept();
                reader = new BufferedReader(new InputStreamReader(clntSock.getInputStream()));

                String result = reader.readLine();
                textArea.append("The result from App is " + result + "\n");
                System.out.print("The result from App is " + result + "\n");
            }
            catch(Exception e){
                //System.out.println("Error: "+e.getMessage());
            }
        }
    }
}
