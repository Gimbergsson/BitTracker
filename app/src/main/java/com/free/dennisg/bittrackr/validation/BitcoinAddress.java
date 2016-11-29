package com.free.dennisg.bittrackr.validation;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Dennis Gimbergsson
 */

public class BitcoinAddress {

    /*
    * These three functions below are to validate that the user has entered a correct Bitcoin address to lookup.
    *
     */

    private final static String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    public static boolean ValidateBitcoinAddress(String addr) {
        if (addr.length() < 26 || addr.length() > 35) return false;
        byte[] decoded = DecodeBase58(addr, 58, 25);
        if (decoded == null) return false;

        byte[] hash = Sha256(decoded, 0, 21, 2);

        return Arrays.equals(Arrays.copyOfRange(hash, 0, 4), Arrays.copyOfRange(decoded, 21, 25));
    }

    private static byte[] DecodeBase58(String input, int base, int len) {
        byte[] output = new byte[len];
        for (int i = 0; i < input.length(); i++) {
            char t = input.charAt(i);

            int p = ALPHABET.indexOf(t);
            if (p == -1) return null;
            for (int j = len - 1; j > 0; j--, p /= 256) {
                p += base * (output[j] & 0xFF);
                output[j] = (byte) (p % 256);
            }

            System.out.println(p);
            if (p != 0 && p != 5){
                // If 5 = multisig address, If 0 = regular address
                return null;
            }
        }

        return output;
    }

    private static byte[] Sha256(byte[] data, int start, int len, int recursion) {
        if (recursion == 0) return data;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Arrays.copyOfRange(data, start, start + len));
            return Sha256(md.digest(), 0, 32, recursion - 1);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /*
    * Bitcoin address validation end.
     */
}
