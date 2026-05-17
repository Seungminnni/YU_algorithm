// 22313529 이승민
import java.util.Scanner;

public class HW4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] dynamic = new int[n + 1];

        dynamic[1] = 0;

        for (int i = 2; i <= n; i++) {
            dynamic[i] = dynamic[i - 1] + 1;

            if (i % 2 == 0) {
                dynamic[i] = Math.min(dynamic[i], dynamic[i / 2] + 1);
            }

            if (i % 3 == 0) {
                dynamic[i] = Math.min(dynamic[i], dynamic[i / 3] + 1);
            }

            if (i % 5 == 0) {
                dynamic[i] = Math.min(dynamic[i], dynamic[i / 5] + 1);
            }
        }

        System.out.println(dynamic[n]);
    }
}
