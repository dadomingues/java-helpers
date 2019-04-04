package dadomingues.javahelpers;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.apache.commons.text.StringEscapeUtils;

import javax.swing.text.MaskFormatter;
import java.math.BigInteger;
import java.text.ParseException;

public class Str extends StringUtils{

    enum EscapeTypes {
        HTML,
        JSON,
        CSV,
        SQL,
        STRING
    }

    enum NamingFlavors {
        LCAMEL,
        SNAKE,
        KEBAB,
        UCAMEL,
        CONSTANT
    }

    public static String alphaPad(String unpadded, int length) {
        return rightPad(unpadded, length, " ");
    }

    public static String numPad(String unpadded, int length) {
        return leftPad(unpadded, length, "0");
    }

    public static String trim(String text, char c) {
        return text;
    }

    public static String trim(String text) {
        return text.trim();
    }

    public static String name(String text, NamingFlavors flavor) {
        text = StringUtils.stripAccents(text).replaceAll("[^0-9a-zA-Z\\s]","");
        switch (flavor) {
            case UCAMEL: return CaseUtils.toCamelCase(text, true);
            case LCAMEL: return CaseUtils.toCamelCase(text, false);
            case CONSTANT: return text.toUpperCase().replaceAll("\\s","_");
            case SNAKE: return text.toLowerCase().replaceAll("\\s","_");
            case KEBAB: return text.toLowerCase().replaceAll("\\s","-");
            default: return text;
        }
    }

    public static String escape(String input, EscapeTypes escape) {
        switch (escape) {
            case STRING: return StringEscapeUtils.escapeJava(input);
            case JSON: return StringEscapeUtils.escapeJson(input);
            case CSV: return StringEscapeUtils.escapeCsv(input);
            case HTML: return StringEscapeUtils.escapeHtml4(input);
            case SQL: return escapeSQL(input);
        }
        return input;
    }

    private static String escapeSQL(String input) {
        return input.replaceAll("['\"\0\r\n\\\\]","\\\\$0");
    }

    /**
     * Return initial letters from each word
     * Reference: http://commons.apache.org/proper/commons-lang/javadocs/api-3.1/src-html/org/apache/commons/lang3/text/WordUtils.html
     * @param text
     * @return
     */
    public static String initials(String text) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }
        char[] buf = new char[text.length() / 2 + 1];
        int count = 0;
        boolean lastWasGap = true;
        for (char c: text.toCharArray()) {
            if (c==' ') {
                lastWasGap = true;
            } else if (lastWasGap) {
                buf[count++] = c;
                lastWasGap = false;
            } else {
                continue; // ignore
            }
        }
        return new String(buf, 0, count);
    }

    public static String mask(Long unmaskedLong, String maskFormat) {
        String unmasked = String.valueOf(unmaskedLong);
        return mask(unmasked, maskFormat);
    }

    public static String mask(BigInteger unmaskedBigInt, String maskFormat) {
        String unmasked = String.valueOf(unmaskedBigInt);
        return mask(unmasked, maskFormat);
    }

    public static String mask(String unmasked, String maskFormat) {
        try {
            MaskFormatter mask = new MaskFormatter(maskFormat);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(unmasked);
        } catch (ParseException e) {
            System.out.println("Format error in mask '" + maskFormat + "' for '" + unmasked + "' (use MaskFormatter)");
        }
        return unmasked;
    }

    public static String unmask(String masked, String maskFormat) {
        try {
            MaskFormatter mask = new MaskFormatter(maskFormat);
            mask.setValueContainsLiteralCharacters(false);
            return (String) mask.stringToValue(masked);
        } catch (ParseException e) {
            System.out.println("Format error in mask '" + maskFormat + "' for '" + masked + "' (use MaskFormatter)");
        }
        return masked;
    }

}
