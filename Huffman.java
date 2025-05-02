import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Huffman {
    public static void main(String[] args) {
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
            e.printStackTrace();
        }
    }
}

