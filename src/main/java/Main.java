import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String opcion = ""; // Inicializamos la variable vacía

        // El bucle "do" inicia aquí
        do {
            System.out.println("\nAUDITOR DE CONTRASEÑAS");
            System.out.println("=======================");
            System.out.println("Elija una opción (1-3) o 'salir' para cerrar la app:");
            System.out.println("1. Auditoría de Hash contrastado con fichero.");
            System.out.println("2. Decodificador CESAR con API REST.");
            System.out.println("3. Fuerza bruta en paralelo con multihilos.");
            System.out.print(">> "); // Un indicador visual para escribir

            opcion = sc.nextLine(); // Leemos la opción dentro del bucle

            switch (opcion) {
                case "1":
                    // Asumiendo que esta clase y método existen
                    SecuritySuite.auditoriaHash();
                    System.out.println("[!] Ejecutando Auditoría de Hash...");
                    break;
                case "2":
                    // SecuritySuite.decodificadorCesar();
                    System.out.println("[!] Ejecutando Decodificador Cesar...");
                    break;
                case "3":
                    // SecuritySuite.fuerzaBrutaMultihilos();
                    System.out.println("[!] Ejecutando Fuerza Bruta...");
                    break;
                case "salir":
                    System.out.println("Cerrando la aplicación. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija entre 1 y 3.");
            }

            // El bucle se repite MIENTRAS la opción sea distinta de "salir"
        } while (!opcion.equals("salir"));

        sc.close(); // Buena práctica: cerrar el scanner al final
    }
}
