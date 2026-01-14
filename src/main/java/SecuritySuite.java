import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class SecuritySuite {

    public static void auditoriaHash() {
        // Implementación de la auditoría de hash
        System.out.println("Auditoría de Hash iniciada...");
        // Aquí iría el código para auditar hashes contra un fichero
        String hashObjetivo = "4a630b8e79a0cd2fbae3f58e751abb28d0f4918f76af188d8996f13fabe08af8";
        String rutaFichero = "diccionario.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
            String linea;
            boolean encontrado = false;
            while ((linea = br.readLine()) != null) {
                String hashLinea = generarHash(linea);
                if (hashLinea.equals(hashObjetivo)) {
                    System.out.println(hashLinea);
                    System.out.println("¡CONTRASEÑA ENCONTRADA! La clave es: " + linea);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Contraseña no encontrada en el fichero.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    public static void decodificadorCesar() {
        StringBuilder resultado = new StringBuilder();
        int desplazamiento;

        for(int i = 1; i <= 27 ; i++) {
            desplazamiento = i;
            String texto = "KRÑD OXPGR"; // Texto cifrado de ejemplo
            resultado.setLength(0); // Limpiar el StringBuilder para cada intento
            for (int j = 0; j < texto.length(); j++) {
                char caracter = texto.charAt(j);

                if (Character.isLetter(caracter)) {
                    char base = Character.isLowerCase(caracter) ? 'a' : 'A';
                    char descifrado = (char) ((caracter - base - desplazamiento + 26) % 26 + base);
                    resultado.append(descifrado);
                } else {
                    resultado.append(caracter);
                }
            }
            System.out.println("Desplazamiento " + desplazamiento + ": " + resultado.toString());
        }
    }

    public static String generarHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: Algoritmo no encontrado." + e.getMessage());
        }

        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] resumen = md.digest();
        return HexFormat.of().formatHex(resumen);
    }
}
