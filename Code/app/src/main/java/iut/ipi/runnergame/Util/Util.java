package iut.ipi.runnergame.Util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Util {
    public static String roundFloatNDigits(float value, int n) {
        String pattern = "#.";

        for(int i = 0; i < n; ++i) {
            pattern += "#";
        }

        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.CEILING);

        return df.format(value);
    }
}
