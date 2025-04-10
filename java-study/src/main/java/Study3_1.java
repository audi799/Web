public class Study3_1 {
    public static void main(String[] args) {
        //int neg = -5;
        int neg = Integer.MIN_VALUE;
        int abs = Math.abs(neg);
//        int abs = Math.absExact(neg);
        System.out.println(abs);

        int a = 2000000000;
        int b = 2000000000;
        System.out.println(a + b);
//        System.out.println(Math.addExact(a, b));

        System.out.println("--multiplyFull--");
        int aa = 2000000000;
        int bb = 2;
        System.out.println(aa * bb);
        System.out.println(Math.multiplyFull(aa, bb));

        System.out.println("--가변인자--");
        System.out.println(sum(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    public static int sum(int... nums) { // int[]
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return sum;
    }
}
