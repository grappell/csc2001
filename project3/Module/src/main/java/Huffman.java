import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project 3: Huffman Encoding / Decoding.
 *
 * Implement the methods below to compress and decompress text files using
 * Huffman coding. See the assignment write-up for the full specification.
 *
 *  - Treat characters as 8-bit values in the range [0, 255].
 *  - When writing output files, write the newline character explicitly as
 *    '\n' (do not rely on System.lineSeparator() / println()), so your
 *    output is identical across Windows/Mac/Linux. A BufferedWriter over
 *    a FileWriter, writing "\n" yourself, works well for this.
 *  - Encoded files store the code as literal '0'/'1' characters (one byte
 *    each), not as packed bits, so the "encoded" file will actually be
 *    larger than the original -- that's expected for this assignment.
 *  - createHuffTree() is built on top of addNode()/removeMin(), which
 *    operate generically over a java.util.Collection<HuffmanNode>.
 *  - HuffmanNode is a record, so its components are read with
 *    node.charAscii(), node.freq(), node.left(), and node.right() -- no
 *    "get" prefix.
 */
public class Huffman {

    /** Number of possible 8-bit character values. */
    public static final int NUM_CHARS = 256;

    /**
     * Opens the text file named filename and counts the frequency of
     * occurrence of every character in it.
     * Returns an int array of size NUM_CHARS, indexed by ASCII value, with
     * the count of each character (0 for characters that do not occur).
     */
    public static int[] cntFreq(String filename) throws IOException {

        int[] freq = new int[NUM_CHARS];

        var path = Path.of(filename);

        Files.readString(path).chars().forEach(c -> freq[c]++);

//        var collected = Files.readString(path).chars()
//                .mapToObj(c -> (char) c)
//                .collect(Collectors.groupingBy(c -> c, Collectors.summingInt(e -> 1)));
//
//        collected.forEach((ele, count) -> System.out.println("Char: " + ele + " - " + count));
//        collected.forEach((ele, count) -> freq[ele] = count);

        return freq;
    }

    /**
     * Adds node to collection, preserving whatever ordering your chosen
     * implementation of collection relies on to make the minimum element
     * easy to find.
     *
     * You choose what kind of Collection to pass in when you call this
     * from createHuffTree(). For example, an ArrayList kept sorted by
     * HuffmanNode.comesBefore() as you insert (an "ordered list"), or a
     * PriorityQueue. How you implement this method depends on which you pick.
     */
    public static void addNode(Collection<HuffmanNode> collection, HuffmanNode node) {
        collection.add(node);
    }

    /**
     * Removes and returns the smallest HuffmanNode in collection, using
     * HuffmanNode.comesBefore() (or compareTo(), which relies on it) to
     * determine "smallest". Returns null if collection is empty.
     *
     * This must work for ANY Collection passed in -- not just the one you
     * use inside createHuffTree(). For example, if you back createHuffTree()
     * with a PriorityQueue, poll() it; for any other collection, find the
     * minimum node and remove it.
     */
    public static HuffmanNode removeMin(Collection<HuffmanNode> collection) {
        if(collection instanceof PriorityQueue<HuffmanNode> pq) return pq.poll();
        HuffmanNode min = collection.stream().min(HuffmanNode::compareTo).orElse(null);
        if(min != null) collection.remove(min);
        return min;
    }

    /**
     * Builds a Huffman tree from an array of character frequencies (as
     * produced by cntFreq()) and returns the root HuffmanNode of the tree.
     *
     * Use addNode()/removeMin() above to manage the collection of
     * not-yet-combined nodes as you build the tree -- pick whichever
     * concrete Collection type you want to back them with (e.g. a sorted
     * ArrayList, or a PriorityQueue). The rest of this method, and the
     * resulting tree, should not depend on which one you chose.
     *
     * Special cases:
     *  - If freq contains no non-zero counts (empty input file), return null
     *  - If freq contains exactly one non-zero count (input file has only
     *    one unique character), return a single leaf HuffmanNode for that
     *    character (no combine() call needed).
     */
    public static HuffmanNode createHuffTree(int[] freq) {

        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

        for(int i = 0; i < freq.length; i++) {
            if(freq[i] == 0) continue;
            addNode(queue, new HuffmanNode(i, freq[i]));
        }

        while(queue.size() > 1) queue.add(HuffmanNode.combine(removeMin(queue), removeMin(queue)));
        return queue.poll();
    }

    /**
     * Traverses the Huffman tree rooted at root and builds the Huffman
     * code for every character that appears in it. Going left appends
     * '0' to the code, going right appends '1'.
     *
     * Returns a String array of size NUM_CHARS, indexed by ASCII value.
     * Characters that do not appear in the tree have an empty string ""
     * at their index. The same is true of the lone character in a
     * single-node tree (the only-one-unique-character case) - it has no
     * left/right branch to build a code out of; huffmanEncode/huffmanDecode
     * handle that case entirely through the header.
     */
    public static String[] createCode(HuffmanNode root) {
        String[] codes = new String[NUM_CHARS];
        Arrays.fill(codes, "");
        Map<Integer, String> charMap = new HashMap<>();
        traverseAndBuild(root, charMap, "");
        // charMap.forEach((e, c) -> System.out.println(e + ": " + c)); // Debugging
        charMap.forEach((e, c) -> codes[e] = c);
        return codes;
    }

    /**
     * Preorder traverse of the tree
     */
    private static void traverseAndBuild(HuffmanNode root, Map<Integer, String> ans, String current) {
        if(root == null) return;
        if(root.isLeaf()) {
            if(current.isEmpty()) current = "0";
            ans.put(root.charAscii(), current);
            return;
        }
        traverseAndBuild(root.left(), ans, current + "0");
        traverseAndBuild(root.right(), ans, current + "1");
    }

    /**
     * Given a frequency array (as produced by cntFreq()), returns a
     * header string listing the ASCII values and their associated
     * frequencies, separated by single spaces, in ascending order of
     * ASCII value. Only characters with a non-zero count are included.
     *
     * Example: for the text "aaabbbbcc" this returns "97 3 98 4 99 2".
     */
    public static String createHeader(int[] freq) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < freq.length; i++) {
            if(freq[i] == 0) continue;
            sb.append(i).append(" ").append(freq[i]).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Reads the input text file inFile and writes to outFile:
     *   1. A header line (see createHeader), followed by a newline
     *   2. The Huffman-encoded contents of inFile, using the codes from
     *      createCode() -- UNLESS inFile has only one unique character,
     *      see below.
     * If outFile already exists, its old contents are overwritten.
     *
     * Special cases:
     *  - Empty input file: outFile should end up empty too
     *  - Input file with only one unique character: should contain only the
     *    header line (e.g. "97 5") and nothing else - no second line of code bits.
     */
    public static void huffmanEncode(String inFile, String outFile) throws IOException {

        Path in = Path.of(inFile);
        Path out = Path.of(outFile);

        if(!Files.isRegularFile(in)) throw new RuntimeException("Invalid File Provided");

        // Empty file special case
        if(Files.readString(in).isEmpty()) {
            Files.write(out, new byte[]{}, StandardOpenOption.CREATE);
            return;
        }

        int[] freq = cntFreq(inFile);
        String header = createHeader(freq);
        HuffmanNode tree = createHuffTree(freq);
        String[] code = createCode(tree);

        try (var writer = Files.newBufferedWriter(out, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {

            writer.write(header);

            // Only write header for 1 char frequency tables, witch are formatted ASCII FREQ. Append newline if writing encoding
            if(tree.isLeaf()) return;
            else writer.write("\n");

            // Read in all characters
            for(var c: Files.readString(in).chars().toArray()) {
                writer.append(code[c]);
            }
        }

    }

    /**
     * Parses a header string (in the format produced by createHeader) and
     * returns the corresponding frequency array of size NUM_CHARS, indexed
     * by ASCII value, i.e. the same format returned by cntFreq().
     *
     * Example: for the header "97 3 98 4 99 2", freq[97, 98, 99] == {3, 4, 2}.
     */
    public static int[] parseHeader(String headerString) {
        int[] freq = new int[NUM_CHARS];
        var split = Arrays.stream(headerString.split(" ")).mapToInt(Integer::parseInt).toArray();
        for(int i = 0; i < split.length; i += 2) freq[split[i]] = split[i + 1];
        return freq;
    }

    /**
     * Reads an encoded file (in the format written by huffmanEncode) and
     * writes the decoded text to decodeFile.
     *
     * Recreate the frequency array from the header with parseHeader(),
     * rebuild the Huffman tree with createHuffTree(), then walk the tree
     * for each bit of the second line: '0' -> left child, '1' -> right
     * child. When a leaf is reached, emit its character and restart at
     * the root.
     *
     * Special case: if the recreated tree is a single leaf (the
     * only-one-unique-character case), there is no second line to read
     * The header's count alone tells you how many times that character occurred,
     * so write it out that many times directly.
     */
    public static void huffmanDecode(String encodedFile, String decodeFile) throws IOException {

        Path in = Path.of(encodedFile);
        Path out = Path.of(decodeFile);
        if(!Files.isRegularFile(in)) throw new RuntimeException("Invalid File Provided");

        if(Files.readString(in).isEmpty()) {
            Files.write(out, new byte[]{}, StandardOpenOption.CREATE);
            return;
        }

        String[] lines = Files.readAllLines(in).toArray(String[]::new);
        var freq = parseHeader(lines[0]);
        var bits = lines.length > 1 ? lines[1] : "";
        var tree = createHuffTree(freq);

        createCode(tree);

        StringBuilder bldr = new StringBuilder();
        var current = tree;

        if(current.isLeaf()) {
            int count = freq[current.charAscii()];
            bldr.repeat(current.charAscii(), count);
        }

        for(int i = 0; i < bits.length(); i++) {

            if(bits.charAt(i) == '0') current = current.left();
            else current = current.right();

            if(current.isLeaf()) {
                bldr.append((char) current.charAscii());
                current = tree;
            }

        }

        try (var writer = Files.newBufferedWriter(out, StandardOpenOption.CREATE)) {
            writer.write(bldr.toString());
            writer.flush();
        }
    }
}
