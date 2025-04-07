package co.com.poo.uco.service.Pedido;

import co.com.poo.uco.model.Pedido;
import java.util.List;
/**
 * Interfaz que define el contrato para gestionar pedidos.
 * Cualquier clase que implemente esta interfaz debe proporcionar
 * operaciones para agregar, obtener, buscar y eliminar pedidos.
 */
public interface PedidoRepository {
    void agregarPedido(Pedido pedido);
    List<Pedido> obtenerPedidos();
    Pedido bucarPedidoPorId(int id);
    void eliminarPedido(int id);
}
