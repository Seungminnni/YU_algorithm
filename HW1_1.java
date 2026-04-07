// 22313529 이승민

import java.util.ArrayList;
import java.util.Arrays;

public class HW1_1 {
    public static void main(String[] args) {
        Solution1 S = new Solution1();
        int[] numbers = {5, 0, 2, 7};
        System.out.println("입력 = " + Arrays.toString(numbers));
        System.out.println("출력 = " + Arrays.toString(S.solution(numbers)));
    }
}

class Solution1 {
    public int[] solution(int[] numbers) {
        ArrayList<Integer> list = new ArrayList<>(); // 새로운 어래이 리스트 생성
        for (int i = 0; i < numbers.length; i++) { // 반복문 시작, 배열의 끝까지 반복
            for (int j = i + 1; j < numbers.length; j++) { // i 다음 숫자는 j, 다음 j카운트 1추가 5,0,2,7 중에 5자리와 0자리를 말함
                int sum = numbers[i] + numbers[j]; // 배열의 i번째 수와 j번째(i+1) 수 더하기
                if (list.indexOf(sum) < 0) { // 리스트에 같은 값이 존재 하지 않으면
                    list.add(sum); // 그 값을 리스트에 추가
                }
            }
        }

        int[] result = new int[list.size()]; // 리스트 크기와 같은 새로운 배열 생성
        for (int i = 0; i < list.size(); i++) { // 처음부터 리스트 크기까지 반복
            result[i] = list.get(i); // result 배열에 정렬된 리스트의 처음부터 값을 대입
        }
        Arrays.sort(result); // result 배열을 오름차순으로 정렬
        return result; // 결과 배열을 반환 solution(int[] numbers)에 반환값으로 들어감
    }
}