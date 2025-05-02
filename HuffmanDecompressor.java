import java.io.*;

/**
 * Clase que implementa la descompresión de datos utilizando el algoritmo de Huffman.
 * Esta clase permite descomprimir cadenas de bits codificadas con Huffman
 * a partir de un árbol de Huffman y leer datos comprimidos desde archivos.
 * 
 * @author Diana Sosa
 */
public class HuffmanDecompressor {
    
    /**
     * Descomprime una cadena de bits utilizando un árbol de Huffman.
     * 
     * Este método recorre la cadena de bits y navega por el árbol de Huffman
     * siguiendo las instrucciones: '0' para ir a la izquierda y '1' para ir a la derecha.
     * Cuando alcanza una hoja del árbol, añade el carácter correspondiente al resultado
     * y vuelve a la raíz del árbol para seguir con el siguiente bit.
     * 
     * @param root El nodo raíz del árbol de Huffman usado para la descompresión
     * @param bits La cadena de bits a descomprimir
     * @return Una cadena que contiene el texto descomprimido
     */
    public String decompress(HuffmanNode root, String bits) {
        StringBuilder result = new StringBuilder();
        HuffmanNode current = root;

        for (char bit : bits.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;

            if (current.isLeaf()) {
                result.append(current.character);
                current = root;
            }
        }
        return result.toString();
    }

    /**
     * Lee datos comprimidos desde un archivo en formato de bits.
     * 
     * Este método utiliza la clase BitSetInputStream para leer todos los bits
     * almacenados en el archivo especificado por la ruta.
     * 
     * @param path La ruta del archivo que contiene los datos comprimidos
     * @return Una cadena que representa todos los bits leídos del archivo
     * @throws IOException Si ocurre un error durante la lectura del archivo
     */
    public String readCompressed(String path) throws IOException {
        BitSetInputStream in = new BitSetInputStream(path);
        return in.readAllBits();
    }
}
