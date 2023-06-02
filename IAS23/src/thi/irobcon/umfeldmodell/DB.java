package thi.irobcon.umfeldmodell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DB {

	public static final String DRIVER = "org.h2.Driver";

	public static String HOST = "localhost:3393";
	public static String DB = "//tmp/isx/db/ifsdb";
	String URL = "jdbc:h2:tcp://" + HOST + DB;
	
	protected static final String USER = "ifs";
	protected static final String PASSWORD = "";

	protected Connection connection;
	
	public boolean open() {
		
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			connection.setAutoCommit(false);
			return true;
		} 
		catch (Exception e) {
			System.out.println("Fehler öffnen DB");
			return false;
		}
		
	}
	
	public void close() {
		try {
			connection.rollback();
			connection.close();
			connection = null;
		} 
		catch (SQLException e) {
		}
	}
	
	public void insertMesswert(int nummer, int messung, double distanz, double winkel) {
		try {
			String cmd = "INSERT INTO " + "Messwerte " + "(nummer, messung, distanz, winkel) " +
					"VALUES " + "(" + nummer + "," + messung + "," + distanz + "," + winkel + ")";
			Statement stmt = connection.createStatement();
			stmt.execute(cmd);
			connection.commit();
			stmt.close();
		} 
		catch (SQLException e) {
			try { connection.rollback(); } catch (SQLException e1) {}
			System.out.println("Fehler Speichern Messwert");
		}
		
	}
	
	public int updateMessicherheit(String name, double messSicherheit) {
		try {
			String cmd = String.format("UPDATE Sensoren SET Messsicherheit = %f WHERE name = '%s'", messSicherheit, name);
			cmd = cmd.replace(",", ".");
			Statement stmt = connection.createStatement();
			int anzahl = stmt.executeUpdate(cmd);
			connection.commit();
			stmt.close();
			return anzahl;
		} catch (SQLException e) {
			try { connection.rollback(); } catch (SQLException e1) {}
			System.out.println("Fehler Aktualisieren Messwert");
			return 0;
		}
		
	}
	
	public Vector<Messwert> selectMesswerte(int messung) {
		Vector<Messwert> res = new Vector<>();
		try {
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Messwerte WHERE messung = ?");
			pstmt.setInt(1, messung);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int nummer = rs.getInt("nummer");
				double distanz = rs.getDouble("distanz");
				double winkel = rs.getDouble("winkel");
				Messwert messwert = new Messwert(nummer, distanz, winkel);
				res.add(messwert);
			}
			rs.close();
			pstmt.close();
		} 
		catch (SQLException e) {
			System.out.println("Fehler Lesen Messwerte");
		}
		return res;
	}
	
	public void deleteMessung(int nummer) {
		String cmd1 = "DELETE FROM Messwerte WHERE Messung = " + nummer;
		String cmd2 = "DELETE FROM Messungen WHERE nummer = " + nummer;
		
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(cmd1);
			stmt.execute(cmd2);
			connection.commit();
			stmt.close();
		} 
		catch (SQLException e) {
			try { connection.rollback(); } catch (SQLException e1) {}
			System.out.println("Fehler Löschen Messung");
		}
	}
}
