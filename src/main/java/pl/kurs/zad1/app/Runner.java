package pl.kurs.zad1.app;

import pl.kurs.zad1.models.Person;
import pl.kurs.zad1.service.ObjectContainer;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner {
    public static void main(String[] args) throws IOException {


        ObjectContainer<Person> peopleFromWarsaw = new ObjectContainer<>(p -> p.getCity().equals("Warsaw"));
        peopleFromWarsaw.add(new Person("Jan", "Warsaw", 30));
        peopleFromWarsaw.add(new Person("Weronika", "Warsaw", 20));
        peopleFromWarsaw.add(new Person("Waldek", "Monaco", 34));


        List<Person> females = peopleFromWarsaw.getWithFilter(p -> p.getName().endsWith("a"));
        System.out.println("Lista Females : " + females);

        peopleFromWarsaw.removeIf(p -> p.getAge() > 50);
        System.out.println("People from Warsaw : " + peopleFromWarsaw);


        peopleFromWarsaw.storeToFile("youngPeopleFromWarsaw.txt", p -> p.getAge() < 30, p -> p.getName() + ";" + p.getAge() + ";" + p.getCity());
        peopleFromWarsaw.storeToFile("warsawPeople.txt");
        ObjectContainer<Person> peopleFromWarsawFromFile = ObjectContainer.fromFile(
                "warsawPeople.txt",
                line -> {
                    Pattern pattern = Pattern.compile("Person\\{name='(.*?)', city='(.*?)', age=(\\d+)}");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String name = matcher.group(1);
                        String city = matcher.group(2);
                        int age = Integer.parseInt(matcher.group(3));
                        return new Person(name, city, age);
                    } else {
                        throw new IllegalArgumentException("Malformed line: " + line);
                    }
                });

    }
}
