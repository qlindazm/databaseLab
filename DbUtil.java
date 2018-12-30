
import java.sql.*;
import java.io.*;

import java.util.Properties;

public class DbUtil{
	private static Connection conn;
	private static String driverName;
    private static String url;
    private static String user;
    private static String password;
 

    static {
        try {
            InputStream in = DbUtil.class.getClassLoader()
                    .getResourceAsStream("dblab.properties");
            Properties properties = new Properties();
            properties.load(in);
 
            driverName = properties.getProperty("driverName");
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
 
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, user, password);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void test(){
    	System.out.print(conn);
    }

}