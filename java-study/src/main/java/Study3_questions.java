import java.util.Arrays;

public class Study3_questions {
    public static void main(String[] args) {
        System.out.println(max());
        System.out.println(max(Double.NaN, 57, -6)); // 57
        System.out.println(round(3.141592, 2)); // 3.14
        System.out.println(round(Double.NaN, 10)); // NaN

    }

    public static double max(double... nums) {
        // 매개변수 가변인자 nums가 가지는 값 중 가장 큰 값을 반환하는 메서드 max를 완성하세요.
        // 단, nums가 null이거나 길이가 0이라면 0.0을 반환하세요. 추가로, NaN은 검사 대상에서 제외하세요.(모든 인자가 검사 대상이 아니라면 이때도 마찬가지로 0.0을 반환하세요)

        if (nums == null || nums.length == 0) {
            return 0.0;
//            return 0D;
        }

        double max = Double.NaN;
        for (double num : nums) {
            if (Double.isNaN(num)) {
                continue;
            }

            if (Double.isNaN(max) || num > max) {
                max = num;
            }
        }
        return Double.isNaN(max) ? 0.0 : max;

//        int nanCount = 0;
//        int valueCount = 0;
//        for (int i = 0; i < nums.length; i++) {
//            if (Double.isNaN(nums[i])) {
//                nanCount++;
//                continue;
//            }
//            valueCount++;
//        }
//
//        double[] values = new double[valueCount];
//        for (int i = 0; i < values.length; i++) {
//            if (Double.isNaN(nums[i])) {
//                continue;
//            }
//            values[i] = nums[i];
//        }
//
//        if (nums.length == nanCount) {
//            return 0.0;
//        }
//
//        Arrays.sort(values);
//        double max = values[values.length - 1];
//
//        return max;
    }

    public static double round(double num, int digit) {
        // 매개변수 num에 대해 소수점 digit 자리까지 반올림하여 반환하는 메서드 round를 완성하세요.
        // 단, num이 무한하거나 NaN일 경우 그대로 반환하세요. 추가로 digit이 10보다 크다면 10을 할당하여 사용하고, 0보다 작으면 0을 할당하여 사용하세요.

        if (Double.isInfinite(num) || Double.isNaN(num)) {
            return num;
        }
//        1.
//        if (digit > 10) {
//            digit = 10;
//        }
//        2.
//        digit = digit > 10 ? 10 : digit;
//        3.
        digit = Math.min(digit, 10);
        digit = Math.max(digit, 0);

        final double factor = Math.pow(10, digit);
        num *= factor;
        num = Math.round(num);
        num /= factor;

        return num;
    }
}
