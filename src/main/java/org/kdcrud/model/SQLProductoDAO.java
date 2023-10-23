package org.kdcrud.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
public class SQLProductoDAO implements ProductoDAO{
    private SQLConnection sqlConnection;

    public SQLProductoDAO(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }
    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        Connection connection = sqlConnection.getConnection();

        try {
            String query = "SELECT * FROM Productos";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(resultSet.getInt("idProducto"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setPrecio_base(resultSet.getDouble("precio_base"));
                producto.setPrecio_venta(resultSet.getDouble("precio_venta"));
                producto.setCategoria(resultSet.getString("categoria"));
                producto.setCantidad(resultSet.getInt("cantidad"));
                productos.add(producto);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*sqlConnection.closeConnection();*/
        }
        return productos;
    }
    @Override
    public void agregarProducto(Producto producto) {
        String query = "INSERT INTO Productos (nombre, descripcion, precio_base, precio_venta, categoria, cantidad) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = sqlConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setDouble(3, producto.getPrecio_base());
            statement.setDouble(4, producto.getPrecio_venta());
            statement.setString(5, producto.getCategoria());
            statement.setInt(6, producto.getCantidad());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void eliminarProducto(int idProducto) {
        String query = "DELETE FROM Productos WHERE idProducto = ?";

        Connection connection = sqlConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idProducto);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actualizarProducto(Producto producto) {
        String query = "UPDATE Productos " +
                "SET nombre = ?, descripcion = ?, precio_base = ?, precio_venta = ?, categoria = ?, cantidad = ? " +
                "WHERE idProducto = ?";

        Connection connection = sqlConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setDouble(3, producto.getPrecio_base());
            statement.setDouble(4, producto.getPrecio_venta());
            statement.setString(5, producto.getCategoria());
            statement.setInt(6, producto.getCantidad());
            statement.setInt(7, producto.getIdProducto());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
