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
 * I make no promises about the completeness or efficiency of this class or project as a whole.
 *
 *
 * TODO - README: https://www.javaworld.com/article/2077424/learn-java/does-java-pass-by-reference-or-pass-by-value.html
 * This README is more for the developer's own sanity than anything else.
 * I switch between languages a lot and get confused about how each one handles parameter passing (by reference or by value).
 * TL;DR: Java passes object references by value, therefore changes made to those objects from within a method
 * persist after that method terminates.
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
     */
    public static <U extends Comparable<U>, T extends List<U>> void bubbleSort(T container) {
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
    }

    /**
     * An O(n^2) algorithm for sorting.
     * https://en.wikipedia.org/wiki/Insertion_sort
     *
     * @param container the container to be sorted
     */
    public static <U extends Comparable<U>, T extends List<U>> void insertionSort(T container) {
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
    }

    /**
     * An O(nlogn) algorithm for sorting.
     * https://en.wikipedia.org/wiki/Merge_sort
     *
     * @param container to be sorted
     */
    public static <U extends Comparable<U>, T extends List<U>> void mergeSort(T container) {
        mergeSort(container, 0, container.size() - 1);
    }

    /**
     * An O(nlogn) algorithm for sorting.
     *
     * @param container to be sorted
     * @param i beginning of range to sort from
     * @param k end of range to sort to
     */
    @SuppressWarnings("WeakerAccess")
    public static <U extends Comparable<U>, T extends List<U>> void mergeSort(T container, int i, int k) {
        if (i == k) return;
        int mid = (i + k) / 2;
        mergeSort(container, i, mid);
        mergeSort(container, mid + 1, k);
        merge(container, i, mid, k);
    }

    /**
     * Merge two sorted sub-lists into one list.
     * O(n)
     *
     * @param container the overarching container
     * @param i beginning index of first sublist
     * @param mid ending index of first sublist; @mid + 1 is the beginning index of the second sublist
     * @param k ending index of second sublist
     */
    private static <U extends Comparable<U>, T extends List<U>> void merge(T container, int i, int mid, int k) {
        ArrayList<U> mergedList = new ArrayList<>();
        int a = i;
        int b = mid + 1;
        while (a <= mid && b <= k) {
            if (container.get(a).compareTo(container.get(b)) < 1) {
                mergedList.add(container.get(a));
                ++a;
            } else {
                mergedList.add(container.get(b));
                ++b;
            }
        }

        // copy any remaining items
        while (a <= mid) {
            mergedList.add(container.get(a));
            ++a;
        }

        while (b <= k) {
            mergedList.add(container.get(b));
            ++b;
        }

        for (int j = i, d = 0; j <= k; ++j, ++d) {
            container.set(j, mergedList.get(d));
        }
    }

    // TODO: write more sorts
    // TODO: quicksort, sleepsort, selectionsort
}
