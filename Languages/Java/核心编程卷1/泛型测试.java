
public class 泛型测试 {
    public static void main(String[] args) {
        String[] words = {"Mary", "had", "a", "little", "lamb"};
        Pair<String> mm = ArrayAlg.minmax(words);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());
    }
}

class ArrayAlg {
    public static <T extends  Comparable<T>> Pair<String> minmax(String[] a) {
        if (a == null || a.length == 0) return null;
        
        String min = a[0];
        String max = a[1];
        for (int i = 0; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) min = a[i];
            if (max.compareTo(a[i]) < 0) max = a[i];
        }

        return new Pair<>(min, max);
    }
}

class Pair<T> {
    private T min;
    private T max;

    public Pair(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public T getFirst() {
        return this.min;
    }

    public T getSecond() {
        return this.max;
    }
}
