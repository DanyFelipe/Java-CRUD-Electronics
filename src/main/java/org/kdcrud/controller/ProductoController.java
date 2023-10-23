package org.kdcrud.controller;
import org.kdcrud.model.ProductoDAO;
import org.kdcrud.model.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {
    private ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public List<Producto> obtenerProductos() {
        return productoDAO.listarProductos();
    }
    public void agregarProducto(Producto producto) {
        productoDAO.agregarProducto(producto);
    }
    public void eliminarProducto(int idProducto){
        productoDAO.eliminarProducto(idProducto);
    }
    public void actualizarProducto(Producto producto){
        productoDAO.actualizarProducto(producto);
    };
}
