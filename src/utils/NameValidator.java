package utils;

import java.util.Arrays;

import static models.util.isBlank;

public class NameValidator {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 50;

    private static final String[] RESERVED_WORDS = {"admin", "utilisateur"};

    public static boolean isValidName(String name) {
        if (name == null || isBlank(name)) {
            return false;
        }

        String normalizedName = normalizeName(name);

        return isValidNameLength(normalizedName) &&
                isValidNameCharacters(normalizedName) &&
                !isReservedWord(normalizedName);
    }

    private static String normalizeName(String name) {
        return name.trim().replaceAll("\\s+", " ");
    }

    private static boolean isValidNameLength(String name) {
        return name.length() >= MIN_LENGTH && name.length() <= MAX_LENGTH;
    }

    private static boolean isValidNameCharacters(String name) {
        String validCharactersPattern = "^[a-zA-Z\\s\\-']+$";

        return name.matches(validCharactersPattern);
    }

    private static boolean isReservedWord(String name) {
        return Arrays.asList(RESERVED_WORDS).contains(name.toLowerCase());
    }
}
