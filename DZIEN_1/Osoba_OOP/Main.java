public class Main {

    public static void main(String[] args) {

        Address address = new Address();
        address.setCity("Warszawa");
        address.setStreet("Marszałkowska");

        //Obiekt Person
        Person person = new Person();
        person.setName("Marcin");
        person.setAge(53);
        person.setAddress(address);

        System.out.println("Imię: "+ person.getName());
        System.out.println("Wiek: "+ person.getAge());
        System.out.println("Miasto: "+ person.getAddress().getCity());
        System.out.println("Ulica: "+ person.getAddress().getStreet());
    }
}
