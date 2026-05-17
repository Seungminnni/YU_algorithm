import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;

public class HW3 {
    public static void main(String[] args) {
    
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++)
            A[i] = sc.nextInt();
        sc.close();

        // 정렬된 중복 제거 배열 생성
        int[] sorted = A.clone();
        Arrays.sort(sorted);
        
        // 중복 제거: 새로운 배열에 유니크 값만 저장
        int[] unique = new int[n];
        int uniqueCount = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i-1]) {
                unique[uniqueCount++] = sorted[i];
            }
        }
        
        // HashMap: 값 → 압축 인덱스
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < uniqueCount; i++) {
            map.put(unique[i], i);
        }
        
        // 결과 배열 생성: 원본 배열의 각 값을 HashMap으로 조회
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = map.get(A[i]);
        }
        
        // 결과 출력
        for (int i = 0; i < n; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(result[i]);
        }
        System.out.println();
    }
}