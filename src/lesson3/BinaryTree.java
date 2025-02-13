package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    //Трудоемкость = O(n)
    //Ресурсоемкость = O(n)
    @Override
    public boolean remove(Object o) {
        if (root == null) return false;
        root = delete(root, (T) o);
        size--;
        return true;
    }

    private Node<T> delete(Node<T> node, T z) {
        if (z.compareTo(node.value) < 0)
            node.left = delete(node.left, z);
        else {
            if (z.compareTo(node.value) > 0) {
                node.right = delete(node.right, z);
            } else {
                if (node.left != null && node.right != null) {
                    Node<T> min = new Node<>(minimum(node.right).value);
                    min.left = node.left;
                    min.right = node.right;
                    node = min;
                    node.right = delete(node.right, node.value);
                } else {
                    if (node.left != null)
                        node = node.left;
                     else  node = node.right;
                }
            }
        }
        return node;
    }

    private Node<T> minimum(Node<T> node) {
        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private BinaryTreeIterator() {
            // Добавьте сюда инициализацию, если она необходима
        }
        private Node<T> current = null;

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        //Трудоемкость = O(n)
        //Ресурсоемкость = O(1)
        @Override
        public boolean hasNext() {
            return findNext() != null;
        }


        /**
         * Поиск следующего элемента
         * Средняя
         */
        //Трудоемкость = O(n)
        //Ресурсоемкость = O(1)
        @Override
        public T next() {
            current = findNext();
            if (current != null) return current.value;
            return null;
        }

        private Node<T> findNext() {
            if (root == null) return null;
            Node<T> successor = null;
            if (current != null) {
                Node<T> nowRoot = root;
                while (nowRoot != null) {
                    if (nowRoot.value.compareTo(current.value) > 0) {
                        successor = nowRoot;
                        nowRoot = nowRoot.left;
                    } else {
                        nowRoot = nowRoot.right;
                    }
                }
            } else {
                successor = root;
                while (successor.left != null) {
                    successor = successor.left;
                }
            }
            return successor;
        }


        /**
         * Удаление следующего элемента
         * Сложная
         */
        //Трудоемкость = O(n)
        //Ресурсоемкость = O(1)
        @Override
        public void remove() {
            root = delete(root, current.value);
            size--;
        }
    }


    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
