package org.kdcrud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.sql.Connection;

import org.kdcrud.controller.ProductoController;
import org.kdcrud.model.Producto;
import org.kdcrud.model.ProductoDAO;
import org.kdcrud.controller.ProductoController;
import org.kdcrud.model.SQLProductoDAO;
import org.kdcrud.model.SQLConnection;

public class CrudApp {
    private JButton insertarButton;
    private JButton eliminarButton;
    private JButton editarButton;
    private JTable tablaConsultas;
    private JTextField txt_nombre;
    private JTextField txt_precioB;
    private JTextField txt_precioV;
    private JSpinner spinner_cantidad;
    private JTextArea txtA_descripcion;
    private JPanel Main;
    private JTextField txt_categoria;
    private JPanel JPanelLeft;
    private JPanel JPanelRight;
    private DefaultTableModel tableModel;
    private ProductoController controller;
    private int idProductoSeleccionado;

    public CrudApp(ProductoController controller) {
        this.controller = controller;
        initComponents();
        crearTabla();
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!camposVacios()){
                    try {
                        String nombre = txt_nombre.getText();
                        String descripcion = txtA_descripcion.getText();
                        double precioBase = Double.parseDouble(txt_precioB.getText());
                        double precioVenta = Double.parseDouble(txt_precioV.getText());
                        String categoria = txt_categoria.getText();
                        int cantidad = (int) spinner_cantidad.getValue();

                        Producto nuevoProducto = new Producto(2,nombre, descripcion, precioBase, precioVenta, categoria, cantidad);
                        controller.agregarProducto(nuevoProducto);

                        limpiarCampos();
                        llenarJTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Main, "Ingrese números válidos en los campos de precio base y precio de venta.");
                    }
                }else{
                    JOptionPane.showMessageDialog(Main, "Por favor, llene todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaConsultas.getSelectedRow();

                if (filaSeleccionada != -1) {
                    int idProducto = (int) tablaConsultas.getValueAt(filaSeleccionada, 0);

                    controller.eliminarProducto(idProducto);

                    llenarJTable();
                } else {
                    JOptionPane.showMessageDialog(Main, "Selecciona un producto para eliminar.");
                }
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!camposVacios()) {
                    String nombre = txt_nombre.getText();
                    String descripcion = txtA_descripcion.getText();
                    double precioBase = Double.parseDouble(txt_precioB.getText());
                    double precioVenta = Double.parseDouble(txt_precioV.getText());
                    String categoria = txt_categoria.getText();
                    int cantidad = (int) spinner_cantidad.getValue();

                    // Crea un objeto Producto con los datos editados
                    Producto productoActualizado = new Producto(idProductoSeleccionado, nombre, descripcion, precioBase, precioVenta, categoria, cantidad);

                    // Llama al controlador para actualizar el producto
                    controller.actualizarProducto(productoActualizado);

                    llenarJTable();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(Main, "Por favor, llene todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        tablaConsultas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaConsultas.getSelectedRow();

                int idProducto = (int) tablaConsultas.getValueAt(filaSeleccionada, 0);
                String nombre = (String) tablaConsultas.getValueAt(filaSeleccionada, 1);
                String descripcion = (String) tablaConsultas.getValueAt(filaSeleccionada, 2);
                double precioBase = (double) tablaConsultas.getValueAt(filaSeleccionada, 3);
                double precioVenta = (double) tablaConsultas.getValueAt(filaSeleccionada, 4);
                String categoria = (String) tablaConsultas.getValueAt(filaSeleccionada, 5);
                int cantidad = (int) tablaConsultas.getValueAt(filaSeleccionada, 6);

                // Llena los campos de edición con los datos obtenidos
                txt_nombre.setText(nombre);
                txtA_descripcion.setText(descripcion);
                txt_precioB.setText(Double.toString(precioBase));
                txt_precioV.setText(Double.toString(precioVenta));
                txt_categoria.setText(categoria);
                spinner_cantidad.setValue(cantidad);

                // Guarda el ID del producto seleccionado (para su posterior actualización)
                idProductoSeleccionado = idProducto;
            }
        });
    }
    public void initComponents(){
        System.out.println("HOLA");
        JFrame frame = new JFrame("KD Electronics CRUD");
        frame.setContentPane(Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void crearTabla(){
        String[]columns={"Id", "Nombre", "Descripción", "Precio base", "Precio Venta", "Categoría", "Cantidad"};
        tableModel = new DefaultTableModel(columns, 0);
        tablaConsultas.setModel(tableModel);
        llenarJTable();
    }
    public void llenarJTable() {
        tableModel.setRowCount(0);
        DefaultTableModel model = (DefaultTableModel) tablaConsultas.getModel();
        List<Producto> productos = controller.obtenerProductos();

        for (Producto producto : productos) {
            model.addRow(new Object[]{
                    producto.getIdProducto(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio_base(),
                    producto.getPrecio_venta(),
                    producto.getCategoria(),
                    producto.getCantidad()
            });
        }
    }
    private void limpiarCampos() {
        txt_nombre.setText("");
        txtA_descripcion.setText("");
        txt_precioB.setText("");
        txt_precioV.setText("");
        txt_categoria.setText("");
        spinner_cantidad.setValue(0);
    }
    private boolean camposVacios() {
        return txt_nombre.getText().isEmpty() || txtA_descripcion.getText().isEmpty() || txt_precioB.getText().isEmpty() || txt_precioV.getText().isEmpty() || txt_categoria.getText().isEmpty() || ((int) spinner_cantidad.getValue()) == 0;
    }
    public static void main(String[] args) {
        // Configura tu controlador y otros componentes
        SQLConnection sqlConnection = new SQLConnection(); // Configura la conexión
        ProductoDAO productoDAO = new SQLProductoDAO(sqlConnection);
        ProductoController controller = new ProductoController(productoDAO);

        SwingUtilities.invokeLater(() -> new CrudApp(controller));
    }
}
