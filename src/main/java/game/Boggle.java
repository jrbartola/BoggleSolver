package game;

import trie.DictionaryTrie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/* Class representing a boggle board */
public class Boggle {

    private DictionaryTrie dict;
    private int dim;

    public Boggle(int dim, String dictFileName) {
        this.dim = dim;

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
        boolean[][] visited = new boolean[dim][dim];
        Stack<Coordinate> s = new Stack<>();
        s.push(coord);

        while (!s.isEmpty()) {
            Coordinate curr = s.pop();
            if (!visited[curr.row][curr.col]) {
                visited[curr.row][curr.col] = true;

                for (Coordinate adjacent : getAdjacent(curr)) {
                    s.push(adjacent);
                }
            }
        }
        return new ArrayList<String>();
    }

    private List<Coordinate> getAdjacent(Coordinate c) {
        List<Coordinate> adjacent = new ArrayList<>();

        for (int i = c.row - 1; i < c.row + 1; i++) {
            for (int j = c.col - 1; j < c.col + 1; j++) {
                if (i != c.row && j != c.col && i >= 0 &&
                    i < dim && j >= 0 && j < dim) {

                    adjacent.add(new Coordinate(i,j));
                }
            }
        }

        return adjacent;
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
