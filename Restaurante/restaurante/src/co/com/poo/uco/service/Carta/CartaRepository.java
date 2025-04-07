package co.com.poo.uco.service.Carta;

import co.com.poo.uco.model.Producto;
import java.util.List;
/**
 * Interfaz que define el contrato para cargar la carta del restaurante.
 * Cualquier clase que implemente esta interfaz debe proporcionar
 * una manera de obtener una lista de productos.
 */
public interface CartaRepository {
    List<Producto> cargarCarta();
}
