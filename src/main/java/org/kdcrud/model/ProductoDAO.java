package org.kdcrud.model;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public interface ProductoDAO {
    List<Producto> listarProductos();
    void agregarProducto(Producto producto);
    void eliminarProducto(int idProducto);
    void actualizarProducto(Producto producto);
}
