package pl.javastart.task;

public class Main {

    public static void main(String[] args) {

        // tutaj możesz przeprowadzać manualne testy listy

        // lista powinna być parametryzowana (analogicznie jak java.util.ArrayList czy java.util.LinkedList)
        CustomList<String> customListOfStrings = new CustomList<>();
        CustomList<Integer> customListOfIntegers = new CustomList<>();

        for (int i = 0; i < 10; i++) {
            customListOfIntegers.add(i);
        }

        System.out.println(customListOfIntegers);
        customListOfIntegers.remove(0);
        //customListOfIntegers.add(-1, 55);
        System.out.println(customListOfIntegers);

        System.out.println(customListOfStrings);

        customListOfStrings.add("Wyraz1");
        customListOfStrings.add("Wyraz2");
        customListOfStrings.add("Wyraz3");
        customListOfStrings.add("Wyraz4");
        customListOfStrings.add("Wyraz5");

        System.out.println(customListOfStrings);
        customListOfStrings.add(0, "Wyraz6");
        System.out.println(customListOfStrings);
        customListOfStrings.remove(0);
        System.out.println(customListOfStrings);
        customListOfStrings.remove(3);
        System.out.println(customListOfStrings);

    }
}
