package co.com.poo.uco.usecase;

import co.com.poo.uco.model.Pedido;
import co.com.poo.uco.model.Producto;
import co.com.poo.uco.service.Carta.CartaRepository;
import co.com.poo.uco.service.Pedido.PedidoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Clase que centraliza los casos de uso del restaurante,
 * como ver la carta, realizar pedidos, ver pedidos activos,
 * cerrarlos o cancelarlos.
 */
public class RestauranteUseCase {
    private final CartaRepository cartaRepository; // Repositorio que gestiona la carta
    private final PedidoRepository pedidoRepository;  // Repositorio que gestiona los pedidos
    /**
     * Constructor que recibe los repositorios necesarios.
     * param cartaRepository Repositorio de productos
     * param pedidoRepository Repositorio de pedidos
     */
    public RestauranteUseCase(CartaRepository cartaRepository, PedidoRepository pedidoRepository) {
        this.cartaRepository = cartaRepository;
        this.pedidoRepository = pedidoRepository;
    }
    /**
     * Devuelve la carta de productos.
     * devuelve Lista de productos disponibles
     */
    public List<Producto> verCarta() {
        return cartaRepository.cargarCarta();
    }
    /**
     * Registra un nuevo pedido para una mesa con una lista de productos.
     * param numeroMesa Número de mesa
     * param productos Productos seleccionados
     * return Pedido generado
     */
    public Pedido realizarPedido(int numeroMesa,List<Producto> productos) {
        Pedido nuevoPedido = new Pedido(0,numeroMesa,productos);
        pedidoRepository.agregarPedido(nuevoPedido);
        return nuevoPedido;
    }
    /**
     * Devuelve todos los pedidos actualmente activos.
     * return Lista de pedidos
     */
    public List<Pedido> verPedidosActivos(){
        return pedidoRepository.obtenerPedidos();
    }
    /**
     * Cierra un pedido si existe, aplicando un descuento.
     * param idPedido ID del pedido
     * param descuento Porcentaje de descuento (0 a 10)
     * return true si se cerró correctamente
     */
    public boolean cerrarPedido(int idPedido, double decuento) {
        Pedido pedido = pedidoRepository.bucarPedidoPorId(idPedido);
        if (pedido != null) {
            pedido.aplicarDescuento(decuento);
            pedido.cerrarPedido();
            return true;
        }
        return false;
    }
    /**
     * Cancela un pedido si aún no ha sido entregado.
     * param idPedido ID del pedido
     * return true si fue cancelado correctamente
     */
    public boolean cancelarPedido(int idPedido) {
        Pedido pedido = pedidoRepository.bucarPedidoPorId(idPedido);
        if (pedido != null && pedido.isCancelable()) {
            pedidoRepository.eliminarPedido(idPedido);
            return true;
        }
        return false;
    }

    /**
     * Imprime por consola la carta de productos.
     * param restaurante Instancia de RestauranteUseCase
     */
    public static void verCarta(RestauranteUseCase restaurante) {
        List<Producto>carta=restaurante.verCarta();
        if(carta.isEmpty()){
            System.out.println("La carta esta vacia.");
            return;
        }
        System.out.println("\n=== CARTA ===");
        for(Producto p:carta){
            System.out.println(p.getNombre()+" -$ "+p.getPrecio() + "(" + p.getCategoria() + ")");
        }
    }
    /**
     * Permite al usuario realizar un pedido interactivo desde consola.
     * param scanner Objeto Scanner para entrada
     * param restaurante Instancia de RestauranteUseCase
     */
    public static void realizarPedido(Scanner scanner, RestauranteUseCase restaurante) {
        System.out.println("Ingrese el numero de la mesa= ");
        int numeroMesa=scanner.nextInt();
        scanner.nextLine();

        List<Producto>carta=restaurante.verCarta();
        if(carta.isEmpty()){
            System.out.println("No hay productos disponibles.");
            return;
        }
        List<Producto> productosSeleccionados=new ArrayList<>();
        while (true){
            verCarta(restaurante);
            System.out.println("Ingrese el nombre del producto ( 'FIN' para terminar) = ");
            String nombreProducto=scanner.nextLine();

            if (nombreProducto.equalsIgnoreCase("FIN"))
                break;
            boolean encontrado=false;
            for(Producto p:carta){
                if(p.getNombre().equalsIgnoreCase(nombreProducto)){
                    productosSeleccionados.add(p);
                    encontrado=true;
                    break;
                }
            }
            if(!encontrado){
                System.out.println("Producto no encontrado. Intente nuevamente.");
            }
        }
        if (productosSeleccionados.isEmpty()){
            System.out.println("No se agrego ningun producto al pedido.");
            return;
        }
        Pedido pedido = restaurante.realizarPedido(numeroMesa,productosSeleccionados);
        System.out.println("Pedido #" + pedido.getId() + " realizado.");
    }
    /**
     * Muestra todos los pedidos activos en consola.
     * param restaurante Instancia de RestauranteUseCase
     */
    public static void verPedidosActivos(RestauranteUseCase restaurante) {
        List<Pedido>pedidos=restaurante.verPedidosActivos();
        if(pedidos.isEmpty()){
            System.out.println("No hay pedidos activos.");
            return;
        }
        System.out.println("\n=== PEDIDOS ACTIVOS ===");
        for(Pedido p:pedidos){
            System.out.println("ID= " + p.getId() + " | Mesa= " + p.getNumeroMesa() + " | Total= " + p.getTotal());
        }

        /**
         * Permite cerrar un pedido desde consola.
         * param scanner Objeto Scanner para entrada
         * param restaurante Instancia de RestauranteUseCase
         */
    }
    public static void cerrarPedido(Scanner scanner, RestauranteUseCase restaurante) {
        System.out.println("Ingrese el ID del pedido a cerrar= ");
        int idPedido=scanner.nextInt();
        System.out.println("Ingrese el porcentaje de descuento (0-10%)= ");
        double descuento=scanner.nextDouble();
        scanner.nextLine();

        if (restaurante.cerrarPedido(idPedido,descuento)) {
            Pedido pedidoCerrado = restaurante.verPedidosActivos().stream()
                    .filter(p -> p.getId() == idPedido)
                    .findFirst()
                    .orElse(null);

            if (pedidoCerrado != null) {
                System.out.println("Pedido #" + idPedido + " cerrado.");
                System.out.println("Descuento aplicado: $" + pedidoCerrado.getDescuento());
                System.out.println("Total a pagar: $" + pedidoCerrado.getTotal());
            } else {
                System.out.println("Pedido cerrado, pero no se pudo mostrar la información.");
            }
        } else {
            System.out.println("Verifique ID del pedido, no se pudo cerrar.");
        }
/**
 * Permite cancelar un pedido desde consola.
 * param scanner Objeto Scanner para entrada
 * param restaurante Instancia de RestauranteUseCase
 */
    }
    public static void cancelarPedido(Scanner scanner, RestauranteUseCase restaurante) {
        System.out.println("Ingrese el ID del pedido a cancelar= ");
        int idPedido=scanner.nextInt();
        scanner.nextLine();

        if (restaurante.cancelarPedido(idPedido)){
            System.out.println("Pedido #" + idPedido + " cancelado.");
        }else{
            System.out.println("Verifique el ID del pedido o si ya fue entregado, pues no se pudo cancelar. ");
        }
    }
}


