package by.dorogokupets.walletservice.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * A utility class for encoding passwords using the MD5 hashing algorithm.
 */
public class PasswordEncoder {

    /**
     * Encode a text password using the MD5 hashing algorithm.
     *
     * @param text The plain text password to encode.
     * @return The encoded password as an MD5 hash.
     */

    public static String encode(String text) {
        return DigestUtils.md5Hex(text);
    }
}