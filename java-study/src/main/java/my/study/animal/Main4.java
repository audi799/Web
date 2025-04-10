package my.study.animal;

import my.study.animal.component.Gender;
import my.study.animal.component.inheritance.Eagle;
import my.study.animal.component.inheritance.Sparrow;
import my.study.animal.feature.Flyable;

public class Main4 {
    public static void main(String[] args) {
        Flyable eagle = new Eagle(Gender.FEMALE);
        Flyable sparrow = new Sparrow(Gender.FEMALE);

        eagle.fly(); // 자기꺼 override 되어있으니 자기꺼를 실행함.
        sparrow.fly(); // 자기꺼가 없으니 인터페이스의 기본 메서드 default fly() 를 실행한다.
    }
}
