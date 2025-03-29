package co.com.poo.uco.model;

import java.util.List;

public class Pedido {
    private int id;
    private int numeroMesa;
    private List<Producto> productos;
    private boolean entregado;
    private double total;
    private double descuento;

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
        if(porcentaje > 0 && porcentaje <= 10){
            this.descuento = total*(porcentaje/100);
        }
    }
    public void cerrarPedido() {
        this.entregado = true;
        this.total -= descuento;
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
        return total-descuento;
    }
}

