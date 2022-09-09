package entities;

import java.util.List;

public interface IndexedDisplayer {
    static <A> void displayIndexed(List<A> objects) {
        if (objects.isEmpty()) {
            return;
        }
        String className = objects.get(0).getClass().getName();
        int[] index = {0};
        objects.forEach(obj -> System.out.printf("%d. %s\n", index[0]++, obj.toString()));
    }
}
