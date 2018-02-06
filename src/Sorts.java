import java.util.ArrayList;
import java.util.List;

/**
 * Sorts
 * Created on 2/16/2017
 *
 * This is a static class to contain various sorting methods
 *
 * Various sorts written in Java. Just for fun/practice. Originally began this project to give myself a basis for
 * writing bubble sort in MIPS assembly language.
 * I make no promises about the completeness or efficiency of this class or project.
 *
 * @author Tanner Kvarfordt
 * @version 1.0
 */
public final class Sorts {

    /**
     * Private constructor - no instantiation of top-level "static" class
     */
    private Sorts() {
    }

    /**
     * An O(n^2) algorithm for sorting.
     * https://en.wikipedia.org/wiki/Bubble_sort
     *
     * @param container the container to be sorted
     * @return the sorted container
     */
    public static <U extends Comparable<U>, T extends List<U>> T bubbleSort(T container) {
        for (int j = container.size() - 1; j >= 0; --j) {
            for (int i = 0; i < j; ++i) {
                if (container.get(i).compareTo(container.get(i + 1)) > 0) {
                    U temp = container.get(i + 1);
                    container.remove(i + 1);
                    container.add(i + 1, container.get(i));
                    container.remove(i);
                    container.add(i, temp);
                }
            }
        }

        return container;
    }

    /**
     * An O(n^2) algorithm for sorting.
     * https://en.wikipedia.org/wiki/Insertion_sort
     *
     * @param container the container to be sorted
     * @return the sorted container
     */
    public static <U extends Comparable<U>, T extends List<U>> T insertionSort(T container) {
        ArrayList<U> bucket = new ArrayList<>(container.size());

        for (U e : container) {
            if (bucket.isEmpty()) bucket.add(e);
            else {
                boolean inserted = false;
                for (int j = 0; !inserted && j <= bucket.size(); ++j) {
                    if (j == bucket.size()) {
                        bucket.add(e);
                        inserted = true;
                    } else if (e.compareTo(bucket.get(j)) < 0) {
                        bucket.add(j, e);
                        inserted = true;
                    }
                }
            }
        }

        container.clear();
        container.addAll(bucket);
        return container;
    }

    /**
     * An O(nlogn) algorithm for sorting.
     *
     * @param container to be sorted
     * @return the sorted container
     */
    public static <U extends Comparable<U>, T extends List<U>> T mergeSort(T container) {
        return mergeSort(container, 0, container.size() -1);
    }


    /**
     * An O(nlogn) algorithm for sorting.
     *
     * @param container to be sorted
     * @param i beginning of range to sort from
     * @param k end of range to sort to
     * @return the sorted container
     */
    public static <U extends Comparable<U>, T extends List<U>> T mergeSort(T container, int i, int k) {
        // TODO: split list into two sublists
        // TODO: sort each sublist recursively
        // TODO: merge the two sublists
        // TODO: return the sorted list
    }

    // TODO: write more sorts
}
