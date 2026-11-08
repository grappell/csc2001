import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Starter test suite for Project 3 (Huffman Encoding/Decoding).
 * Write more tests!
 * These tests assume the test files are in the same directory as HuffmanTest.java
 */
public class HuffmanTest {

    private static final String root = "./src/main/java/text/";

    @Test
    public void testCntFreq() throws IOException {
        int[] freqList = Huffman.cntFreq(root + "file2.txt");
        int[] ansList = {2, 4, 8, 16, 0, 2, 0}; // counts for ASCII 97..103
        for (int i = 0; i < ansList.length; i++) {
            assertEquals(ansList[i], freqList[97 + i], "mismatch at ASCII " + (97 + i));
        }
    }

    @Test
    public void testComesBefore() {
        HuffmanNode d = new HuffmanNode('d', 5);
        HuffmanNode k = new HuffmanNode('k', 5);
        assertTrue(HuffmanNode.comesBefore(d, k));
        assertFalse(HuffmanNode.comesBefore(k, d));
    }

    @Test
    public void testCreateHeader() throws IOException {
        int[] freqList = Huffman.cntFreq(root + "file2.txt");
        assertEquals("97 2 98 4 99 8 100 16 102 2", Huffman.createHeader(freqList));
    }

    private void testEncode(String name) throws IOException{
        Huffman.huffmanEncode(root + name + ".txt", root + name + "_out.txt");
        betterCompare(root + name + "_out.txt", root + name + "_soln.txt");
        assertTrue(compareFiles(root + name + "_out.txt", root + name + "_soln.txt"));
    }

//    @Test
//    public void testAllTextFiles() throws IOException {
//        try(var stream = Files.newDirectoryStream(Path.of(root))) {
//            stream.forEach(e -> {
//                var name = e.toFile().getName();
//                if(name.substring(name.length() - 3).equals(".txt")) {
//                    try {
//                        testEncode(name);
//                        testDecode(name);
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
//            });
//        }
//    }

    @Test
    public void test01Encode() throws IOException {
        testEncode("file1");
    }

    @Test
    public void test02Encode() throws IOException {
        testEncode("file2");
    }

    @Test
    public void test03Encode() throws IOException {
        testEncode("file3");
    }

    @Test
    public void test04Encode() throws IOException {
        testEncode("file4");
    }

    @Test
    public void test05Encode() throws IOException {
        testEncode("file5");
    }

    @Test
    public void test06Encode() throws IOException {
        testEncode("file6");
    }


    @Test
    public void testMultiLineEncode() throws IOException {
        testEncode("multiline");
    }

    private void testDecode(String name) throws IOException {
        Huffman.huffmanDecode(root + name + "_soln.txt", root + name + "_dec.txt");
//        Huffman.huffmanDecode(root + name + "_out.txt", root + name + "_dec.txt");
        assertTrue(compareFiles(root + name + "_dec.txt", root + name + ".txt"));
    }

    @Test
    public void testDecodeFile1() throws IOException {
        testDecode("file1");
    }

    @Test
    public void testDecodeFile2() throws IOException {
        testDecode("file2");
    }

    @Test
    public void testDecodeFile3() throws IOException {
        testDecode("file3");
    }

    @Test
    public void testDecodeFile4() throws IOException {
        testDecode("file4");
    }

    @Test
    public void testDecodeFile5() throws IOException {
        testDecode("file5");
    }

    @Test
    public void testDecodeFile6() throws IOException {
        testDecode("file6");
    }

    @Test
    public void testDecodeFileMultiline() throws IOException {
        testDecode("multiline");
    }

    @Test
    public void testUnreadable() throws IOException {

        var in = Path.of(root + "unreadable.txt");

        makeUnreadable(in);
        assertThrows(AccessDeniedException.class, () -> testEncode("unreadable"));
        makeReadable(in);

        Huffman.huffmanEncode(root + "unreadable.txt", root + "unreadable_out.txt");
        var out = Path.of(root + "unreadable_out.txt");

        makeUnreadable(out);
        assertThrows(AccessDeniedException.class, () -> testDecode("unreadable"));
        makeReadable(out);

    }

    private static void makeUnreadable(Path p) throws IOException {
        FileStore store = Files.getFileStore(p);
        if (store.supportsFileAttributeView(AclFileAttributeView.class)) {
            AclFileAttributeView view = Files.getFileAttributeView(p, AclFileAttributeView.class);
            view.setAcl(Collections.emptyList());
        } else if (store.supportsFileAttributeView(PosixFileAttributeView.class)) {
            Files.setPosixFilePermissions(p, EnumSet.complementOf(EnumSet.of(PosixFilePermission.GROUP_READ, PosixFilePermission.OWNER_READ)));
        }
    }

    private static void makeReadable(Path p) throws IOException {

        FileStore store = Files.getFileStore(p);

        UserPrincipal user = p.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(System.getProperty("user.name"));
        AclFileAttributeView view = Files.getFileAttributeView(p, AclFileAttributeView.class);

        System.out.println("VIEW: " + view.name());

        AclEntry entry = AclEntry.newBuilder()
                .setType(AclEntryType.ALLOW)
                .setPrincipal(user)
                .setPermissions(EnumSet.allOf(AclEntryPermission.class))
                .build();


        if (store.supportsFileAttributeView(AclFileAttributeView.class)) {
            List<AclEntry> acl = view.getAcl();
            acl.addFirst(entry); // Best practice: insert before DENY entries if applicable
            view.setAcl(acl);
        } else if (store.supportsFileAttributeView(PosixFileAttributeView.class)) {
            Files.setPosixFilePermissions(p, EnumSet.allOf(PosixFilePermission.class));
        }
    }

    @Test
    public void testRemoveMin() {

        var min = new HuffmanNode(72, 1);

        List<HuffmanNode> nodes = List.of(
                new HuffmanNode(1,  10),
                new HuffmanNode(4,  100),
                new HuffmanNode(3,  32),
                min
        );

        List<Collection<HuffmanNode>> li = List.of(
                new ArrayList<>(nodes),
                new PriorityQueue<>(nodes),
                new HashSet<>(nodes)
        );

        li.forEach(e -> assertEquals(min, Huffman.removeMin(e)));
        assertNull(Huffman.removeMin(new ArrayList<>()));

    }

    @Test
    public void testNullTraverseAndBuild() {
        var strs = new String[Huffman.NUM_CHARS];
        Arrays.fill(strs, "");
        assertArrayEquals(strs, Huffman.createCode(null));
    }

    @Test
    public void testLeaf() {
        assertTrue(new HuffmanNode(1, 1).isLeaf());
        assertFalse(new HuffmanNode(1, 1, new HuffmanNode(1, 2), new HuffmanNode(1, 1)).isLeaf());
        assertFalse(new HuffmanNode(1, 1, new HuffmanNode(1, 2), null).isLeaf());
        assertFalse(new HuffmanNode(1, 1, null, new HuffmanNode(1, 2)).isLeaf());
    }

    @Test
    public void testCombine() {
        HuffmanNode a = new HuffmanNode(1, 2);
        HuffmanNode b = new HuffmanNode(2, 1);
        assertEquals(HuffmanNode.combine(a, b), HuffmanNode.combine(b, a));
    }

    @Test
    public void testCompareTo() {
        HuffmanNode a = new HuffmanNode(1, 2);
        HuffmanNode b = new HuffmanNode(2, 1);
        assertTrue(a.compareTo(b) > 0);
        assertTrue(b.compareTo(a) < 0);
        assertEquals(0, a.compareTo(a));
    }

    private static void betterCompare(String a, String b) throws IOException {

        Path og = Path.of(a);
        Path dec = Path.of(b);
        byte[] original = Files.readAllBytes(og);
        byte[] decoded = Files.readAllBytes(dec);

        for (int i = 0; i < Math.min(original.length, decoded.length); i++) {
            if (original[i] != decoded[i]) {

                System.out.println("Mismatch at index: " + i);

                System.out.println(" > Expected: " + (char) original[i]);
                System.out.println(" > Actual: " + (char) decoded[i]);

                System.out.println("Original context: " + new String(original, Math.max(0, i - 5), Math.min(11, original.length - Math.max(0, i - 5))));
                System.out.println("Decoded context: " + new String(decoded, Math.max(0, i - 5), Math.min(11, decoded.length - Math.max(0, i - 5))));

                break;
            }
        }

        assertArrayEquals(Files.readAllBytes(og), Files.readAllBytes(dec));

    }

    /**
     * Compares two files line by line, trimming trailing CR/LF so the
     * comparison works the same on Windows and Unix-style line endings.
     */
    private static boolean compareFiles(String file1, String file2) throws IOException {
        try (BufferedReader r1 = new BufferedReader(new FileReader(file1));
             BufferedReader r2 = new BufferedReader(new FileReader(file2))) {
            String line1, line2;
            while (true) {
                line1 = r1.readLine();
                line2 = r2.readLine();
                if (line1 == null && line2 == null) {
                    return true;
                }
                if (line1 == null || line2 == null) {
                    return false;
                }
                if (!line1.trim().equals(line2.trim())) {
                    return false;
                }
            }
        }
    }
}
