import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
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
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
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
     * @param curr the current node that we are accessing in the tree.
     * @param data the data being added to tree.
     * @return new node that contains the added data.
     */
    private AVLNode<T> add(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            curr = new AVLNode<T>(data);
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(add(curr.getLeft(), data));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(add(curr.getRight(), data));
        }
        balance(curr);
        return curr;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are removing is null.");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = remove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helper method for remove method that updates tree.
     *
     * @param curr current node of subtree.
     * @param data data of type T being removed.
     * @param dummy placeholder node.
     * @return node that contains the data being removed.
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private AVLNode<T> remove(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException(data + "is not located in this tree.");
        }

        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(remove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(remove(curr.getRight(), data, dummy));
        } else if (data.compareTo(curr.getData()) == 0) {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                return curr.getRight();
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else {
                AVLNode<T> dummy2 = new AVLNode<T>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        balance(curr);
        return curr;
    }

    /**
     * Helper method to search for nearest predecessor of subtree.
     *
     * @param curr current root of subtree.
     * @param dummy placeholder remove node.
     * @return nearest predecessor of subtree.
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getRight() != null) {
            curr.setRight(removePredecessor(curr.getRight(), dummy));
            balance(curr);
        } else {
            dummy.setData(curr.getData());
            curr = curr.getLeft();
        }
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
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
    private T get(AVLNode<T> node, T data) {
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
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
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
    private boolean contains(AVLNode<T> node, T data) {
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
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Helper method to calculate the height of any node in the tree
     * @param curr the current node in the tree
     * @return the height of the node in the tree, -1 if the tree is empty
     */
    private int height(AVLNode<T> curr) {
        return curr == null ? -1 : curr.getHeight();
    }


    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * This must be done recursively.
     *
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> deepestNodes = new ArrayList<>();
        deepestBranchesHelper(root, deepestNodes);
        return deepestNodes;
    }

    /**
     * Helper method that determines whether a node is in the deepest path in the tree
     * @param node the current node in the tree
     * @param deepestNodes List of nodes that follow the deepest path
     */
    private void deepestBranchesHelper(AVLNode<T> node, List<T> deepestNodes) {
        if (node == null) {
            return;
        }

        deepestNodes.add(node.getData());
        if (node.getBalanceFactor() == 0) {
            deepestBranchesHelper(node.getLeft(), deepestNodes);
            deepestBranchesHelper(node.getRight(), deepestNodes);
        } else if (node.getBalanceFactor() > 0) {
            deepestBranchesHelper(node.getLeft(), deepestNodes);
        } else if (node.getBalanceFactor() < 0) {
            deepestBranchesHelper(node.getRight(), deepestNodes);
        }
    }


    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * This must be done recursively.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null || data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("You passed improper data parameters.");
        }
        List<T> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        sortedInBetweenHelper(root, data1, data2, result);
        return result;
    }

    /**
     * Helper method that filters the node based on the input parameters
     * @param node the current node in the tree
     * @param data1 the lower bound of the in between
     * @param data2 the upper bound of the in between
     * @param result the List of nodes that fit the search criteria
     */
    private void sortedInBetweenHelper(AVLNode<T> node, T data1, T data2, List<T> result) {
        if (node == null) {
            return;
        }
        if (node.getData().compareTo(data1) > 0) {
            sortedInBetweenHelper(node.getLeft(), data1, data2, result);
        }
        if (node.getData().compareTo(data1) > 0 && node.getData().compareTo(data2) < 0) {
            result.add(node.getData());
        }
        if (node.getData().compareTo(data2) < 0) {
            sortedInBetweenHelper(node.getRight(), data1, data2, result);
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
    public AVLNode<T> getRoot() {
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

    /**
     * Helper method to perform balancing if needed
     * @param curr the current node in the tree
     */
    private void balance(AVLNode<T> curr) {
        update(curr);
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                rightRotate(curr.getRight());
            }
            leftRotate(curr);
        } else if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                leftRotate(curr.getLeft());
            }
            rightRotate(curr);
        }
    }

    /**
     * Helper method to perform a right rotation
     * @param curr the current node in the tree we are rotating
     */
    private void rightRotate(AVLNode<T> curr) {
        AVLNode<T> newRight = new AVLNode<>(curr.getData());

        newRight.setRight(curr.getRight());
        newRight.setLeft(curr.getLeft().getRight());

        curr.setRight(newRight);
        curr.setData(curr.getLeft().getData());
        curr.setLeft(curr.getLeft().getLeft());

        update(newRight);
        update(curr);
    }

    /**
     * Helper method to perform a left rotation
     * @param curr the current node in the tree we are rotating
     */
    private void leftRotate(AVLNode<T> curr) {
        AVLNode<T> newLeft = new AVLNode<>(curr.getData());

        newLeft.setLeft(curr.getLeft());
        newLeft.setRight(curr.getRight().getLeft());

        curr.setLeft(newLeft);
        curr.setData(curr.getRight().getData());
        curr.setRight(curr.getRight().getRight());

        update(newLeft);
        update(curr);
    }

    /**
     * Helper method to update the balance factor and height of node
     * @param curr the current node we are looking at
     */
    private void update(AVLNode<T> curr) {
        if (curr != null) {
            int leftHeight = height(curr.getLeft());
            int rightHeight = height(curr.getRight());
            curr.setHeight(1 + Math.max(leftHeight, rightHeight));
            curr.setBalanceFactor(leftHeight - rightHeight);
        }

    }
}
