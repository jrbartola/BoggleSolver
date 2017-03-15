package trie;

import java.io.*;

/**
 * Created by Jesse on 1/21/2017.
 */
public class DictionaryTrie {
    public TrieNode root;

    /** Creates a new trie.DictionaryTrie object. Result
     *  is a Trie containing all works from newline separated
     *  dictionary file
     *
     * @param dict filename of the dictionary to load from
     * @throws IOException
     */
    public DictionaryTrie(File dict) throws IOException {
        root = new TrieNode();
        BufferedReader br = new BufferedReader(new FileReader(dict));
        String line = br.readLine();
        while (line != null) {
            line.toLowerCase();
            root.add(line);
            line = br.readLine();
        }
    }


    public static void main(String[] args) {
        try {
            DictionaryTrie dt = new DictionaryTrie(new File("src/main/java/dictionary.txt"));
            System.out.println(dt.root.exists("ballerina"));
            System.out.println(dt.root.getNumPrefixesOf("bnt"));
            System.out.println(dt.root.getNumPrefixesOf("bna"));
            System.out.println(dt.root.getNumPrefixesOf("bng"));
            System.out.println(dt.root.getNumPrefixesOf("bne"));
            System.out.println(dt.root.getNumPrefixesOf("bnr"));
            System.out.println(dt.root.getNumPrefixesOf("bno"));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
