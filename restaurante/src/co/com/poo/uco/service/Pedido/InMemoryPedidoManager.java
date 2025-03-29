package co.com.poo.uco.service.Pedido;

import co.com.poo.uco.model.Pedido;
import java.util.ArrayList;
import java.util.List;

public class InMemoryPedidoManager implements PedidoRepository{
    private final List<Pedido> pedidos=new ArrayList<>();
    private int nextId=1;

    @Override
    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }
    @Override
    public List<Pedido> obtenerPedidos() {
        return new ArrayList<>(pedidos);
    }

    @Override
    public Pedido bucarPedidoPorId(int id) {
        return pedidos.stream().filter(p -> p.getId()==id).findFirst().orElse(null);
    }

    @Override
    public void eliminarPedido(int id) {
        pedidos.removeIf(p -> p.getId()==id);
    }
}
