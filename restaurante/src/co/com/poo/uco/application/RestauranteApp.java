package co.com.poo.uco.application;

import co.com.poo.uco.model.Pedido;
import co.com.poo.uco.model.Producto;
import co.com.poo.uco.service.Carta.CartaRepository;
import co.com.poo.uco.service.Pedido.PedidoRepository;
import co.com.poo.uco.service.Carta.InFileCartaManager;
import co.com.poo.uco.service.Pedido.InMemoryPedidoManager;
import co.com.poo.uco.usecase.RestauranteUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestauranteApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CartaRepository cartaRepository=new InFileCartaManager();//USECASE?
        PedidoRepository pedidoRepository=new InMemoryPedidoManager();
        RestauranteUseCase restaurante=new RestauranteUseCase(cartaRepository, pedidoRepository);

        while(true){
            System.out.println("\n=== GESTION DE PEDIDOS ===");
            System.out.println("1. Ver carta");
            System.out.println("2. Realizar un pedido");
            System.out.println("3. Ver pedidos activos");
            System.out.println("4. Cerrar un pedido");
            System.out.println("5. Cancelar un pedido");
            System.out.println("6. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch(opcion){
                case 1:
                    verCarta(restaurante);
                    break;
                case 2:
                    realizarPedido(scanner,restaurante);
                    break;
                case 3:
                    verPedidosActivos(restaurante);
                    break;
                case 4:
                    cerrarPedido(scanner,restaurante);
                    break;
                case 5:
                    cancelarPedido(scanner,restaurante);
                    break;
                case 6:
                    System.out.println("Saliendo del sistema..");
                    return;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente");
            }

        }
    }
    private static void verCarta(RestauranteUseCase restaurante) {
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
    private static void realizarPedido(Scanner scanner, RestauranteUseCase restaurante) {
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
    private static void verPedidosActivos(RestauranteUseCase restaurante) {
        List<Pedido>pedidos=restaurante.verPedidosActivos();
        if(pedidos.isEmpty()){
            System.out.println("No hay pedidos activos.");
            return;
        }
        System.out.println("\n=== PEDIDOS ACTIVOS ===");
        for(Pedido p:pedidos){
            System.out.println("ID= " + p.getId() + " | Mesa= " + p.getNumeroMesa() + " | Total= " + p.getTotal());
        }
    }
    private static void cerrarPedido(Scanner scanner, RestauranteUseCase restaurante) {
        System.out.println("Ingrese el ID del pedido a cerrar= ");
        int idPedido=scanner.nextInt();
        System.out.println("Ingrese el porcentaje de descuento (0-10%)= ");
        double descuento=scanner.nextDouble();
        scanner.nextLine();

        if (restaurante.cerrarPedido(idPedido,descuento)){
            System.out.println("Pedido #" + idPedido + " cerrado.");
        }else{
            System.out.println("Verifique ID pedido, no se pudo cerrar.");
        }
    }
    private static void cancelarPedido(Scanner scanner, RestauranteUseCase restaurante) {
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
