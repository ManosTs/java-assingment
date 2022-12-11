import java.util.stream.Collectors;
import java.util.stream.IntStream;

class PhoneNumber {
    private int[] number;

    private boolean isValid;

    private String prefix;


    //default constructor
    public PhoneNumber() {
    }

    public PhoneNumber(int[] number) {
        this.number = number;
    }

    public int[] getNumber() {
        return number;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    //number array to string
    public String getNumberToString(int[] number) {
        return IntStream.of(number)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(""));
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        String isValid = this.isValid ? "VALID" : "INVALID";
        return getNumberToString(this.number) + " " + "[phone number: " + isValid + "]";
    }

    public PhoneNumber validateGreekNumber(int[] number) {
        PhoneNumber newPhone = new PhoneNumber(number);

        String phoneNumber = newPhone.getNumberToString(newPhone.getNumber());//number array to string

        newPhone.setValid(false);//default false

        //if the number is 10 digits and starts with 2 or 69
        if (
                phoneNumber.length() == 10 && (phoneNumber.startsWith("2") ||
                        phoneNumber.startsWith("69"))
        ) {
            newPhone.setValid(true);
        }

        //if the number is 14 digits and starts with 00302 or 003069
        if (
                phoneNumber.length() == 14 && (phoneNumber.startsWith("00302") ||
                        phoneNumber.startsWith("003069"))
        ) {
            newPhone.setValid(true);
        }

        handlePrefixes(phoneNumber);

        return newPhone;
    }

    private void handlePrefixes(String phoneNumber){
        //set the prefixes
        if (phoneNumber.startsWith("00302")) {
            setPrefix("00302");
        }

        if (phoneNumber.startsWith("003069")) {
            setPrefix("0030");
        }
        if (phoneNumber.startsWith("69")) {
            setPrefix("69");
        }

        if (phoneNumber.startsWith("2")) {
            setPrefix("2" + phoneNumber.substring(1, 3));
        }
    }

    public boolean handleNumberAmbiguities(PhoneNumber phoneNumber) {
        String prefix = phoneNumber.getPrefix();

        return twoDigitsAmbiguities(phoneNumber.getNumber(), prefix);
    }

    private boolean twoDigitsAmbiguities(int[] number, String prefix) {
        boolean isRun = false;

        //start index after the prefix
        int startIndex = 0;
        if (prefix.length() == 3) {
            startIndex = 2;
        }
        if (prefix.length() == 4) {
            startIndex = 3;
        }

        //first find the three digits ambiguities and then run the logic for two digit
        for (int index = startIndex; index < number.length - 1; ++index) {
            threeDigitsAmbiguities(number, index);
            if (String.valueOf(number[index]).length() == 2) {
                int ambiguity = generateAmbiguitiesFromTwoDigits(number[index]);
                number[index] = ambiguity;//update the array

                //print result
                System.out.println(validateGreekNumber(number));
                isRun = true;
            }
        }

        //if array does not have single-digit numbers execute the logic for three-digits ambiguity;
        if (!arrayHasOnlyOneDigitNumbers(number, prefix)) {
            for (int index = startIndex; index < number.length - 1; ++index) {
                threeDigitsAmbiguities(number, index);
                isRun = true;
            }
        }

        return isRun;
    }

    private void threeDigitsAmbiguities(int[] number, int index) {
        if (String.valueOf(number[index]).length() == 3 &&
                isMiddleNumberZero(number[index])) {
            String ambiguityS =
                    removeZero(Integer.toString(number[index]));//remove the zeros from integer
            number[index] = Integer.parseInt(ambiguityS);//parse string to integer and update array

            //print result
            System.out.println(validateGreekNumber(number));

        }
    }

    private boolean arrayHasOnlyOneDigitNumbers(int[] number, String prefix) {
        int startIndex = 0;
        if (prefix.length() == 3) {
            startIndex = 2;
        }

        if (prefix.length() == 4) {
            startIndex = 3;
        }
        int countDigits = 1;

        for (int i = startIndex; i < number.length - 1; i++) {
            if (number[i] / 10 == 0) {
                countDigits++;
            }
        }
        return countDigits == number.length - startIndex;
    }

    //generate two digit ambiguity
    private int generateAmbiguitiesFromTwoDigits(int number) {
        int firstDigit = 0;
        int lastDigit = number % 10;


        //find first digit
        while (number > 10) {
            number = number / 10;
            firstDigit = number;
        }

        //if last number is zero remove it, hence return just the first digit of number
        if (lastDigit == 0) {
            return firstDigit;
        }

        //otherwise two-digit number to three
        return (firstDigit * 100) + lastDigit;
    }


    //check if the middle digit is zero
    private boolean isMiddleNumberZero(int number) {
        int middle = 0;
        int a = number;
        int noOfDigits = 0;
        while (number != 0) {
            number = number / 10;
            noOfDigits++;
        }
        if (noOfDigits % 2 == 1) {//if
            for (int i = 0; i < (noOfDigits / 2) + 1; i++) {
                middle = a % 10;
                a = a / 10;
            }
            return middle == 0;//if middle is zero return true
        }

        return false;
    }

    private String removeZero(String str) {

        return str.replace("0", "");
    }
}
