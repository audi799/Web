public class Study2 {
    public static void main(String[] args) {
        Food food = new Food(365);
        System.out.println(food.getKcal());

        String dt = "2025-04-07";
        String[] dtArray = dt.split("-"); // {"2025", "04", "07"}

        System.out.println(dtArray.length);
        for (String s: dtArray) {
            System.out.println(s);
        }
    }
}
