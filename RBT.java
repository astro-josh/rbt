package RBT;

import java.awt.Color;
import java.util.Stack;

/*
* Joshua Alexander
* RBT.java
* COSC 336
* Homework #3
* Double Linked List Red-Black Tree
* Worked with Robert Smith and Will (Classmates)
 */
// RBNode class
class RBNode {

    private RBNode left, right, parent;
    private int data, count;
    Color c;

    public RBNode(int value) {
        data = value;
        left = null;
        right = null;
        parent = null;
        count = 1;
        c = Color.red;
    }

    public RBNode getLeftChild() {
        return left;
    }

    public RBNode getRightChild() {
        return right;
    }

    public RBNode getParent() {
        return parent;
    }

    public int getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public Color getColor() {
        return c;
    }

    public void setColor(Color x) {
        c = x;
    }

    public void setParent(RBNode x) {
        parent = x;
    }

    public void setLeft(RBNode l) {
        left = l;
    }

    public void setRight(RBNode r) {
        right = r;
    }

    public void setData(int d) {
        data = d;
    }

    public void increaseCount() {
        count++;
    }

    public void decreaseCount() {
        count--;
    }
}

// Red Black Tree class
class RBT {

    private static RBNode root;

    public RBT() {
        root = null;
    }

    public RBNode getRoot() {
        return root;
    }

    // clears the tree, run this before taking more input
    public void clearTree() {
        root = null;
    }

    // returns parent of given node with null check
    public RBNode parentOf(RBNode node) {
        if (node == null || node.getParent() == null || node == root) {
            return null;
        } else {
            return node.getParent();
        }
    }

    // returns left of given node with null check
    public RBNode leftOf(RBNode node) {
        if (node == null) {
            return null;
        } else {
            return node.getLeftChild();
        }
    }

    // returns right of given node with null check
    public RBNode rightOf(RBNode node) {
        if (node == null) {
            return null;
        } else {
            return node.getRightChild();
        }
    }

    // returns grandparent of given node with null check
    public RBNode grandParentOf(RBNode node) {
        if (node == null || node.getParent() == null || node == root) {
            return null;
        } else {
            return node.getParent().getParent();
        }
    }

    // returns brother or sister of given node with null check
    public RBNode siblingOf(RBNode node) {
        if (node == null || parentOf(node) == null || node == root) {
            return null;
        } else if (node == node.getParent().getLeftChild() && node.getParent().getRightChild() != null) {
            return node.getParent().getRightChild();
        } else if (node == node.getParent().getRightChild() && node.getParent().getLeftChild() != null) {
            return node.getParent().getLeftChild();
        } else {
            return null;
        }
    }

    // returns the minElement of the tree
    public RBNode minElement(RBNode node) {
        if (node == null) {
            node = root;
        }

        // traverse left until node is null
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();

        }
        return node;
    }

    // returns the maxElement of the tree
    public RBNode maxElement(RBNode node) {
        if (node == null) {
            node = root;
        }

        // keep traversing right until null pointer is reached
        while (node.getRightChild() != null) {
            node = node.getRightChild();
        }
        return node;
    }

    // checks if node is left of its parent
    public boolean isLeft(RBNode node) {
        if (parentOf(node) != null && node == leftOf(parentOf(node))) {
            return true;
        } else {
            return false;
        }
    }

    // checks if node is rihgt of its parent
    public boolean isRight(RBNode node) {
        if (parentOf(node) != null && node == rightOf(parentOf(node))) {
            return true;
        } else {
            return false;
        }
    }

    // sets color of node with null check
    public void setColor(RBNode node, Color c) {
        if (node != null) {
            node.c = c;
        }
    }

    // to be run after insert to fix and make fit RB tree properties
    private void insertFix(RBNode node) {

        // while the node isnt the root or null
        while (node != null && node != root) {
            // if node and its parent are red
            if (node.getColor().equals(Color.red) && parentOf(node).getColor().equals(Color.red)) {
                // if uncle exists and uncle is red
                if (siblingOf(parentOf(node)) != null && siblingOf(parentOf(node)).getColor().equals(Color.red)) {
                    setColor(parentOf(node), colorSwitch(parentOf(node)));
                    setColor(siblingOf(parentOf(node)), colorSwitch(siblingOf(parentOf(node))));
                    setColor(grandParentOf(node), colorSwitch(grandParentOf(node)));
                    System.out.println("Uncle is red, flip the colors!");
                    node = grandParentOf(node);
                } else if (leftOf(grandParentOf(node)) != null && isLeft(parentOf(node))) {
                    if (node == rightOf(parentOf(node))) {
                        leftRotate(node, 2);
                    }
                    setColor(parentOf(node), colorSwitch(parentOf(node)));
                    setColor(grandParentOf(node), colorSwitch((grandParentOf(node))));
                    rightRotate(node, 3);
                    node = parentOf(node);
                } else if (rightOf(grandParentOf(node)) != null && isRight(parentOf(node))) {
                    if (node == leftOf(parentOf(node))) {
                        rightRotate(node, 2);
                    }
                    setColor(parentOf(node), colorSwitch(parentOf(node)));
                    setColor(grandParentOf(node), colorSwitch(grandParentOf(node)));
                    leftRotate(node, 3);
                    node = parentOf(node);
                } else {
                    setColor(root, Color.black);
                    return;
                }
            } else {
                setColor(root, Color.black);
                return;
            }
        }
        setColor(root, Color.black);
    }

    // right rotate around given node
    public void rightRotate(RBNode node, int num) {
        RBNode grandParent = grandParentOf(node);
        RBNode parent = parentOf(node);

        System.out.println("Right rotation");

        if (num == 3) {
            // right rotation with 3 nodes
            if (grandParent == root) {
                // if grandParent is root
                if (rightOf(parent) != null) {
                    rightOf(parent).setParent(grandParent);
                }
                grandParent.setParent(parent);
                grandParent.setLeft(rightOf(parent));
                parent.setRight(grandParent);
                parent.setParent(null);
                root = parent;

            } else if (parent == root) {
                // if parent is root
                rightOf(node).setParent(parent);
                parent.setParent(node);
                parent.setLeft(rightOf(node));
                node.setRight(parent);
                node.setParent(null);
                root = node;
            } else {
                // if parent and grandParent arent root
                if (isLeft(grandParent)) {
                    grandParent.getParent().setLeft(parent);
                } else {
                    grandParent.getParent().setRight(parent);
                }

                parent.setParent(parentOf(grandParent));

                if (rightOf(parent) != null) {
                    rightOf(parent).setParent(grandParent);
                }

                grandParent.setParent(parent);
                grandParent.setLeft(rightOf(parent));
                parent.setRight(grandParent);
            }
        } else if (num == 2) {
            // right rotation with 2 nodes (pre processing)
            System.out.println("Pre processing rotate.");
            parent.setLeft(rightOf(node));
            rightOf(node).setParent(parent);
            parent.setParent(node);
            node.setParent(grandParent);
            grandParent.setRight(node);
            node.setRight(parent);
        }
    }

    // left rotate around given node
    public void leftRotate(RBNode node, int num) {
        RBNode parent = parentOf(node);
        RBNode grandParent = grandParentOf(node);

        System.out.println("left rotation");

        if (num == 3) {
            // left rotation with 3 nodes
            if (grandParent == root) {
                // if grandParent is root
                if (leftOf(parent) != null) {
                    leftOf(parent).setParent(grandParent);
                }
                grandParent.setParent(parent);
                grandParent.setRight(leftOf(parent));
                parent.setLeft(grandParent);
                parent.setParent(null);
                root = parent;

            } else if (parent == root) {
                // if parent is root
                leftOf(node).setParent(parent);
                parent.setParent(node);
                parent.setRight(leftOf(node));
                node.setLeft(parent);
                node.setParent(null);
                root = node;
            } else {
                // if parnet and grandParent arent the root
                if (isRight(grandParent)) {
                    grandParent.getParent().setRight(parent);
                } else {
                    grandParent.getParent().setLeft(parent);
                }

                parent.setParent(parentOf(grandParent));

                if (leftOf(parent) != null) {
                    leftOf(parent).setParent(grandParent);
                }
                grandParent.setParent(parent);
                grandParent.setRight(leftOf(parent));
                parent.setLeft(grandParent);
            }
        } else if (num == 2) {
            // left rotation with 2 nodes (pre processing)
            System.out.println("Pre processing rotate.");
            parent.setRight(leftOf(node));
            leftOf(node).setParent(parent);
            parent.setParent(node);
            node.setParent(grandParent);
            grandParent.setLeft(node);
            node.setLeft(parent);
        }
    }

    // switches the color of given node
    public Color colorSwitch(RBNode node) {
        if (node != null && node.getColor() == Color.red) {
            return Color.black;
        } else {
            return Color.red;
        }
    }

    // search for a node with a certain int value
    public RBNode search(int key) {
        RBNode currentNode = root;
        RBNode prevNode = currentNode;
        boolean cont = true;

        while (cont && currentNode != null) { // while currentNode isnt null and
            // cont is true
            if (currentNode.getData() < key) { // current is less than key
                prevNode = currentNode; // set previous to current
                currentNode = currentNode.getRightChild(); // current is now the
                // right
            } else if (currentNode.getData() > key) { // current is greater than
                // key
                prevNode = currentNode; // set previous to current
                currentNode = currentNode.getLeftChild(); // current is now left
            } else if (currentNode.getData() == key) { // current equals key
                cont = false; // cont is false to stop while
            }
        }
        if (currentNode != null) {
            return currentNode;
        } else {
            return prevNode;
        }
    }

    // inserts a node into the bst
    public void insert(int data) {

        System.out.println("Inserting: " + data);

        if (root == null) {
            root = new RBNode(data);
            return;
        }

        // current equals node returned by search which will be its right
        // position based on its value
        RBNode currentNode = search(data);

        // if currentNode data equals inserting data, value already exists, just
        // increment the count
        if (currentNode.getData() == data) {
            currentNode.increaseCount();
        } else if (currentNode.getData() < data) { // if current is less than
            // data
            currentNode.setRight(new RBNode(data)); // insert new node to right
            // of current
            rightOf(currentNode).setParent(currentNode); // set parent of
            insertFix(rightOf(currentNode));
            // new node to
            // current
        } else if (currentNode.getData() > data) { // if current is greater than
            // data
            currentNode.setLeft(new RBNode(data)); // insert new node to left of
            // current
            leftOf(currentNode).setParent(currentNode); // set parent of
            insertFix(leftOf(currentNode)); // new node to
            // current
        }
    }

    // prints in order traversal of tree
    // should be left, root, right & least to greatest
    public void inOrder() {
        RBNode currentNode = root;
        RBNode temp = null;
        String p;

        Stack<RBNode> stack = new Stack<RBNode>();

        while (currentNode != null) {
            stack.push(currentNode);
            currentNode = currentNode.getLeftChild();
        }

        System.out.println("Root is: " + root.getData());

        while (!stack.isEmpty()) {
            temp = stack.pop();

            if (temp.getParent() != null) {
                p = "  Parent Data: " + temp.getParent().getData();
            } else {
                p = "  Parent Data: Null";
            }

            System.out.println(temp.getData() + " Count: " + temp.getCount() + " Color: " + temp.getColor() + p);

            if (temp.getRightChild() != null) {
                temp = temp.getRightChild();

                while (temp != null) {
                    stack.push(temp);
                    temp = temp.getLeftChild();
                }
            }
        }
    }

    // check count for delete function, decrease count if greater than 1,
    // otherwise node needs to be deleted
    private boolean hasDuplicate(RBNode node) {
        if (node.getCount() > 1) {
            node.decreaseCount();
            return false;
        } else {
            return true;
        }
    }

    // delete a node from tree
    public void delete(int x) {
        RBNode currentNode = search(x);

        // if node returned from search's data isnt equal to x then its not found
        if (currentNode.getData() == x) {

            // if delCount returns true(means count is equal to 1), then delete
            // if delCount returns false, dont have to delete, just reduce count)
            if (hasDuplicate(currentNode)) {

                // 4 cases
                // checks if it has no children
                if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) {
                    // checks if its the root
                    if (currentNode.getParent() != null) {
                        // if not root, check if its left or right child
                        parentOf(currentNode).setColor(Color.black);
                        siblingOf(currentNode).setColor(Color.red);
                        if (isLeft(currentNode)) {
                            // if left child, set its parent's left pointer to null
                            currentNode.getParent().setLeft(null);
                        } else {
                            // else it is right child, set its parent's right
                            // pointer to null
                            currentNode.getParent().setRight(null);
                        }
                    } else {
                        root = null;
                    }
                    // finally set the node to null
                    currentNode = null;

                } // has two children
                else if (currentNode.getLeftChild() != null && currentNode.getRightChild() != null) {
                    if (currentNode.getColor().equals(Color.red)) {
                        // red with two children
                        // get max of left child
                        RBNode temp = maxElement(leftOf(currentNode));
                        // swap data of max of left child with node that is being
                        // deleted
                        currentNode.setData(temp.getData());

                        // if left child of temp exists, swap its data with left child
                        // and set right child to null
                        if (temp.getLeftChild() != null) {
                            //temp.setData(temp.getLeftChild().getData());
                            //temp.setLeft(null);
                            temp = temp.getLeftChild();
                            temp.setParent(currentNode);
                            currentNode.setLeft(temp);
                        } else {
                            // if max (temp) of left child is left child, set left
                            // pointer to null, else set right pointer of parent to null
                            if (temp == currentNode.getLeftChild()) {
                                currentNode.setLeft(null);
                            } else {
                                temp.getParent().setRight(null);
                            }
                        }
                        colorSwitch(currentNode);
                        leftOf(currentNode).setColor(siblingOf(leftOf(currentNode)).getColor());
                        rightOf(currentNode).setColor(siblingOf(rightOf(currentNode)).getColor());

                    } else {
                        // black node with two reds
                        // get max of left child
                        RBNode temp = maxElement(leftOf(currentNode));
                        parentOf(temp).setColor(Color.black);
                        if (siblingOf(temp) != null) {
                            siblingOf(temp).setColor(Color.red);
                        }
                        // swap data of max of left child with node that is being
                        // deleted
                        currentNode.setData(temp.getData());

                        // if right child of temp exists, swap its data with right child
                        // and set right child to null
                        if (temp.getLeftChild() != null) {
                            temp = temp.getLeftChild();
                            temp.setParent(currentNode);
                            currentNode.setLeft(temp);
                        } else {
                            // if max (temp) of left child is left child, set left
                            // pointer to null, else set right pointer of parent to null
                            if (temp == currentNode.getLeftChild()) {
                                currentNode.setLeft(null);
                            } else {
                                temp.getParent().setRight(null);
                            }
                        }
                    }
                    root.setColor(Color.black);
                } // only has left child
                else if (currentNode.getLeftChild() != null && currentNode.getRightChild() == null) {

                    if (currentNode.getParent() != null) {
                        // if current parent isn't null, set parent of current's
                        // left child to current's parent
                        currentNode.getLeftChild().setParent(currentNode.getParent());
                        if (isLeft(currentNode)) {
                            // if current is left child, set left of parent to
                            // current's left child
                            currentNode.getParent().setLeft(currentNode.getLeftChild());
                        } else {
                            // esle set right of parent to current's left child
                            currentNode.getParent().setRight(currentNode.getLeftChild());
                        }
                    } else {
                        // else set current to current's left child and set parent
                        // to null (if current's parent is null)
                        currentNode = currentNode.getLeftChild();
                        currentNode.setColor(Color.black);
                        currentNode.setParent(null);
                    }
                } // only has right child
                else if (currentNode.getLeftChild() == null && currentNode.getRightChild() != null) {

                    if (currentNode.getParent() != null) {
                        // if current parent isn't null, set parent of current's
                        // right child to current's parent
                        currentNode.getRightChild().setParent(currentNode.getParent());
                        if (isLeft(currentNode)) {
                            // if current is left child, set left of parent to
                            // current's right child
                            currentNode.getParent().setLeft(currentNode.getRightChild());
                        } else {
                            // else set right of parent to current's right child
                            currentNode.getParent().setRight(currentNode.getRightChild());
                        }
                    } else {
                        // else set current to current's right child and set parent
                        // to null (if current's parent is null)
                        currentNode = currentNode.getRightChild();
                        currentNode.setColor(Color.black);
                        currentNode.setParent(null);
                    }
                }
            }
        } else {
            // else element to delete is not in tree
            System.out.println("Element to delete is not in tree.");
        }
    }
}
