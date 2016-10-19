package com.lake.utils;

import java.util.*;

public class Solution {

	public static void main(String[] args) {
		
		System.out.println(new Solution().reverseWords("       "));
	}
	
    public void rotate(int[][] matrix) {
    	if(matrix == null) return ;
    	int n = matrix.length;
        int[][] tem = new int[n][n];
        for(int i = 0; i < n; i ++) {
        	for(int j = 0; j < n; j ++) {
        		tem[j][n-1-i] = matrix[i][j];
        	}
        }
        for(int i = 0; i < n; i ++) {
        	for(int j = 0; j < n; j ++) {
        		matrix[i][j] = tem[i][j];
        	}
        }
    }
	
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character,Integer> map = new HashMap<Character,Integer>();
        for (int i = 0; i < magazine.length(); i++) {
			char c = magazine.charAt(i);
			Integer count = map.get(c);
			if(count == null) {
				map.put(c, 1);
			} else {
				map.put(c, count + 1);
			}
		}
        
        for (int i = 0; i < ransomNote.length(); i++) {
        	char c = ransomNote.charAt(i);
			Integer count = map.get(c);
			if(count == null || count == 0) return false;
			else {
				map.put(c, count - 1);
			}
        }
        return true;
    }
	
    public boolean isBalanced(TreeNode root) {
        if(helpIsBalanced(root) != -1) return true;
        return false;
        
    }
	
    public int helpIsBalanced(TreeNode root) {
        if(root == null) return 0;
        int l = helpIsBalanced(root.left);
        if(l == -1) return -1;
        int h = helpIsBalanced(root.right);
        if(h == -1) return -1;
        if(l-h>1 || h-l>1) return -1;
        return Math.max(l,h) + 1;
    }
    
    public int minDepth(TreeNode root) {
        if(root == null) return 0;
        if(root.left == null) {
        	if(root.right == null) return 1;
        	else return minDepth(root.right) + 1;
        } else {
        	if(root.right == null) return minDepth(root.left) + 1;
        	else return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
        }
    }
	
    public int maxPathSum(TreeNode root) {
    	Sum s = new Sum(Integer.MIN_VALUE);
    	helpMaxPathSum(root,s);
    	return s.sum;
    }
	
    public int helpMaxPathSum(TreeNode root, Sum sum) {
    	if(root == null) return 0;
    	int l = Math.max(0, helpMaxPathSum(root.left,sum));
    	int h = Math.max(0, helpMaxPathSum(root.right,sum));
    	int s = l + h + root.val;
    	sum.sum = s > sum.sum ? s : sum.sum;
    	return Math.max(l,h) + root.val;
    }
    
    public int sumNumbers(TreeNode root) {
        if(root == null) return 0;
        Sum sum = new Sum();
        helpSumNumbers(root,"",sum);
        return sum.sum;
    }
	
    static class Sum {
    	int sum;
    	
    	public Sum() {
    		sum = 0;
    	}
    	
    	public Sum(int n) {
    		sum = n;
    	}
    }
    
    public void helpSumNumbers(TreeNode root, String n, Sum sum) {
        if(root.left == null) {
        	if(root.right == null) {
        		sum.sum += Integer.parseInt(n + root.val);
        	} else {
        		helpSumNumbers(root.right,n+root.val,sum);
        	}
        } else {
        	if(root.right == null) {
        		helpSumNumbers(root.left,n+root.val,sum);
        	} else {
        		helpSumNumbers(root.left,n+root.val,sum);
        		helpSumNumbers(root.right,n+root.val,sum);
        	}
        }
    }
    
    public TreeNode lowestCommonAncestorBT(TreeNode root, TreeNode p, TreeNode q) {
    	if(root == null) return null;
        List<TreeNode> listp = new ArrayList<TreeNode>();
        List<TreeNode> listq = new ArrayList<TreeNode>();
        findPath(root,p,listp);
        findPath(root,q,listq);
        
        
        Set<TreeNode> set = new HashSet<TreeNode>(listq);
        for (int i = listp.size() - 1; i >= 0; i--) {
			if(set.contains(listp.get(i))) {
				return listp.get(i);
			}
		}
        return null;
    }
	
    public boolean findPath(TreeNode root, TreeNode p, List<TreeNode> list) {
    	list.add(root);
    	if(root == p) return true;
    	if(root.left != null) {
    		if(findPath(root.left, p, list)) return true;
    	}
    	if(root.right != null) {
    		if(findPath(root.right, p, list)) return true;
    	}
    	list.remove(list.size() - 1);
    	return false;
    }
    
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
    	if(root == null) return null;
        List<TreeNode> listp = new ArrayList<TreeNode>();
        List<TreeNode> listq = new ArrayList<TreeNode>();
        
        TreeNode tn = root;
        while(true) {
        	listp.add(tn);
	        if(tn.val == p.val) {
	        	break;
	        } else if(tn.val < p.val) {
	        	tn = tn.right;
	        } else {
	        	tn = tn.left;
	        }
        }
        
        tn = root;
        while(true) {
        	listq.add(tn);
	        if(tn.val == q.val) {
	        	break;
	        } else if(tn.val < q.val) {
	        	tn = tn.right;
	        } else {
	        	tn = tn.left;
	        }
        }
        
        Set<TreeNode> set = new HashSet<TreeNode>(listq);
        for (int i = listp.size() - 1; i >= 0; i--) {
			if(set.contains(listp.get(i))) {
				return listp.get(i);
			}
		}
        return null;
    }
	
    public int sumOfLeftLeaves(TreeNode root) {
        if(root == null) return 0;
        int sum = 0;
        if(root.left != null) {
        	if(root.left.left == null && root.left.right == null) {
        		sum += root.left.val;
        	} else {
        		sum += sumOfLeftLeaves(root.left);
        	}
        }
        sum += sumOfLeftLeaves(root.right);
        return sum;
    }
	
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<TreeNode> now = new ArrayList<TreeNode>();
        List<TreeNode> temp = new ArrayList<TreeNode>();
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if(root == null) return list;
        now.add(root);
        boolean order = true;
        while(!now.isEmpty()) {
        	List<Integer> l = new ArrayList<Integer>();
        	for(int i = now.size() - 1; i >= 0; i --) {
        		TreeNode tn = now.get(i);
        		l.add(tn.val);
        		if(order) {
        			if( tn.left != null) {
        				temp.add(tn.left);
        			}
        			if( tn.right != null) {
        				temp.add(tn.right);
        			}
        		} else {
        			if( tn.right != null) {
        				temp.add(tn.right);
        			}
        			if( tn.left != null) {
        				temp.add(tn.left);
        			}
        		}
        	}
        	order = !order;
        	now.clear();
        	List<TreeNode> t = now;
        	now = temp;
        	temp = t;
        	list.add(l);
        }
        return list;
    }
	
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m + n - 1;
        for(;m > 0 && n > 0; i --) {
        	if(nums1[m - 1] > nums2[n - 1]) {
        		nums1[i] = nums1[m - 1];
        		m --;
        	} else {
        		nums1[i] = nums2[n - 1];
        		n --;
        	}
        }
        while(m>0) {
        	nums1[i--] = nums1[m-1];
        	m --;
        }
        while(n>0) {
        	nums1[i--] = nums2[n-1];
        	n --;
        }
    }
	
    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right))+1;
    }

    public String reverseWords(String s) {
        String[] strs = s.split("\\s+",-1);
        StringBuilder sb = new StringBuilder();
        for (int i = strs.length - 1; i >= 0; i--) {
			sb.append(strs[i]);
			sb.append(' ');
		}
        return sb.toString().trim();
    }
	
	public int kthSmallest(TreeNode root, int k) {
		List<Integer> list = new ArrayList<Integer>();
		kthSmallestHelp(root,list);
		return list.get(k-1);
    }
	
    public void kthSmallestHelp(TreeNode root,  List<Integer> list) {
    	if(root == null) return;
    	kthSmallestHelp(root.left,list);
    	list.add(root.val);
    	kthSmallestHelp(root.right,list);
    }
    
    public ListNode detectCycle(ListNode head) {
    	ListNode fast = head, slow = head;
    	while(fast != null) {   
    		fast = fast.next;
    		if(fast == null) return null;
    		fast = fast.next;
    		slow = slow.next;
    		if(fast == slow) {
    			fast = head;
    			while(fast != slow) {
    				fast = fast.next;
    	    		slow = slow.next;
    			}
    			return fast;
    		}
    	}
    	return null;
    }
	
    public boolean hasCycle(ListNode head) {
    	ListNode fast = head, slow = head;
    	while(fast != null) {
    		fast = fast.next;
    		if(fast == null) return false;
    		fast = fast.next;
    		slow = slow.next;
    		if(fast == slow) return true;
    	}
    	return false;
    }
	
    public int bulbSwitch(int n) {
    	
        return (int)Math.sqrt(n);
    }
	
    public int[] countBits(int num) {
        int[] res = new int[num+1];
        for(int i=1;i<=num;i++){
            res[i] = res[i >> 1] + (i & 1);
        }
        return res;
    }
	
    public String reverseVowels(String s) {
        List<Character> list = new ArrayList<Character>();
        char[] cs = s.toCharArray();
        boolean[] b = new boolean[s.length()];
        for (int i = 0; i < cs.length; i++) {
        	char c = cs[i];
			if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
				b[i] = true;
				list.add(c);
			}
		}
        for (int i = b.length - 1; i >= 0; i--) {
			if(b[i]) {
				cs[i] = list.get(0);
				list.remove(0);
			}
		}
        return new String(cs);
    }
	
    public String reverseString(String s) {
    	char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length>>1; i++) {
			char c = cs[i];
			cs[i] = cs[cs.length-1-i];
			cs[cs.length-1-i] = c;
		}
        return new String(cs);
    }
	
    public List<Integer> topKFrequent(int[] nums, int k) {
    	Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for (int i = 0; i < nums.length; i++) {
			Integer value = map.get(nums[i]);
			if(value == null) {
				map.put(nums[i], 1);
			} else {
				map.put(nums[i], value+1);
			}
		}
        class KeyValue implements Comparable<KeyValue>{
        	int value;
        	int key;
        	KeyValue(int value, int key) {
        		this.key = key;
        		this.value = value;
        	}
        	public int compareTo(KeyValue kv) {
        		return value - kv.value;
        	}
        }
        Iterator<Integer> iterator = map.keySet().iterator();
        PriorityQueue<KeyValue> pq = new PriorityQueue<KeyValue>();
        for (int i = 0; i < k; i++) {
        	int key = iterator.next();
        	KeyValue kv = new KeyValue(map.get(key),key);
        	pq.add(kv);
		}
        while(iterator.hasNext()) {
        	int key = iterator.next();
        	KeyValue kv = new KeyValue(map.get(key),key);
        	pq.add(kv);
        	pq.poll();
        }
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < k; i++) {
        	list.addFirst(pq.poll().key);
		}
        return list;
    }
	
    public boolean isPowerOfFour(int num) {
    	if(num<=0) return false;
        int t = Integer.parseInt("00101010101010101010101010101010", 2);System.out.println(t);
        if((num & t) != 0) return false;
        num = num & (num-1);
        return num == 0;
    }
	
    public int integerBreak(int n) {
        if(n < 6) {
        	return n/2 * (n - n/2);
        }
        int sum = 1;
        if(n % 3 == 1) {
        	sum *= 4;
        	n -= 4;
        	while(n > 0) {
        		sum *= 3;
            	n -= 3;
        	}
        	return sum;
        }
        while(n >= 3) {
    		sum *= 3;
        	n -= 3;
    	}
        return n == 0 ? sum : sum * n;
    }
	
    public int numDecodings(String s) {
    	if(s.length() == 0) return 0;
    	if(s.charAt(0) == '0') return 0;
    	List<Integer> list = new ArrayList<Integer>();
    	list.add(1);
    	list.add(2);
    	int t = 1;
    	int len = 0;
    	boolean f = false;
    	for (int i = 0; i < s.length(); i++) {
    		char c = s.charAt(i);
			if(c>'2') {
				if(f) {
					if(s.charAt(i-1) == '2' && c>'6') {
						t *= calfibo(list,len);
						len = 0;
						f = false;
					} else {
						t *= calfibo(list,len+1);
						len = 0;
						f = false;
					}
				} else continue;
			}
			else if(c>'0'){
				f = true;
				len ++;
			} else {
				if(f) {
					if(len>1) {
						t *= calfibo(list,len-1);
						len = 0;
						f = false;
					} else {
						len = 0;
						f = false;
					}
				} else return 0;
			}
		}
    	if(len != 0) {
    		t *= calfibo(list,len);
    	}
    	return t;
    }
	
    public int calfibo(List<Integer> list, int n) {
    	if(n>list.size()) {
    		for (int i = list.size(); i < n; i++) {
				list.add(list.get(i-1)+list.get(i-2));
			}
    	}
    	return list.get(n-1);
    }
    
    public int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; ) {
			if(nums[i] != i+1 && nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[nums[i]-1]) {
				int t = nums[i];
				nums[i] = nums[nums[i]-1];
				nums[t-1] = t;
			} else {
				i ++;
			}
		}
        for (int i = 0; i < nums.length; i++) {
			if(nums[i] != i+1) return i+1;
		}
        return nums.length+1;
    }
	
    public ListNode rotateRight(ListNode head, int k) {
    	if(head == null) return null;
        ListNode now = head, last = head;
        int len = 0;
        while(now != null) {
        	last = now;
        	now = now.next;
        	len ++;
        }
        k = k % len;
        k = len - k - 1;
        now = head;
        while(k>0) {
        	now = now.next;
        	k --;
        }
        last.next = head;
        head = now.next;
        now.next = null;
        return head;
    }
	
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int[] need = new int[gas.length];
        for (int i = 0; i < need.length; i++) {
			need[i] = cost[i] - gas[i];
		}
        
        if (gas.length == 1) return gas[0]>=cost[0]?0:-1;
        int begin = 0;
        int current = 0;
        int n = need.length;
        for (int i = 0; begin != need.length && n != 0; i = (i + 1 + need.length)%need.length) {
        	current -= need[i];
        	n --;
			if (current < 0) {
				current = 0;
				if(begin > i) return -1;
				begin = i+1;
				n = need.length;
			}
		}
        return n == 0?begin:-1;
        
    }
	
    public boolean canJump(int[] nums) {
    	if (nums.length < 2) return true;
    	int [] a = new int[nums.length];
    	a[nums.length-1] = 1;
    	for(int i = nums.length - 2; i > 0; i --) {
    		if (nums[i] >= a[i+1]) {
    			a[i] = 1;
    		} else {
    			a[i] = a[i+1] +1;
    		}
        }
    	if (nums[0] >= a[1]) {
			return true;
		} else {
			return false;
		}
    }
	
    public boolean canJumpHelp(int[] nums, int index) {
    	if(index == nums.length) return true;
        for(int i = 1; i < nums[index]; i ++) {
        	if(canJumpHelp(nums,index+i)) return true;
        }
        return false;
    }
    
    public List<List<Integer>> threeSum(int[] nums) {
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        
        for (int i = 0; i < nums.length; i++) {
			Integer t;
			if ((t = map.get(nums[i])) != null) {
				map.put(nums[i], 1+t);
			} else {
				map.put(nums[i], 1);
			}
		}
    	
    	return threeSumhelp(map);
    }

    public List<List<Integer>> threeSumhelp(Map<Integer,Integer> map) {
    	List<List<Integer>> list = new ArrayList<List<Integer>>();
    	
    	Iterator<Integer> iterator = map.keySet().iterator();
    	if (iterator.hasNext()) {
    		int k = iterator.next();
    		Integer t;
			t = map.get(k);
			if(t == 1) {
				map.remove(k);
			} else {
				map.put(k, t-1);
			}
			list.addAll(twoSum(map,0-k,k));
			map.remove(k);
			list.addAll(threeSumhelp(map));
			map.put(k, t);
    	}
    	
    	return list;
    }
    
	public List<List<Integer>> twoSum(Map<Integer,Integer> map, int target, int last) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		
		Iterator<Integer> iterator = map.keySet().iterator();
		if (iterator.hasNext()) {
    		int k = iterator.next();
    		Integer t,old;
    		old = map.get(k);
			if(old == 1) {
				map.remove(k);
			} else {
				map.put(k, old-1);
			}
			t = map.get(target-k);
			if(t != null) {
				List<Integer> l = new ArrayList<Integer>();
				l.add(last);
				l.add(k);
				l.add(target-k);
				Collections.sort(l);
				list.add(l);
			} 
			map.remove(k);
			list.addAll(twoSum(map,target,last));
			map.put(k, old);
    	}
		
		return list;
	}
}
