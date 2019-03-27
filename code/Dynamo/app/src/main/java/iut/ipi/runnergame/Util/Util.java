package iut.ipi.runnergame.Util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Util {
    /**
     * genere un string via un float et garde n digits
     * @param value la valeur du float
     * @param n le nombre de digits a garder
     * @return un string contenant le nombre de digits
     */
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
