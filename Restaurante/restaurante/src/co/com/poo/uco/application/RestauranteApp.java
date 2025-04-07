package co.com.poo.uco.application;

import co.com.poo.uco.service.Carta.CartaRepository;
import co.com.poo.uco.service.Pedido.PedidoRepository;
import co.com.poo.uco.service.Carta.InFileCartaManager;
import co.com.poo.uco.service.Pedido.InMemoryPedidoManager;
import co.com.poo.uco.usecase.RestauranteUseCase;

import java.util.Scanner;

import static co.com.poo.uco.usecase.RestauranteUseCase.*;

public class RestauranteApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CartaRepository cartaRepository = new InFileCartaManager();
        PedidoRepository pedidoRepository = new InMemoryPedidoManager();
        RestauranteUseCase restaurante = new RestauranteUseCase(cartaRepository, pedidoRepository);

        while (true) {
            System.out.println("\n=== GESTION DE PEDIDOS ===");
            System.out.println("1. Ver carta");
            System.out.println("2. Realizar un pedido");
            System.out.println("3. Ver pedidos activos");
            System.out.println("4. Cerrar un pedido");
            System.out.println("5. Cancelar un pedido");
            System.out.println("6. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    verCarta(restaurante);
                    break;
                case 2:
                    realizarPedido(scanner, restaurante);
                    break;
                case 3:
                    verPedidosActivos(restaurante);
                    break;
                case 4:
                    cerrarPedido(scanner, restaurante);
                    break;
                case 5:
                    cancelarPedido(scanner, restaurante);
                    break;
                case 6:
                    System.out.println("Saliendo del sistema..");
                    return;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente");
            }

        }
    }
}
