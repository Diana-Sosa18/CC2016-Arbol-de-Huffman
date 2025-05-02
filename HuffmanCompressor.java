import java.util.*;
import java.io.*;

public class HuffmanCompressor {
    private Map<Character, String> huffmanCodes = new HashMap<>();
    private HuffmanNode root;

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

    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;
        if (node.isLeaf()) {
            huffmanCodes.put(node.character, code);
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    public String compress(String data) {
        StringBuilder encoded = new StringBuilder();
        for (char c : data.toCharArray()) {
            encoded.append(huffmanCodes.get(c));
        }
        return encoded.toString();
    }

    public void saveCompressed(String encoded, String path) throws IOException {
        try (BitSetOutputStream out = new BitSetOutputStream(path)) {
            for (char bit : encoded.toCharArray()) {
                out.writeBit(bit == '1');
            }
        }
    }

    public HuffmanNode getTreeRoot() {
        return root;
    }

    public Map<Character, String> getCodes() {
        return huffmanCodes;
    }
}
