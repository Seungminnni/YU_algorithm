import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class HW22 {

	public static void main(String[] args) {
		Tree23<String, Integer> st = new Tree23<>();
		Scanner sc = new Scanner(System.in);
		System.out.print("입력 파일 이름? ");
		String fname = sc.nextLine();
		System.out.print("난수 생성을 위한 seed 값? ");
		Random rand = new Random(sc.nextLong());
		sc.close();

		try {
			sc = new Scanner(new File(fname));
			long start = System.currentTimeMillis();
			while (sc.hasNext()) {
				String word = sc.next();
				if (!st.contains(word))
					st.put(word, 1);
				else	st.put(word, st.get(word) + 1);
			}
			long end = System.currentTimeMillis();
			System.out.println("입력 완료: 소요 시간 = " + (end-start) + "ms");

			System.out.println("### 생성 시점의 트리 정보");
			print_tree(st);     //정상적으로 출력되면 50점

			ArrayList<String> keyList = (ArrayList<String>) st.keys();
			Collections.shuffle(keyList, rand);
			int loopCount = (int)(keyList.size() * 0.95);
			//for (int i = 0; i < loopCount; i++) {
				//st.delete(keyList.get(i));						// 주석 처리 가능
			//}
			//System.out.println("\n### 키 삭제 후 트리 정보");		// 주석 처리 가능
			//print_tree(st);										// 주석 처리 가능. 여기까지 정상적으로 출력되면 100점
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		if (sc != null)
			sc.close();
	}

	private static void print_tree(Tree23<String, Integer> st) {
		System.out.println("등록된 단어 수 = " + st.size());
		System.out.println("트리의 깊이 = " + st.depth());

		String maxKey = "";
		int maxValue = 0;
		for (String word : st.keys())
			if (st.get(word) > maxValue) {
				maxValue = st.get(word);
				maxKey = word;
			}
		System.out.println("가장 빈번히 나타난 단어와 빈도수: " + maxKey + " " + maxValue);
	}

	static class Tree23<K extends Comparable<K>, V> {

   	 	private Node root;
    	private int size;

   	 	private class Node {
        	K key1, key2;
			V val1, val2;
			Node left, mid, right;

			Node(K k, V v) {
				this.key1 = k;
				this.val1 = v;
			}

			
			boolean is3Node() {
				return key2 != null;
			}

			
			boolean isLeaf() {
				return left == null;
			}
		}

		public Tree23() {
			root = null;
			size = 0;
		}

		public V get(K key) {
            Node p = root;
            while (p != null) {
                int c1 = key.compareTo(p.key1);
                if (c1 == 0) return p.val1;

                if (c1 < 0) {
                    p = p.left;
                } else if (!p.is3Node()) {
                    p = p.right;
                } else {
                    int c2 = key.compareTo(p.key2);
                    if (c2 == 0) return p.val2;
                    if (c2 < 0) p = p.mid;
                    else p = p.right;
                }
            }
            return null;
        }

		public boolean contains(K key) { return get(key) != null; }
		public boolean isEmpty()       { return size == 0; }
		public int size()              { return size; }

		public Iterable<K> keys() {
			ArrayList<K> list = new ArrayList<>();
			inorder(root, list);
			return list;
		}

		public int depth() {
            int d = 0;
            Node p = root;
            while (p != null) { d++; p = p.left; }
            return d;
        }

		private void inorder(Node p, ArrayList<K> list) {
            if (p == null) return;
            inorder(p.left, list);
            list.add(p.key1);
            if (p.is3Node()) {
                inorder(p.mid, list);
                list.add(p.key2);
            }
            inorder(p.right, list);
        }

		public void put(K key, V val) {
            if (isEmpty()) {
                root = new Node(key, val);
                size++;
                return;
            }

            Node up = insert(root, key, val);
            if (up != null) {
                root = up;
            }
        }

        private Node insert(Node p, K key, V val) {
            if (key.compareTo(p.key1) == 0) { p.val1 = val; return null; }
            if (p.is3Node() && key.compareTo(p.key2) == 0) { p.val2 = val; return null; }
            if (p.isLeaf()) {
                size++;
                return split(p, key, val, null, null);
            }

            Node up = null;
            int c1 = key.compareTo(p.key1);
            if (c1 < 0) {
                up = insert(p.left, key, val);
            } else if (!p.is3Node()) {
                up = insert(p.right, key, val);
            } else {
                int c2 = key.compareTo(p.key2);
                if (c2 < 0) up = insert(p.mid, key, val);
                else up = insert(p.right, key, val);
            }

            if (up != null) {
                return split(p, up.key1, up.val1, up.left, up.right);
            }
            return null;
        }

        private Node split(Node p, K key, V val, Node subLeft, Node subRight) {
            if (!p.is3Node()) {
                int c1 = key.compareTo(p.key1);
                if (c1 < 0) {
                    p.key2 = p.key1; p.val2 = p.val1;
                    p.key1 = key;    p.val1 = val;
                    p.mid = subRight;
                    p.left = subLeft;
                } else {
                    p.key2 = key; p.val2 = val;
                    p.mid = subLeft;
                    p.right = subRight;
                }
                return null;

            } else {
                Node up = new Node(null, null);
                Node newRight = new Node(null, null);

                if (key.compareTo(p.key1) < 0) {
                    up.key1 = p.key1; up.val1 = p.val1;
                    newRight.key1 = p.key2; newRight.val1 = p.val2;
                    newRight.left = p.mid; newRight.right = p.right;
                    p.key1 = key; p.val1 = val;
                    p.left = subLeft; p.right = subRight;

                } else if (key.compareTo(p.key2) > 0) {
                    up.key1 = p.key2; up.val1 = p.val2;
                    newRight.key1 = key; newRight.val1 = val;
                    newRight.left = subLeft; newRight.right = subRight;
                    p.right = p.mid;

                } else {
                    up.key1 = key; up.val1 = val;
                    newRight.key1 = p.key2; newRight.val1 = p.val2;
                    newRight.left = subRight;
                    newRight.right = p.right;
                    p.right = subLeft;
                }

                p.key2 = null; p.val2 = null; p.mid = null;

                up.left = p;
                up.right = newRight;

                return up;
            }
        }
	}
}
