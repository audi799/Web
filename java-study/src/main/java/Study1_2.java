public class Study1_2 {
    public static void main(String[] args) {
        String s1 = new String("Hello");
        String s2 = new String("Hello");
        System.out.println(s1 == s2);
        // 위의 형태 문자열은 동등비교하면 안됨.

        String t1 = "Hi";
        String t2 = "Hi";
        System.out.println(t1 == t2);
        // 위의 형태는 문자열 동등비교하면 true로 나옴.
    }
}
