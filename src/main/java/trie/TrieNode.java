package trie;

/**
 * Class for trie.TrieNode implementation
 */
public class TrieNode {
    private TrieNode[] alphabet;
    private int count;
    private boolean leaf;

    public TrieNode() {
        count = 0;
        alphabet = new TrieNode[256];
        leaf = false;
    }

    public TrieNode(int alphasize) {
        count = 0;
        alphabet = new TrieNode[alphasize];
        leaf = false;
    }

    private int getCharIndex(char c) {
        return c;// - 'a';
    }

    public TrieNode getNodeFor(char c) {
        return alphabet[getCharIndex(c)];
    }

    public void add(String s) {
        add(s, 0);
    }

    private void add(String s, int index) {
        if (s.length() == index) return;

        count++;

        char currChar = s.charAt(index);
        TrieNode child = getNodeFor(currChar);

        if (child == null) {
            child = new TrieNode();
            alphabet[getCharIndex(currChar)] = child;
        }

        if (index + 1 == s.length()) {
            child.leaf = true;
        }

        child.add(s, index + 1);
    }

    public boolean exists(String s) {
        return exists(s, 0);
    }

    private boolean exists(String s, int index) {
        if (s.length() == index)
            return this.leaf;

        TrieNode child = getNodeFor(s.charAt(index));

        if (child == null) return false;

        return child.exists(s, index + 1);
    }

    public int getNumPrefixesOf(String s) {
        return getNumPrefixes(s, 0);
    }

    private int getNumPrefixes(String s, int index) {
        if (index == s.length()) return count;

        TrieNode child = getNodeFor(s.charAt(index));

        if (child == null) return 0;

        return child.getNumPrefixes(s, index+1);
    }

}
