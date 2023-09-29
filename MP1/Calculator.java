// Group 21: Alberto Luis, Po-Chieh Wang

import java.util.Scanner;

// Define the class Calculator
public class Calculator {
    private String name;

    // Define the addition function
    public float addition(float A, float B) {
        return A + B;
    }

    // Define the subtraction function
    public float subtraction(float A, float B) {
        return A - B;
    }

    // Define the multiplicaiton function
    public float multiplication(float A, float B) {
        return A * B;
    }

    // Set team name for welcome message
    public void setname(String N) {
        this.name = N;
    }

    // Print the welcome message
    public void getName() {
        String message = String.format("Welcome to the Calculator designed by %s .\r\n" + //
                "Enter A to Add, S to Subtract , M to Multiply , and Q to quit.", name);
        System.out.println(message);
    }

    // Begin main program

    public static void main(String[] args) {
        // Create a new object of type: Calculator
        Calculator mycalc = new Calculator();
        mycalc.setname("Group 21");               // Group 21
        Scanner scanner = new Scanner(System.in);   // Begin scanner tool for user input
        boolean flag = false;                       // Flag to determine if program should continue to run

        // Begin while loop to run calculator program
        while (true) {
            mycalc.getName();                                   // Print the welcome message to the console
            String arithmeticOperation = scanner.nextLine();    // Define new variable that sets the operation to be calculated

            switch (arithmeticOperation) {                      // Switch to determine which operation to perform
                case "A":   // Add
                case "S":   // Subtract
                case "M":   // Multiply
                    break;
                case "Q":   // Quit program
                    flag = true;
                    break;
                default:
                    continue;
            }

            if (flag == true) { // Method to break out of the program
                break;
            }

            // Begin to take arguments in from user in try catch block to catch errors
            float a;                                            // First argument
            System.out.println("Enter argument 1");
            try{
                a = Float.parseFloat(scanner.nextLine());
            }
            catch(Exception e){
                System.out.println("Wrong type of input");
                continue;
            }

            System.out.println("Enter argument 2");
            float b;                                            // Second argument
            try{
                b = Float.parseFloat(scanner.nextLine());
            }
            catch(Exception e){
                System.out.println("Wrong type of input");
                continue;
            }

            // Perform the arithmetic operation
            switch (arithmeticOperation) {
                case "A":
                    System.out.println("The sum of " + a + " and " + b + " is " + mycalc.addition(a, b));
                    break;
                case "S":
                    System.out.println("The difference of " + a + " and " + b + " is " + mycalc.subtraction(a, b));
                    break;
                case "M":
                    System.out.println("The multiplication of " + a + " and " + b + " is " + mycalc.multiplication(a, b));
                    break;
                default:
                    continue;
            }

        }

        scanner.close();    // No need for further user input
    }
}