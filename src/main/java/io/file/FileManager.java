package io.file;

import model.PhoneBook;

public interface FileManager {
    PhoneBook read();
    void save(PhoneBook phoneBook);
}
