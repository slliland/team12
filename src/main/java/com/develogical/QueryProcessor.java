package com.develogical;

public class QueryProcessor {

    public String process(String query) {

        if (query.toLowerCase().contains("shakespeare")) {
            return "William Shakespeare (26 April 1564 - 23 April 1616) was an " +
                    "English poet, playwright, and actor, widely regarded as the greatest " +
                    "writer in the English language and the world's pre-eminent dramatist.";
        }

        if (query.contains("your name")) {
            return "Jason2";
        }

        // Get all numbers in the query
        // e.g. Which of the following numbers is the largest: 1, 8, 90?
        if (query.contains("largest")) {
            String[] numbers = query.split(":")[1].split(",");
            int max = Integer.MIN_VALUE;
            for (String number : numbers) {
                int n = Integer.parseInt(number.trim());
                if (n > max) {
                    max = n;
                }
            }
            return String.valueOf(max);
        }

        // e.g. What is 5 plus 3
        if (query.contains("plus")) {
            String[] numbers = query.split("plus")[1].split(" ");
            int sum = 0;
            for (String number : numbers) {
                sum += Integer.parseInt(number.trim());
            }
            return String.valueOf(sum);
        }


        return "";
    }

}