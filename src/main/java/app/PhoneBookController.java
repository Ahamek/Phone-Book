package app;

import animation.Loader;
import io.Options;
import io.file.FileUtils;
import model.Contact;
import model.PhoneBook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PhoneBookController {
    private PhoneBook phoneBook = null;
    private final Scanner input = new Scanner(System.in);


    public PhoneBookController() {
        new Loader();
        try {
            phoneBook = FileUtils.read();
        } catch (FileNotFoundException e) {
            System.err.println("File does not exist. Now has been created!");
        }
    }

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
        System.out.println("Enter the name of the record to be deleted:");
        String name = input.nextLine();
        boolean removed = phoneBook.remove(name);
        if(removed) {
            System.out.println("Record deleted.");
        } else {
            System.out.println("There is no record with such a name");
        }
    }

    private void searchByTelephone() {
        System.out.println("Provide a phone number or part of it:");
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
        System.out.println("Provide a phone number or part of it:");
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
        System.out.println("Enter the contact name:");
        String name = input.nextLine();
        System.out.println("Enter the phone-number:");
        String telephone = input.nextLine();
        try {
            boolean add = phoneBook.add(name, telephone);
            if (add) {
                System.out.println("Record added.");
            } else {
                System.out.println("Record cannot be added. An entry with this name already exists.");
            }
        } catch(IllegalArgumentException e) {
            System.err.println("The name and phone number cannot be blank.");
        }
    }

    private void close() {
        input.close();
        try {
            FileUtils.save(phoneBook);
            System.out.println("Zapisano zmiany.");
        } catch (IOException e) {
            System.err.println("Nie uda³o siê zapisaæ zmian");
        }
        System.out.println("Bye bye.");
    }
}