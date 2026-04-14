package com.mycompany.mavenproject1;// folder my code is in

import java.util.Scanner; // take user input 



// were we save the user input for objects
class Login {
    // Stored user details after registration
    private String storedUsername;
    private String storedPassword;
    private String storedPhone;
    private String firstName;
    private String lastName;
    
// Six methods ////////////////////////////////////////
  // here this methods cheks,username if under 5 and has"_" 
    public boolean checkUserName(String username) {
        boolean hasUnderscore = username.contains("_");
        boolean correctLength = username.length() <= 5;
        return hasUnderscore && correctLength;
    }

   // method to chek pasword
    public boolean checkPasswordComplexity(String password) {
        boolean longEnough = password.length() >= 8;
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= 'A' && c <= 'Z') hasCapital = true;
            if (c >= '0' && c <= '9') hasNumber = true;
            if (c == '!' || c == '@' || c == '#' || c == '$' || c == '%' || c == '&' || c == '*') hasSpecial = true;
        }

        return longEnough && hasCapital && hasNumber && hasSpecial;
    }


     // Checks if cell phone number is a valid South African number.
   
    public boolean checkCellPhoneNumber(String phone) {
        // Regex: ^\\+27 = starts with +27, [0-9]{9} = nine digits, $ = end
        return phone.matches("^\\+27[0-9]{9}$");
    }

    
     // Registers a new user.
     // Returns success message or specific error message.
     
    public String registerUser(String username, String password, String phone, String fName, String lName) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // Save all details
        storedUsername = username;
        storedPassword = password;
        storedPhone = phone;
        firstName = fName;
        lastName = lName;

        return "User registered successfully!";
    }

    
     // Checks if login credentials match the stored ones.
     
    public boolean loginUser(String username, String password) {
        if (storedUsername == null) return false;
        return username.equals(storedUsername) && password.equals(storedPassword);
    }

    
     // Returns the appropriate message after login attempt.
    
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

// ======================== MAIN CLASS (APP ENTRY) ========================
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();
        boolean exit = false;

        System.out.println("===========================================");
        System.out.println("       WELCOME TO THE REGISTRATION PAGE    ");
        System.out.println("===========================================\n");

        while (!exit) {
            // Display menu
            System.out.println("==============");
            System.out.println("  SELECT OPTION");
            System.out.println("==============");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 1, 2, or 3.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    // REGISTRATION FLOW
                    System.out.println("\n--- REGISTRATION ---");
                    
                    System.out.print("Create username (must contain _ and be ≤5 chars): ");
                    String regUser = scanner.nextLine();
                    
                    System.out.print("Create password (8+ chars, 1 capital, 1 number, 1 special: !@#$%&*): ");
                    String regPass = scanner.nextLine();
                    
                    System.out.print("Enter cell phone number (format: +27XXXXXXXXX): ");
                    String regPhone = scanner.nextLine();
                    
                    System.out.print("Enter your first name: ");
                    String regFirst = scanner.nextLine();
                    
                    System.out.print("Enter your last name: ");
                    String regLast = scanner.nextLine();
                    
                    String regResult = login.registerUser(regUser, regPass, regPhone, regFirst, regLast);
                    System.out.println(regResult + "\n");
                    break;

                case 2:
                    // LOGIN FLOW
                    System.out.println("\n--- LOGIN ---");
                    
                    System.out.print("Username: ");
                    String loginUser = scanner.nextLine();
                    
                    System.out.print("Password: ");
                    String loginPass = scanner.nextLine();
                    
                    String loginStatus = login.returnLoginStatus(loginUser, loginPass);
                    System.out.println(loginStatus + "\n");
                    
                    // If login successful, we could break or stay – but brief just prints welcome
                    if (login.loginUser(loginUser, loginPass)) {
                        // You can add extra logic here if needed, e.g., break to exit after login
                        System.out.println("You have successfully logged in!\n");
                    }
                    break;

                case 3:
                    System.out.println("Goodbye! Thanks for using the Chat App.\n");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.\n");
            }
        }

        scanner.close();
    }
}
