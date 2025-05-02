/**
 * Clase que representa un nodo en un árbol de Huffman.
 * Los nodos contienen un carácter, su frecuencia asociada y referencias a sus nodos hijos.
 * Esta clase implementa la interfaz Comparable para permitir la ordenación de nodos
 * basada en su frecuencia, lo que es esencial para la construcción del árbol de Huffman.
 * 
 * @author Diana Sosa
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
    /** El carácter almacenado en este nodo. */
    char character;
    
    /** La frecuencia de aparición del carácter en el texto original. */
    int frequency;
    
    /** Referencia al hijo izquierdo en el árbol binario. */
    HuffmanNode left;
    
    /** Referencia al hijo derecho en el árbol binario. */
    HuffmanNode right;

    /**
     * Constructor que crea un nuevo nodo de Huffman con un carácter y su frecuencia.
     * 
     * @param character El carácter a almacenar en este nodo
     * @param frequency La frecuencia del carácter en el texto original
     */
    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    /**
     * Compara este nodo con otro nodo basándose en su frecuencia.
     * Este método es necesario para implementar la interfaz Comparable,
     * permitiendo ordenar los nodos en una cola de prioridad durante
     * la construcción del árbol de Huffman.
     * 
     * @param o El nodo con el que comparar
     * @return Un valor negativo si este nodo tiene menor frecuencia,
     *         cero si tienen la misma frecuencia, o
     *         un valor positivo si este nodo tiene mayor frecuencia
     */
    @Override
    public int compareTo(HuffmanNode o) {
        return this.frequency - o.frequency;
    }

    /**
     * Determina si este nodo es una hoja en el árbol de Huffman.
     * Un nodo es una hoja si no tiene hijos (ni izquierdo ni derecho).
     * En un árbol de Huffman, solo los nodos hoja contienen caracteres
     * del texto original.
     * 
     * @return true si este nodo es una hoja (no tiene hijos), false en caso contrario
     */
    public boolean isLeaf() {
        return (this.left == null && this.right == null);
    }
}

