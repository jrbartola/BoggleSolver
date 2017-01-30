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
        HashMap<BoggleLetter, BoggleLetter> parentMap = new HashMap<>();
        List<BoggleLetter> lettersInUse = new ArrayList<>();
        List<String> wordList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        TrieNode prevNode = null;
        TrieNode charNode = dict.root;
        Stack<BoggleLetter> s = new Stack<>();
        s.push(new BoggleLetter(coord, matrix[coord.row][coord.col]));

        while (!s.isEmpty()) {
            BoggleLetter curr = s.pop();

            while (curr.getParent() != lettersInUse.get(lettersInUse.size()-1)) {
                curr = curr.getParent();
            }
            char currChar = curr.getLetter();//matrix[curr.getCoordinate().row][curr.getCoordinate().col];

            lettersInUse.add(curr);
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

                if (wordString.length() >= 2 && child.exists(wordString)) {
                    wordList.add(wordString);
                }

                //getAdjacent(curr).forEach(a -> s.push(a));
                for (BoggleLetter bl : getAdjacent(curr)) {
                    if (!lettersInUse.contains(bl)) {
                        s.push(bl);
                        bl.setParent(curr);
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
                lettersInUse.remove(lettersInUse.size()-1);
            }

        }

        return wordList;
    }

    private List<BoggleLetter> getAdjacent(BoggleLetter bl) {
        int row = bl.getCoordinate().row;
        int col = bl.getCoordinate().col;

        List<BoggleLetter> adjacent = new ArrayList<>();

        for (int i = row - 1; i < row + 1; i++) {
            for (int j = col - 1; j < col + 1; j++) {
                if (!(i == row && j == col) && i >= 0 &&
                    i < dim && j >= 0 && j < dim) {

                    adjacent.add(new BoggleLetter(new Coordinate(i, j), matrix[i][j]));
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
