package pl.javastart.task;

// Uzupełnij klasę. Możesz ją dowolnie modyfikować.
// Celem jest, żeby przechodziły testy w src/test/java/[...]/CustomListTest
public class CustomList<E> implements CustomListInterface<E> {
    //************************************************************************
    /*
    Implementacja odwzorowująca LinkedList. Dalej w tym samym pliku pozostawiłem wykomentowaną implementację
    odwzorowującą ArrayList.
     */
    //******************************************************************************

    private class Node {
        Node nextNode;
        Node prevNode;
        E obj;

        public Node(E obj) {
            this.obj = obj;
        }

        /*
        Brak setterów i getterów ze względu na to, że jest to klasa wewnętrzna i prywatna, więc tylko
        metody klasy głównej będą mogły wpływać na stan obiektów klasy Node.
         */
    }

    private Node firstNode = null;
    private Node lastNode = null;
    private int currentElementNumber;

    @Override
    public boolean add(E element) {
        Node newNode = new Node(element);

        if (currentElementNumber == 0) {
            firstNode = newNode;
            lastNode = firstNode;
        } else {
            lastNode.nextNode = newNode;
            newNode.prevNode = lastNode;
            lastNode = newNode;
        }

        currentElementNumber++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index > currentElementNumber || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == currentElementNumber) {
            add(element);
            return;
        }

        Node nodeToBeAdded = new Node(element);

        if (index == 0) {
            addAsFirstNode(nodeToBeAdded);
        }

        Node previousNode = findNode(index - 1);
        Node nextNode = previousNode.nextNode;  // unikam dzięki temu ponownego użycia pętli tylko po to, by znaleźć kolejny węzeł

        addAsMiddleNode(previousNode, nodeToBeAdded, nextNode);
        currentElementNumber++;
    }

    private void addAsFirstNode(Node nodeToBeAdded) {
        firstNode.prevNode = nodeToBeAdded;
        nodeToBeAdded.nextNode = firstNode;
        firstNode = nodeToBeAdded;
    }

    private void addAsMiddleNode(Node previousNode, Node middleNode, Node nextNode) {
        previousNode.nextNode = middleNode;
        middleNode.prevNode = previousNode;
        middleNode.nextNode = nextNode;
        nextNode.prevNode = middleNode;
    }

    @Override
    public E remove(int index) {
        if (index >= currentElementNumber || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == (currentElementNumber - 1)) {
            E removedElement = lastNode.obj;
            removeLastNode();
            currentElementNumber--;
            return removedElement;
        }

        if (index == 0) {
            E removedElement = firstNode.obj;
            Node secondNode = firstNode.nextNode;

            if (secondNode == null) {
                nullifyList();
                currentElementNumber--;
                return removedElement;
            }

            secondNode.prevNode = null;
            firstNode = secondNode;
            currentElementNumber--;
            return removedElement;
        }

        Node removedNode = findNode(index);
        E removedElement = removedNode.obj;
        removeMiddleNode(removedNode);
        currentElementNumber--;

        return removedElement;
    }

    private void removeMiddleNode(Node middleNode) {
        Node previousNode = middleNode.prevNode;
        Node nextNode = middleNode.nextNode;
        previousNode.nextNode = nextNode;
        nextNode.prevNode = previousNode;
    }

    private void removeLastNode() {
        if (lastNode.prevNode == null) {
            nullifyList();
            return;
        }

        Node penultimateNode = lastNode.prevNode;
        penultimateNode.nextNode = null;
        lastNode = penultimateNode;
    }

    private void nullifyList() {
        firstNode.nextNode = null;
        lastNode.prevNode = null;
        firstNode = lastNode = null;
    }

    @Override
    public int size() {
        return currentElementNumber;
    }

    @Override
    public String toString() {
        String resultString = "[";
        Node currentNode = firstNode;

        while (currentNode != null) {
            resultString += currentNode.obj.toString();
            if (currentNode.nextNode != null) {
                resultString += ", ";
            }
            currentNode = currentNode.nextNode;
        }

        return resultString + "]";
    }

    @Override
    public E get(int index) {
        if (index >= currentElementNumber || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        return findNode(index).obj;
    }

    private Node findNode(int index) {
        Node foundNode = firstNode;

        if (index == (currentElementNumber - 1)) {
            return lastNode;
        }

        for (int i = 0; i < index; i++) {
            foundNode = foundNode.nextNode;
        }

        return foundNode;
    }





    //******************************************************************************
    // Poniżej wspomniana na początku implementacja odwzorowująca ArrayList
    //******************************************************************************

//    private static final int DEFAULT_CAPACITY = 10;
//    private E[] array = (E[]) new Object[DEFAULT_CAPACITY];
//    private int currentElementNumber;
//
//    @Override
//    public boolean add(E element) {
//        if (currentElementNumber == array.length) {
//            extendArray();
//        }
//        array[currentElementNumber++] = element;
//        return true;
//    }
//
//    @Override
//    public void add(int index, E element) {
//        if (index > currentElementNumber || index < 0) {
//            throw new IndexOutOfBoundsException();
//        }
//
//        if (currentElementNumber == array.length) {
//            extendArray();
//        }
//
//        if (index == currentElementNumber) {
//            add(element);
//            return;
//        }
//
//        shiftArrayRight(index);
//        array[index] = element;
//    }
//
//    @Override
//    public E remove(int index) {
//        if (index >= currentElementNumber || index < 0) {
//            throw new IndexOutOfBoundsException();
//        }
//
//        if (index == (currentElementNumber - 1)) {
//            //array[index] = null;  // choć to chyba niezupełnie konieczne, jako że i tak ważniejszy jest stan currentElementNumber
//            currentElementNumber--;
//            return array[index];
//        }
//
//        E removedElement = array[index];  // postanowiłem to zwrócić, by odwzorować sygnaturę prawdziwego ArrayList.remove(int index)
//        shiftArrayLeft(index);
//
//        return removedElement;
//    }
//
//    private void shiftArrayLeft(int startIndex) {
//        /*
//        Poniżej tyle kombinacji (zamiast zwykłego przesuwania elementów w pętli np. w stylu array[i] = array[i + 1])
//        tylko po to, by nie utracić wartości usuwanego elementu (ma on zostać zwrócony przez metodę remove(int index))
//         */
//        E[] arrayRightPart = (E[]) new Object[array.length - startIndex - 1];
//        System.arraycopy(array, startIndex + 1, arrayRightPart, 0, arrayRightPart.length);
//        System.arraycopy(arrayRightPart, 0, array, startIndex, arrayRightPart.length);
//        currentElementNumber--;
//    }
//
//    private void shiftArrayRight(int startIndex) {
//        if (currentElementNumber >= array.length) {
//            extendArray();
//        }
//
//        for (int i = currentElementNumber; i > startIndex; i--) {
//            array[i] = array[i - 1];
//        }
//
//        currentElementNumber++;
//    }
//
//    @Override
//    public int size() {
//        return currentElementNumber;
//    }
//
//    @Override
//    public String toString() {
//        String resultString = "[";
//
//        for (int i = 0; i < currentElementNumber; i++) {
//            resultString += array[i].toString() + ((i == (currentElementNumber - 1)) ? "" : ", ");
//        }
//
//        return resultString + "]";
//    }
//
//    @Override
//    public E get(int index) {
//        if (index >= currentElementNumber || index < 0) {
//            throw new IndexOutOfBoundsException();
//        }
//
//        return array[index];
//    }
//
//    private void extendArray() {
//        array = Arrays.copyOf(array, array.length + DEFAULT_CAPACITY);
//    }
}
