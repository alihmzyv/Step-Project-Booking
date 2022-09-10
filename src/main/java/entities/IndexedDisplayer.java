package entities;

import java.util.List;

public interface IndexedDisplayer {
    static <A> void displayIndexed(List<A> objects) {
        if (objects.isEmpty()) {
            System.out.println("There is nothing to display.");
            return;
        }
        int[] index = {1};
        objects.forEach(obj -> System.out.printf("%d. %s\n", index[0]++, obj.toString()));
    }
}
