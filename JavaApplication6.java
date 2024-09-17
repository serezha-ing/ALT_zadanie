import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaApplication6 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calculate(String input) throws Exception {
        Matcher matcher = Pattern.compile("\"([^\"]{1,10})\"\\s*([+\\-*/])\\s*(\"([^\"]{1,10})\"|\\d{1,2})").matcher(input);

        if (!matcher.matches()) {
            throw new Exception("Неверный формат ввода");
        }

        String str1 = matcher.group(1);
        String operation = matcher.group(2);
        String operand = matcher.group(3);

        String result;
        switch (operation) {
            case "+" -> {
                if (!operand.startsWith("\"")) {
                    throw new Exception("Некорректное выражение для сложения");
                }
                String str2 = matcher.group(4);
                result = str1 + str2;
            }
            case "-" -> {
                if (!operand.startsWith("\"")) {
                    throw new Exception("Некорректное выражение для вычитания");
                }
                String str2 = matcher.group(4);
                result = str1.replaceFirst(Pattern.quote(str2), "");
            }
            case "*" -> {
                int multiplier = Integer.parseInt(operand);
                if (multiplier < 1 || multiplier > 10) {
                    throw new Exception("Множитель вне допустимого диапазона");
                }
                result = str1.repeat(multiplier);
            }
            case "/" -> {
                int divisor = Integer.parseInt(operand);
                if (divisor < 1 || divisor > 10) {
                    throw new Exception("Делитель вне допустимого диапазона");
                }
                result = str1.substring(0, str1.length() / divisor);
            }
            default -> throw new Exception("Неподдерживаемая операция");
        }

        if (result.length() > 40) {
            result = result.substring(0, 40) + "...";
        }

        return result;
    }
}