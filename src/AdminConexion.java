import java.sql.Connection;
import java.sql.DriverManager;


public class AdminConexion {
	
	public static Connection ObtenerConexion (){
		
		Connection con = null;
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/prueba1?serverTimezone=UTC", "root", "");
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
			
			return con;
			
			
		}
	}
	

