package ww.pjt;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnect {
	//DRIVER is the JDBC driver for the connection to the database
	private final String DBDRIVER="com.mysql.jdbc.Driver";
	//DBURL is the connection URL
	private final String DBURL="jdbc:mysql://127.0.0.1:3306/irdatabase2";
	//DBUSER is the username of MySQL
	private final String DBUSER="root";
	//DBPASSWORD is the password of MySQL
	private final String DBPASSWORD="123456";
	private Connection conn;
	//method to make link
	public Connection makeConnection(){
		try {
			Class.forName(DBDRIVER);
			conn = DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return conn;
	}
	//method to close link
	public void close(){
		try {
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
