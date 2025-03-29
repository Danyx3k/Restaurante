package co.com.poo.uco.model;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private int numero;
    private List<Pedido> pedidos;

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
