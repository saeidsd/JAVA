package main;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Red Black Tree implementation
 * 
 * @author Saeid Samadidana
 * @version 1.0
 */
public class RedBlackTree {

	private Node root;
	private Node nil = new Node();
	private static final char RED = 'r';
	private static final char BLACK = 'b';

	class Node {
		private Node parent;
		private Node left;
		private Node right;
		private char color;
		private int key;

		private Node() {
			this.setColor(BLACK);
		}

		public Node(Node parent, Node left, Node right, char color, int key) {
			this.setParent(parent);
			this.setLeft(left);
			this.setRight(right);
			this.color = color;
			this.setKey(key);
		}

		public Node(int key) {
			this.setColor(BLACK);
			this.setKey(key);
			this.setLeft(nil);
			this.setRight(nil);
		}

		public Node(Node parent, int key) {
			this.setColor(RED);
			this.setKey(key);
			this.setLeft(nil);
			this.setRight(nil);
			this.setParent(parent);
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public char getColor() {
			return color;
		}

		public void setColor(char color) {
			this.color = color;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public Node getUncle() {
			if (this == null || this.parent == null || this.getGrandParent() == null)
				return nil;

			return this.parent.isLeft() ? this.getGrandParent().getRight() : this.getGrandParent().getLeft();
		}

		private Node getGrandParent() {

			return (this != null && this.getParent() != null) ? this.getParent().getParent() : null;

		}

		public boolean isRed() {
			return this.getColor() == RED;
		}

		public boolean isBlack() {
			return this.getColor() == BLACK;
		}

		public boolean isLeft() {
			return (this.getParent() != null && this == this.getParent().getLeft());
		}

		public boolean isRight() {
			return !isLeft();
		}

	}

	public void insert(int key) {

		if (root == null)
			root = new Node(key);
		else {
			Node parent = locateNewNodeParent(root, key);
			Node node = new Node(parent, key);
			if (key > parent.getKey())
				node.getParent().setRight(node);
			else
				node.getParent().setLeft(node);
			afterInsert(node);
		}

	}

	private Node locateNewNodeParent(Node currentNodeentNode, int key) {

		if (key > currentNodeentNode.getKey()) {
			if (currentNodeentNode.getRight() == nil)
				return currentNodeentNode;
			else
				return locateNewNodeParent(currentNodeentNode.getRight(), key);
		} else {
			if (currentNodeentNode.getLeft() == nil)
				return currentNodeentNode;
			else
				return locateNewNodeParent(currentNodeentNode.getLeft(), key);
		}

	}

	private void afterInsert(Node node) {

		Node parent = node.getParent();
		Node uncle = node.getUncle();
		Node grandParent = node.getGrandParent();

		if (node != root) {

			if (parent.isRed()) {

				if (parent.isRed() && uncle.isRed()) {
					grandParent.setColor(RED);
					parent.setColor(BLACK);
					uncle.setColor(BLACK);
					afterInsert(grandParent);

				} else if (grandParent != null) {

					if (parent.isLeft()) {
						if (node.isRight())
							parent = rotateLeft(node);

						parent = rotateRight(parent);
						parent.setColor(BLACK);
						parent.getRight().setColor(RED);
					} else {
						if (node.isLeft())
							parent = rotateRight(node);

						parent = rotateLeft(parent);
						parent.setColor(BLACK);
						parent.getLeft().setColor(RED);
					}
				}

			}
		}

		root.setColor(BLACK);

	}

	private Node rotateLeft(Node node) {

		if (node == root) {
			root = node.getRight();
			node.setRight(node.getRight().getLeft());
			node.getRight().setParent(null);
			node.getRight().setLeft(node);

			node.getRight().getLeft().setParent(node);
			node.setParent(root);

		} else {

			Node parent = node.getParent();

			node.setParent(parent.getParent());
			if (parent == root) {
				root = node;
				root.setParent(null);
			} else {

				if (parent.isLeft())
					parent.getParent().setLeft(node);
				else
					parent.getParent().setRight(node);

			}

			parent.setRight(node.getLeft());
			node.getLeft().setParent(parent);
			node.setLeft(parent);

			parent.setParent(node);
		}
		return node;

	}

	private Node rotateRight(Node node) {

		if (node == root) {
			root = node.getLeft();
			Node right = node.getLeft().getRight();

			right.setParent(node);
			root.setParent(null);
			node.setLeft(right);

			root.setRight(node);
			node.setParent(root);

		} else {
			Node parent = node.getParent();

			node.setParent(parent.getParent());
			if (parent == root) {
				root = node;
				root.setParent(null);
			} else {

				if (parent.isLeft())
					parent.getParent().setLeft(node);
				else
					parent.getParent().setRight(node);

			}

			parent.setLeft(node.getRight());
			node.getRight().setParent(parent);
			node.setRight(parent);
			parent.setParent(node);
		}

		return node;

	}

	private Node search(int key) {
		return search(root, key);
	}

	private Node search(Node node, int key) {
		if (key == node.getKey()) {
			return node;
		} else if (key > node.getKey()) {
			return search(node.getRight(), key);
		} else {
			return search(node.getLeft(), key);
		}

	}

	public void delete(int key) {

		Node node = search(key);

		Node tempNode = nil;
		Node successor = nil;

		if (node.getLeft() == nil || node.getRight() == nil)
			successor = node;
		else
			successor = getSuccessor(node);

		if (successor.getLeft() != nil)
			tempNode = successor.left;
		else
			tempNode = successor.right;

		tempNode.parent = successor.parent;

		if (successor.getParent() == null)
			root = tempNode;

		else if (successor.getParent().getLeft() != nil && successor.parent.left == successor)
			successor.parent.left = tempNode;

		else if (successor.getParent().getRight() != nil && successor.parent.right == successor)
			successor.parent.right = tempNode;

		if (successor != node) {
			node.key = successor.key;
		}

		if (successor.getColor() == BLACK)
			afterDelete(tempNode);
	}

	private Node getSuccessor(Node node) {

		if (node.getLeft() != null && node.getLeft() != nil)
			return minNode(node.getRight());
		Node parent = node.getParent();

		while (parent != nil && node == parent.getRight()) {
			node = parent;
			parent = parent.getParent();
		}

		return parent;
	}

	private Node minNode(Node node) {
		while (node.getLeft() != null && node.getLeft() != nil)
			node = node.getLeft();
		return node;
	}

	private void afterDelete(Node node) {

		while (node != root && node.isBlack()) {
			if (node.isLeft()) {

				Node sibling = node.getParent().getRight();
				if (sibling.isRed()) {
					Node parent;
					sibling.setColor(BLACK);
					node.getParent().setColor(RED);
					parent =
					// rotateLeft(node.getParent());
					rotateLeft(node.getParent());
					// sibling = node.getParent().getRight();
					sibling = parent.getRight();
				}
				if (sibling.getLeft() != null && sibling.getRight() != null && sibling.getLeft().isBlack()
						&& sibling.getRight().isBlack()) {
					sibling.setColor(RED);
					node = node.getParent();
				} else if (sibling.getRight() != null && sibling.getRight().isBlack()) {
					sibling.getLeft().setColor(BLACK);
					sibling.setColor(RED);
					rotateRight(sibling);
					// sibling = node.getParent().getRight();
					sibling = node.getRight();
				}

				sibling.setColor(node.getParent().getColor());
				node.getParent().setColor(BLACK);
				if (sibling.getRight() != null)
					sibling.getRight().setColor(BLACK);
				rotateLeft(node.getParent());
				node = root;

			} else {
				Node parent = null;
				Node sibling = node.getParent().getLeft();
				if (sibling.isRed()) {

					sibling.setColor(BLACK);
					node.getParent().setColor(RED);
					parent = rotateRight(node.getParent());
					// rotateRight(node.getParent());
					// sibling = node.getParent().getLeft();
					sibling = parent.getLeft();
				}
				if (sibling.getLeft() != null && sibling.getRight() != null && sibling.getLeft().isBlack()
						&& sibling.getRight().isBlack()) {
					sibling.setColor(RED);
					// node = node.getParent();
					node = parent;
				} else if (sibling.getLeft() != null && sibling.getLeft().isBlack()) {

					sibling.getRight().setColor(BLACK);
					sibling.setColor(RED);
					// sibling =
					rotateLeft(sibling);
					// sibling = node.getParent().getLeft();
					sibling = parent.getLeft();
				}

				if (node.getParent() == null)
					sibling.setColor(BLACK);
				else
					sibling.setColor(node.getParent().getColor());
				if (node.getParent() != null)
					node.getParent().setColor(BLACK);
				if (sibling.getLeft() != null)
					sibling.getLeft().setColor(BLACK);
				rotateRight(node.getParent());
				node = root;

			}
		}
		node.setColor(BLACK);

	}

	public void print() {

		Node marker = new Node();
		Node emptyNode = new Node();

		Queue<Node> queue = new ArrayBlockingQueue<Node>(1000);

		queue.offer(root);
		queue.offer(marker);
		while (!queue.isEmpty()) {
			Node currentNode = queue.poll();
			if (currentNode == marker && !queue.isEmpty()) {
				queue.offer(marker);
				System.out.println("");
			} else {
				if (currentNode == emptyNode) {
					System.out.print(" - ");
				} else {
					if (currentNode != marker) {
						String print = "  " + currentNode.getKey() + "  ";
						if (currentNode.isRed())
							print = " <" + currentNode.getKey() + "> ";
						System.out.print(print);
					}
					if (currentNode.getLeft() == null)
						queue.offer(emptyNode);
					else
						queue.offer(currentNode.getLeft());

					if (currentNode.getRight() == null)
						queue.offer(emptyNode);
					else
						queue.offer(currentNode.getRight());
				}
			}
		}
	}
}