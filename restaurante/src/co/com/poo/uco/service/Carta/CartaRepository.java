package co.com.poo.uco.service.Carta;

import co.com.poo.uco.model.Producto;
import java.util.List;

public interface CartaRepository {
    List<Producto> cargarCarta();
}
