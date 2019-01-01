import java.sql.*;
import java.io.*;
import java.text.*;
import java.util.Date;
import java.util.Properties;

public class DbUtil{
	private static Connection conn;
	private static String driverName;
    private static String url;
    private static String user;
    private static String password;

    public String userID = null;
    

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
    public static void release(){
        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    /* check user when login
     * no such user return -1
     * password uncorrect return 0
     * success return 1
     */
    public int login(String un, String pwd){
    	Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        int flag = -1;
        try{
            st = conn.createStatement();
            rs = st.executeQuery("select * from user;");
            while(rs.next() && flag==-1){
                if(rs.getString("userID").equals(un)){
                    flag = 0;
                    if(rs.getString("password").equals(pwd))
                        flag = 1;
                }
            }
            if(flag==1){
                ps = conn.prepareStatement("select readerid from userdetail where userid=?;");
                ps.setString(1,un);
                rs = ps.executeQuery();
                if(rs.next())userID = rs.getString("readerid");
            }
            rs.close();
            ps.close();
            st.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return flag;
    }
    //based barcode
    public  void borrowBook(String barcode){
        if(userID==null)
            return;
        PreparedStatement ps = null;
        try{   
            ps = conn.prepareStatement("insert into borrow (readerid,barcode,borrowdate) values(?,?,?);");
            ps.setString(1, userID);
            ps.setString(2, barcode);
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
            ps.setString(3, ft.format(dNow));
            ps.executeUpdate();
            
            ps = conn.prepareStatement("update book set state='借出', historyborrowed=historyborrowed+1 where barcode=?;");
            ps.setString(1, barcode);            
            ps.executeUpdate();

            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    //based barcode
    public  void returnBook(String barcode){
        if(userID==null)
            return;
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("update borrow set returndate=? where readerid=? and barcode=?;");
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
            ps.setString(1, ft.format(dNow));
            ps.setString(2, userID);
            ps.setString(3, barcode);
            ps.executeUpdate();
            
            ps = conn.prepareStatement("update book set state='可借' where barcode=?;");
            ps.setString(1, barcode);            
            ps.executeUpdate();
            

            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
}