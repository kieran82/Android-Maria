package marineapplications.marineapplicationwifi;

import java.io.UnsupportedEncodingException;

/**
 * Created by kieranmoroney on 31/03/2016.
 */
public class HexStringConverter {
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    private static HexStringConverter hexStringConverter = null;

    private HexStringConverter() {
    }

    /**
     * Gets hex string converter instance.
     *
     * @return the hex string converter instance
     */
    public static HexStringConverter getHexStringConverterInstance() {
        if (hexStringConverter == null) hexStringConverter = new HexStringConverter();
        return hexStringConverter;
    }

    /**
     * String to hex string.
     *
     * @param input the input
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public String stringToHex(String input) throws UnsupportedEncodingException {
        if (input == null) throw new NullPointerException();
        return asHex(input.getBytes());
    }

    /**
     * Hex to string string.
     *
     * @param txtInHex the txt in hex
     * @return the string
     */
    public String hexToString(String txtInHex) {
        byte[] txtInByte = new byte[txtInHex.length() / 2];
        int j = 0;
        for (int i = 0; i < txtInHex.length(); i += 2) {
            txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);
        }
        return new String(txtInByte);
    }

    private String asHex(byte[] buf) {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }

}