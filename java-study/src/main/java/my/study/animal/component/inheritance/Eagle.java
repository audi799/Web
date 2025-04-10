package my.study.animal.component.inheritance;

import my.study.animal.component.Gender;
import my.study.animal.feature.Flyable;

public class Eagle extends Bird implements Flyable {
    public Eagle(Gender gender) {
        super("독수리", gender);
    }

    @Override
    public void fly() {
        System.out.println(this.getSpecies() + "이/가 날았다.");
    }
}
