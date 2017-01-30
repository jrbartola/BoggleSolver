package game;

/**
 * Created by Jesse on 1/30/2017.
 */
public class BoggleLetter {

    private Coordinate coordinate;
    private char letter;
    private BoggleLetter parent;

    public BoggleLetter(Coordinate c, char letter) {
        coordinate = c;
        this.letter = letter;
        parent = null;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public char getLetter() {
        return letter;
    }

    public void setParent(BoggleLetter p) {
        parent = p;
    }

    public BoggleLetter getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (!(obj instanceof BoggleLetter))
            return false;

        BoggleLetter bl = (BoggleLetter)obj;

        return letter == bl.getLetter() && coordinate.equals(bl.getCoordinate());

    }
}
