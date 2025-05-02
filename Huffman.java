import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Clase principal para la compresión y descompresión de archivos utilizando el algoritmo de Huffman.
 * <p>
 * Esta clase proporciona un programa principal que demuestra el proceso de compresión y descompresión
 * mediante el algoritmo de Huffman. El programa lee un archivo de texto original, lo comprime
 * guardando el resultado en un archivo, luego lo descomprime y compara si el archivo restaurado
 * es idéntico al original.
 * </p>
 * <p>
 * El algoritmo de Huffman es una técnica de compresión de datos sin pérdida que asigna códigos de longitud
 * variable a diferentes caracteres, asignando códigos más cortos a los caracteres más frecuentes y códigos
 * más largos a los menos frecuentes, optimizando así el tamaño del archivo comprimido.
 * </p>
 * 
 * @author Diana Sosa
 */
public class Huffman {
    
    /**
     * Método principal que ejecuta la demostración del algoritmo de Huffman.
     * <p>
     * El programa realiza los siguientes pasos:
     * <ol>
     *   <li>Lee y muestra parte del contenido del archivo original</li>
     *   <li>Comprime el archivo utilizando el algoritmo de Huffman</li>
     *   <li>Descomprime el archivo comprimido</li>
     *   <li>Valida que el archivo original y el descomprimido sean idénticos</li>
     * </ol>
     * </p>
     * 
     * @param args Argumentos de línea de comandos (no utilizados en esta implementación)
     */
    public static void main(String[] args) {
        // Rutas de los archivos a utilizar
        String inputPath = "green-eggs.txt";
        String compressedPath = "compressed.txt";
        String decompressedPath = "decompressed.txt";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Paso 1: Mostrar archivo original
            System.out.println("=== PASO 1: LEER ARCHIVO ORIGINAL ===");
            String originalText = new String(Files.readAllBytes(Paths.get(inputPath)));
            System.out.println("Contenido original:");
            System.out.println("----------------------------------");
            System.out.println(originalText.substring(0, Math.min(originalText.length(), 500))); // muestra parte
            System.out.println("... (Contenido truncado)");
            System.out.println("----------------------------------");
            System.out.print("Presiona ENTER para continuar con la compresión...");
            reader.readLine();

            // Paso 2: Comprimir
            System.out.println("\n=== PASO 2: COMPRIMIENDO CON HUFFMAN ===");
            HuffmanCompressor compressor = new HuffmanCompressor();
            compressor.buildTree(originalText);
            String compressedBits = compressor.compress(originalText);
            Files.write(Paths.get(compressedPath), compressedBits.getBytes());
            System.out.println("Archivo comprimido guardado como: " + compressedPath);
            System.out.println("Tamaño del archivo original: " + originalText.length() + " caracteres");
            System.out.println("Tamaño del archivo comprimido: " + compressedBits.length() + " bits (como texto)");
            System.out.print("Presiona ENTER para continuar con la descompresión...");
            reader.readLine();

            // Paso 3: Descomprimir
            System.out.println("\n=== PASO 3: DESCOMPRIMIENDO ===");
            String bitsFromFile = new String(Files.readAllBytes(Paths.get(compressedPath)));
            HuffmanDecompressor decompressor = new HuffmanDecompressor();
            String restoredText = decompressor.decompress(compressor.getTreeRoot(), bitsFromFile);
            Files.write(Paths.get(decompressedPath), restoredText.getBytes());
            System.out.println("Archivo descomprimido guardado como: " + decompressedPath);

            // Validación
            System.out.println("\n=== VALIDACIÓN FINAL ===");
            if (originalText.equals(restoredText)) {
                System.out.println("✅ El archivo descomprimido es idéntico al original.");
            } else {
                System.out.println("❌ El archivo descomprimido es diferente al original.");
            }

        } catch (IOException e) {
            /**
             * Maneja las excepciones de entrada/salida que puedan ocurrir durante
             * la lectura o escritura de archivos.
             */
            e.printStackTrace();
        }
    }
}