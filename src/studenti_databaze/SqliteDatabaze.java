package studenti_databaze;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public interface SqliteDatabaze {
	 public static void ulozit_data(String JDatabaze,Databaze_studentu ukladanaDatabaze) {
	     // Register the SQLite JDBC driver
		 boolean tStudent=false,tZnamky=false;
	        try {
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            System.err.println("SQLite JDBC driver not found!");
	            e.printStackTrace();
	            return;
	        }
	        String dbsoubor=JDatabaze + ".db";
	        Path dbcesta = Paths.get(dbsoubor);
	        if (Files.exists(dbcesta)) {
	            try {
	                Files.delete(dbcesta);
	            } catch (IOException e) {
	                System.err.println("Databaze se nepodarila smazat.: " + dbsoubor);
	                System.out.println("Manualne smazete soubor "+dbsoubor+" a zkuste ukladani znova");
	                e.printStackTrace();
	            }
	        }

	        String url = "jdbc:sqlite:"+JDatabaze+".db";

	        // SQL statement for creating the "student" table.
	        String sqlStudent = "CREATE TABLE IF NOT EXISTS studenti ("
	                + " ID_s INTEGER PRIMARY KEY, "
	                + " Jmeno VARCHAR(255) NOT NULL, "
	                + " Prijmeni VARCHAR(255) NOT NULL, "
	                + " D_narozeni DATE, "
	                + " obor TINYINT NOT NULL"
	                + ");";

	        // SQL statement for creating the "znamky" table.
	        String sqlZnamky = "CREATE TABLE IF NOT EXISTS znamky ("
	                + " ID_z INTEGER PRIMARY KEY AUTOINCREMENT, "
	                + " ID_s INTEGER NOT NULL, "
	                + " znamka TINYINT, "
	                + " FOREIGN KEY (ID_s) REFERENCES studenti(ID_s)"
	                + ");";

	        try (Connection conn = DriverManager.getConnection(url);
	             Statement stmt = conn.createStatement()) {

	            // Create the student table
	            stmt.execute(sqlStudent);
	            tStudent=true;

	            // Create the znamky table
	            stmt.execute(sqlZnamky);
	            tZnamky=true;
		        if(tStudent && tZnamky) {
		        	System.out.println("Databaze byla uspesne vytvorena");
		        }
		        stmt.execute("PRAGMA foreign_keys = ON;");
		        System.out.println("Ukladaji se data nevypinejte program, aby nedoslo ke poskozeni dat.");
		        Integer[] id_studentu=ukladanaDatabaze.getIDs();
		        
		        for(Integer id:id_studentu) {
		        	System.out.println(ukladanaDatabaze.vypsat_studenta(id).getJmeno());
		        	PreparedStatement sqlVlozStudenta = conn.prepareStatement("INSERT INTO studenti VALUES(?,?,?,?,?)");
		        	sqlVlozStudenta.setInt(1, id);
		        	sqlVlozStudenta.setString(2, ukladanaDatabaze.vypsat_studenta(id).getJmeno());
		        	sqlVlozStudenta.setString(3, ukladanaDatabaze.vypsat_studenta(id).getPrijmeni());
		        	sqlVlozStudenta.setLong(4, ukladanaDatabaze.vypsat_studenta(id).getDatum_narozeni().toEpochDay());
		        	sqlVlozStudenta.setInt(5, ukladanaDatabaze.vypsat_studenta(id).getObor());
		        	sqlVlozStudenta.execute();
		        	PreparedStatement sqlVlozZnamky = conn.prepareStatement("INSERT INTO znamky(ID_s,znamka) values(?,?)");
		        	for(int znamka:ukladanaDatabaze.vypsat_studenta(id).znamky) {
		        	sqlVlozZnamky.setInt(1, id);
		        	sqlVlozZnamky.setInt(2, znamka);
		        	sqlVlozZnamky.execute();
		        	}
		        }
	        } catch (SQLException e) {
	            System.err.println(e.getMessage());
	        }
	        
	        System.out.println("Data byla ulozena");
	        
	}
	 
	 public static Databaze_studentu nacist_data(String JDatabaze) {
		 Databaze_studentu nacitanaDatabaze=new Databaze_studentu();
		 System.out.println("Nacitam data");
	        try {
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            System.err.println("SQLite JDBC driver not found!");
	            e.printStackTrace();
	            return null;
	        }

	        String url = "jdbc:sqlite:"+JDatabaze+".db";
    		String sqlVypsatStudenty= "SELECT * from studenti";
    		String sqlVypsatZnamky="SELECT znamka from znamky WHERE ID_s=";
	        try (Connection conn = DriverManager.getConnection(url);
		             Statement stmt = conn.createStatement();
	        		var rs = stmt.executeQuery(sqlVypsatStudenty)){
	        		
	        		while(rs.next()) {
	        			LocalDate narozeni=LocalDate.ofEpochDay(rs.getLong("D_narozeni"));
	        			nacitanaDatabaze.pridat_studenta( new Student(rs.getString("jmeno"),rs.getString("prijmeni"),rs.getInt("ID_s"),narozeni,rs.getInt("obor")));
	        		}
	        		
	        	
		        } catch (SQLException e) {
		            System.err.println("Nebylo mozne nacist studenty");
			         return null;
		        }
	        Integer[] IDs=nacitanaDatabaze.getIDs();
	        for(Integer ID:IDs) {
	        try (Connection conn = DriverManager.getConnection(url);
		             Statement stmt = conn.createStatement();
	        		var rs = stmt.executeQuery(sqlVypsatZnamky+ID)){
	        		while(rs.next()) {
	        			nacitanaDatabaze.pridat_znamku(ID, rs.getInt(1));
	        		}
	        }
		     catch (SQLException e) {
		         System.err.println("Nebylo mozne nacist znamky ke studentovy s ID:"+ID);
		         return null;
		     }
	        }
	       System.out.println("Data byla nactena");
		 return nacitanaDatabaze;
	 }

}
