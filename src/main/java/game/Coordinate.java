package game;

/**
 * Created by Jesse on 1/21/2017.
 */
public class Coordinate {

    public int row;
    public int col;
    public int level;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
        level = 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (!(obj instanceof Coordinate))
            return false;

        Coordinate c = (Coordinate)obj;

        return row == c.row && col == c.col;

    }

}
