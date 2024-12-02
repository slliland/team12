package com.develogical;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryProcessor {

    public String process(String query) {

        if (query == null || query.trim().isEmpty()) {
            return "I'm sorry, I didn't understand that. Could you please rephrase?";
        }

        query = query.toLowerCase().trim();

        // Handle Shakespeare query
        if (query.contains("shakespeare")) {
            return "William Shakespeare (26 April 1564 - 23 April 1616) was an " +
                    "English poet, playwright, and actor, widely regarded as the greatest " +
                    "writer in the English language and the world's pre-eminent dramatist.";
        }

        // Handle 'your name' query
        if (query.contains("your name")) {
            return "Team12";
        }

        // Handle arithmetic operations
        String arithmeticResult = handleArithmetic(query);
        if (arithmeticResult != null) {
            return arithmeticResult;
        }

        // Handle comparison queries
        String comparisonResult = handleComparison(query);
        if (comparisonResult != null) {
            return comparisonResult;
        }

        // Handle prime number queries
        String primeResult = handlePrimeQueries(query);
        if (primeResult != null) {
            return primeResult;
        }

        // Handle exponentiation queries
        String exponentiationResult = handleExponentiation(query);
        if (exponentiationResult != null) {
            return exponentiationResult;
        }

        // Handle special queries like square and cube
        String specialResult = handleSpecialQueries(query);
        if (specialResult != null) {
            return specialResult;
        }

        // If query type is not recognized
        return "I'm sorry, I don't know the answer to that.";
    }

    /**
     * Handles basic arithmetic operations: addition, subtraction, multiplication, division.
     *
     * @param query The user query string.
     * @return The result of the arithmetic operation as a string, or null if not an arithmetic query.
     */
    private String handleArithmetic(String query) {
        // Define patterns for different arithmetic operations
        Pattern additionPattern = Pattern.compile("what is (\\d+) plus (\\d+)\\??", Pattern.CASE_INSENSITIVE);
        Pattern subtractionPattern = Pattern.compile("what is (\\d+) minus (\\d+)\\??", Pattern.CASE_INSENSITIVE);
        Pattern multiplicationPattern = Pattern.compile("what is (\\d+) multiplied by (\\d+)\\??", Pattern.CASE_INSENSITIVE);
        Pattern divisionPattern = Pattern.compile("what is (\\d+) divided by (\\d+)\\??", Pattern.CASE_INSENSITIVE);

        Matcher matcher;

        // Addition
        matcher = additionPattern.matcher(query);
        if (matcher.find()) {
            int num1 = Integer.parseInt(matcher.group(1));
            int num2 = Integer.parseInt(matcher.group(2));
            int sum = num1 + num2;
            return String.valueOf(sum);
        }

        // Subtraction
        matcher = subtractionPattern.matcher(query);
        if (matcher.find()) {
            int num1 = Integer.parseInt(matcher.group(1));
            int num2 = Integer.parseInt(matcher.group(2));
            int difference = num1 - num2;
            return String.valueOf(difference);
        }

        // Multiplication
        matcher = multiplicationPattern.matcher(query);
        if (matcher.find()) {
            int num1 = Integer.parseInt(matcher.group(1));
            int num2 = Integer.parseInt(matcher.group(2));
            int product = num1 * num2;
            return String.valueOf(product);
        }

        // Division
        matcher = divisionPattern.matcher(query);
        if (matcher.find()) {
            int num1 = Integer.parseInt(matcher.group(1));
            int num2 = Integer.parseInt(matcher.group(2));
            if (num2 == 0) {
                return "Cannot divide by zero.";
            }
            double quotient = (double) num1 / num2;
            return String.format("%.2f", quotient);
        }

        return null; // Not an arithmetic query
    }

    /**
     * Handles comparison queries to find the largest number in a list.
     *
     * @param query The user query string.
     * @return The largest number as a string, or null if not a comparison query.
     */
    private String handleComparison(String query) {
        // Pattern to match "Which of the following numbers is the largest: 28, 87, 33?"
        Pattern comparisonPattern = Pattern.compile(
                "which of the following numbers is the largest[:\\s]+([\\d\\s,]+)\\??",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = comparisonPattern.matcher(query);
        if (matcher.find()) {
            String numbersStr = matcher.group(1);
            String[] numberTokens = numbersStr.split("[,\\s]+");
            List<Integer> numbers = new ArrayList<>();

            for (String token : numberTokens) {
                if (!token.isEmpty()) {
                    try {
                        numbers.add(Integer.parseInt(token));
                    } catch (NumberFormatException e) {
                        // Handle invalid number formats gracefully
                        return "One of the provided numbers is invalid.";
                    }
                }
            }

            if (numbers.isEmpty()) {
                return "No valid numbers were provided for comparison.";
            }

            int max = Collections.max(numbers);
            return String.valueOf(max);
        }

        return null; // Not a comparison query
    }

    /**
     * Handles queries asking which numbers in a list are prime.
     *
     * @param query The user query string.
     * @return A comma-separated list of prime numbers as a string, or null if not a prime query.
     */
    private String handlePrimeQueries(String query) {
        // Pattern to match "Which of the following numbers are primes: 8, 39, 43, 13, 78?"
        // Also handle singular "prime" in case the user writes "is a prime"
        Pattern primePattern = Pattern.compile(
                "which of the following numbers (?:are primes|is a prime)[:\\s]+([\\d\\s,]+)\\??",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = primePattern.matcher(query);
        if (matcher.find()) {
            String numbersStr = matcher.group(1);
            String[] numberTokens = numbersStr.split("[,\\s]+");
            List<Integer> numbers = new ArrayList<>();

            for (String token : numberTokens) {
                if (!token.isEmpty()) {
                    try {
                        numbers.add(Integer.parseInt(token));
                    } catch (NumberFormatException e) {
                        // Handle invalid number formats gracefully
                        return "One of the provided numbers is invalid.";
                    }
                }
            }

            if (numbers.isEmpty()) {
                return "No valid numbers were provided for prime checking.";
            }

            List<Integer> primeNumbers = new ArrayList<>();
            for (int number : numbers) {
                if (isPrime(number)) {
                    primeNumbers.add(number);
                }
            }

            if (primeNumbers.isEmpty()) {
                return "None of the provided numbers are prime.";
            }

            // Convert list of primes to a comma-separated string
            return primeNumbers.stream()
                    .map(String::valueOf)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
        }

        return null; // Not a prime query
    }

    /**
     * Determines whether a given number is prime.
     *
     * @param number The number to check.
     * @return True if the number is prime, false otherwise.
     */
    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        // Only need to check up to the square root of the number
        int sqrt = (int) Math.sqrt(number);
        for (int i = 2; i <= sqrt; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Handles exponentiation queries like "What is 27 to the power of 74?"
     *
     * @param query The user query string.
     * @return The result of the exponentiation as a string, or null if not an exponentiation query.
     */
    private String handleExponentiation(String query) {
        // Pattern to match "What is 27 to the power of 74?"
        Pattern exponentiationPattern = Pattern.compile(
                "what is (\\d+) to the power of (\\d+)\\??",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = exponentiationPattern.matcher(query);
        if (matcher.find()) {
            String baseStr = matcher.group(1);
            String exponentStr = matcher.group(2);

            try {
                BigInteger base = new BigInteger(baseStr);
                int exponent = Integer.parseInt(exponentStr);
                if (exponent < 0) {
                    return "Negative exponents are not supported.";
                }
                BigInteger result = base.pow(exponent);
                return result.toString();
            } catch (NumberFormatException e) {
                return "Invalid numbers provided for exponentiation.";
            } catch (ArithmeticException e) {
                return "Error during exponentiation calculation.";
            }
        }

        return null; // Not an exponentiation query
    }

    /**
     * Handles special queries like finding numbers that are both squares and cubes.
     *
     * @param query The user query string.
     * @return The number(s) that satisfy the condition as a string, or null if not a special query.
     */
    private String handleSpecialQueries(String query) {
        // Pattern to match "Which of the following numbers is both a square and a cube: 1459, 4096, 1895, 676, 4474, 729, 2396?"
        Pattern specialPattern = Pattern.compile(
                "which of the following numbers is both a square and a cube[:\\s]+([\\d\\s,]+)\\??",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = specialPattern.matcher(query);
        if (matcher.find()) {
            String numbersStr = matcher.group(1);
            String[] numberTokens = numbersStr.split("[,\\s]+");
            List<Integer> numbers = new ArrayList<>();

            for (String token : numberTokens) {
                if (!token.isEmpty()) {
                    try {
                        numbers.add(Integer.parseInt(token));
                    } catch (NumberFormatException e) {
                        // Handle invalid number formats gracefully
                        return "One of the provided numbers is invalid.";
                    }
                }
            }

            List<Integer> squareAndCubes = new ArrayList<>();
            for (int number : numbers) {
                double sqrt = Math.sqrt(number);
                double cbrt = Math.cbrt(number);
                if (sqrt == (int) sqrt && cbrt == (int) cbrt) {
                    squareAndCubes.add(number);
                }
            }

            if (squareAndCubes.isEmpty()) {
                return "None of the provided numbers are both squares and cubes.";
            }

            // Convert list to a comma-separated string
            return squareAndCubes.stream()
                    .map(String::valueOf)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
        }

        return null; // Not a special query
    }
}
