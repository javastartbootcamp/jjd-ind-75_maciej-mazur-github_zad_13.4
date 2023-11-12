package pl.javastart.task;

public interface CustomListInterface<E> {
    boolean add(E element);

    void add(int index, E element);

    E remove(int index);

    int size();

    E get(int index);
}
