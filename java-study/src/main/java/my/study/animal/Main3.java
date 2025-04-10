package my.study.animal;

import my.study.animal.component.Animal;
import my.study.animal.component.Gender;
import my.study.animal.component.inheritance.*;
import my.study.animal.feature.Flyable;

public class Main3 {
    public static void main(String[] args) {
        Animal[] animals = {
                new Chicken(Gender.MALE),
                new Eagle(Gender.FEMALE),
                new Hamster(Gender.MALE),
                new NorwayForest(Gender.FEMALE),
                new Pitbull(Gender.MALE),
                new Sparrow(Gender.FEMALE)
        };

        // 내 코드
        for (Animal animal : animals) {
            if (animal instanceof Flyable) {
                System.out.println(animal.getSpecies() + "은(는) 날 수 있다.");
            } else {
                System.out.println(animal.getSpecies() + "은(는) 날 수 없다.");
            }
        }

        // 강사님 코드
        for (Animal animal : animals) {
            String toPrint = String.format("%s은(는) 날수 %s.", animal.getSpecies(), animal instanceof Flyable ? "있다":"없다");
            System.out.println(toPrint);
        }
    }
}
