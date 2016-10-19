package com.lake.utils;

import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println(new Main().isStackOutSequence(new int[]{1,2,3,4,5,6,7}, new int[]{3,4,2,1,7,6,5}));
	}
	
	//判断入栈序列为a时，b是否可能是出栈序列
	public boolean isStackOutSequence(int[] a ,int[] b) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		int n = a.length;
		int i = 0, j = 0;
		while(i < n || list.size() > 0) {
			if(list.size() == 0) { 
				list.addFirst(a[i]);
				i ++;
			} else {
				if(b[j] == list.peekFirst()) {
					list.pollFirst();
					j ++;
				} else {
					if(i >= n) return false;
					list.addFirst(a[i]);
					i ++;
				}
			}
		}
		return true;
	}
	
	//找区间里的所有质数(包括n)
	public List<Integer> findAllPrime(int n) {
		boolean[] notPrime = new boolean[n+1];
		int i = 1;
		while(i * i < n) {
			i ++;
			if(notPrime[i]) continue;
			for(int j = i * 2; j <= n; j += i) {
				notPrime[j] = true;
			}
		}
		List<Integer> list = new ArrayList<Integer>();
		for(i = 2; i <= n; i ++) {
			if(!notPrime[i]) {
				list.add(i);
			}
		}
		return list;
	}
	
	//判断质数
	public boolean isPrime(int n) {
		if(n < 2) return false;
		for(int i = 2; i * i <= n; i ++) {
			if(n % i == 0) return false;
		}
		return true;
	}
	
	//序列化二叉树
	public String serializeBinaryTree(TreeNode root) {
		if(root == null) {
			return "null";
		}
		return root.val+","+serializeBinaryTree(root.left)+","+serializeBinaryTree(root.right);
	}
	
	//高效版本
	public void buildSerializeString(TreeNode root, StringBuilder sb) {
		if(root == null) {
			sb.append("null").append(",");
			return ;
		}
		sb.append(root.val).append(",");
		buildSerializeString(root.left,sb);
		buildSerializeString(root.right,sb);
	}
	
	//反序列化二叉树
	public TreeNode deserializeBinaryTree(String s) {
		LinkedList<String> list = new LinkedList<String>(Arrays.asList(s.split(",")));
		return buildDeserializeBinaryTree(list);
	}
	
	public TreeNode buildDeserializeBinaryTree(LinkedList<String> list) {
		String s = list.remove();
		if(s.equals("null")) {
			return null;
		}
		
		TreeNode tn = new TreeNode(Integer.parseInt(s));
		tn.left = buildDeserializeBinaryTree(list);
		tn.right= buildDeserializeBinaryTree(list);
		return tn;
	}
	
	//二叉查找树转双向链表
	public TreeNode BST2DoubleList(TreeNode root) {
		return HelpBST2DoubleList(root).head;
	}
	
	class DoubleListProxy {
		TreeNode head;
		TreeNode tail;
	}
	
	public DoubleListProxy HelpBST2DoubleList(TreeNode root) {
		if(root == null) {
			return new DoubleListProxy();
		}
		
		DoubleListProxy left = HelpBST2DoubleList(root.left);
		DoubleListProxy right = HelpBST2DoubleList(root.right);
		root.left = left.tail;
		root.right = right.head;
		if(left.tail != null) {
			left.tail.right = root;
		}
		if(right.head != null) {
			right.head.left = root;
		}
		
		DoubleListProxy res = new DoubleListProxy();
		res.head = left.head == null ? root : left.head;
		res.tail = right.tail == null ? root : right.tail;
		return res;
	}
	
	//除数为0返回null
	public String devideBigInteger(String num1, String num2) {
		if(num2.equals("0")) return null;
		
		class HelpDevideBigInteger {
			int index = 0;
			
			boolean compareBigInteger(int[] m, int[] n, int from, int to) {
				int mlen = to - from;
				int nlen = n.length;
				while(n[nlen - 1] == 0) nlen --;
				if(mlen> nlen) return true;
				else if(nlen > mlen) return false;
				else {
					for(int i = 0; i < mlen; i ++) {
						if(m[to - 1 - i] > n[nlen - 1 - i]) return true;
						else if(m[to - 1 - i] < n[nlen - 1 - i]) return false;
					}
					return true;
				}
			}
			
			int divideBigInteger(int[] m, int[] n, int from, int to) {
				int t = m[to - 1];
				if(to - from > n.length) t = t * 10 + m[to - 2];
				int p = t / n[n.length - 1];
				int[] cache = new int[n.length+1];
				int c = 0;
				for(int i = 0; i < n.length; i ++) {
					int temp = n[i] * p + c;
					cache[i] = temp % 10;
					c = temp / 10;
				}
				if(c != 0) cache[cache.length - 1] = c;
				while(!compareBigInteger(m,cache,from,to)) {
					p --;
					substractBigInteger(cache,n,0);
				}
				substractBigInteger(m,cache,from);
				while(to > 0 && m[to - 1] == 0) to --;
				index = to;
				return p;
			}
			
			void substractBigInteger(int[] m, int[] n, int from) {
				int c = 0, i = 0;
				int nlen = n.length;
				while(n[nlen - 1] == 0) nlen --;
				for(; i < nlen; i ++) {
					int temp = m[from+i] - n[i] + c;
					if(temp < 0) {
						c = -1;
						m[from+i] = temp + 10;
					} else {
						c = 0;
						m[from+i] = temp;
					}
				}
				if(c != 0) m[from+i] += c;
			}
		}
		
		HelpDevideBigInteger hdb = new HelpDevideBigInteger();
		int m = num1.length();
		int n = num2.length();
		int[] n1 = new int[m], n2 = new int[n];
		
		for (int i = 0; i < m; i++) {
			n1[i] = num1.charAt(m-1-i) - '0';
		}
		for (int i = 0; i < n; i++) {
			n2[i] = num2.charAt(n-1-i) - '0';
		}
		
		if(!hdb.compareBigInteger(n1, n2, 0, m)) {
			StringBuilder sbmod = new StringBuilder();
			for(int i = m - 1; i >= 0; i --) {
				sbmod.append(n1[i]);
			}
			return "商：0	余："+sbmod.toString();
		}
		
		int[] ans = new int[m-n+1];
		int from = m - n, to = m;
		for(int i = m - n; i >= 0;) {
			if(hdb.compareBigInteger(n1, n2, from, to)) {
				ans[i] = hdb.divideBigInteger(n1, n2, from, to);
			} else {
				from --;
				i --;
				if(i >= 0) {
					ans[i] = hdb.divideBigInteger(n1, n2, from, to);
				} else break;
			}
			to = hdb.index;
			from = to - n;
			i = from;
		}
		
		StringBuilder sb = new StringBuilder();
		if(ans[ans.length - 1] != 0) sb.append(ans[ans.length - 1]);
		for(int i = ans.length - 2; i >= 0; i --) {
			sb.append(ans[i]);
		}
		
		StringBuilder sbmod = new StringBuilder();
		for(int i = to - 1; i >= 0; i --) {
			sbmod.append(n1[i]);
		}
		return "商："+sb.toString()+"	余："+sbmod.toString();
	}
	
	public String multiplyBigInteger(String num1, String num2) {
		int m = num1.length();
		int n = num2.length();
		int[] ans = new int[m+n+1], n1 = new int[m], n2 = new int[n];
		for (int i = 0; i < m; i++) {
			n1[i] = num1.charAt(m-1-i) - '0';
		}
		for (int i = 0; i < n; i++) {
			n2[i] = num2.charAt(n-1-i) - '0';
		}
		
		int c = 0, t = 0;
		int i = 0, j = 0;
		for (i = 0; i < n; i++) {
			for (j = 0; j < m; j++) {
				t = n1[j] * n2[i] + c + ans [i + j];
				c = t / 10;
				ans[i + j] = t % 10;
			}
			t =  c + ans [i + j];
			c = 0;
			while(t > 10) {
				ans[i + j] = t -10;
				j ++;
				t = 1 + ans[i + j];
			}
			ans[i + j] = t;
		}
		
		StringBuilder sb = new StringBuilder();
		boolean flag = true;
		for(i = m + n; i >= 0; i --) {
			if(flag) {
				if(ans[i] != 0) {
					flag = false;
					sb.append(ans[i]);
				}
			} else sb.append(ans[i]);
		}
		return sb.toString();
	}
	
	public boolean compareBigInteger(String num1, String num2) {
		int m = num1.length();
		int n = num2.length();
		if(m > n) return true;
		else if(m < n) return false;
		else {
			int ans = num1.compareTo(num2);
			return ans < 0 ? false : true;
		}
	}
	
	//大数减两个非负整数减
	public String subtractBigInteger(String num1, String num2) {
		int m = num1.length();
		int n = num2.length();
		if(!compareBigInteger(num1,num2)) {
			return '-' + subtractBigInteger(num2, num1);
		} else { 
			int c = 0, i = 0;
			char[] cs = new char[m];
			for (; i < n; i++) {
				int t = num1.charAt(m-1-i) - num2.charAt(n-1-i) + c;
				c = 0;
				if(t < 0) {
					c = -1;
					t += 10;
				}
				cs[m - 1 - i] = (char)(t + '0');
			}
			for (; i < m; i++) {
				int t = num1.charAt(m-1-i) - '0' + c;
				c = 0;
				if(t < 0) {
					c = -1;
					t += 10;
				}
				cs[m - 1 - i] = (char)(t + '0');
			}
			if(cs[0] == '0') return new String(cs,1,m - 1);
			return new String(cs);
		}
	}
	
	//大数加，不考虑非法输入，两个非负整数
	public String addBigInteger(String num1, String num2) {
		int m = num1.length();
		int n = num2.length();
		if(m < n) {
			return addBigInteger(num2, num1);
		} else { 
			int c = 0, i = 0;
			char[] cs = new char[m+1];
			for (; i < n; i++) {
				int t = num1.charAt(m-1-i) - '0' + num2.charAt(n-1-i) - '0' + c;
				c = 0;
				if(t > 9) {
					c = 1;
					t -= 10;
				}
				cs[m - i] = (char)(t + '0');
			}
			for (; i < m; i++) {
				int t = num1.charAt(m-1-i) - '0' + c;
				c = 0;
				if(t > 9) {
					c = 1;
					t -= 10;
				}
				cs[m - i] = (char)(t + '0');
			}
			if(c == 1) {
				cs[0] = '1';
				return new String(cs);
			}
			return new String(cs,1,m);
		}
	}
	
	public void reverse(char[] chars) {
		int length = chars.length;
		for (int i = 0; i < length/2; i++) {
			char c = chars[i];
			chars[i] = chars[length - 1 - i];
			chars[length - 1 - i] = c;
		}
	}
	
	//最长递增子序列O(NlogN)能打印序列,只能打印一组，需打印所有还得下面的ON2解法DFS
	public int LISnlogn(int[] a) {
		if(a.length == 0) return 0;
		int[] dp = new int[a.length];
		int[] pre = new int[a.length];
		int max = 1;
		for (int i = 1; i < a.length; i++) {
			//二分查找
			int low = 0, high = max -1;
			int mid = 0;
			while(high-low>1) {
				mid = ((high - low) >> 1) + low;
				if(a[dp[mid]] <a[i]) {
					low = mid;
				} else if(a[dp[mid]] >a[i]) {
					high = mid;
				} else {
					low = mid;
					break;
				}
			}
			if(a[dp[low]] >= a[i]) {
				mid = low;
			}
			if(a[dp[low]] < a[i] && a[dp[high]] >= a[i]) {
				mid = high;
			}
			if(a[dp[high]] < a[i]) {
				mid = high+1;
			}
			if(mid == 0) {
				dp[mid] = i;
				pre[i] = i;
				continue;
			}
			dp[mid] = i;
			pre[i] = dp[mid-1];
			max = max > mid+1?max :mid+1;
		}
		int k = dp[max-1];
		while(pre[k] != k) {
			System.out.print(a[k]+" ");
			k = pre[k];
		}
		System.out.print(a[k]);
		return max;
	}
	
	//最长递增子序列O(N2)能打印序列
	public int LIS(int[] a) {
		int[] dp = new int[a.length];
		int max = 0;
		Arrays.fill(dp, 1);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < i; j++) {
				if (a[j] < a[i]) {
					dp[i] = dp[i] > dp[j] + 1?dp[i]:dp[j]+1;
				}
			}
			max = max > dp[i] ?max :dp[i];
		}
		int t = Integer.MAX_VALUE;
		for(int i = max,j = a.length-1;i>0;j--) {
			if(dp[j] == i && a[j] < t) {
			    i--;
			    t = a[j];
			    System.out.print(a[j]+" ");	//打印任一组最长递增子序列，反过来打印
			}
		}
		return max;
	}
	
	public int kmp(String s,String p) {
		int[] next = new int[p.length()];
		int k = -1;
		int j = 0;
		int plen = p.length();
		int slen = s.length();
		next[0] = -1;
		while(j < plen - 1) {
			if(k == -1 || p.charAt(k) == p.charAt(j)) {
				k ++;
				j ++;
				next[j] = k;
			} else {
				k = next[k];
			}
		}
		
		int i = 0;
		j = 0;
		while(i < slen && j < plen) {
			if(j == -1 || s.charAt(i) == p.charAt(j)) {
				i ++;
				j ++;
			} else {
				j = next[j];
			}
		}
		if(j == plen) return i - j;
		return -1;
	}
	
	//java二分查找实现
	public int binarySearch(int[] array,int value) {
		if(array == null || array.length == 0) return -1;
		int low = 0;
		int high = array.length-1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = array[mid];

            if (midVal < value)
                low = mid + 1;
            else if (midVal > value)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);
	}
	
	//prim最小生成树
	public void prim(int[][] a) {
		int n = a.length;
		int count = n;
		int[][] tem = new int[n][2];
		boolean[] b = new boolean[n];
		b[0] = true;
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 1; i < n; i++) {
			if(a[0][i] != 0) {
				set.add(i);
				tem[i][0] = a[0][i];
				tem[i][1] = 0;
			}
		}
		while(--count>0) {
			int index = 0;
			int min = Integer.MAX_VALUE;
			Iterator<Integer> iterator = set.iterator();
			while (iterator.hasNext()) {
				int t = iterator.next();
				if(min>tem[t][0]) {
					min = tem[t][0];
					index = t;
				}
			}
			set.remove(index);
			b[index] = true;
			System.out.println(tem[index][1]+1+"->"+(index+1)+" "+tem[index][0]);
			for (int i = 0; i < n; i++) {
				if(a[index][i] != 0 && !b[i]) {
					if(!set.add(i)) {
						if(tem[i][0] > a[index][i]) {
							tem[i][0] = a[index][i];
							tem[i][1] = index;
						}
					} else {
						tem[i][0] = a[index][i];
						tem[i][1] = index;
					}
				}
			}
		}
	}
	
	//深搜判断arr字符串数组能够首尾相连（类似成语接龙）例："abc"->"cjg"->"gol",能返回1否返回-1.
	public int canArrangeWords(String arr[]) {
		Map<Integer,List<Integer>> map = new HashMap<Integer,List<Integer>>();
		for(int i = 0 ; i<arr.length;i++) {
			int n = arr[i].charAt(0)-'a';
			int m = arr[i].charAt(arr[i].length()-1) - 'a';
			List<Integer> l;
			if((l = map.get(n)) != null) {
				l.add(m);
			} else {
				l = new LinkedList<Integer>();
				l.add(m);
				map.put(n, l);
			}
		}
		Iterator<Integer> iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			if(can(map,iterator.next(),arr.length)) return 1;
		}
		return -1;
	}
	
	public boolean can(Map<Integer,List<Integer>> map,int n,int size) {
		if(size == 0) return true;
		List<Integer> l = map.get(n);
		if(l == null) return false;
		for(int i = 0; i <l.size(); i++) {
			int t = l.remove(i);
			if(can(map,t,size-1)) return true;
			l.add(i,t);
		}
		return false;
	}
	
	//全排列
	public void perm(int[] a,int index) {
		if(index == a.length-1) {
			System.out.println(Arrays.toString(a));
		} else {
			perm(a,index+1);
			for(int i = index + 1; i < a.length; i ++) {
				int t = a[index];
				a[index] = a[i];
				a[i] = t;
				perm(a,index+1);
				t = a[index];
				a[index] = a[i];
				a[i] = t;
			}
		}
	}
	
	public int  NumberOf1(int n) {
		int i = 0;
        while(n != 0) {
        	n &= n - 1;
        	i ++;
        }
        return i;
    }
	
	public boolean isPowerOfThree(int n) {
    	System.out.println((int)Math.pow(3, Math.round(Math.log(n)/Math.log(3))));
        return n <= 0 ? false : n == (int)Math.pow(3, Math.round(Math.log(n)/Math.log(3)));
    }
	
	//回文子串
	public String longestPalindrome(String s) {
        int max = 1, index = 0, r = 0, center = 0;
    	int[] tem = new int[s.length()*2-1];
    	tem[0] = 1;
    	for(int i = 1; i < s.length()*2-1; i ++) {
    		if(i/2<r) {
    			int t = i/2+tem[2*center-i]/2;
    			if(t<r) {
    				tem[i] = tem[2*center-i];
    			} else {
    				int tr = r+1;
        			int tl = i-tr;
        			while(tl>=0 && tr<s.length()) {
        				if(s.charAt(tl) != s.charAt(tr)) {
        					break;
        				}
        				tl--;
        				tr++;
        			}
        			r = tr-1;
        			if(max < tr-tl-1){
        				max = tr-tl-1;
        				index = tl+1;
        			}
        			tem[i] = tr-tl-1;
        			center = i;
    			}
    		} else {
    			int tl = (i-1)/2;
    			int tr = i-tl;
    			while(tl>=0 && tr<s.length()) {
    				if(s.charAt(tl) != s.charAt(tr)) {
    					break;
    				}
    				tl--;
    				tr++;
    			}
    			r = tr-1;
    			if(max < tr-tl-1){
    				max = tr-tl-1;
    				index = tl+1;
    			}
    			tem[i] = tr-tl-1;
    			center = i;
    		}
    	}
        return s.substring(index, index+max);
    }
	
	//快排
	public void quickSort(int[] a) {
		quick(a,0,a.length -1);
	}
	
	public void quick(int[] a, int low, int high) {
		if (low >= high) return ;
		swap(a,low,new Random().nextInt(high - low + 1) + low);
		int m = low;
		for(int i = low + 1; i <= high; i ++) {
			if(a[i] < a[low]) {
				swap(a,++m,i);
			}
		}
		swap(a,low,m);
		quick(a,low,m-1);
		quick(a,m+1,high);
	}
	
	public void swap(int[] a, int m, int n) {
//		a[m] ^= a[n];
//		a[n] ^= a[m];
//		a[m] ^= a[n];
		int t = a[m];
		a[m] = a[n];
		a[n] = t;
	}
	
	//选择排序
	public int[] selectSort(int a[]) {
		for(int i = 0; i < a.length -1; i ++) {
			int temp = 0;
			for(int j = i+1; j < a.length; j++) {
				if (a[j]<a[i]) {
					temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
		return a;
	}
	
	//希尔排序
	public int[] shellSort(int a[]){  
	    for(int d = a.length / 2; d >= 1; d /= 2){  
	    	for(int i = d; i < 2 * d; i ++) {
	    		for (int j = i; j < a.length; j += d) {
	    			int temp = a[j];
	    			int k = 0;
	    			for(k = j - d;k >= 0 && a[k+d] < a[k]; k -= d) {
	    				a[k+d] = a[k];
	    			}
	    			a[k + d] = temp;
	    		}
	    	}
	    }  
	    return a;
	}  
	
	//插入排序
	public int[] InsertSort(int a[]) {  
		int temp = 0;
	    for(int i = 1; i < a.length; i ++) {
	    	temp = a[i];
	    	int j = 0;
	    	for(j = i-1; j >= 0 && a[j] > temp; j --) {
	    		a[j+1] = a[j];
	    	}
	    	a[j+1] = temp;
	    }  
	    return a;
	}  
}


class Point {
    int x;
    int y;
    Point() { x = 0; y = 0; }
    Point(int a, int b) { x = a; y = b; }
 }

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

class TrieNode {
	int num=0;
	boolean ishave;
	TrieNode[] tns = new TrieNode[26];
    // Initialize your data structure here.
    public TrieNode() {
        
    }
}

class DoubleListNode {
	int value;
	DoubleListNode pre;
	DoubleListNode next;
	
	DoubleListNode(int value) {
		this.value = value;
	}
}

class Trie {
    public TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
    	char c;
    	TrieNode tn = root;
        for(int i = 0; i < word.length(); i ++) {
        	c = word.charAt(i);
        	if(tn.tns[c-'a'] == null) {
        		TrieNode tntemp = new TrieNode();
        		tn.tns[c-'a'] = tntemp;
        		tn.num++;
        		tn = tntemp;
        	} else {
        		tn = tn.tns[c-'a'];
        	}
        }
        tn.ishave = true;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
    	char c;
    	TrieNode tn = root;
    	for(int i = 0; i < word.length(); i ++) {
    		c = word.charAt(i);
    		if(tn.tns[c-'a'] == null) {
    			return false;
    		} else {
    			tn = tn.tns[c-'a'];
    		}
    	}
    	return tn.ishave;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
    	char c;
    	TrieNode tn = root;
    	for(int i = 0; i < prefix.length(); i ++) {
    		c = prefix.charAt(i);
    		if(tn.tns[c-'a'] == null) {
    			return false;
    		} else {
    			tn = tn.tns[c-'a'];
    		}
    	}
    	return true;
    }
}