import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Huffman {
    public static void main(String[] args) {
        String inputPath = "green-eggs.txt";
        String compressedPath = "compressed.txt";
        String decompressedPath = "decompressed.txt";

        try {
            // Leer archivo original
            String originalText = new String(Files.readAllBytes(Paths.get(inputPath)));

            // Crear compresor
            HuffmanCompressor compressor = new HuffmanCompressor();
            compressor.buildTree(originalText);
            String compressedBits = compressor.compress(originalText);

            // Guardar como texto (puedes usar BitSetOutputStream para binario real)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(compressedPath))) {
                writer.write(compressedBits);
            }

            // Leer bits comprimidos
            String bitsFromFile = new String(Files.readAllBytes(Paths.get(compressedPath)));

            // Descomprimir
            HuffmanDecompressor decompressor = new HuffmanDecompressor();
            String restoredText = decompressor.decompress(compressor.getTreeRoot(), bitsFromFile);

            // Guardar archivo descomprimido
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(decompressedPath))) {
                writer.write(restoredText);
            }

            // Verificar si es igual al original
            if (originalText.equals(restoredText)) {
                System.out.println("✅ El archivo fue comprimido y descomprimido correctamente.");
            } else {
                System.out.println("❌ Error: el archivo descomprimido no coincide con el original.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
