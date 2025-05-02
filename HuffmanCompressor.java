import java.util.*;
import java.io.*;

/**
 * Implementación de un compresor de archivos utilizando el algoritmo de Huffman.
 * Esta clase permite comprimir texto utilizando codificación de Huffman, que asigna
 * códigos binarios más cortos a los caracteres más frecuentes y códigos más largos
 * a los menos frecuentes para lograr compresión de datos.
 */
public class HuffmanCompressor {
    /** Almacena la correspondencia entre caracteres y sus códigos Huffman. */
    private Map<Character, String> huffmanCodes = new HashMap<>();
    
    /** La raíz del árbol de Huffman construido. */
    private HuffmanNode root;

    /**
     * Construye el árbol de Huffman basado en la frecuencia de caracteres en los datos proporcionados
     * y genera los códigos para cada carácter.
     *
     * @param data El texto original a partir del cual se construirá el árbol de Huffman.
     */
    public void buildTree(String data) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : data.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            queue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            HuffmanNode parent = new HuffmanNode('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            queue.add(parent);
        }

        root = queue.poll();
        generateCodes(root, "");
    }

    /**
     * Genera los códigos Huffman para cada carácter mediante un recorrido recursivo del árbol.
     * Este método es privado y es llamado por buildTree.
     *
     * @param node El nodo actual en el recorrido del árbol.
     * @param code El código acumulado hasta el nodo actual.
     */
    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;
        if (node.isLeaf()) {
            huffmanCodes.put(node.character, code);
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    /**
     * Comprime una cadena de texto utilizando los códigos Huffman generados previamente.
     * El método buildTree debe ser llamado antes de usar este método.
     *
     * @param data La cadena de texto a comprimir.
     * @return Una cadena de '0's y '1's que representa el texto comprimido.
     */
    public String compress(String data) {
        StringBuilder encoded = new StringBuilder();
        for (char c : data.toCharArray()) {
            encoded.append(huffmanCodes.get(c));
        }
        return encoded.toString();
    }

    /**
     * Guarda los datos comprimidos en un archivo, escribiendo los bits individuales.
     * Utiliza un BitSetOutputStream para escribir los datos bit a bit.
     *
     * @param encoded La cadena binaria comprimida (secuencia de '0's y '1's).
     * @param path La ruta del archivo donde se guardarán los datos comprimidos.
     * @throws IOException Si ocurre un error de entrada/salida durante la escritura.
     */
    public void saveCompressed(String encoded, String path) throws IOException {
        try (BitSetOutputStream out = new BitSetOutputStream(path)) {
            for (char bit : encoded.toCharArray()) {
                out.writeBit(bit == '1');
            }
        }
    }

    /**
     * Obtiene la raíz del árbol de Huffman construido.
     *
     * @return El nodo raíz del árbol de Huffman.
     */
    public HuffmanNode getTreeRoot() {
        return root;
    }

    /**
     * Obtiene el mapa de códigos Huffman generados.
     *
     * @return Un mapa que asocia cada carácter con su código Huffman correspondiente.
     */
    public Map<Character, String> getCodes() {
        return huffmanCodes;
    }
}
