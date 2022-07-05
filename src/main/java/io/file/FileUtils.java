package io.file;

import model.Contact;
import model.PhoneBook;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileUtils {
    private static final String FILE_NAME = "contacts.csv";

    public static void save(PhoneBook phoneBook) throws IOException {
        var writer = new BufferedWriter(new FileWriter(FILE_NAME));
        for (Contact contact : phoneBook) {
            writer.write(contact.toCSV());
            writer.newLine();
        }
        writer.close();
    }

    public static PhoneBook read() throws FileNotFoundException {
        PhoneBook book;
        var buffReader = new BufferedReader(new FileReader(FILE_NAME));
        Map<String, Contact> contacts = buffReader.lines()
                .map(line -> line.split(";"))
                .map(split -> new Contact(split[0], split[1]))
                .collect(Collectors.toMap(Contact::getName, Function.identity()));
        book = new PhoneBook(new TreeMap<>(contacts));
        return book;
    }
}