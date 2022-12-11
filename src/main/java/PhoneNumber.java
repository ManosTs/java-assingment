import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class PhoneNumber {
    private int[] number;

    private boolean isValid;

    private String prefix;

    private List<String> interpretations = new ArrayList<String>();

    public PhoneNumber(int[] number) {
        this.number = number;
    }

    public PhoneNumber() {

    }

    public int[] getNumber() {
        return number;
    }

    public boolean isValid() {
        return isValid;
    }

    public void addInterpretations(String interpretation) {
        this.interpretations.add(interpretation);
    }

    public List<String> getInterpretations() {
        return interpretations;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

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
        String phoneNumber = newPhone.getNumberToString(newPhone.getNumber());
        newPhone.setValid(false);

        //if the number is 10 digits but doesn't start with 2 or 69, false
        if (phoneNumber.length() == 10 &&
                phoneNumber.startsWith("2")) {
            newPhone.setValid(true);
        }


        if (phoneNumber.length() == 10 &&
                phoneNumber.startsWith("69")) {
            newPhone.setValid(true);
        }

        //if the number is 14 digits but doesn't start with 00302 or 003069, false
        if (phoneNumber.length() == 14 &&
                phoneNumber.startsWith("00302")) {
            newPhone.setValid(true);
        }

        if (phoneNumber.length() == 14 &&
                phoneNumber.startsWith("003069")) {
            newPhone.setValid(true);
        }

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

        return newPhone;
    }

    public boolean handleNumberAmbiguities(PhoneNumber phoneNumber) {
        String prefix = phoneNumber.getPrefix();

        return twoDigitsAmbiguities(phoneNumber.getNumber(), prefix);
    }

    private boolean twoDigitsAmbiguities(int[] number, String prefix) {
        boolean isRun = false;
        int startIndex = 0;
        if (prefix.length() == 3) {
            startIndex = 2;
        }

        if (prefix.length() == 4) {
            startIndex = 3;
        }

        for (int index = startIndex; index < number.length - 1; ++index) {
            threeDigitsAmbiguities(number, index);
            if (String.valueOf(number[index]).length() == 2) {
                int ambiguity = generateAmbiguitiesFromTwoDigits(number[index]);
                number[index] = ambiguity;
                String isValid = validateGreekNumber(number).isValid() ? "VALID" : "INVALID";
                String numberStr = getNumberToString(validateGreekNumber(number).getNumber());
                System.out.println(numberStr + " [phone number:" + isValid + "]");
                isRun = true;
            }
        }

        //if array does not have single digit numbers execute the logic for threedigits ambiguity;
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
                    removeZero(Integer.toString(number[index]));
            number[index] = Integer.parseInt(ambiguityS);
            String isValid = validateGreekNumber(number).isValid() ? "VALID" : "INVALID";
            String numberStr = getNumberToString(validateGreekNumber(number).getNumber());
            System.out.println(numberStr + " [phone number:" + isValid + "]");

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

    private int generateAmbiguitiesFromTwoDigits(int number) {
        int firstDigit = 0;
        int lastDigit = number % 10;


        while (number > 10) {
            number = number / 10;
            firstDigit = number;
        }
        if (lastDigit == 0) {
            return firstDigit;
        }
        return (firstDigit * 100) + lastDigit;
    }

    private boolean isMiddleNumberZero(int number) {
        int middle = 0;
        int a = number;
        int noOfDigits = 0;
        while (number != 0) {
            number = number / 10;
            noOfDigits++;
        }
        if (noOfDigits % 2 == 1) {
            for (int i = 0; i < (noOfDigits / 2) + 1; i++) {
                middle = a % 10;
                a = a / 10;
            }
            return middle == 0;
        }

        return false;
    }

    private String removeZero(String str) {

        return str.replace("0", "");
    }
}
