package co.com.poo.uco.model;

import java.util.List;
/**
 * Clase que representa un pedido realizado en una mesa.
 * Contiene información sobre los productos, estado del pedido, total y posibles descuentos.
 */
public class Pedido {
    private int id;
    private int numeroMesa;
    private List<Producto> productos;
    private boolean entregado;
    private double total;
    private double descuento;
/**
 * Constructor de la clase Pedido.
 * Inicializa el pedido con su id, número de mesa, lista de productos y calcula el total.
 */
    public Pedido(int id, int numeroMesa, List<Producto> productos) {
        this.id = id;
        this.numeroMesa = numeroMesa;
        this.productos = productos;
        this.entregado = false;
        this.total = calcularTotal();
        this.descuento = 0;
    }

    private double calcularTotal() {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

    public void aplicarDescuento(double porcentaje){
        if (porcentaje > 0 && porcentaje <= 10) {
            this.descuento = total * (porcentaje / 100);
        }
    }
    /**
     * Indica si el pedido se puede cancelar (solo si no ha sido entregado).
     */
    public void cerrarPedido() {
        this.entregado = true;
    }

    public boolean isCancelable(){
        return !entregado;
    }

    public int getId() {
        return id;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public double getTotal() {
        return total - descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}


