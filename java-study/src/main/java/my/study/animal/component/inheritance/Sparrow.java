package my.study.animal.component.inheritance;

import my.study.animal.component.Gender;
import my.study.animal.feature.Flyable;

public class Sparrow extends Bird implements Flyable {
    public Sparrow(Gender gender) {
        super("참새", gender);
    }

    @Override
    public void fly() {
        System.out.println(this.getSpecies() + "이/가 날았다.");
    }
}
