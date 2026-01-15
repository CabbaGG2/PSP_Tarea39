import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.stream.Collectors;

public class SecuritySuite {

    // --- AQUI USAMOS JACKSON ---
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

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

        for(int i = 1; i <= 26 ; i++) {
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

            String primeraPalabra = resultado.toString().split(" ")[0];

            boolean esPalabraReal = esPalabra(primeraPalabra); // Aquí se llamaría a la API para verificar

            if(esPalabraReal) {
                System.out.println("¡Palabra válida encontrada con desplazamiento " + desplazamiento + "! " + resultado.toString());
            } else{
                System.out.println("FALLO con desplazamiento " + desplazamiento + ": " + resultado.toString());
            }

            try { Thread.sleep(200); } catch (InterruptedException e) {}
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

    public static boolean esPalabra(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) return false;

        // filtramos si la palabra no tiene vocales (optimización local)
        // así no tenemos que realizar una consulta en línea innecesaria
        if (!palabra.toLowerCase().matches(".*[aeiouáéíóúü].*")) {
            return false;
        }

        try {
            Map<String, String> params = new HashMap<>();
            params.put("text", palabra);
            params.put("language", "es");
            params.put("enabledOnly", "false");

            String formBody = params.entrySet().stream()
                    .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                            URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.languagetool.org/v2/check"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(formBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // --- AQUI USAMOS JACKSON ---
            // 1. Convertimos el String JSON a un árbol de nodos
            JsonNode rootNode = mapper.readTree(response.body());

            // 2. Obtenemos el array "matches"
            JsonNode matches = rootNode.get("matches");

            // 3. Si el array está vacío, significa que NO hay errores -> Palabra válida
            return matches.isEmpty();

        } catch (Exception e) {
            System.out.println("Error verificando palabra: " + e.getMessage());
            return false;
        }
    }
}
