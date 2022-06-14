package User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    private String name;
    private String phno;
    private String password;

    public User(){

    }
    public User(String name, String phno, String password){
        this.name = name;
        this.phno = phno;
        this.password = password;
    }

    public void addUser(Connection createConnection) throws SQLException {

        Statement stmt = createConnection.createStatement();

        stmt.executeUpdate("insert into user (name, phno, password) values(\"" + this.name +"\", \"" + this.phno +"\", \"" + this.password + "\")");

        /*ResultSet r1= stmt.executeQuery("select * from user");

        while(r1.next()){
            System.out.println(r1.getString(1) + r1.getString(2) + r1.getString(3));
        }*/
    }

}
