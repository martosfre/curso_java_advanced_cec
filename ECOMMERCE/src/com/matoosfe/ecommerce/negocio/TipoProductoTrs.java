package com.matoosfe.ecommerce.negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.matoosfe.ecommerce.modelo.ConexionBdd;
import com.matoosfe.ecommerce.modelo.TipoProducto;
import com.matoosfe.ecommerce.modelo.Usuario;

/**
 * Clase que administrar las operaciones relacionadas con tipo de producto
 * 
 * @author CEC
 *
 */
public class TipoProductoTrs implements ICrudC {

	@Override
	public String guardar(Object registro) throws Exception {
		String mensaje = null;

		// 1.Recuperar la Conexión
		Connection con = ConexionBdd.conectarBdd();

		// 2.Establecer la operación: DDL o DML
		//Si colocan 0 en un campo autoincrement automáticamente coloca el valor
		String sqlInsUsu = "INSERT INTO `tipo_producto`\r\n" + 
				"(`id_tip_pro`,\r\n" + 
				"`nombre_tip_pro`,\r\n" + 
				"`descripcion_tip_pro`)\r\n" + 
				"VALUES\r\n" + 
				"(0, ?, ?);";

		// 3.Elegir el tipo de Objeto JDBC a utilizar
		PreparedStatement stConUsu = con.prepareStatement(sqlInsUsu);

		/*
		 * 4.Proceder a ejecutar la sentencia SQL a) execute -> DDL -> boolean b)
		 * executeUpdate -> DML excepto el Select -> Insert, Update, Delete -> int c)
		 * executeQuery -> Select -> ResultSet (Tabla de Información)
		 */
		//4.1 Setear parámetros
		TipoProducto tipPro = (TipoProducto) registro;
		stConUsu.setString(1, tipPro.getNombreTipPro());
		stConUsu.setString(2, tipPro.getDescripcionTipPro());
		
		//4.2 Ejecutar sentencia
		int numFilAfe = stConUsu.executeUpdate();

		/*
		 * 5.Procesar la información, tabla se convierte en un puntero a) Varios
		 * registros -> while b) Un solo -> if
		 */
		if(numFilAfe > 0) {
			mensaje = "Registro guardado correctamente";
		}

		return mensaje;
	}

	@Override
	public String actualizar(Object registro) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String eliminar(Object registro) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> consultarTodos() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
