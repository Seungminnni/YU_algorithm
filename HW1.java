// 22313529 이승민

class Solution {
    int[] answer = new int[2];

    public int[] solution(int[][] arr) {
        answer = new int[2];
        int n = arr.length;
        compress(arr, 0, 0, n);
        return answer;
    }

    public void compress(int[][] arr, int x, int y, int size) {
        int baseValue = arr[x][y];

        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (arr[i][j] != baseValue) {
                    int half = size / 2;

                    compress(arr, x, y, half);                 // 왼쪽 위
                    compress(arr, x, y + half, half);          // 오른쪽 위
                    compress(arr, x + half, y, half);          // 왼쪽 아래
                    compress(arr, x + half, y + half, half);   // 오른쪽 아래

                    return;
                }
            }
        }

        answer[baseValue]++;
    }
}
