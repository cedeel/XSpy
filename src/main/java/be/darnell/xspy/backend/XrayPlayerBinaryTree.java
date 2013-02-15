/*
 * Copyright (c) 2013 cedeel.
 * All rights reserved.
 * 
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * The name of the author may not be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
