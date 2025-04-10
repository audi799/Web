import java.util.Arrays;

public class Study3_questions_2 {
    public static void main(String[] args) {
        System.out.println(getRadius(30));
        System.out.println(Arrays.toString(lotto()));
        System.out.println(rootOfQuadEq(2, 15, 5));
    }

    public static double getRadius(double area) {
        // 어떠한 (정)원의 넓이가 매개변수 area로 주어질 때, 해당 원의 반지름을 반환하는 메서드 getRadius를 완성하세요.
        // 원의 넓이를 구하는 공식은 πr^2 입니다. (이때 r은 반지름)
        // area = πr^2 -> r^2 = area / π = 나온 값을 루트처리.
        return Math.sqrt(area / Math.PI);
    }

    public static int[] lotto() {
        int[] result = new int[6];
        // 1이상 45 이하의 무작위 정수 6개를 인자로 가지는 배열을 반환하는 메서드 lotto를 완성하세요. 단, 인자는 겹치면 안됩니다.
        boolean duplication = false;

        for (int i = 0; i < 6; i++) {
            int randomValue = 0;

            // 0 ~ 45안에 들어가는 랜덤 숫자 만든다.
            while (randomValue == 0 || randomValue > 45) {
                randomValue = (int) (100 * Math.random());
            }

            // 중복된 숫자있는지 판별한다.
            for (int j = 0; j < i; j++) {
                if (result[j] == randomValue) {
                    duplication = true;
                    break;
                }
                else {
                    duplication = false;
                }
            }

            // 중복숫자가 있으면 다시 랜덤행, 그렇지않으면 숫자 저장
            if (duplication) {
                i--;
            } else {
                result[i] = randomValue;
            }
        }

        return result;
    }

    public static int rootOfQuadEq(double a, double b, double c) {
        // 이차방정식 y = ax^2 + bx + c 꼴에서 각 a, b, c가 매개변수로 주어질때 해당 이차방정식이 (서로다른) 실근을 가진다면 1을, 중근을 가진다면 0을, 허근을 가진다면 -1을 반환하는 메서드 rootOfQuadEq를 완성하세요
        // 이차방정식의 근의 공식은 (-b +-√(b^2 - 4ac)) / 2a 입니다.
        // D = b^2 - 4ac
        // 실근: D > 0 / 중근: D = 0 / 허근: D < 0
        double value = b * b - 4 * a * c;
        System.out.println("value: " + value);

        int result = 0;

        if (value > 0) {
            result = 1;
        } else if (value == 0) {
            result = 0;
        } else {
            result = -1;
        }

        return result;
    }
}
