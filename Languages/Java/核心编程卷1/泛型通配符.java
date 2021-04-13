public class 泛型通配符 {
    public static void main(String[] args) {
        Manager ceo = new Manager("Gus Greedy", 8000);
        Manager cfo = new Manager("Sid Sneaky", 60000);
        Pair<Manager> buddies = new Pair<>(ceo, cfo);
        printBuddies(buddies);

        ceo.setBonus(1000000);
        cfo.setBonus(500000);

        Manager[] managers = {ceo, cfo};

        Pair<Employee> result = new Pair<>();
        minmaxBonus(managers, result);

        System.out.println("first: " + result.getFirst().getName() + ", second: " + result.getSecond().getName());
    }

    public static void printBuddies(Pair<? extends Employee> p) {
        Employee first = p.getFirst();
        Employee second = p.getSecond();
        System.out.println(first.getName() + " and " + second.getName() + " are buddies.");
    }

    public static void minmaxBonus(Manager[] a, Pair<? super Manager> result) {
        if (a.length == 0) return;
        Manager min = a[0];
        Manager max = a[1];
        for (int i = 1; i < a.length; i++) {
            if (min.getBonus() > a[i].getBonus()) min = a[i];
            if (max.getBonus() < a[i].getBonus()) max = a[i];
        }

        result.setFirst(min);
        result.setSecond(max);
    }

    public static void maxminBonus(Manager[] a, Pair<? super Manager> result) {
        minmaxBonus(a, result);
        PairAlg.swapHelper(result);
    }
}

class PairAlg {
    public static boolean hasNulls(Pair<?> p) {
        return p.getFirst() == null || p.getSecond() == null;
    }

    public static void swap(Pair<?> p) { swapHelper(p);}

    public static <T> void swapHelper(Pair<T> p) {
        T t = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(t);
    }
}

class Employee {
    private String Name;
    private double Bonus;

    public Employee(String name, double bonus) {
        this.Name = name;
        this.Bonus = bonus;
    }

    public void info() {
        System.out.println("Name: " + this.Name + ", Bonus: " + this.Bonus);
    }

    public String getName() {
        return this.Name;
    }

    public double getBonus() {
        return this.Bonus;
    }

    public void setBonus(double bonus) {
        this.Bonus = bonus;
    }
}

class Manager extends Employee {

    public Manager(String name, double bonus) {
        super(name, bonus);
    }
}

