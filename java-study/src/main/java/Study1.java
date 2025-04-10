public class Study1 {
    public static void main(String[] args) {
        long data = 8876L;

        float f = 3.14f;
        double d = 3.14d;

        String s = "김갑수";

        final int test = 5;

        byte b1 = 5;
        byte b2 = 3;
        byte b3 = b1;
//        b3 = b3 + b2; //이건 안됨
        b3 += b2; // 이건 됨.
    }
}
