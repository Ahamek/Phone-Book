package app;

import io.Options;
import model.Contact;
import model.PhoneBook;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PhoneBookController {
    private final PhoneBook phoneBook = new PhoneBook();
    private final Scanner input = new Scanner(System.in);

    public void mainLoop() {
        Options option = null;
        do {
            showOptions();
            try {
                option = chooseOption();
                executeOption(option);
            } catch (NoSuchElementException e) {
                System.out.println("Incorrect option " + e.getMessage());
            }
        } while(option != Options.CLOSE);
    }

    private void showOptions() {
        System.out.println(">>> Options:");
        for (Options value : Options.values()) {
            System.out.println(value);
        }
    }

    private Options chooseOption() {
        int option = input.nextInt();
        input.nextLine();
        return Options.convertToOption(option);
    }

    private void executeOption(Options options) {
        switch (options) {
            case ADD_CONTACT -> addContact();
            case SEARCH_BY_NAME -> searchByName();
            case SEARCH_BY_TEL -> searchByTelephone();
            case DELETE -> delete();
            case CLOSE -> close();
        }
    }

    private void delete() {
        System.out.println("Podaj nazwê rekordu do usuniêcia:");
        String name = input.nextLine();
        boolean removed = phoneBook.remove(name);
        if(removed) {
            System.out.println("Rekord usuniêty");
        } else {
            System.out.println("Nie ma rekordu o takiej nazwie");
        }
    }

    private void searchByTelephone() {
        System.out.println("Provide a phone number or part of it: ");
        String telFragment = input.nextLine();
        List<Contact> contacts = phoneBook.findByTelephone(telFragment);
        if (contacts.isEmpty()) {
            System.out.println("No results.");
        } else {
            System.out.println("Found records:");
            contacts.forEach(System.out::println);
        }
    }

    private void searchByName() {
        System.out.println("Provide a phone number or part of it: ");
        String nameFragment = input.nextLine();
        List<Contact> contacts = phoneBook.findByName(nameFragment);
        if(contacts.isEmpty()) {
            System.out.println("No results.");
        } else {
            System.out.println("Found records:");
            contacts.forEach(System.out::println);
        }
    }

    private void addContact() {
        System.out.println("Podaj nazwê kontaktu:");
        String name = input.nextLine();
        System.out.println("Podaj nr telefonu:");
        String telephone = input.nextLine();
        try {
            boolean add = phoneBook.add(name, telephone);
            if (add) {
                System.out.println("Rekord dodany.");
            } else {
                System.out.println("Nie mo¿na dodaæ rekordu. Wpis o takiej nazwie ju¿ istnieje.");
            }
        } catch(IllegalArgumentException e) {
            System.err.println("Nazwa ani numer telefonu nie mog¹ byæ puste");
        }
    }

    private void close() {
        input.close();
        System.out.println("Bye bye.");
    }
}