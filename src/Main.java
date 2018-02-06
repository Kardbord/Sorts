import java.text.DecimalFormat;
import java.util.*;

/**
 * Sorts.Sorts
 * Created on 2/16/2017
 * <p>
 * Main class for the Sorts project
 * This class's primary purpose is for testing/demonstrating methods from the Sorts class.
 * To change the sort method used, uncomment the desired sort in the Main::printSortPrint method.
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

        // Make some things to sort
        ArrayList<Integer> a = new ArrayList<>(capacity);
        Vector<Character> v = new Vector<>(capacity);
        LinkedList<Double> l = new LinkedList<>();
        ArrayList<String> s = new ArrayList<>(capacity);
        Vector<String> v2 = new Vector<>(capacity);

        // Put some stuff in the things
        char c = 'a' + 9;
        for (int i = 0; i < capacity; ++i) {
            a.add(gen.nextInt(101));
            l.add(gen.nextDouble());
            s.add(randString(1));
            v2.add(randString(2));
            v.add(c);
            --c;
        }

        // Sort and print the things :)
        printSortPrint(a);
        System.out.println();
        printSortPrint(v);
        System.out.println();
        printSortPrint(l);
        System.out.println();
        printSortPrint(s);
        System.out.println();
        printSortPrint(v2);
    }

    /**
     * Prints container, sorts container using the uncommented sort method, prints the sorted container.
     *
     * @param container is the container to be printed, sorted, and printed.
     * @param <U>       is some comparable data type.
     * @param <T>       is a List of U.
     */
    private static <U extends Comparable<U>, T extends List<U>> void printSortPrint(T container) {
        printContainer(container);

        // container = Sorts.bubbleSort(container);
        // container = Sorts.insertionSort(container);
        Sorts.mergeSort(container);
        // More sort methods to come
        // TODO: provide all sorting methods here in a comment block

        System.out.print("|--- Sorted ---> ");

        printContainer(container);
    }

    /**
     * @param container will have its contents printed on a line
     */
    private static <U extends Comparable<U>, T extends List<U>> void printContainer(T container) {
        for (U e : container) {
            if (e instanceof Double || e instanceof Float) {
                DecimalFormat df = new DecimalFormat("0.00");
                System.out.print(df.format(e) + ", ");
            } else System.out.print(e + ", ");
        }
    }

    /**
     * Generates and returns a random String of the given length containing characters A through Z
     *
     * @param length of the random string to be generated
     * @return a random string of the given length
     */
    private static String randString(int length) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder rand = new StringBuilder();
        Random gen = new Random();
        for (int i = 0; i < length; ++i) {
            rand.append(candidateChars.charAt(gen.nextInt(candidateChars.length())));
        }
        return rand.toString();
    }
}