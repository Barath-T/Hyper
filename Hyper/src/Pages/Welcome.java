package Pages;

import Pages.Chat.Client;
import Pages.Chat.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Welcome extends JFrame implements ActionListener{
    JRadioButton rb1,rb2,rb3;
    JButton b;
    JLabel l1, l2;

    Welcome(String userName){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rb1=new JRadioButton("Awesome");
        rb1.setBounds(100,75,100,30);
        rb2=new JRadioButton("Good");
        rb2.setBounds(100,105,100,30);
        rb3=new JRadioButton("Not good");
        rb3.setBounds(100,135,100,30);

        l1 = new JLabel("Welcome "  + userName + ",");
        l1.setBounds(100, 10,200,30);
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);

        l2 = new JLabel("How are you today?");
        l2.setBounds(100, 30,200,30);
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup bg=new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);
        bg.add(rb3);
        b=new JButton("click");
        b.setBounds(100,200,80,30);
        b.addActionListener(this);
        add(l1);
        add(l2);
        add(rb1);
        add(rb2);
        add(rb3);
        add(b);

        setSize(300,300);
        setLayout(null);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(rb1.isSelected()){
            JOptionPane.showMessageDialog(this,"Let your unique awesomeness and positive energy inspire confidence in others..\nHave a great day!!");
        }
        if(rb2.isSelected()){
            JOptionPane.showMessageDialog(this,"Wherever you go ,no matter what the weather,always bring your own sunshine..\nHave a great day!!");
        }
        if(rb3.isSelected()){
            JOptionPane.showMessageDialog(this,"Just in case..No one has told you today \nYOU ARE AMAZING\nHave a great day!!");
        }
        this.dispose();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                new Server();
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                new Client();
            }
        };
        t1.start();
        t2.start();


    }
}