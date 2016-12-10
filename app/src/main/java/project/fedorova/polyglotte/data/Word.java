package project.fedorova.polyglotte.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Word implements Serializable{
    private UUID ID;
    private Set<String> themes = null;
    private String word;
    private Set<String> translations = null;

    public Word(UUID id, String newWord, Set<String> newTranslations, Set<String> newThemes) {
        word = newWord;
        ID = id;
        if (newTranslations == null) {
            translations = new HashSet<String>();
        } else {
            translations = newTranslations;
        }
        if (newThemes == null) {
            themes = new HashSet<String>();
        } else {
            themes = newThemes;
        }
    }

    public void changeWord(String newWord) {
        word = newWord;
    }

    public void changeThemes(Set<String> newThemes) {
        themes.clear();
        themes = newThemes;
    }

    public void changeTranslations(Set<String> newTranslations) {
        translations.clear();
        translations = newTranslations;
    }

    public boolean hasTheme(String theme) {
        return themes.contains(theme);
    }

    public boolean checkTranslation(String translation) {
        return translations.contains(translation);
    }

    public static Word createCurrentWord() {
        return new Word(UUID.randomUUID(), "", null, null);
    }

    public Set<String> getTranslations() {
        return translations;
    }

    public Set<String> getThemes() {
        return themes;
    }

    public String getWord() {
        return word;
    }

    public UUID getID() {
        return ID;
    }
}