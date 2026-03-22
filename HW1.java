import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HW1 {
    public static void main(String[] args) {
        Solution1 S = new Solution1();
        int[] numbers = {5, 0, 2, 7};
        System.out.println("입력 = " + Arrays.toString(numbers));
        System.out.println("출력 = " + Arrays.toString(S.solution(numbers)));
    }
}

class Solution1 {
    public int[] solution(int[] numbers) {
        List<Integer> list = new ArrayList<>(); // 새로운 어래이 리스트 생성
            // 예외처리
            if (numbers.length < 2) { // 배열의 길이가 2보다 작을 경우
                throw new IllegalArgumentException("배열의 길이는 2 이상이어야 합니다.");
            } else if (numbers.length > 100) { // 배열의 길이가 100보다 클 경우
                throw new IllegalArgumentException("배열의 길이는 100 이하이어야 합니다.");
            }

        for (int i = 0; i < numbers.length; i++) { // 반복문 시작, 배열의 끝까지 반복
            if (numbers[i] < 0 || numbers[i] > 100) { // numbers 배열의 각 요소들이 0보다 작은지 혹은 큰지 확인 후 예외처리 던짐
                throw new IllegalArgumentException("배열의 각 요소는 0 이상 100 이하이어야 합니다.");
            }
            for (int j = i + 1; j < numbers.length; j++) { // i 다음 숫자는 j, 다음 j카운트 1추가 5,0,2,7 중에 5자리와 0자리를 말함
                int sum = numbers[i] + numbers[j]; // 배열의 i번째 수와 j번째(i+1) 수 더하기
                if (!list.contains(sum)) { // 리스트에 같은 값이 존재 하지 않으면
                    list.add(sum); // 그 값을 리스트에 추가
                }
            }
        }

        Collections.sort(list); // 리스트에 있는 값을 오름 차순으로 정렬

        int[] result = new int[list.size()]; // 리스트 크기와 같은 새로운 배열 생성
        for (int i = 0; i < list.size(); i++) { // 처음부터 리스트 크기까지 반복
            result[i] = list.get(i); // result 배열에 정렬된 리스트의 처음부터 값을 대입
        }
        return result; // 결과 배열을 반환 solution(int[] numbers)에 반환값으로 들어감
    }
}