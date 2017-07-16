import java.util.ArrayList;
import java.util.Random;

/**
 * Sorts.Sorts
 * Created on 2/16/2017
 *
 * Sorts.Sorts.Main class for the Sorts.Sorts project
 * This class's primary purpose is for testing methods from Sorts.Sorts.java
 *
 * @author Tanner Kvarfordt
 * @version 1.0
 */
public class Main {

    /**
     * @param args command line args
     */
    public static void main(String[] args) {
        Random gen = new Random();

        int capacity = 10;
        ArrayList<Integer> c = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; ++i){
            c.add(gen.nextInt(101));
        }

        for(Integer e : c){
            System.out.println(e);
        }

        System.out.println("---------------------------------------------");

        c = Sorts.bubbleSort(c);

        for (Integer e: c){
            System.out.println(e);
        }
    }
}