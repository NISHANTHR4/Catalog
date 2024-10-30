package org.example;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        // Here i am taking  input from the testcase.json
        String filePath1 = "src/testcase1.json";
        String filePath2 = "src/testcase2.json";

        try {
            JSONObject jsonObject1 = loadingJson(filePath1);
            JSONObject jsonObject2 = loadingJson(filePath2);

            BigInteger secret1 = SecretConst(jsonObject1);
            BigInteger secret2 = SecretConst(jsonObject2);

            System.out.println("Secret from Test Case 1: " + secret1);
            System.out.println("Secret from Test Case 2: " + secret2);
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    private static JSONObject loadingJson(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return new JSONObject(new JSONTokener(reader));
        }
    }

    private static BigInteger decodeYValue(String value, int base) {
        return new BigInteger(value, base); // Since values are large i am using BigInteger
    }

    private static BigInteger SecretConst(JSONObject jsonObject) {
        int n = jsonObject.getJSONObject("keys").getInt("n");
        int k = jsonObject.getJSONObject("keys").getInt("k");

        // Assuming we have k roots to determine the polynomial
        BigInteger[] xValues = new BigInteger[k];
        BigInteger[] yValues = new BigInteger[k];

        for (int i = 1; i <= k; i++) {
            JSONObject root = jsonObject.getJSONObject(String.valueOf(i));
            String base = root.getString("base");
            String value = root.getString("value");
            int radix = Integer.parseInt(base);

            xValues[i - 1] = BigInteger.valueOf(i);
            yValues[i - 1] = decodeYValue(value, radix);
        }

        //the secret value also might be big, so we are using BigInteger
        BigInteger secret = calculateSecret(xValues, yValues, k);
        return secret;
    }

    private static BigInteger calculateSecret(BigInteger[] xValues, BigInteger[] yValues, int k) {

        BigInteger secret = BigInteger.ZERO;

        for (int i = 0; i < k; i++) {
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    numerator = numerator.multiply(BigInteger.ZERO.subtract(xValues[j]));
                    denominator = denominator.multiply(xValues[i].subtract(xValues[j]));
                }
            }

            BigInteger term = numerator.divide(denominator).multiply(yValues[i]);
            secret = secret.add(term);
        }

        return secret.mod(BigInteger.TEN.pow(10)); 
    }
}
