package Pages.Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Client extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    private JLabel heading =  new JLabel("Client Area");
    private JTextArea msg = new JTextArea();
    private JTextField msgin = new JTextField();
    private Font font = new Font("Roboto",Font.PLAIN,20);



    public Client()
    {
        try

        {
            Thread.sleep(1000);
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection done");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            createGUI();
            handleEvents();
            startReading();
            //startWriting();
        }
        catch(Exception e)
        {

        }
    }
    private void handleEvents()
    {
        msgin.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e)
            {

            }
            public void keyPressed(KeyEvent e)
            {

            }
            public void keyReleased(KeyEvent e)
            {
                //System.out.println("Key released"+e.getKeyCode());
                if(e.getKeyCode()==10)
                {
                    // System.out.println("Enter");
                    String contenttoSend = msgin.getText();
                    msg.append("ME:"+contenttoSend+"\n");
                    out.println(contenttoSend);
                    out.flush();
                    msgin.setText("");
                }
            }

        });
    }
    private void createGUI()
    {
        this.setTitle("Client messenger");
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        heading.setFont(font);
        msg.setFont(font);
        msgin.setFont(font);

        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        msg.setEditable(false);

        this.setLayout(new BorderLayout());
        this.add(heading,BorderLayout.NORTH);
        JScrollPane js = new JScrollPane(msg);
        this.add(js);
        this.add(msgin,BorderLayout.SOUTH);

    }


    public void startReading()
    {
        Runnable r1;
        r1 = ()->{
            System.out.println("Reading started");
            try
            {

                while(true)
                {

                    String msg1 = br.readLine();

                    if(msg.equals("exit"))
                    {
                        System.out.println("Server terminated the chat");
                        JOptionPane.showMessageDialog(this, "Server terminated the chat");
                        msgin.setEnabled(false);
                        socket.close();
                        break;
                    }
                    //System.out.println("Server: " + msg1);
                    msg.append("Server: " + msg1 + "\n");

                }

            }
            catch(Exception e)
            {
                //e.printStackTrace();
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
                while(true && !socket.isClosed())
                {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }

                }
            }
            catch(Exception e)
            {
                //e.printStackTrace();
                System.out.println("Connection closed");
            }

        };
        new Thread(r2).start();
    }

}