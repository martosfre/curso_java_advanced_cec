package com.matoosfe.ecommerce.form.util;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import com.matoosfe.ecommerce.modelo.TipoProducto;

/**
 * Clase que representa el modelo para administrar los valores del combo box
 * tipo de producto
 * 
 * @author martosfre
 *
 */
public class ComboBoxModelTipoProducto implements ComboBoxModel<TipoProducto>{

	private List<TipoProducto> tipoProductos;
	private int indice;
	
	/**
	 * @param tipoProductos
	 */
	public ComboBoxModelTipoProducto(List<TipoProducto> tipoProductos) {
		this.tipoProductos = tipoProductos;
	}

	@Override
	public int getSize() {
		return tipoProductos.size();
	}

	@Override
	public TipoProducto getElementAt(int index) {
		indice = index;
		return tipoProductos.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedItem(Object anItem) {
		if(tipoProductos.contains(anItem)) {
			indice = tipoProductos.indexOf(anItem);
		}
	}
	
	@Override
	public Object getSelectedItem() {
		return tipoProductos.get(indice);
	}
	

}
