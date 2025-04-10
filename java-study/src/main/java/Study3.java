public class Study3 {
    public static void main(String[] args) {
        System.out.println(Integer.parseInt("1100", 2)); // 2진법 -> 10진법으로 변환. 12
        System.out.println(Integer.parseInt("ff", 16)); // 16진법 -> 10진법으로 변환. 255

        Integer ir = 10;
        byte b = ir.byteValue();
        System.out.println(b);

        Integer ig = 5000;
        int ip1 = ig.intValue();
        int ip2 = ip1;

        Integer imx = Integer.MAX_VALUE;
        float f = imx.floatValue();
        double d = imx.doubleValue();
        System.out.println(imx); // 214783647
        System.out.println(f); // 2.14748365E9 (float 는 6~7자리까지 표현, 일부값 유실 가능)
        System.out.println(d); // 2.147483647E9

        Long lr = 500L;
        long lp1 = lr.longValue(); // Unnecessary unboxing
        long lp2 = lr;

        System.out.println(Double.NaN);
        System.out.println(0.0 / 0);
        System.out.println(Double.NaN == Double.NaN);

        System.out.println(100.0 / 0 == Double.POSITIVE_INFINITY);

        System.out.println("--double--");
        double posInf = +100.0 / 0;
        double negInf = -100.0 / 0;
        System.out.println(posInf > 0);
        System.out.println(negInf < 0);
        System.out.println(Double.isFinite(posInf));
        System.out.println(Double.isFinite(negInf));

        System.out.println("--NaN--");
        double someNaN = 0D / 0; // NaN
        System.out.println(someNaN == Double.NaN); // false
        System.out.println(Double.isNaN(someNaN)); // true
        
    }
}
