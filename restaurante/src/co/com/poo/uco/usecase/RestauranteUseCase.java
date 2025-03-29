package co.com.poo.uco.usecase;

import co.com.poo.uco.model.Pedido;
import co.com.poo.uco.model.Producto;
import co.com.poo.uco.service.Carta.CartaRepository;
import co.com.poo.uco.service.Pedido.PedidoRepository;

import java.util.List;
import java.util.Optional;

public class RestauranteUseCase {
    private final CartaRepository cartaRepository;
    private final PedidoRepository pedidoRepository;

    public RestauranteUseCase(CartaRepository cartaRepository, PedidoRepository pedidoRepository) {
        this.cartaRepository = cartaRepository;
        this.pedidoRepository = pedidoRepository;
    }
    public List<Producto> verCarta() {
        return cartaRepository.cargarCarta();
    }
    public Pedido realizarPedido(int numeroMesa,List<Producto> productos) {
        Pedido nuevoPedido = new Pedido(0,numeroMesa,productos);
        pedidoRepository.agregarPedido(nuevoPedido);
        return nuevoPedido;
    }
    public List<Pedido> verPedidosActivos(){
        return pedidoRepository.obtenerPedidos();
    }
    public boolean cerrarPedido(int idPedido, double decuento) {
        Pedido pedido = pedidoRepository.bucarPedidoPorId(idPedido);
        if (pedido != null) {
            pedido.aplicarDescuento(decuento);
            pedido.cerrarPedido();
            return true;
        }
        return false;
    }
    public boolean cancelarPedido(int idPedido) {
        Pedido pedido = pedidoRepository.bucarPedidoPorId(idPedido);
        if (pedido != null && pedido.isCancelable()) {
            pedidoRepository.eliminarPedido(idPedido);
            return true;
        }
        return false;
    }
}
