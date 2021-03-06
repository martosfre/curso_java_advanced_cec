package com.matoosfe.ecommerce.negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.matoosfe.ecommerce.modelo.ConexionBdd;
import com.matoosfe.ecommerce.modelo.TipoProducto;

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
		try (Connection con = ConexionBdd.conectarBdd()) { //Cerrando automáticamente el recurso

			// 2.Establecer la operación: DDL o DML
			// Si colocan 0 en un campo autoincrement automáticamente coloca el valor
			String sqlInsUsu = "INSERT INTO `tipo_producto`\r\n" + "(`id_tip_pro`,\r\n" + "`nombre_tip_pro`,\r\n"
					+ "`descripcion_tip_pro`)\r\n" + "VALUES\r\n" + "(0, ?, ?);";

			// 3.Elegir el tipo de Objeto JDBC a utilizar
			try (PreparedStatement ptInsTipPro = con.prepareStatement(sqlInsUsu)){
				/*
				 * 4.Proceder a ejecutar la sentencia SQL a) execute -> DDL -> boolean b)
				 * executeUpdate -> DML excepto el Select -> Insert, Update, Delete -> int c)
				 * executeQuery -> Select -> ResultSet (Tabla de Información)
				 */
				// 4.1 Setear parámetros
				TipoProducto tipPro = (TipoProducto) registro;
				ptInsTipPro.setString(1, tipPro.getNombreTipPro());
				ptInsTipPro.setString(2, tipPro.getDescripcionTipPro());

				// 4.2 Ejecutar sentencia
				int numFilAfe = ptInsTipPro.executeUpdate();

				/*
				 * 5.Procesar la información, tabla se convierte en un puntero a) Varios
				 * registros -> while b) Un solo -> if
				 */
				if (numFilAfe > 0) {
					mensaje = "Registro guardado correctamente";
				}
			} catch (Exception e) {
				throw new Exception("Error al cerrar el pt");
			}
		} catch (Exception e) {
			throw new Exception("Error al cerrar la conexión");
		}
		return mensaje;
	}

	@Override
	public String actualizar(Object registro) throws Exception {
		String mensaje = null;

		try (Connection con = ConexionBdd.conectarBdd()) {
			String sqlUpdUsu = "UPDATE `ecommerce`.`tipo_producto`\r\n" + "SET\r\n" + "`nombre_tip_pro` = ?,\r\n"
					+ "`descripcion_tip_pro` = ?\r\n" + "WHERE `id_tip_pro` = ?;";
			try (PreparedStatement ptUpdTipPro = con.prepareStatement(sqlUpdUsu)){
				TipoProducto tipPro = (TipoProducto) registro;
				ptUpdTipPro.setString(1, tipPro.getNombreTipPro());
				ptUpdTipPro.setString(2, tipPro.getDescripcionTipPro());
				ptUpdTipPro.setInt(3, tipPro.getIdTipPro());

				int numFilAfe = ptUpdTipPro.executeUpdate();

				if (numFilAfe > 0) {
					mensaje = "Registro actualizado correctamente";
				}
			} catch (Exception e) {
				throw new Exception("Error al cerrar el pt");
			}
		} catch (Exception e) {
			throw new Exception("Error al cerrar la conexión");
		}

		return mensaje;
	}

	@Override
	public String eliminar(Object registro) throws Exception {
		String mensaje = null;

		try(Connection con = ConexionBdd.conectarBdd()) {
			String sqlUpdUsu = "DELETE FROM `ecommerce`.`tipo_producto`\r\n" + "WHERE id_tip_pro = ?;";
			try (PreparedStatement ptDelTipPro = con.prepareStatement(sqlUpdUsu)){
				TipoProducto tipPro = (TipoProducto) registro;
				ptDelTipPro.setInt(1, tipPro.getIdTipPro());

				int numFilAfe = ptDelTipPro.executeUpdate();

				if (numFilAfe > 0) {
					mensaje = "Registro eliminado correctamente";
				}
			} catch (Exception e) {
				throw new Exception("Error al cerrar el pt");
			}
		} catch (Exception e) {
			throw new Exception("Error al cerrar la conexión");
		}

		return mensaje;
	}

	@Override
	public List<TipoProducto> consultarTodos() throws Exception {
		List<TipoProducto> listaTipoProductos = new ArrayList<>();
		try (Connection con = ConexionBdd.conectarBdd()){
			String sqlConTipPro = "SELECT * FROM tipo_producto;";
			try (Statement stConTipPro = con.createStatement();
				 ResultSet rs = stConTipPro.executeQuery(sqlConTipPro);) {
				while (rs.next()) {
					TipoProducto tipPro = new TipoProducto();
					tipPro.setIdTipPro(rs.getInt(1));
					tipPro.setNombreTipPro(rs.getString(2));
					tipPro.setDescripcionTipPro(rs.getString(3));
					listaTipoProductos.add(tipPro);
				}
			} catch (Exception e) {
				throw new Exception("Error al cerrar el pt");
			}
		} catch (Exception e) {
			throw new Exception("Error al cerrar la conexión");
		}

		return listaTipoProductos;
	}

	public List<TipoProducto> consultarPorNombreDescripcion(String text) throws Exception {
		List<TipoProducto> listaTipoProductos = new ArrayList<>();
		try(Connection con = ConexionBdd.conectarBdd()) {
			String sqlConTipPro = "SELECT * FROM ecommerce.tipo_producto where nombre_tip_pro"
					+ " LIKE ? or descripcion_tip_pro LIKE ?;";
			try (PreparedStatement stConTipPro = con.prepareStatement(sqlConTipPro);){
				stConTipPro.setString(1, "%" + text + "%");
				stConTipPro.setString(2, "%" + text + "%");
				try {
					ResultSet rs = stConTipPro.executeQuery();

					while (rs.next()) {
						TipoProducto tipPro = new TipoProducto();
						tipPro.setIdTipPro(rs.getInt(1));
						tipPro.setNombreTipPro(rs.getString(2));
						tipPro.setDescripcionTipPro(rs.getString(3));
						listaTipoProductos.add(tipPro);
					}
				} catch (Exception e) {
					throw new Exception("Error al cerrar el rs");
				}
			} catch (Exception e) {
				throw new Exception("Error al cerrar el pt");
			}
		} catch (Exception e) {
			throw new Exception("Error al cerrar la conexión");
		}

		return listaTipoProductos;
	}

	public TipoProducto consultarPorId(int idTipPro) throws Exception {
		TipoProducto tipPro = null;
		try(Connection con = ConexionBdd.conectarBdd()) {
			String sqlConTipPro = "SELECT * FROM ecommerce.tipo_producto where id_tip_pro = ?;";
			try (PreparedStatement stConTipPro = con.prepareStatement(sqlConTipPro);){
				stConTipPro.setInt(1,  idTipPro);
				try {
					ResultSet rs = stConTipPro.executeQuery();

					if (rs.next()) {
						tipPro = new TipoProducto();
						tipPro.setIdTipPro(rs.getInt(1));
						tipPro.setNombreTipPro(rs.getString(2));
						tipPro.setDescripcionTipPro(rs.getString(3));
					}
				} catch (Exception e) {
					throw new Exception("Error al cerrar el rs");
				}
			} catch (Exception e) {
				throw new Exception("Error al cerrar el pt");
			}
		} catch (Exception e) {
			throw new Exception("Error al cerrar la conexión");
		}
		return tipPro;
	}
}
