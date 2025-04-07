package co.com.poo.uco.service.Carta;

import co.com.poo.uco.model.Producto;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementación de {link CartaRepository} que carga los productos desde un archivo de texto.
 * El archivo debe estar ubicado en la ruta especificada dentro de los recursos del proyecto.
 */
public class InFileCartaManager implements CartaRepository {
    private List<Producto> carta = new ArrayList<>();
    private static final String FILE_PATH = "co/com/poo/uco/resources/carta.txt";

    public InFileCartaManager() {
        loadCarta();
    }

    /**
     * Método privado que lee el archivo de carta y carga los productos en la lista.
     * Se espera que el archivo tenga un encabezado en la primera línea y datos separados por comas.
     * Formato esperado por línea: nombre,precio,categoria
     */
    private void loadCarta() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length == 3) {
                    carta.add(new Producto(
                            data[0].trim(),                          // Nombre
                            Double.parseDouble(data[1].trim()),      // Precio
                            data[2].trim()                           // Categoría
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer el archivo de carta=" + e.getMessage());
        }
    }

    @Override
    public List<Producto>cargarCarta(){
        return carta;
    }
}