import java.util.Objects;

public class Ex07_PersonOOP {
    // Cel: klasa, konstruktor, gettery, toString, equals/hashCode
    static class Person {
        private final String name;
        private final int age;
        public Person(String name, int age) {
            this.name = name; this.age = age;
        }
        public String getName() { return name; }
        public int getAge() { return age; }
        
        @Override public String toString() {
            return "Person{name='%s', age=%d}".formatted(name, age);
        }

        // CTRL  + / - komentowanie/odkomentowanie
        // CTRL + D  powielenie
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person p = (Person) o;
            return age == p.age && Objects.equals(name, p.name);
        }
        @Override public int hashCode() { return Objects.hash(name, age); }
    }
    public static void main(String[] args) {
        Person a = new Person("Anna", 30);
        Person b = new Person("Anna", 30);
        System.out.println(a);
        System.out.println("equals? " + a.equals(b));
        System.out.println("Wiek osoby: " + a.getAge());
    }
}
