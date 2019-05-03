package com.matoosfe.ecommerce.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para administrar la Base de datos
 * @author @martosfre
 *
 */
public class ConexionBdd {
	private static final String USUARIO = "root";
	private static final String PASSWORD = "toor";
	/*
	 * A partir de la versi�n 5.8.1 se necesita en la cadena de conexi�n setear el serverTimeZone
	 * https://medium.com/@johanmenz/error-java-sql-sqlexception-the-server-time-zone-value-81495c7d3732
	 */
	
	/* Para dar permiso para conectarse remotamente a las bases de datos
	 * CREATE USER 'root'@'%' IDENTIFIED BY 'toor';
	   GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION; 
	 */
	private static final String CADENABDD_MYSQL = "jdbc:mysql://localhost:3306/ecommerce?serverTimezone=UTC";
	
	public static Connection conectarBdd() throws ClassNotFoundException, SQLException {
		Connection con = null;
		try {
			//1)Reconocer al Driver - Nuevo driver 5.8.1
			Class.forName("com.mysql.cj.jdbc.Driver");
			//2)Conectarme a la BDD
			con = DriverManager.getConnection(CADENABDD_MYSQL, USUARIO, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("Driver incorrecto:" + e.getMessage());
		} catch (SQLException e) {
			throw new SQLException("Credenciales o URL incorrecta:" + e.getMessage());
		}
		return con;
	}
}
