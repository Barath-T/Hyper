package Pages;

import User.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Login {
    JFrame loginFrame, signupFrame;

    Welcome welcomeFrame;
    JLabel lblUsername,lblPassword, lblPhno, lblCreate;
    JPanel loginPanel, createPanel;
    JTextField txtUsername, txtPhone;
    JPasswordField pwdPassword;

    JButton btnLogin, btnCreate;
    public Boolean authenticate = false;


   public Login() {
        loginFrame = new JFrame();
        signupFrame = new JFrame();
        loginPanel = new JPanel();
        createPanel = new JPanel();

        lblUsername = new JLabel("USERNAME");
        lblPassword = new JLabel("PASSWORD");
        lblCreate = new JLabel("Don't have an account?");
        lblPhno = new JLabel("PHONE");

        txtUsername = new JTextField(20);
        txtPhone = new JTextField(10);
        pwdPassword = new JPasswordField(20);

        btnLogin = new JButton("Login");
        btnCreate = new JButton("Create");
    }

     public void setLogin() {

            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            loginPanel.setLayout(null);
            loginFrame.add(loginPanel);

            loginFrame.setSize(300, 300);

            lblUsername.setBounds(10, 20, 80, 25);
            loginPanel.add(lblUsername);

            txtUsername.setBounds(100, 20, 165, 25);
            loginPanel.add(txtUsername);


            lblPassword.setBounds(10, 50, 80, 25);
            loginPanel.add(lblPassword);

            pwdPassword.setBounds(100, 50, 165, 25);
            loginPanel.add(pwdPassword);


            btnLogin.setBounds(10, 80, 80, 25);
            loginPanel.add(btnLogin);

            btnLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    authenticate = authenticateUser();
                }
            });

            lblCreate.setBounds(100, 80, 165, 25);
            loginPanel.add(lblCreate);

            lblCreate.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    loginFrame.dispose();
                    setCreate();

                }
            });

            loginFrame.setVisible(true);

        }

        private void setCreate(){
            signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            createPanel.setLayout(null);
            signupFrame.add(createPanel);

            signupFrame.setSize(300, 300);

            lblUsername.setBounds(10, 20, 80, 25);
            createPanel.add(lblUsername);

            txtUsername.setBounds(100, 20, 165, 25);
            createPanel.add(txtUsername);


            lblPhno.setBounds(10, 50, 80, 25);
            createPanel.add(lblPhno);

            txtPhone.setBounds(100, 50, 165, 25);
            createPanel.add(txtPhone);


            lblPassword.setBounds(10, 80, 80, 25);
            createPanel.add(lblPassword);

            pwdPassword.setBounds(100, 80, 165, 25);
            createPanel.add(pwdPassword);


            btnCreate.setBounds(10, 110, 80, 25);
            createPanel.add(btnCreate);

            btnCreate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    signupFrame.dispose();
                    createUser(txtUsername.getText(), txtPhone.getText(), String.valueOf(pwdPassword.getPassword()));
                }
            });
            signupFrame.setVisible(true);

        }

        private void createUser(String name, String phno, String password){
            User newUser = new User(name, phno, password);


            try{
                Connection createConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "eswari12");
                newUser.addUser(createConnection);
                createConnection.close();

                setLogin();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }

        public Boolean authenticateUser(){
            try{
                Connection createConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "eswari12");

                PreparedStatement pstmt = createConnection.prepareStatement("select * from user where name = ? and password = ? ");
                pstmt.setString(1, txtUsername.getText() );
                pstmt.setString(2, String.valueOf(pwdPassword.getPassword()));

                ResultSet r = pstmt.executeQuery();


                if(r.next() == false){
                    JOptionPane.showMessageDialog(loginFrame, "userid or password is incorrect!", "Cant login!", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    loginFrame.dispose();
                    welcomeFrame = new Welcome(txtUsername.getText());
                    System.out.println("afWel");
                    createConnection.close();
                    return true;
                }

                createConnection.close();

            }
            catch(Exception e){
                System.out.println(e);
            }
            return false;
        }

    }

