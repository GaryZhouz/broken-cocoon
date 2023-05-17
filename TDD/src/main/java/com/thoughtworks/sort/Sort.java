package com.thoughtworks.sort;

public class Sort {
    public static int[] bubble(int... array) {
        return bubble(Sort::swap, array);
    }

    public static int[] bubble(SwapFunc func, int... array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] < array[j]) {
                    func.swap(i, j, array);
                }
            }
        }
        return array;
    }

    private static void swap(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    interface SwapFunc {
        void swap(int i, int j, int[] array);
    }
}
