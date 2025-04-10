package my.study.animal;

import my.study.animal.component.Gender;
import my.study.animal.component.inheritance.Chicken;
import my.study.animal.component.inheritance.Eagle;
import my.study.animal.component.inheritance.Pitbull;
import my.study.animal.feature.Flyable;

public class Main2 {
    public static void main(String[] args) {
        Pitbull pitbull = new Pitbull(Gender.MALE);

        System.out.println(pitbull.getGender().getDisplayText());

        Eagle eagle = new Eagle(Gender.FEMALE);
        Chicken chicken = new Chicken(Gender.MALE);

        Flyable eagleFlyable = eagle;
        eagleFlyable.fly();
    }
}
