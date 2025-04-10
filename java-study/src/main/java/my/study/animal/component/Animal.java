package my.study.animal.component;

public class Animal {
    private final String species; // 종: 고양이
    private final Gender gender; // 성별: F or M or N
    private String name; // 이름: 뽀삐
    private int age; // 나이

    protected Animal(String species, Gender gender) {
        this.species = species;
        this.gender = gender;
    }

    public void eat() {
        System.out.println(this.name + "이/가 먹었다.");
    }

    public void move() {
        System.out.println(this.name + "이/가 움직였다.");
    }

    public String getSpecies() {
        return species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException(age + "는 올바른 age 값이 아닙니다.");
        }
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }
}
