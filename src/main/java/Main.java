import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String number = "";

        //insert number
        Scanner sc = new Scanner(System.in);
        System.out.print("Input a number:");
        number = sc.nextLine().trim();

        int[] numberArray = Arrays.stream(number.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        PhoneNumber phoneNumber = new PhoneNumber(numberArray);


        String isValid = phoneNumber.validateGreekNumber(numberArray).isValid() ? "VALID" : "INVALID";
        String numberStr = phoneNumber.getNumberToString(phoneNumber.validateGreekNumber(numberArray).getNumber());
        System.out.println(numberStr + " [phone number:" + isValid + "]");

        boolean isRun = phoneNumber.handleNumberAmbiguities(phoneNumber);

//        System.out.println(isRun);
    }

}
