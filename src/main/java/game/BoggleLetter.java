package game;

/**
 * Created by Jesse on 1/30/2017.
 */
public class BoggleLetter {

    private Coordinate coordinate;
    private char letter;

    public BoggleLetter(Coordinate c, char letter) {
        coordinate = c;
        this.letter = letter;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public char getLetter() {
        return letter;
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
