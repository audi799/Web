package my.study.animal;

import java.io.IOException;

public class Main5 {
    public static void main(String[] args) {
//        throw new NullPointerException(); // Unchecked
//        throw new IOException(); // Checked
        NullPointerException e = new NullPointerException();
        throw e;
    }
}
