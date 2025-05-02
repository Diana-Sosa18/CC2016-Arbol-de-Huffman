import java.io.*;

public class HuffmanDecompressor {
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

    public String readCompressed(String path) throws IOException {
        BitSetInputStream in = new BitSetInputStream(path);
        return in.readAllBits();
    }
}

