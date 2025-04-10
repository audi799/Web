public class StudyString {
    public static void main(String[] args) {
        String name = "김갑수";
        int age = 78;
        double eyeSight = 0.4;

        String toPrint = String.format("이름은 %s, 나이는 %d살, 시력은 %.1f이다.", name, age, eyeSight);
        System.out.println(toPrint);

        String joinPrint = String.join("-", "2024", "04", "04");
        System.out.println(joinPrint);

        System.out.println("hello".endsWith("lo"));
        System.out.println("hello".startsWith("he"));

        String equalsData = "school";
        System.out.println(equalsData.equals("school"));
        System.out.println(equalsData.equalsIgnoreCase("SCHOOL"));

        System.out.println("ㅋ".repeat(5));

        String contact = "공일공-1234-55oo";
        contact = contact.replaceAll("[^0-9]", ""); // 숫자가 아닌걸 모두 빈문자열("")로 치환.
        System.out.println(contact);

        String sOriginal = "    hello    ";
        String sStrip = sOriginal.strip();
        String sStripL = sOriginal.stripLeading();
        String sStripT = sOriginal.stripTrailing();
        System.out.println(sOriginal);
        System.out.println(sStrip);
        System.out.println(sStripL);
        System.out.println(sStripT);

        String charArray = "Hello World";
        char[] cs = charArray.toCharArray();
        for (char c : cs) {
            System.out.println(c);
        }
    }
}
