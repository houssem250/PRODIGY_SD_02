package com.example.guessnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NumberCategorizer {

    static List<String> categories;

    public static List<String> categorizeNumber(int number, int min, int max) {
        if (number < min || number > max) {
            System.out.println("Number out of range.");
            return null; // Return null instead of setting categories to null
        }

        categories = new ArrayList<>();

        // Parity
        String parity = (number % 2 == 0) ? "Even" : "Odd";
        categories.add(parity);

        // Range
        String range = (number <= (min + max) / 2) ? "Low" : "High";
        categories.add(range);

        // Divisibility
        List<Integer> divisors = generateDivisors(number);
        divisors.stream()
                .filter(divisor -> number % divisor == 0)
                .forEach(divisor -> categories.add("Divisible by " + divisor));

        // Magnitude
        String magnitude;
        if (number < 10) magnitude = "Single-digit";
        else if (number < 100) magnitude = "Double-digit";
        else if (number < 1000) magnitude = "Triple-digit";
        else magnitude = "Four-digit";
        categories.add(magnitude);

        // Power of Two
        boolean isPowerOfTwo = (number & (number - 1)) == 0;
        String powerOfTwo = isPowerOfTwo ? "Power of 2" : "Not a power of 2";
        categories.add(powerOfTwo);

        // Square Numbers
        int sqrt = (int) Math.sqrt(number);
        boolean isPerfectSquare = (sqrt * sqrt == number);
        String perfectSquare = isPerfectSquare ? "Perfect square" : "Not a perfect square";
        categories.add(perfectSquare);

        // Digit Sum
        int sumOfDigits = String.valueOf(number).chars().map(Character::getNumericValue).sum();
        String digitSum = (sumOfDigits % 2 == 0) ? "Sum of digits is even" : "Sum of digits is odd";
        categories.add(digitSum);

        // Binary Representation
        String binary = Integer.toBinaryString(number);
        int numberOfBits = binary.length();
        long numberOfOnes = binary.chars().filter(ch -> ch == '1').count();
        categories.add("Number of bits: " + numberOfBits);

        // Palindrome
        String numberStr = String.valueOf(number);
        String palindrome = new StringBuilder(numberStr).reverse().toString().equals(numberStr) ? "Palindrome" : "Not a palindrome";
        categories.add(palindrome);

        // Hexadecimal Representation
        String hex = Integer.toHexString(number).toUpperCase();
        String hexRepresentation;
        if (hex.length() == 1) hexRepresentation = "Single hexadecimal digit";
        else if (hex.length() == 2) hexRepresentation = "Two hexadecimal digits";
        else if (hex.length() == 3) hexRepresentation = "Three hexadecimal digits";
        else hexRepresentation = "Four hexadecimal digits";
        categories.add(hexRepresentation);

        return categories;
    }

    private static List<Integer> generateDivisors(int number) {
        // Generate divisors up to a reasonable limit based on the number
        int limit = (int) Math.sqrt(number);
        List<Integer> divisors = new ArrayList<>();

        for (int i = 1; i <= limit; i++) {
            if (number % i == 0) {
                if (i != 1 && i != number) {
                    divisors.add(i);
                }
                if (i != number / i && number / i != 1 && number / i != number) {
                    divisors.add(number / i);
                }
            }
        }

        return divisors.stream().sorted().collect(Collectors.toList());
    }

}
