package dadomingues.javahelpers;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

public class Id {

    public static final int CODE_BASE = 36;
    public static final int CODE_MAX_LENGTH = 8;
    public static final Character CODE_PAD =  '0';
    /** length in bytes of an ID */
    public static final int ID_LENGTH = 16;

    // Holds 128 bit unsigned value:
    private static BigInteger nextId;
    private static final BigInteger mask128;
    private static final Object idLock = new Object();

    static {
        // 128 bit unsigned mask
        byte[] maskBytes128 = new byte[16];
        Arrays.fill(maskBytes128, (byte) 0xff);
        mask128 = new BigInteger(1, maskBytes128);
    }


    /**
     * Genereate unique ID from UUID in positive space
     * Reference: http://www.gregbugaj.com/?p=587
     * Copied from Alberto Marianno solution
     * @return long value representing UUID
     */
    public static Long uuid() {
        long val = -1;
        do {
            final UUID uid = UUID.randomUUID();
            final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
            buffer.putLong(uid.getLeastSignificantBits());
            buffer.putLong(uid.getMostSignificantBits());
            final BigInteger bi = new BigInteger(buffer.array());
            val = bi.longValue();
        }
        // We also make sure that the ID is in positive space, if its not we simply repeat the process
        while (val < 0);
        return val;
    }

    /**
     * Generates a non-cryptographic globally unique id.
     * Reference: https://github.com/apache/lucene-solr/blob/master/lucene/core/src/java/org/apache/lucene/util/StringHelper.java
     * @return byte[] id
     **/
    public static byte[] random() {

        // NOTE: we don't use Java's UUID.randomUUID() implementation here because:
        //
        //   * It's overkill for our usage: it tries to be cryptographically
        //     secure, whereas for this use we don't care if someone can
        //     guess the IDs.
        //
        //   * It uses SecureRandom, which on Linux can easily take a long time
        //     (I saw ~ 10 seconds just running a Lucene test) when entropy
        //     harvesting is falling behind.
        //
        //   * It loses a few (6) bits to version and variant and it's not clear
        //     what impact that has on the period, whereas the simple ++ (mod 2^128)
        //     we use here is guaranteed to have the full period.

        byte bits[];
        synchronized(idLock) {
            bits = nextId.toByteArray();
            nextId = nextId.add(BigInteger.ONE).and(mask128);
        }

        // toByteArray() always returns a sign bit, so it may require an extra byte (always zero)
        if (bits.length > ID_LENGTH) {
            assert bits.length == ID_LENGTH + 1;
            assert bits[0] == 0;
            //return ArrayUtil.copyOfSubArray(bits, 1, bits.length);
            return Arrays.copyOfRange(bits,1,bits.length);
        } else {
            byte[] result = new byte[ID_LENGTH];
            System.arraycopy(bits, 0, result, result.length - bits.length, bits.length);
            return result;
        }
    }

    public static String toString(byte id[]) {
        if (id == null) {
            return "(null)";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(new BigInteger(1, id).toString(Character.MAX_RADIX));
            if (id.length != ID_LENGTH) {
                sb.append(" (INVALID FORMAT)");
            }
            return sb.toString();
        }
    }

    /**
     * Convert a Long to an alphanumeric code following radix standard with the
     * chars sequence 0123456789abcdefghijklmnopqrstuvwxyz (max base = 36).
     * Example: to generate a 6 chars sequence, the max param value in
     * "val" is 2176782335 (encode to ZZZZZZ). If a grater value is passed,
     * the code return will be a truncated value with the least significant symbols (last chars).
     * @param val
     * @return
     */
    public static String encode(Long val) {
        return normalize(pad(Long.toString(val, CODE_BASE)));
    }

    public static String encodeToHex(Long val) {
        return normalize(pad(Long.toString(val, 16)));
    }

    /**
     * Convert an alphanumerico code to a Long following radix standard with the
     * chars sequence 0123456789abcdefghijklmnopqrstuvwxyz (max base = 36).
     * Example: given a 6 chars sequence, the max value returned
     * will be 2176782335 (param code="ZZZZZZ")
     * @param code
     * @return
     */
    public static Long decode(String code) {
        return Long.valueOf(clean(code), CODE_BASE);
    }

    public static Long decodeFromHex(String code) {
        return Long.valueOf(clean(code), 16);
    }


    public static String clean(String code) {
        return clean(code, CODE_PAD);
    }

    public static String clean(String code, char padChar) {
        char[] symbols = code.toCharArray();
        for (int i=0; i<symbols.length; i++) {
            if (symbols[i]!=padChar) break;
            symbols[i] = ' ';
        }
        return String.copyValueOf(symbols).trim();
    }


    public static String pad(String code) {
        return pad(code, CODE_MAX_LENGTH, CODE_PAD);
    }

    public static String pad(String code, int length, char padChar) {
        return String.format("%1$" + length + "s", code).replace(' ', padChar);
    }


    public static String normalize(String code) {
        return normalize(code, CODE_MAX_LENGTH);
    }

    public static String normalize(String code, int length) {
        return code.toUpperCase().substring(code.length() - length);
    }

}
