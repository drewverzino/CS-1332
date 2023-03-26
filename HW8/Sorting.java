import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Andrew Verzino
 * @version 1.0
 * @userid averzino3
 * @GTID 903696663
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("One of your parameters is null.");
        }

        for (int i = arr.length - 1; i >= 1; i--) {
            int maxIdx = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[maxIdx]) > 0) {
                    maxIdx = j;
                }
            }

            T temp = arr[i];
            arr[i] = arr[maxIdx];
            arr[maxIdx] = temp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("One of your parameters is null.");
        }

        int start = 0;
        int end = arr.length - 1;
        int swapped = -1;
        while (end > start) {
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = i;
                }
            }
            if (swapped == -1) {
                break;
            }
            end = swapped;
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    T temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                    swapped = i;
                }
            }
            start = swapped;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("One of your parameters is null.");
        }

        int length = arr.length;
        int mid = length / 2;

        T[] leftArr = (T[]) new Object[mid];
        T[] rightArr = (T[]) new Object[length - mid];

        for (int i = 0; i < mid; i++) {
            leftArr[i] = arr[i];
        }

        for (int i = mid; i < length; i++) {
            rightArr[i - mid] = arr[i];
        }

        if (length != 1) {
            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);
        }


        int left = 0;
        int right = 0;
        int curr = 0;

        while (left < mid && right < length - mid) {
            if (comparator.compare(leftArr[left], rightArr[right]) <= 0) {
                arr[curr] = leftArr[left];
                left++;
            } else {
                arr[curr] = rightArr[right];
                right++;
            }
            curr++;
        }

        while (left < mid) {
            arr[curr] = leftArr[left];
            curr++;
            left++;
        }

        while (right < length - mid) {
            arr[curr] = rightArr[right];
            curr++;
            right++;
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null || comparator == null || rand == null || k < 1 || k > arr.length) {
            throw new IllegalArgumentException("One of your input parameters is incorrect or null.");
        }

        return kthSelect(arr, 0, arr.length - 1, k, comparator, rand);
    }

    /**
     *
     * @param <T> the generic type of the parameter arr
     * @param arr the original array we are performing the algorithm on
     * @param left the left most index of our partition
     * @param right the right most index of our partition
     * @param k the number used to find the kth smallest value
     * @param comparator the object used to make comparisons within the array
     * @param rand the random object used to generate an integer between [left, right]
     * @return value at index k - 1
     */
    private static <T> T kthSelect(T[] arr, int left, int right, int k, Comparator<T> comparator, Random rand) {
        int pivotIndex = rand.nextInt(right - left + 1) + left;
        T pivot = arr[pivotIndex];
        arr[pivotIndex] = arr[left];
        arr[left] = pivot;
        int i = left + 1;
        int j = right;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        T temp = arr[j];
        arr[j] = arr[left];
        arr[left] = temp;

        if (j == k - 1) {
            return arr[j];
        }

        if (j > k - 1) {
            return kthSelect(arr, left, j - 1, k, comparator, rand);
        } else {
            return kthSelect(arr, j + 1, right, k, comparator, rand);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Your array is null.");
        }

        ArrayList<Integer>[] buckets = (ArrayList<Integer>[]) new ArrayList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new ArrayList<>();
        }

        int mod = 10;
        int div = 1;
        boolean cont = true;

        while (cont) {
            cont = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    cont = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new ArrayList<Integer>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            int arrIdx = 0;
            for (ArrayList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[arrIdx++] = num;
                    }
                    bucket.clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Your list is null.");
        }
        int[] sorted = new int[data.size()];
        PriorityQueue<Integer> items = new PriorityQueue<>(data);
        for (int i = 0; i < data.size(); i++) {
            sorted[i] = items.remove();
        }
        return sorted;
    }
}
