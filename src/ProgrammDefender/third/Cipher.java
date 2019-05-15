package ProgrammDefender.third;

import org.apache.commons.codec.binary.Base64;

public class Cipher {
    private String KEY = "some-secret-key-of-your-choice";

    public Cipher(String KEY) {
        this.KEY = KEY;
    }

    public Cipher() {
    }

    public String encrypt(String text) {
        return new String(Base64.encodeBase64(this.xor(text.getBytes())));
    }

    public String decrypt(String key) {
        try {
            return new String(this.xor(Base64.decodeBase64(key.getBytes())), "UTF-8");
        } catch (java.io.UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private byte[] xor(byte[] input) {
        final byte[] output = new byte[input.length];
        final byte[] secret = this.KEY.getBytes();
        int spos = 0;
        for (int pos = 0; pos < input.length; ++pos) {
            output[pos] = (byte) (input[pos] ^ secret[spos]);
            spos += 1;
            if (spos >= secret.length) {
                spos = 0;
            }
        }
        return output;
    }
}