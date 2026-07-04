package pet_utf8;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DBUtil {
   
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL =
        "jdbc:mysql://localhost:3306/pet_db?useSSL=false&serverTimezone=GMT&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "Mysql@123456";
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);                                 
            // 加载驱动
            conn = DriverManager.getConnection(URL, USER, PASSWORD); 
            // 建立连接
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败：" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("数据库连接失败：" + e.getMessage());
        }
        return conn;
    }
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
