package co.com.poo.uco.service.Carta;

import co.com.poo.uco.model.Producto;
import co.com.poo.uco.service.Carta.CartaRepository;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InFileCartaManager implements CartaRepository {
    private List<Producto> carta = new ArrayList<>();
    private static final String FILE_PATH = "carta.txt";

    public InFileCartaManager() {
        loadCarta();
    }


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
                            data[0].trim(),
                    Double.parseDouble(data[1].trim()),
                    data[2].trim()
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