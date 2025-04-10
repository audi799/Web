package my.study.animal;

public class Main5_questions {
    public static void main(String[] args) {
        System.out.println(sum("17", "3")); // 20
        System.out.println(sum("56", null)); // NULL
        System.out.println(sum("ㅋ", null)); // NULL
        System.out.println(sum(null, "ㅋ")); // NULL
        System.out.println(sum("56", "ㅋ")); // INVALID
        System.out.println(sum("2000000000", "2000000000")); // FLOW
        System.out.println(sum("-2000000000", "-2000000000")); // FLOW
    }

    public static String sum(String a, String b) {
        // 전달 받은 문자열 a와 b를 정수로 변환하여 그 정수를 합하고 다시 문자열로 변환하여 반환하는 메서드 sum 을 완성하세요
        // a, b 중 하나 이상 null 일 경우 "NULL"을,
        // a, b 중 하나 이상을 정수로 변환할 수 없는 경우 "INVALID"를,
        // 두 수의 합이 정수의 범위에 대해 오버플로우가 발생하는 경우, "FLOW"를 반환하세요.
        if (a == null | b == null) {
            return "NULL";
        }

        try {
            int convertA = Integer.parseInt(a);
            int convertB = Integer.parseInt(b);
            int sum = Math.addExact(convertA, convertB);

            return String.valueOf(sum);
        } catch (NumberFormatException e) {
            return "INVALID";
        } catch (ArithmeticException e) {
            return "FLOW";
        }
    }
}