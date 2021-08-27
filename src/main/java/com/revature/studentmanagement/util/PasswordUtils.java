package com.revature.studentmanagement.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;

public class PasswordUtils {


    private String salt;
    private final Random random = new SecureRandom();


    public PasswordUtils() {
        Properties appProperties = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            appProperties.load(loader.getResourceAsStream("properties.properties"));
            this.salt = appProperties.getProperty("salt");
            if (salt == null) throw new IllegalStateException("No salt found for password encryption.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The generateSecurePassword method fully encrypts a plaintext password via hash and Base64 encoding.
     * @param password - plaintext password to be encrypted.
     * @return - final encrypted password.
     */
    public String generateSecurePassword(String password) {
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    /**
     * The hash method hashes the plaintext password as a byte[] using the Salt value as a byte[].
     * @param password - plaintext password in char array.
     * @param salt - the hash key in byte array.
     * @return - hashed password as a byte array.
     */
    private byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, 10000, 256);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
}
