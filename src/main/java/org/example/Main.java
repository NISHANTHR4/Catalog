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
