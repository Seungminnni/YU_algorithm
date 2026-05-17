// 22313529 이승민

import java.util.ArrayList;
import java.util.Scanner;

public class HW2Combination {
    static ArrayList<Integer> numbers = new ArrayList<>();
    static ArrayList<Integer> finalSet = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> resultSets = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("정수 n과 k를 입력? ");
        int n = sc.nextInt();
        int k = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }

        makefinalset(0, k);

        for (int i = 0; i < resultSets.size(); i++) {
            System.out.print(resultSets.get(i) + " ");
        }
    }

    public static void makefinalset(int s, int k) {
        if (finalSet.size() == k) {
            resultSets.add(new ArrayList<>(finalSet));
            return;
        }

        for (int i = s; i < numbers.size(); i++) {
            finalSet.add(numbers.get(i));

            makefinalset(i + 1, k);

            finalSet.remove(finalSet.size() - 1);
        }
    }
}
