package game;

import trie.DictionaryTrie;
import trie.TrieNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/* Class representing a boggle board */
public class Boggle {

    private DictionaryTrie dict;
    private int dim;
    private char[][] matrix;

    public Boggle(int dim, char[][] matrix, String dictFileName) {
        this.dim = dim;
        this.matrix = matrix;

        try {
            this.dict = new DictionaryTrie(new File(dictFileName ));
        } catch (IOException e) {
            System.err.println("Could not find reference to dictionary file. Aborting..");
            e.printStackTrace();
        }

    }

    public List<String> solve() {
        List<String> solutions = new ArrayList<String>();

        // Go through entire boggle board searching for words
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                dfsFind(new Coordinate(i, j));
            }
        }

        return solutions;
    }

    private List<String> dfsFind(Coordinate coord) {
        // Setup our DFS
        boolean[][] visited = new boolean[dim][dim];
        HashMap<Character, Character> path = new HashMap<>();
        List<String> wordList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        TrieNode prevNode = null;
        TrieNode charNode = dict.root;
        Stack<Coordinate> s = new Stack<>();
        s.push(coord);

        while (!s.isEmpty()) {
            Coordinate curr = s.pop();
            char currChar = matrix[curr.row][curr.col];

            if (!visited[curr.row][curr.col]) {
                visited[curr.row][curr.col] = true;

                // Retrieve next letter in word from dictionary
                TrieNode child = charNode.getNodeFor(currChar);

                // If we have a match continue searching the word
                if (child != null) {
                    sb.append(currChar);
                    prevNode = charNode;
                    charNode = child;

                    // If the word we've built up so far is valid, add it to
                    // the resultant word list

                    if (child.exists(sb.toString())) {
                        wordList.add(sb.toString());
                    }

                    getAdjacent(curr).forEach(a -> s.push(a));

                } else {
                    // Backtrack to the previous character
                    sb.deleteCharAt(sb.length()-1);
                    charNode = prevNode;
                }

            }
        }
        return new ArrayList<String>();
    }

    private List<Coordinate> getAdjacent(Coordinate c) {
        List<Coordinate> adjacent = new ArrayList<>();

        for (int i = c.row - 1; i < c.row + 1; i++) {
            for (int j = c.col - 1; j < c.col + 1; j++) {
                if (!(i == c.row && j == c.col) && i >= 0 &&
                    i < dim && j >= 0 && j < dim) {

                    adjacent.add(new Coordinate(i,j));
                }
            }
        }

        return adjacent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder((dim * 2 - 1) * dim);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                sb.append(matrix[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        boolean[][] visited = new boolean[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (visited[i][j] == false) {
                    System.out.print("null");
                }
            }
        }
    }


}
