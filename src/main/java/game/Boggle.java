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
                solutions.addAll(dfsFind(new Coordinate(i, j)));
            }
        }

        return solutions;
    }

    private List<String> dfsFind(Coordinate coord) {
        // Setup our DFS
       // boolean[][] visited = new boolean[dim][dim];
        HashMap<Coordinate, Coordinate> parentMap = new HashMap<>();
        List<Coordinate> coordsInUse = new ArrayList<>();
        List<String> wordList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        TrieNode prevNode = null;
        TrieNode charNode = dict.root;
        Stack<Coordinate> s = new Stack<>();
        s.push(coord);

        while (!s.isEmpty()) {
            Coordinate curr = s.pop();
            char currChar = matrix[curr.row][curr.col];

            coordsInUse.add(curr);
            // Retrieve next letter in word from dictionary
            TrieNode child = charNode.getNodeFor(currChar);

            // If we have a match continue searching the word
            if (child != null) {

                sb.append(currChar);

                prevNode = charNode;
                charNode = child;

                // If the word we've built up so far is valid, add it to
                // the resultant word list
                String wordString = sb.toString();

                if (child.exists(wordString) && wordString.length() >= 2) {
                    wordList.add(wordString);
                }

                //getAdjacent(curr).forEach(a -> s.push(a));
                for (Coordinate c : getAdjacent(curr)) {
                    if (!coordsInUse.contains(c)) {
                        s.push(c);
                        parentMap.put(c, curr);
                    }
                }

            } else {



                // Backtrack to the previous character

                /**
                 * psuedocode:
                 *
                 * if (!hashmap.get(s.peek()).equals(currWord.get(currword.size() - 1))) {
                 *
                 * }
                 *
                 */


                sb.deleteCharAt(sb.length()-1);
                charNode = prevNode;
                coordsInUse.remove(coordsInUse.size()-1);
            }

        }

        return wordList;
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
        Boggle b = new Boggle(3, new char[][] {{'a','r','m'},
                {'b','e','s'}, {'n','i','m'}}, "src/main/java/dictionary.txt");
        b.solve();//.forEach(r -> System.out.println(r));
    }


}
