package co.com.poo.uco.service.Pedido;

import co.com.poo.uco.model.Pedido;
import java.util.List;

public interface PedidoRepository {
    void agregarPedido(Pedido pedido);
    List<Pedido> obtenerPedidos();
    Pedido bucarPedidoPorId(int id);
    void eliminarPedido(int id);
}
