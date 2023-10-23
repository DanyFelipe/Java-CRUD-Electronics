package org.kdcrud.model;

public class Producto {
    int idProducto;
    String nombre;
    String descripcion;
    Double precio_base;
    Double precio_venta;
    String categoria;
    int cantidad;
    public Producto() {
    }

    public Producto(int idProducto, String nombre, String descripcion, Double precio_base, Double precio_venta, String categoria, int cantidad) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio_base = precio_base;
        this.precio_venta = precio_venta;
        this.categoria = categoria;
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio_base() {
        return precio_base;
    }

    public Double getPrecio_venta() {
        return precio_venta;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio_base(Double precio_base) {
        this.precio_base = precio_base;
    }

    public void setPrecio_venta(Double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
