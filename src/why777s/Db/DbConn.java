package why777s.Db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by wangzhaojun on 2017/2/21.
 */
public final class DbConn {
    public static Connection getconn(){
        String url = "jdbc:mysql://localhost:3306/chat_user";
        String userName = "root";
        String pwd="";
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try{
            conn = DriverManager.getConnection(url,userName,pwd);
        }catch (SQLException e2){
            e2.printStackTrace();
        }


        return  conn;
    }
}
