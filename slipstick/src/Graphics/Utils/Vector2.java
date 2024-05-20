package Graphics.Utils;

public class Vector2 {

    public int x, y;

    public static final Vector2 Zero = new Vector2(0, 0);

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x = this.y = 0;
    }

    public Vector2(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;;
    }

    /**
     * Adds the given {@link Vector2} to this {@link Vector2}.
     * @param vector the vector to be added
     */
    public void Add(Vector2 vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    /**
     * Adds the two given vectors.
     * @param vector_1 the first vector
     * @param vector_2 the second vector
     * @return the sum of the vectors
     */
    public static Vector2 Add(Vector2 vector_1, Vector2 vector_2) {
        Vector2 res = new Vector2();
        res.Add(vector_1);
        res.Add(vector_2);
        return res;
    }

    /**
     * Adds the given number to this {@link Vector2} x component.
     * @param x the number to be added
     */
    public void AddX(int x) {
        this.x += x;
    }

    /**
     * Adds the given number to this {@link Vector2} y component.
     * @param y the number to be added
     */
    public void AddY(int y) {
        this.y += y;
    }

    /**
     * Calculates the vector which point to from one point to another.
     * @param to where it points to
     * @param from where the vector starts
     * @return the direction from one vector to the other
     */
    public static Vector2 DirectionToFrom(Vector2 to, Vector2 from) {
        Vector2 res = new Vector2(to);
        res.x -= from.x;
        res.y -= from.y;
        return res;
    }

    /**
     * Rotates the vector by the specified amount.
     * @param degree the rotation in degrees
     */
    public void RotateBy(float degree) {
        double radian = Math.toRadians(degree);

        double newX = x * Math.cos(radian) - y * Math.sin(radian);
        double newY = x * Math.sin(radian) + y * Math.cos(radian);

        x = (int) newX;
        y = (int) newY;
    }

    /**
     * Rotates the vector by the specified amount.
     * @param vector the vector to be rotated
     * @param degree the rotation in degrees
     * @return a new vector that is the rotated version of the original vector
     */
    public static Vector2 RotateBy(Vector2 vector, float degree) {
        double radian = Math.toRadians(degree);

        Vector2 res = new Vector2(vector);
        double newX = res.x * Math.cos(radian) - res.y * Math.sin(radian);
        double newY = res.x * Math.sin(radian) + res.y * Math.cos(radian);

        res.x = (int) newX;
        res.y = (int) newY;
        return res;
    }

    /**
     * Gets the rotation of the vector.
     * @param vector    the vector
     * @return          the rotation of the vector
     */
    public static float ToRotation(Vector2 vector) {
        return (float) Math.atan2(vector.y, vector.x);
    }

    /**
     * Gets the rotation of the vector.
     * @return          the rotation of the vector
     */
    public float ToRotation() {
        return (float) Math.atan2(this.y, this.x);
    }

    /**
     * Multiplies the vector by the specified amount.
     * @param vector a vector
     * @param mult the number the vector is multiplied by
     * @return a new vector that is the multiplied version of the original
     */
    public static Vector2 Mult(Vector2 vector, float mult) {
        return new Vector2((int) (vector.x * mult), (int) (vector.y * mult));
    }

    /**
     * Multiplies the vector's components by the specified amounts.
     * @param vector a vector
     * @param multX the number the vector's x component is multiplied by
     * @param multY the number the vector's y component is multiplied by
     * @return a new vector that is the multiplied version of the original
     */
    public static Vector2 Mult(Vector2 vector, float multX, float multY) {
        return new Vector2((int) (vector.x * multX), (int) (vector.y * multY));
    }

    @Override
    public String toString() {
        return "Vector2 { X: " + this.x + "; Y: " + this.y + " }";
    }
}
