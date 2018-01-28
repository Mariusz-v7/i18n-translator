package pl.mrugames.social.i18n.model;

import java.util.Arrays;
import java.util.List;

public class ListOfStrings {
    private final List<String> strings = Arrays.asList("${i18n.one}", "${i18n.two}");

    public List<String> getStrings() {
        return strings;
    }
}
