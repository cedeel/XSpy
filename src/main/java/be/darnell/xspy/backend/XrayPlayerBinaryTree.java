package be.darnell.xspy.backend;

import be.darnell.xspy.XrayPlayer;

public class XrayPlayerBinaryTree {
	
	private Node root;
	
	private static class Node {
		Node left;
		Node right;
		XrayPlayer data;
		
		Node (XrayPlayer newData) {
			left = null;
			right = null;
			data = newData;
		}
	}
	
	public XrayPlayerBinaryTree() {
		root = null;
	}
	
	public boolean lookup(XrayPlayer data) {
		return lookup(root, data);
	}
	
	private boolean lookup(Node node, XrayPlayer data) {
		if (node == null)
			return false;
		
		if (node.toString().equals(node.data.toString()))
			return true;
		
		else if (data.getXlevel() < node.data.getXlevel())
			return lookup(node.left, data);
		
		else
			return lookup(node.right, data);
	}
	
	public void insert(XrayPlayer data) {
		root = insert(root, data);
	}
	
	private Node insert(Node node, XrayPlayer data) {
		if (node == null)
			node = new Node(data);
		else {
			if (data.getXlevel() <= node.data.getXlevel())
				node.left = insert(node.left, data);
			else
				node.right = insert(node.right, data);
		}
		
		return node;
	}
	
	public int size() {
		return (size(root));
	}
	
	private int size(Node node) {
		if (node == null) return 0;
		
		return (size(node.left) + 1 + size(node.right));
	}
	
	/*
	public void printTree() {
		printTree(root);
	}
	
	private void printTree(Node node) {
		if(node == null) return;
		
		printTree(node.left);
	}
	*/
	
	public XrayPlayer[] getList() {
		return getList(root);
	}
	
	public XrayPlayer[] getList(Node node) {
		XrayPlayer[] list = new XrayPlayer[size()];
		int pos = 0;
		
		getList(node.right);
		list[pos] = node.data;
		pos++;
		getList(node.left);
		
		return list;
	}
	
}
