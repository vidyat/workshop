package com.sample.qwords.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import java.lang.reflect.Field;
import javax.crypto.Cipher;
import org.junit.Test;
import static org.junit.Assert.*;


public class RandomUtilsTest {

    /**
     * Test that getCipher() returns a non-null Cipher instance
     * with the correct transformation (DES)
     */
    @Test
    public void testGetCipherReturnsValidDESCipher() {
        Cipher cipher = RandomUtils.getCipher();
        
        assertNotNull("Cipher should not be null", cipher);
        assertEquals("Cipher should use DES transformation", "DES", cipher.getAlgorithm());
    }

    @Test
    public void testGetCredentialsWithEmptyInputs() {
        /**
         * Test getCredentials method with empty input strings
         */
        AWSCredentials credentials = RandomUtils.getCredentials("", "");
        assertNotNull(credentials);
        assertEquals("", credentials.getAWSAccessKeyId());
        assertEquals("", credentials.getAWSSecretKey());
    }

    @Test
    public void testGetCredentialsWithLongInputs() {
        /**
         * Test getCredentials method with very long input strings
         */
        String longString = new String(new char[1000]).replace("\0", "a");
        AWSCredentials credentials = RandomUtils.getCredentials(longString, longString);
        assertNotNull(credentials);
        assertEquals(longString, credentials.getAWSAccessKeyId());
        assertEquals(longString, credentials.getAWSSecretKey());
    }

    @Test
    public void testGetCredentialsWithNullInputs() {
        /**
         * Test getCredentials method with null input strings
         */
        try {
            RandomUtils.getCredentials(null, null);
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }    }

    @Test
    public void testGetCredentialsWithSpecialCharacters() {
        /**
         * Test getCredentials method with special characters in input strings
         */
        String specialChars = "!@#$%^&*()_+{}[]|\\:;\"'<>,.?/~`";
        AWSCredentials credentials = RandomUtils.getCredentials(specialChars, specialChars);
        assertNotNull(credentials);
        assertEquals(specialChars, credentials.getAWSAccessKeyId());
        assertEquals(specialChars, credentials.getAWSSecretKey());
    }

    /**
     * Test case for getCredentials method with valid access key and secret key
     */
    @Test
    public void testGetCredentialsWithValidKeys() {
        String accessKey = "testAccessKey";
        String secretKey = "testSecretKey";

        AWSCredentials credentials = RandomUtils.getCredentials(accessKey, secretKey);

        assertNotNull("Credentials should not be null", credentials);
        assertTrue("Credentials should be instance of BasicAWSCredentials", credentials instanceof BasicAWSCredentials);
        assertEquals("Access key should match", accessKey, credentials.getAWSAccessKeyId());
        assertEquals("Secret key should match", secretKey, credentials.getAWSSecretKey());
    }

    /**
     * Test that getCreds returns a BasicAWSCredentials object with correct access key ID and secret key
     */
    @Test
    public void testGetCredsReturnsValidCredentials() {
        String testAccessKeyId = "testAccessKeyId";
        String testSecretKey = "testSecretKey";

        AWSCredentials result = RandomUtils.getCreds(testAccessKeyId, testSecretKey);

        assertNotNull("Returned AWSCredentials should not be null", result);
        assertTrue("Returned object should be an instance of BasicAWSCredentials", result instanceof BasicAWSCredentials);
        assertEquals("Access key ID should match the input", testAccessKeyId, result.getAWSAccessKeyId());
        assertEquals("Secret key should match the input", testSecretKey, result.getAWSSecretKey());
    }

    @Test
    public void testGetCredsWithEmptyInputs() {
        /**
         * Test getCreds method with empty strings as inputs.
         * This tests the scenario where input is empty.
         */
        AWSCredentials credentials = RandomUtils.getCreds("", "");
        assertTrue(credentials instanceof BasicAWSCredentials);
        assertEquals("", ((BasicAWSCredentials) credentials).getAWSAccessKeyId());
        assertEquals("", ((BasicAWSCredentials) credentials).getAWSSecretKey());
    }

    @Test
    public void testGetCredsWithLongInputs() {
        /**
         * Test getCreds method with very long string inputs.
         * This tests the scenario where input is outside accepted bounds.
         */
        String longString = new String(new char[1000]).replace("\0", "a");
        AWSCredentials credentials = RandomUtils.getCreds(longString, longString);
        assertTrue(credentials instanceof BasicAWSCredentials);
        assertEquals(longString, ((BasicAWSCredentials) credentials).getAWSAccessKeyId());
        assertEquals(longString, ((BasicAWSCredentials) credentials).getAWSSecretKey());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testGetCredsWithMixedInputTypes() {
        /**
         * Test getCreds method with mixed input types (one null, one valid).
         * This tests exception handling for partially invalid inputs.
         */
        RandomUtils.getCreds("validId", null);
        fail("Expected NullPointerException was not thrown");

        RandomUtils.getCreds(null, "validKey");
        fail("Expected NullPointerException was not thrown");
    }

    @Test
    public void testGetCredsWithNullInputs() {
        /**
         * Test getCreds method with null inputs.
         * This tests the scenario where input is invalid (null).
         */
        try {
            RandomUtils.getCreds(null, null);
            fail("Expected NullPointerException was not thrown");
        } catch (java.lang.IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testGetCredsWithSpecialCharacters() {
        /**
         * Test getCreds method with inputs containing special characters.
         * This tests the scenario where input format might be considered incorrect.
         */
        String specialChars = "!@#$%^&*()_+{}[]|\":;'<>?,./";
        AWSCredentials credentials = RandomUtils.getCreds(specialChars, specialChars);
        assertTrue(credentials instanceof BasicAWSCredentials);
        assertEquals(specialChars, ((BasicAWSCredentials) credentials).getAWSAccessKeyId());
        assertEquals(specialChars, ((BasicAWSCredentials) credentials).getAWSSecretKey());
    }

    /**
     * Test that getCipher returns a Cipher instance with the correct algorithm
     */
    @Test
    public void test_getCipher_returnsCorrectAlgorithm() {
        Cipher cipher = RandomUtils.getCipher();
        assertEquals("getCipher should return a Cipher instance with DES algorithm", "DES", cipher.getAlgorithm());
    }

    /**
     * Test that getCipher returns a non-null Cipher instance
     */
    @Test
    public void test_getCipher_returnsNonNullCipher() {
        Cipher cipher = RandomUtils.getCipher();
        assertNotNull("getCipher should return a non-null Cipher instance", cipher);
    }

}