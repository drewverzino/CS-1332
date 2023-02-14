import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Andrew Verzino
 * @version 1.0
 * @userid averzino3
 * @GTID 903696663
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The Collection you are passing is null.");
        }


        for (T curr : data) {
            if (curr == null) {
                clear();
                throw new IllegalArgumentException("An Object in your Collection is null.");
            }
            add(curr);
        }

    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are inserting is null.");
        }

        root = add(root, data);
    }

    /**
     * Helper method for add method.
     *
     * @param node the current node that we are accessing in the tree.
     * @param data the data being added to tree.
     * @return new node that contains the added data.
     */
    private BSTNode<T> add(BSTNode<T> node, T data) {
        if (node == null) {
            size++;
            node = new BSTNode<>(data);
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(add(node.getLeft(), data));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(add(node.getRight(), data));
        }
        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are removing is null.");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = remove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helper method for remove method that updates tree.
     *
     * @param node current node of subtree.
     * @param data data of type T being removed.
     * @param dummy placeholder node.
     * @return node that contains the data being removed.
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private BSTNode<T> remove(BSTNode<T> node, T data, BSTNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException(data + "is not located in this tree.");
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(remove(node.getLeft(), data, dummy));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(remove(node.getRight(), data, dummy));
        } else {
            dummy.setData(node.getData());
            size--;

            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                node.setRight(removeSuccessor(node.getRight(), dummy2));
                node.setData(dummy2.getData());
            }
        }
        return node;
    }

    /**
     * Helper method to search for nearest successor of subtree.
     *
     * @param node current root of subtree.
     * @param dummy placeholder remove node.
     * @return nearest successor of subtree.
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> node, BSTNode<T> dummy) {
        if (node.getLeft() == null) {
            dummy.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(removeSuccessor(node.getLeft(), dummy));
        }
        return node;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are accessing is null.");
        }
        return get(root, data);
    }

    /**
     * Helper method for get method.
     *
     * @param node current node of subtree.
     * @param data data of type T being retrieved.
     * @return node's data that is being retrieved.
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private T get(BSTNode<T> node, T data) {
        if (node != null) {
            if (node.getData().equals(data)) {
                return node.getData();
            } else if (node.getData().compareTo(data) > 0) {
                return get(node.getLeft(), data);
            } else {
                return get(node.getRight(), data);
            }
        }

        throw new NoSuchElementException(data + "is not located in this tree.");
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are accessing is null.");
        }

        return contains(root, data);
    }

    /**
     * Helper method for contains method.
     *
     * @param node current node of subtree.
     * @param data data of type T being searched.
     * @return true if data is in tree, false otherwise.
     */
    private boolean contains(BSTNode<T> node, T data) {
        if (node != null) {
            if (node.getData().equals(data)) {
                return true;
            } else if (node.getData().compareTo(data) > 0) {
                return contains(node.getLeft(), data);
            } else {
                return contains(node.getRight(), data);
            }
        }

        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> nodes = new ArrayList<>();
        return preorder(root, nodes);

    }

    /**
     * Helper method for preorder traversal of the tree.
     *
     * @param node the current node that we are accessing in the tree
     * @param nodes the list of nodes that have been traversed
     * @return a complete list of nodes in the subtree in preorder.
     */
    private List<T> preorder(BSTNode<T> node, List<T> nodes) {
        if (node != null) {
            nodes.add(node.getData());
            preorder(node.getLeft(), nodes);
            preorder(node.getRight(), nodes);
        }
        return nodes;
    }


    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> nodes = new ArrayList<>();
        return inorder(root, nodes);
    }

    /**
     * Helper method for inorder traversal of the tree.
     *
     * @param node the current node that we are accessing in the tree
     * @param nodes the list of nodes that have been traversed
     * @return a complete list of nodes in the subtree in inorder.
     */
    private List<T> inorder(BSTNode<T> node, List<T> nodes) {
        if (node != null) {
            inorder(node.getLeft(), nodes);
            nodes.add(node.getData());
            inorder(node.getRight(), nodes);
        }
        return nodes;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> nodes = new ArrayList<>();
        return postorder(root, nodes);
    }

    /**
     * Helper method for postorder traversal of the tree.
     *
     * @param node the current node that we are accessing in the tree
     * @param nodes the list of nodes that have been traversed
     * @return a complete list of nodes in the subtree in postorder.
     */
    private List<T> postorder(BSTNode<T> node, List<T> nodes) {
        if (node != null) {
            postorder(node.getLeft(), nodes);
            postorder(node.getRight(), nodes);
            nodes.add(node.getData());
        }
        return nodes;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            BSTNode<T> temp = queue.poll();
            list.add(temp.getData());
            if (temp.getLeft() != null) {
                queue.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                queue.add(temp.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }

        return height(root);
    }

    /**
     * Helper method for calculating height of tree.
     *
     * @param node the current node that we are accessing in the tree
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int height(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }

        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException(String.format("You must access [0, %d) elements.", size));
        }
        List<T> kElements = new ArrayList<>();
        reverseInOrder(root, k, kElements);

        return kElements;
    }

    /**
     * Helper method to traverse through tree in reverse inorder.
     *
     * @param node the current node in the tree
     * @param k the number of elements we are adding to list
     * @param list List of maximum k elements
     */
    private void reverseInOrder(BSTNode<T> node, int k, List<T> list) {
        if (node == null) {
            return;
        }
        if (list.size() < k) {
            reverseInOrder(node.getRight(), k, list);
            if (list.size() < k) {
                list.add(0, node.getData());
                reverseInOrder(node.getLeft(), k, list);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
