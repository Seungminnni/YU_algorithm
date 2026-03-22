// 22313529 이승민

class ListNode {
    int val;
    ListNode next;
    ListNode() {} // 리스트 노드 생성자
    ListNode(int val) { this.val = val; } // 리스트 노드의 인자를 초기화
    ListNode(int val, ListNode next) { this.val = val; this.next = next; } // 다음 노드의 인자를 초기화
}


public class HW2 {
    public static void main(String[] args) {
        ListNode head = null; // 리스트 노드의 헤드 초기화
        head = new ListNode(-1); // 리스트 노드의 헤드에 -1값을 가진 노드 생성 .....
        head.next = new ListNode(5);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(0);

        Solution2 sol = new Solution2(); // 삽입정렬 알고리즘 객체 생성
        head = sol.insertionSortList(head); // 삽입정렬 알고지름을 통한 리스트 노드의 head를 원래 ListNode의 head로 반영
        printList(head); // 정렬된 리스트 노드를 출력함
    }

    public static void printList(ListNode node) { // printlist 메소드가 정의 되지 않아 새롭게 정의함
        StringBuilder sb = new StringBuilder(); // 문자열 객체 생성
        while (node != null) { // 노드가 null일때 까지 반복
            sb.append(node.val); // 문자열 객체에 val값을 삽입
            if (node.next != null) { // 만약 다음 노드에 값이 있다면
                sb.append(" -> "); // -> 를 추가
            }
            node = node.next; // 다음 노드로 이동
        }
        System.out.println(sb.toString()); // 문자열 객체에 저장된 값을 출력
    }
}

class Solution2 {
    public ListNode insertionSortList(ListNode head){ // 단일 연결 리스트를 삽입 정렬하는 알고리즘
        if (head == null) // 정렬할 리스트가 비어있을 경우
            return head; // null 반환

        ListNode temp = new ListNode(0); // 임시 리스트 정의
        ListNode curr = head; // 원래 리스트의 head를 현재로 정의

        while (curr != null){ // 현재 노드가 null이 아니면
            ListNode nextTemp = curr.next; // 다음 임시 노드를 원래 리스트의 현재의 다음으로 정의

            ListNode prev = temp; // 정렬된 리스트 초기를 반복문 밖에서 선언한 0으로 지속적으로 초기화 그래서 앞에서부터 비교가 가능하도록 조치

            while (prev.next != null && prev.next.val < curr.val){ // 정렬된 리스트의 이전이 빈값이 아니거나 이전의 다음 값이 원래 리스트의 현재보다 작을 경우 다음 노드로 이동 만약에 크다면 반복문 탈출
                prev = prev.next; // prev를 다음 노드로 이동
            }
            curr.next = prev.next; // curr의 다음을 prev의 다음으로 설정 예) 0 -1 5 3 이라면  3의 다음을 -1의 다음인 5로 정의
            prev.next = curr; // prev의 다음을 curr로 변경 예) -1의 다음인 3을 curr로 정의

            // 다음 노드 처리로 이동
            curr = nextTemp; // curr을 처음에 저장해둔 다음 노드로 이동
        }

        return temp.next; // 초기 0을 제외한 temp리스트를 반환
    }
}