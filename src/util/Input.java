package util;

import java.math.BigDecimal;
import java.util.Scanner;

import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
import exceptions.InvalidMobileNumberException;

public class Input {

    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String label) {
        while (true) {
            System.out.print("Enter " + label + ": ");
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("" + label + " cannot be empty");
                }
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int getInt(String label) {
        while (true) {
            System.out.print("Enter " + label + ": ");
            try {
                int result = scanner.nextInt();
                scanner.nextLine(); // consume the newline
                return result;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    public static double getDouble(String label) {
        while (true) {
            System.out.print("Enter " + label + ": ");
            try {
                double result = scanner.nextDouble();
                scanner.nextLine(); // consume the newline
                return result;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    public static boolean getBoolean(String label) {
        while (true) {
            System.out.print("Enter " + label + ": ");
            try {
                boolean result = scanner.nextBoolean();
                scanner.nextLine(); // consume the newline
                return result;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter true or false.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    public static BigDecimal getBigDecimal(String label) {
        while (true) {
            System.out.print("Enter " + label + ": ");
            try {
                String input = scanner.nextLine().trim();
                BigDecimal result = new BigDecimal(input);
                return result;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }

    public static String getEmail() {
        while (true) {
            System.out.print("Enter email: ");
            try {
                String input = scanner.nextLine().trim();
                if (!Validation.isValidEmail(input)) {
                    throw new InvalidEmailException("Email is not valid");
                }
                return input;
            } catch (InvalidEmailException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String getPassword() {
        System.out.println(
                "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and no special character.");
        while (true) {
            System.out.print("Enter password: ");
            try {
                String input = scanner.nextLine().trim();
                if (!Validation.isValidPassword(input)) {
                    throw new InvalidPasswordException("Password is not valid");
                }
                return input;
            } catch (InvalidPasswordException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String getMobileNo() {
        while (true) {
            System.out.print("Enter mobile number: ");
            try {
                String input = scanner.nextLine().trim();
                if (!Validation.isValidMobileNo(input)) {
                    throw new InvalidMobileNumberException("Mobile number is not valid");
                }
                return input;
            } catch (InvalidMobileNumberException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
