package Pages.Chat;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame
{
    ServerSocket server;
    BufferedReader br;
    PrintWriter out;

    JLabel heading = new JLabel();
    JTextArea textArea = new JTextArea();
    Font font = new Font("Roboto",Font.PLAIN,18);
    JTextField field =new JTextField();
    public Server()
    {
        try
        {
            System.out.println("Waiting for connection");
            server = new ServerSocket(7777);
            Socket socket = server.accept();
            System.out.println("CONNECTION ESTABLISHED");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            //  createGUI();
            startReading();
            startWriting();
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }

    }

    public void startReading()
    {
        Runnable r1 = ()->{
            System.out.println("Reading started");
            try
            {
                while(true)
                {

                    String msg = br.readLine();

                    if(msg.equals("exit"))
                    {
                        System.out.println("Client terminated the chat");
                        server.close();
                        break;
                    }
                    System.out.println("Client: " + msg);
                }


            }
            catch(Exception e)
            {
                // e.printStackTrace();
                System.out.println("Connection closed");
            }
        };
        new Thread (r1).start();
    }

    public void startWriting()
    {
        System.out.println("writer started");
        Runnable r2 = ()->{
            try{

                while(true && !server.isClosed())
                {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit"))
                    {
                        server.close();
                        break;
                    }

                }
            }
            catch(Exception e)
            {
                // e.printStackTrace();
                System.out.println("Connection closed");

            }
        };
        new Thread(r2).start();
    }


}