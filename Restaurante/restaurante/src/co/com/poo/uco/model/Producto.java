package co.com.poo.uco.model;

/**
 * Clase que representa un producto disponible en el restaurante.
 * Cada producto tiene un nombre, un precio y una categoría.
 */
public class Producto {
    private String nombre;
    private double precio;
    private String categoria;

    public Producto(String nombre, double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }
    public String getNombre() {
        return nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public String getCategoria() {
        return categoria;
    }

}
