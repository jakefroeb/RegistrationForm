import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by jakefroeb on 9/29/16.
 */
public class MainTest {
    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, username VARCHAR, address VARCHAR, email VARCHAR )");
        return conn;
    }
    @Test
    public void insertAndSelectUserTest()throws SQLException{
        Connection conn = startConnection();
        Main.insertUser(conn,"Alice","123 Home St","aBonez@gmail.com");
        Main.insertUser(conn,"Bob","address","email");
        ArrayList<User> users = Main.selectUsers(conn);
        conn.close();
        assertTrue(users.size() == 2);
    }
    @Test
    public void updateUserTest()throws SQLException{
        Connection conn = startConnection();
        Main.insertUser(conn,"Alice","123 Home St","aBonez@gmail.com");
        Main.insertUser(conn,"Bob","address","email");
        Main.updateUser(conn,1,"Bobby","123 home st","bobbyemail");
        User bobby = Main.selectUsers(conn).get(0);
        conn.close();
        assertTrue(bobby.username.equals("Bobby"));
    }
    @Test
    public void deleteUserTest()throws SQLException{
        Connection conn = startConnection();
        Main.insertUser(conn,"Alice","123 Home St","aBonez@gmail.com");
        Main.insertUser(conn,"Bob","address","email");
        Main.deleteUser(conn,1);
        ArrayList<User> testUsers = Main.selectUsers(conn);
        conn.close();
        assertTrue(testUsers.size() == 1);
    }

}
