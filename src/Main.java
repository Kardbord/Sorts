import java.util.ArrayList;
import java.util.List;
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
     * @param args command line args that I don't use in this project
     */
    public static void main(String[] args) {
        Random gen = new Random();

        int capacity = 10;
        ArrayList<Integer> c = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; ++i) {
            c.add(gen.nextInt(101));
        }

        printSortPrint(c);

    }

    private static <U extends Comparable<U>, T extends List<U>> void printSortPrint(T container) {
        for (U e : container) {
            System.out.print(e + ", ");
        }

        container = Sorts.bubbleSort(container);

        System.out.print("|--- Sorted ---> ");

        for (U e : container) {
            System.out.print(e + ", ");
        }
    }
}