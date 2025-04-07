package co.com.poo.uco.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase que representa una mesa en un restaurante.
 * Cada mesa tiene un número identificador y una lista de pedidos asociados.
 */
public class Mesa {
    private int numero;
    private List<Pedido> pedidos;

/**
 * Constructor de la clase Mesa.
 * Inicializa el número de la mesa y la lista de pedidos vacía.
 * parametro numero es el Número identificador de la mesa
 */
    public Mesa(int numero) {
        this.numero = numero;
        this.pedidos = new ArrayList<>();
    }
    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }
    public int getNumero() {
        return numero;
    }
    public List<Pedido> getPedidos() {
        return pedidos;
    }

}
