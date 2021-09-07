package top.noahlin.astera.service;

public interface SensitiveFilterService {
    String filter(String text);

    boolean isSymbol(char c);
}
