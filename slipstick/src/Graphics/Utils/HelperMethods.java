package Graphics.Utils;

public class HelperMethods {

    /**
     * Returns where the t value is between the from and to values.
     * @param from  the start of the interval
     * @param to    the end of the interval
     * @param t     the current value
     * @param clamped   wether the result is clamped, if yes then the result value is between 0 and 1
     * @return the result lerp value
     */
    public static float GetLerpValue(float from, float to, float t, boolean clamped) {
        if (clamped) {
            if (from < to) {
                if (t < from)
                    return 0f;

                if (t > to)
                    return 1f;
            }
            else {
                if (t < to)
                    return 1f;

                if (t > from)
                    return 0f;
            }
        }

        return (t - from) / (to - from);
    }

    /**
     * Remaps a value from a given interval int another.
     * @param fromValue the starting value
     * @param fromMin   the starting interval's beginning
     * @param fromMax   the starting interval's end
     * @param toMin     the new interval's beginning
     * @param toMax     the new interval's end
     * @param clamped   wether the result is clamped, if yes then the result value is between 0 and 1.
     *                  Should use {@code true} for most of the time.
     * @return the remapped value
     */
    public static float Remap(float fromValue, float fromMin, float fromMax, float toMin, float toMax, boolean clamped) {
        return Lerp(toMin, toMax, GetLerpValue(fromMin, fromMax, fromValue, clamped));
    }

    /**
     * Linear interpolation between two values
     * @param value1    the first value
     * @param value2    the second value
     * @param amount    the interpolation amount
     * @return the interpolated value
     */
    public static float Lerp(float value1, float value2, float amount)
    {
        return value1 + (value2 - value1) * amount;
    }
}