
// Purpose: Encode a string into morse code, or decode a string of morse code
import java.util.Scanner;
import java.lang.StringBuilder;
import java.util.HashMap;

//' ' seperates letters, '/' seperates words
public class mCode {
    public static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        // initialize necessary variables
        String input = "";
        HashMap<Character, String> map = new HashMap<>();
        map = fillMap(map);
        // main loop
        while (true) {
            mainFunc(map);
            System.out.println("Would you like to encode or decode again? 'Y' for yes, anything else for no ");
            input = s.nextLine();
            if (input.toUpperCase().equals("Y")) {
                continue;
            } else {
                break;
            }
        }
        System.out.println("Thank you!");
        s.close();
    }

    private static void mainFunc(HashMap<Character, String> map) {
        String option = "", input = "";
        // while loop in order to handle invalid options
        while (true) {
            System.out.println("Encode or Decode? Input E or D: ");
            option = s.nextLine();
            System.out.println();

            if (option.toUpperCase().equals("E")) {

                System.out.println("What message would you like to encode?: ");
                input = s.nextLine();
                System.out.println("Your encoded message is: ");
                System.out.println(encode(input.toUpperCase(), map));
                break;

            } else if (option.toUpperCase().equals("D")) {

                System.out.println("Make sure that each 'letter' is seperated");
                System.out.println("by a space and each 'word' is seperated with ' / '\n");
                System.out.println("What message would you like to decode?: ");
                input = s.nextLine();
                input += " ";
                System.out.println("Your decoded message is: ");
                System.out.println(decode(input.toUpperCase(), map));
                break;

            } else {

                System.out.println("Invalid option.");
                continue;

            }
        }
    }

    // method to build encoded string
    private static String encode(String input, HashMap<Character, String> map) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            // Words in morse code are seperated by " / "
            if (c == ' ') {
                result.append(" / ");
            } else {
                String morse = map.get(c);
                morse += " ";
                result.append(morse);
            }
        }
        return result.toString();
    }

    // method to decode input
    private static String decode(String input, HashMap<Character, String> map) {
        StringBuilder result = new StringBuilder();
        String[] inputs = input.split(" ");
        for (String val : inputs) {
            // ' ' in morse code is " / ",
            // so after split() there will be string in inputs[] that is "/",
            // which must be removed
            if (val.equals("/")) {
                result.append("");
            } else {
                char letter = getKeyFromValue(map, val);
                result.append(letter);
            }
        }
        return result.toString();
    }

    // helper method for decode
    private static char getKeyFromValue(HashMap<Character, String> map, String value) {
        for (Character c : map.keySet()) {
            if (map.get(c).equals(value)) {
                return c;
            }
        }
        return ' ';
    }

    // helper method that fills HashMap with the pattern
    // <English Symbol, Morse Code Sequence>
    private static HashMap<Character, String> fillMap(HashMap<Character, String> map) {
        // All letters
        map.put('A', ".-");
        map.put('B', "-...");
        map.put('C', "-.-.");
        map.put('D', "-..");
        map.put('E', ".");
        map.put('F', "..-.");
        map.put('G', "--.");
        map.put('H', "....");
        map.put('I', "..");
        map.put('J', ".---");
        map.put('K', "-.-");
        map.put('L', ".-..");
        map.put('M', "--");
        map.put('N', "-.");
        map.put('O', "---");
        map.put('P', ".--.");
        map.put('Q', "--.-");
        map.put('R', ".-.");
        map.put('S', "...");
        map.put('T', "-");
        map.put('U', "..-");
        map.put('V', "...-");
        map.put('W', ".--");
        map.put('X', "-..-");
        map.put('Y', "-.--");
        map.put('Z', "--..");

        // Numbers
        map.put('0', "-----");
        map.put('1', ".----");
        map.put('2', "..---");
        map.put('3', "...--");
        map.put('4', "....-");
        map.put('5', ".....");
        map.put('6', "-....");
        map.put('7', "--...");
        map.put('8', "---..");
        map.put('9', "----.");

        // Symbols
        map.put('.', ".-.-.-");
        map.put(',', "--..--");
        map.put('?', "..--..");
        map.put('!', "-.-.--");
        map.put(':', "---...");
        map.put('"', ".-..-.");
        map.put('\'', ".----.");
        map.put('=', "-...-");
        map.put('/', "-..-.");
        map.put('(', "-.--.");
        map.put(')', "-.--.-");
        map.put('&', ".-...");
        map.put('+', ".-.-.");
        map.put('-', "-....-");
        map.put('_', "-....-");
        map.put('%', "----- -..-. -----");
        map.put('@', ".--.-.");

        return map;
    }
}