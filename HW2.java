import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class HW2 {

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
				else
					st.put(word, st.get(word) + 1);
			}
			long end = System.currentTimeMillis();
			System.out.println("입력 완료: 소요 시간 = " + (end - start) + "ms");

			System.out.println("### 생성 시점의 트리 정보");
			print_tree(st);		// 정상적으로 출력되면 50점

			// ArrayList<String> keyList = (ArrayList<String>) st.keys();
			// Collections.shuffle(keyList, rand);
			// int loopCount = (int) (keyList.size() * 0.95);
			// 삭제 기능을 구현했다면 여기서 keyList의 앞부분을 지우면 됨
			// System.out.println("\n### 키 삭제 후 트리 정보");		// 주석 처리 가능
			// print_tree(st);										// 주석 처리 가능. 여기까지 정상적으로 출력되면 100점
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
}

class Tree23<K extends Comparable<K>, V> {

	private class Node {
		int numkeys;
		K key1, key2;
		V value1, value2;
		Node c1, c2, c3;

		Node() {
			this.numkeys = 0;
		}

		Node(K k, V v) {
			this.key1 = k;
			this.value1 = v;
			this.numkeys = 1;
		}

		boolean isLeaf() {
			return c1 == null;
		}
	}

	private Node root;
	private int n;

	public Tree23() {
		root = null;
		n = 0;
	}

	public V get(K key) {
		Node node = root;
		while (node != null) {
			int cmp1 = key.compareTo(node.key1);
			if (cmp1 == 0)
				return node.value1;

			if (cmp1 < 0) {
				node = node.c1;
			} else if (node.numkeys == 1) {
				node = node.c2;
			} else {
				int cmp2 = key.compareTo(node.key2);
				if (cmp2 == 0)
					return node.value2;

				if (cmp2 < 0)
					node = node.c2;
				else
					node = node.c3;
			}
		}
		return null;
	}

	public boolean contains(K key) {
		return get(key) != null;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}

	public Iterable<K> keys() {
		ArrayList<K> list = new ArrayList<>();
		inorder(root, list);
		return list;
	}

	private void inorder(Node node, ArrayList<K> list) {
		if (node == null)
			return;
		inorder(node.c1, list);
		list.add(node.key1);

		if (node.numkeys == 1) {
			inorder(node.c2, list);
		} else {
			inorder(node.c2, list);
			list.add(node.key2);
			inorder(node.c3, list);
		}
	}

	public int depth() {
		int d = 0;
		Node node = root;
		while (node != null) {
			d++;
			node = node.c1;
		}
		return d;
	}

	public void put(K key, V value) {
		if (isEmpty()) {
			root = new Node(key, value);
			n++;
			return;
		}

		Node newRoot = insert(root, key, value);
		if (newRoot != null) {
			root = newRoot;
		}
	}

	private Node insert(Node node, K key, V value) {
		if (key.compareTo(node.key1) == 0) {
			node.value1 = value;
			return null;
		}
		if (node.numkeys == 2 && key.compareTo(node.key2) == 0) {
			node.value2 = value;
			return null;
		}

		if (node.isLeaf()) {
			n++;
			return split(node, key, value, null, null);
		}

		Node childNode;
		if (key.compareTo(node.key1) < 0) {
			childNode = node.c1;
		} else if (node.numkeys == 1) {
			childNode = node.c2;
		} else if (key.compareTo(node.key2) < 0) {
			childNode = node.c2;
		} else {
			childNode = node.c3;
		}

		Node upNode = insert(childNode, key, value);
		if (upNode != null) {
			return split(node, upNode.key1, upNode.value1, upNode.c1, upNode.c2);
		}
		return null;
	}

	private Node split(Node node, K k, V v, Node left, Node right) {
		if (node.numkeys == 1) {
			Node oldC2 = node.c2;

			if (k.compareTo(node.key1) < 0) {
				node.key2 = node.key1;
				node.value2 = node.value1;
				node.key1 = k;
				node.value1 = v;
				node.c1 = left;
				node.c2 = right;
				node.c3 = oldC2;
			} else {
				node.key2 = k;
				node.value2 = v;
				node.c2 = left;
				node.c3 = right;
			}
			node.numkeys = 2;
			return null;
		}

		Node up = new Node(null, null);
		Node rightNode = new Node(null, null);

		if (k.compareTo(node.key1) < 0) {
			up.key1 = node.key1;
			up.value1 = node.value1;
			up.numkeys = 1;

			rightNode.key1 = node.key2;
			rightNode.value1 = node.value2;
			rightNode.numkeys = 1;
			rightNode.c1 = node.c2;
			rightNode.c2 = node.c3;

			node.key1 = k;
			node.value1 = v;
			node.numkeys = 1;
			node.key2 = null;
			node.value2 = null;
			node.c1 = left;
			node.c2 = right;
			node.c3 = null;
		} else if (k.compareTo(node.key2) > 0) {
			up.key1 = node.key2;
			up.value1 = node.value2;
			up.numkeys = 1;

			rightNode.key1 = k;
			rightNode.value1 = v;
			rightNode.numkeys = 1;
			rightNode.c1 = left;
			rightNode.c2 = right;

			node.numkeys = 1;
			node.key2 = null;
			node.value2 = null;
			node.c3 = null;
		} else {
			up.key1 = k;
			up.value1 = v;
			up.numkeys = 1;

			rightNode.key1 = node.key2;
			rightNode.value1 = node.value2;
			rightNode.numkeys = 1;
			rightNode.c1 = right;
			rightNode.c2 = node.c3;

			node.numkeys = 1;
			node.key2 = null;
			node.value2 = null;
			node.c2 = left;
			node.c3 = null;
		}

		up.c1 = node;
		up.c2 = rightNode;
		return up;
	}
}
