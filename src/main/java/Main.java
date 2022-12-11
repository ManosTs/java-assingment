import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String number = "";

        //insert number
        Scanner sc = new Scanner(System.in);
        System.out.print("Input a number:");
        number = sc.nextLine().trim();

        //convert string to array of integers split by space
        int[] numberArray = Arrays.stream(number.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        //phone number instance
        PhoneNumber phoneNumber = new PhoneNumber(numberArray);


        //first interpretation//run validateGreekNumber to validate the number
        System.out.println(phoneNumber.validateGreekNumber(numberArray));

        //handle ambiguities// return true to check if the method(void) ran without problem
        boolean isRun = phoneNumber.handleNumberAmbiguities(phoneNumber);
    }

}
