package my.study.animal;

import my.study.animal.component.Animal;
import my.study.animal.component.Gender;
import my.study.animal.component.inheritance.Hamster;
import my.study.animal.component.inheritance.NorwayForest;
import my.study.animal.component.inheritance.Pitbull;

public class Main {
    public static void main(String[] args) {
//        Animal dog = new Animal("핏불테리어", "M");
//        dog.setName("뽀삐");
//        dog.setAge(2);
//        dog.eat();
//        dog.move();
//
//        Animal cat = new Animal("핏불태리어", "F");
//        cat.setName("순자");
//        cat.setAge(3);
//        cat.eat();
//        cat.move();

//        Pitbull pitbull = new Pitbull("F");
//        pitbull.setName("뽀삐");

        Animal[] animals = {
                new Pitbull(Gender.FEMALE),
                new NorwayForest(Gender.FEMALE),
                new Pitbull(Gender.FEMALE),
                new Hamster(Gender.FEMALE),
                new NorwayForest(Gender.FEMALE),
                new NorwayForest(Gender.FEMALE),
                new Pitbull(Gender.FEMALE),
                new Hamster(Gender.FEMALE),
                new Hamster(Gender.FEMALE),
                new NorwayForest(Gender.FEMALE),
                new Pitbull(Gender.FEMALE),
        };

        int pitbullCount = 0;
        int norwayForestCount = 0;
        int hamsterCount = 0;
        // 코드 작성
        // - getSpecies()를 포함한 문자열 비교 ㄴㄴ
        // - Animal 및 이름 상속받는 클래스 구조 변경 ㄴㄴ
        // - 연산자 파일 잘 볼 것

        for (Animal animal : animals) {
            if (animal instanceof Pitbull) {
                pitbullCount++;
            }
            if (animal instanceof NorwayForest) {
                norwayForestCount++;
            }
            if (animal instanceof Hamster) {
                hamsterCount++;

            }
        }

        System.out.println("핏불: " + pitbullCount + "마리");
        System.out.println("노르웨이숲: " + norwayForestCount + "마리");
        System.out.println("햄스터: " + hamsterCount + "마리");
    }
}
