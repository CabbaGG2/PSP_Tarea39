

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public class PasswordCracker implements Callable<String> {
    private final char inicioRango;
    private final char finRango;
    private final String hashObjetivo;
    private final String rutaFichero;
    private final AtomicBoolean passwordEncontrada;

    public PasswordCracker(char inicioRango, char finRango, String hashObjetivo, String rutaFichero, AtomicBoolean passwordEncontrada) {
        this.inicioRango = Character.toLowerCase(inicioRango);
        this.finRango = Character.toLowerCase(finRango);
        this.hashObjetivo = hashObjetivo;
        this.rutaFichero = rutaFichero;
        this.passwordEncontrada = passwordEncontrada;
    }

    @Override
    public String call() {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (passwordEncontrada.get()) {
                    return null;
                }
                if (!linea.isEmpty()) {
                    char primeraLetra = Character.toLowerCase(linea.charAt(0));
                    if (primeraLetra >= inicioRango && primeraLetra <= finRango) {
                        String hashLinea = SecuritySuite.generarHash(linea);
                        if (hashLinea.equals(hashObjetivo)) {
                            if (passwordEncontrada.compareAndSet(false, true)) {
                                System.out.println("Hilo " + Thread.currentThread().getName() + " encontró la contraseña.");
                                return linea;
                            }
                            return null;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero en el hilo " + Thread.currentThread().getName() + ": " + e.getMessage());
        }
        return null;
    }
}
