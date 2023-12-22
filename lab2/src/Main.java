import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    static ArrayList<Integer> new_array = new ArrayList<>();
    public enum SortingType{BubbleSorter, ShellSorter, MergeSorter, QuickSorter};

   interface Sorted
    {
        public ArrayList<Integer> sort(ArrayList<Integer> input);
    }

    static class BubbleSorter implements Sorted {
        @Override
        public ArrayList<Integer> sort(ArrayList<Integer> input)
        {
            new_array = input;
            int n = input.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (new_array.get(j) > new_array.get(j + 1)) {
                        Collections.swap(new_array, j, j + 1);
                    }
                }
            }
            return new_array;
        }
    }

    static class ShellSorter implements Sorted {
        @Override
        public ArrayList<Integer> sort(ArrayList<Integer> input)
        {
            new_array = input;
            int n = input.size();
            for (int she = n / 2; she > 0; she /= 2) {
                for (int i = she; i < n; i++) {
                    int temp = new_array.get(i);
                    int j;
                    for (j = i; j >= she && new_array.get(j - she) > temp; j -= she) {
                        new_array.set(j, new_array.get(j - she));
                    }
                    new_array.set(j, temp);
                }
            }
            return new_array;
        }
    }

    static class MergeSorter implements Sorted {
        @Override
        public ArrayList<Integer> sort(ArrayList<Integer> input)
        {
            new_array = input;
            mergeSort(new_array);
            return new_array;
        }
        public static void mergeSort(ArrayList<Integer> new_array) {
            if (new_array.size() > 1) {
                int mid = new_array.size() / 2;
                ArrayList<Integer> left = new ArrayList<>(new_array.subList(0, mid));
                ArrayList<Integer> right = new ArrayList<>(new_array.subList(mid, new_array.size()));

                mergeSort(left);
                mergeSort(right);

                merge(new_array, left, right);
            }
        }

        private static void merge(ArrayList<Integer> new_array, ArrayList<Integer> left, ArrayList<Integer> right) {
            int i = 0, j = 0, k = 0;
            while (i < left.size() && j < right.size()) {
                if (left.get(i) < right.get(j)) {
                    new_array.set(k++, left.get(i++));
                } else {
                    new_array.set(k++, right.get(j++));
                }
            }

            while (i < left.size()) {
                new_array.set(k++, left.get(i++));
            }

            while (j < right.size()) {
                new_array.set(k++, right.get(j++));
            }
        }
    }
    static class QuickSorter implements Sorted {
        @Override
        public ArrayList<Integer> sort(ArrayList<Integer> input) {
            new_array = input;
            quickSort(new_array, 0, new_array.size() - 1);
            return new_array;
        }

        private void quickSort(ArrayList<Integer> arr, int low, int high) {
            if (low < high) {
                int pi = partition(arr, low, high);

                quickSort(arr, low, pi - 1);
                quickSort(arr, pi + 1, high);
            }
        }

        private int partition(ArrayList<Integer> arr, int low, int high) {
            int pivot = arr.get(high);
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (arr.get(j) < pivot) {
                    i++;
                    Collections.swap(arr, i, j);
                }
            }

            Collections.swap(arr, i + 1, high);
            return i + 1;
        }
    }

    public class SortingFactory {
        public static Sorted createSorter(SortingType algorithm) {
            switch (algorithm) {
                case BubbleSorter:
                    return new BubbleSorter();
                case ShellSorter:
                    return new ShellSorter();
                case MergeSorter:
                    return new MergeSorter();
                case QuickSorter:
                    return new QuickSorter();
                default:
                    throw new IllegalArgumentException("Error in name of algoritm");
            }
        }
    }

    public static void FillArr(ArrayList<Integer> input, int n)
    {
        Random random = new Random();
        System.out.printf("Array: [");
        for (int i = 0; i < n; i++) {
            input.add(random.nextInt(n));
            if ((n < 100 && i < n-1) || (n >= 100 && i < 49))
              System.out.printf(input.get(i) + ", ");
        }
        if (n >=100)
            System.out.printf(input.get(49) + "...]\n");
        else
            System.out.printf(input.get(n-1) + "]\n");
        return;
    }

    public static void BeginSort(ArrayList<Integer> input, Sorted sorter){
        long time = System.currentTimeMillis();
        ArrayList<Integer> array_sort = sorter.sort(input);
        time = System.currentTimeMillis() - time;
        System.out.printf("Sorted array: [");
        for (int i = 0; i < 49 && i < array_sort.size()-1; i++) {
            System.out.printf(array_sort.get(i) + ", ");
        }
        if (array_sort.size() > 50)
            System.out.printf(input.get(49) + "...]\n");
        else
            System.out.printf(input.get(array_sort.size()-1) + "]\n");
        System.out.printf("Sorting Time: " + time + "\n");
    }

    public static void main(String[] args) {
        for (int count : new int[]{10, 1000, 10000, 1000000}) {
            System.out.printf("Number of elements in current iteration " + count + "\n");
            ArrayList<Integer> array = new ArrayList<>();
            FillArr(array, count);
            for (SortingType type: new SortingType[]{SortingType.BubbleSorter,SortingType.ShellSorter, SortingType.MergeSorter, SortingType.QuickSorter}) {
                System.out.printf("Sorting type in current iteration " + type + "\n");
                Sorted sorter = SortingFactory.createSorter(type);
                BeginSort(array,sorter);
            }

        }
    }

    }
