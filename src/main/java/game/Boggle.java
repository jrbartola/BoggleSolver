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
        //solutions.addAll(dfsFind(new Coordinate(0, 0)));

        return solutions;
    }


    private List<String> dfsFind(Coordinate coord) {
        List<String> wordList = new ArrayList<String>();
        List<Coordinate> usedLetters = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        TrieNode currNode = dict.root;
       // TrieNode prevNode = null;
        TrieNode child;

        Stack<Coordinate> s = new Stack<>();
        s.push(coord);

        while (!s.empty()) {
            Coordinate curr = s.pop();
            char currChar = matrix[curr.row][curr.col];

            while (curr.level != sb.length() + 1) {
                // Backtrack to a letter the same distance away from our start coordinate
                sb.deleteCharAt(sb.length()-1);
                usedLetters.remove(usedLetters.size()-1);
            }

            if (usedLetters.contains(curr)) {
                // We can't use the same letter twice in a single word
                continue;
            }

            child = null;
            if (sb.length() == 0) {
                child = currNode.getNodeFor(currChar);
            } else {
                child = dict.root.getEndOfPrefix(sb.toString());
            }


            if (child != null) {
                // If words exist with this prefix add it to stringBuilder
                sb.append(currChar);
                usedLetters.add(curr);

                //prevNode = currNode;
                currNode = child;

                if (sb.length() > 2 && dict.root.exists(sb.toString())) {
                    wordList.add(sb.toString());
                }

                for (Coordinate c : getAdjacent(curr)) {
                    c.level = curr.level + 1;
                    s.push(c);
                }

            }

        }

        return wordList;

    }

    private List<BoggleLetter> getAdjacent(BoggleLetter bl) {
        int row = bl.getCoordinate().row;
        int col = bl.getCoordinate().col;

        List<BoggleLetter> adjacent = new ArrayList<>();

        for (int i = row - 1; i < row + 2; i++) {
            for (int j = col - 1; j < col + 2; j++) {
                if (!(i == row && j == col) && i >= 0 &&
                    i < dim && j >= 0 && j < dim) {

                    adjacent.add(new BoggleLetter(new Coordinate(i, j), matrix[i][j]));
                }
            }
        }

        return adjacent;
    }

    private List<Coordinate> getAdjacent(Coordinate coord) {
        int row = coord.row;
        int col = coord.col;

        List<Coordinate> adjacent = new ArrayList<>();

        for (int i = row - 1; i < row + 2; i++) {
            for (int j = col - 1; j < col + 2; j++) {
                if (!(i == row && j == col) && i >= 0 &&
                        i < dim && j >= 0 && j < dim) {

                    adjacent.add(new Coordinate(i, j));
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
        Boggle f = new Boggle(4, new char[][] {{'b', 'o', 'r', 'e'},
                {'r', 't', 'a', 'n'}, {'j', 'l', 'o', 'p'}, {'n', 'a', 'v', 'y'}}, "src/main/java/dictionary.txt");
        //b.solve().forEach(r -> System.out.println(r));
        System.out.println();
        f.solve().forEach(r -> System.out.println(r));
    }


}
