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
				else	st.put(word, st.get(word) + 1);
			}
			long end = System.currentTimeMillis();
			System.out.println("입력 완료: 소요 시간 = " + (end-start) + "ms");
			
			System.out.println("### 생성 시점의 트리 정보");
			print_tree(st);
			
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

	static class Tree23<Key extends Comparable<Key>, Value>{
		
   	 	private Node root;
    	private int size;

   	 	private class Node {
        	int num_c;          
        	Key key1, key2;        
			Value val1, val2;      
			Node left, mid, right; 
			
			Node(Key k, Value v) {
				this.key1 = k;
				this.val1 = v;
				this.num_c = 1;
			}
		
			boolean isLeaf() {
				return left == null;
			}
		}

		public Tree23() {
			root = null;
			size = 0;
		}

		public Value get(Key key) {   
            Node node = root;
            while (node != null) {
                int pre1= key.compareTo(node.key1);
                if (pre1 == 0) return node.val1;
                
                if (pre1 < 0) {
                    node = node.left;
                } else if (node.num_c == 1) {
                    node = node.right;
                } else {
                    int pre2 = key.compareTo(node.key2);
                    if (pre2 == 0) return node.val2;
                    
                    if (pre2 < 0) node = node.mid;
                    else node = node.right;
                }
            }
            return null; 
        }
		public boolean contains(Key key) {    
            return get(key) != null;
        }
		public boolean isEmpty() {   
			return size == 0;
		}
		public int size() {   
			return size;
		}
		public Iterable<Key> keys() {   
			ArrayList<Key> list = new ArrayList<>();
			inorder(root, list);
			return list;
		}
		public int depth() {
            int i = 0;
            Node node = root;
            while (node != null) {
                i++;
                node = node.left; 
            }
            return i;
        }
		private void inorder(Node node, ArrayList<Key> list) {
            if (node == null) return;
            inorder(node.left, list);
            list.add(node.key1);
            
            if (node.num_c == 1) {
                inorder(node.right, list);
            } else {
                inorder(node.mid, list);
                list.add(node.key2);
                inorder(node.right, list);
            }
        }

		public void put(Key key, Value value) {
            if (isEmpty()) {
                root = new Node(key, value);
                size++;
                return;
            }
            
            Node up = insert(root, key, value); 
            if (up != null) {
                root = up;
            }
        }

        private Node insert(Node node, Key key, Value value) {
            if (key.compareTo(node.key1) == 0) { node.val1 = value; return null; }
            if (node.num_c == 2 && key.compareTo(node.key2) == 0) { node.val2 = value; return null; }
            if (node.isLeaf()) {
                size++;
                return split(node, key, value, null, null);
            }

            Node up = null;
            if (key.compareTo(node.key1) < 0) {
                up = insert(node.left, key, value);
            } else if (node.num_c == 1) { 
                up = insert(node.right, key, value);
            } else { 
                if (key.compareTo(node.key2) < 0) up = insert(node.mid, key, value);
                else up = insert(node.right, key, value);
            }
            
            if (up != null) {
                return split(node, up.key1, up.val1, up.left, up.right);
            }
            return null; 
        }

    
        private Node split(Node node, Key k, Value v, Node sub_left, Node sub_right) {
            if (node.num_c == 1) { 
                if (k.compareTo(node.key1) < 0) {
                    node.key2 = node.key1; node.val2 = node.val1;
                    node.key1 = k; node.val1 = v;
                    node.mid = sub_right; 
                    node.left = sub_left;
                } else {
                    node.key2 = k; node.val2 = v;
                    node.mid = sub_left;
                    node.right = sub_right;
                }
                node.num_c = 2;
                return null; 
                
            } else {
                Node up_node = new Node(null, null);  
                Node new_right = new Node(null, null); 

                if (k.compareTo(node.key1) < 0) {
                    up_node.key1 = node.key1; up_node.val1 = node.val1;
                    new_right.key1 = node.key2; new_right.val1 = node.val2;
                    new_right.left = node.mid; new_right.right = node.right;
                    
                    node.key1 = k; node.val1 = v;
                    node.left = sub_left; node.right = sub_right; 
                    
                } else if (k.compareTo(node.key2) > 0) {
                    up_node.key1 = node.key2; up_node.val1 = node.val2;
                    new_right.key1 = k; new_right.val1 = v;
                    new_right.left = sub_left; new_right.right = sub_right;
                    
                    node.right = node.mid; 
                    
                } else {
                    up_node.key1 = k; up_node.val1 = v;
                    new_right.key1 = node.key2; new_right.val1 = node.val2;
                    new_right.left = sub_right;  
                    new_right.right = node.right;  
                    
                    node.right = sub_left;       
                }

                node.num_c = 1;
                node.key2 = null; node.val2 = null; node.mid = null;

                up_node.left = node;
                up_node.right = new_right;

                return up_node;
            }
        }
	}
}
