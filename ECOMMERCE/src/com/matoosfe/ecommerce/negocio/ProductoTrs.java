package com.matoosfe.ecommerce.negocio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.matoosfe.ecommerce.modelo.ConexionBdd;
import com.matoosfe.ecommerce.modelo.Producto;
import com.matoosfe.ecommerce.modelo.TipoProducto;

/**
 * Clase que administrar las operaciones relacionadas con de producto
 * 
 * @author CEC
 *
 */
public class ProductoTrs implements ICrudC {

	@Override
	public String guardar(Object registro) throws Exception {
		String mensaje = null;

		try (Connection con = ConexionBdd.conectarBdd()) {
			String sqlInsPro = "INSERT INTO `ecommerce`.`producto`\r\n" + 
					"(`id_pro`,\r\n" + 
					"`nombre_pro`,\r\n" + 
					"`descripcion_pro`,\r\n" + 
					"`precio_pro`,\r\n" + 
					"`fecha_cad_pro`,\r\n" + 
					"`id_tip_pro`)\r\n" + 
					"VALUES\r\n" + 
					"(0,?,?,?,?,?);";

			try (PreparedStatement ptInsPro = con.prepareStatement(sqlInsPro)){
				Producto pro = (Producto) registro;
				ptInsPro.setString(1, pro.getNombrePro());
				ptInsPro.setString(2, pro.getDescripcionPro());
				ptInsPro.setBigDecimal(3, pro.getPrecioPro());
				//Para guardar una fecha en la bdd se debe convertir de java.util.Date a java.sql.Date
				ptInsPro.setDate(4, new java.sql.Date(pro.getFechaCadPro().getTime()));
				ptInsPro.setInt(5, pro.getTipoProducto().getIdTipPro());

				int numFilAfe = ptInsPro.executeUpdate();

				if (numFilAfe > 0) {
					mensaje = "Registro guardado correctamente";
				}
			} catch (Exception e) {
				throw new Exception("Error al cerrar el pt:" + e.getMessage());
			}
		} catch (Exception e) {
			throw new Exception("Error al cerrar la conexión" + e.getMessage());
		}
		return mensaje;
	}

	@Override
	public String actualizar(Object registro) throws Exception {
		String mensaje = null;

		try (Connection con = ConexionBdd.conectarBdd()) {
			String sqlUpdPro = "UPDATE `ecommerce`.`producto`\r\n" + 
					"SET\r\n" + 
					"`nombre_pro` = ?,\r\n" + 
					"`descripcion_pro` = ?,\r\n" + 
					"`precio_pro` = ?,\r\n" + 
					"`fecha_cad_pro` = ?,\r\n" + 
					"`id_tip_pro` = ?\r\n" + 
					"WHERE `id_pro` = ?;";
			try (PreparedStatement ptUpdPro = con.prepareStatement(sqlUpdPro)){
				Producto pro = (Producto) registro;
				ptUpdPro.setString(1, pro.getNombrePro());
				ptUpdPro.setString(2, pro.getDescripcionPro());
				ptUpdPro.setBigDecimal(3, pro.getPrecioPro());
				ptUpdPro.setDate(4, new java.sql.Date(pro.getFechaCadPro().getTime()));
				ptUpdPro.setInt(5, pro.getTipoProducto().getIdTipPro());
				ptUpdPro.setInt(6, pro.getIdPro());
				int numFilAfe = ptUpdPro.executeUpdate();

				if (numFilAfe > 0) {
					mensaje = "Registro actualizado correctamente";
				}
			}catch (Exception e) {
				throw new Exception("Error al cerrar el pt:" + e.getMessage());
			}
		} catch (Exception e) {
			throw new Exception("Error al cerrar la conexión" + e.getMessage());
		}

		return mensaje;
	}

	@Override
	public String eliminar(Object registro) throws Exception {
		String mensaje = null;

		try(Connection con = ConexionBdd.conectarBdd()) {
			String sqlDelPro = "DELETE FROM `ecommerce`.`producto`\r\n" + "WHERE id_pro = ?;";
			try (PreparedStatement ptDelPro = con.prepareStatement(sqlDelPro)){
				Producto pro = (Producto) registro;
				ptDelPro.setInt(1, pro.getIdPro());

				int numFilAfe = ptDelPro.executeUpdate();

				if (numFilAfe > 0) {
					mensaje = "Registro eliminado correctamente";
				}
			} catch (Exception e) {
				throw new Exception("Error al cerrar el pt:" + e.getMessage());
			}
		} catch (Exception e) {
			throw new Exception("Error al cerrar la conexión" + e.getMessage());
		}


		return mensaje;
	}

	@Override
	public List<Producto> consultarTodos() throws Exception {
		TipoProductoTrs admTipProTrs = new  TipoProductoTrs();
		List<Producto> listaProductos = new ArrayList<>();
		try (Connection con = ConexionBdd.conectarBdd()){
			String sqlConPro = "SELECT * FROM producto;";
			try (Statement stConPro = con.createStatement();
				 ResultSet rs = stConPro.executeQuery(sqlConPro);) {
				while (rs.next()) {
					Producto pro = new Producto();
					pro.setIdPro(rs.getInt(1));
					pro.setNombrePro(rs.getString(2));
					pro.setDescripcionPro(rs.getString(3));
					pro.setPrecioPro(rs.getBigDecimal(4));
					pro.setFechaCadPro(rs.getDate(5));
					pro.setTipoProducto(admTipProTrs.consultarPorId(rs.getInt(6)));
					listaProductos.add(pro);
				}
			} catch (Exception e) {
				throw new Exception("Error al cerrar el pt");
			}
		} catch (Exception e) {
			throw new Exception("Error al cerrar la conexión");
		}

		return listaProductos;
	}

	public List<Producto> consultarPorNombreDescripcion(String text) throws Exception {
		TipoProductoTrs admTipProTrs = new  TipoProductoTrs();
		List<Producto> listaProductos = new ArrayList<>();
		try(Connection con = ConexionBdd.conectarBdd()) {
			String sqlConPro = "SELECT * FROM ecommerce.producto where nombre_pro"
					+ " LIKE ? or descripcion_pro LIKE ?;";
			try (PreparedStatement stConPro = con.prepareStatement(sqlConPro);){
				stConPro.setString(1, "%" + text + "%");
				stConPro.setString(2, "%" + text + "%");
				try {
					ResultSet rs = stConPro.executeQuery();

					while (rs.next()) {
						Producto pro = new Producto();
						pro.setIdPro(rs.getInt(1));
						pro.setNombrePro(rs.getString(2));
						pro.setDescripcionPro(rs.getString(3));
						pro.setPrecioPro(rs.getBigDecimal(4));
						pro.setFechaCadPro(rs.getDate(5));
						pro.setTipoProducto(admTipProTrs.consultarPorId(rs.getInt(6)));
						listaProductos.add(pro);
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

		return listaProductos;
	}
}
