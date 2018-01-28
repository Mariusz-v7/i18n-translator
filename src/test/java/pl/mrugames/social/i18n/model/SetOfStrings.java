package pl.mrugames.social.i18n.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SetOfStrings {
    private final Set<String> strings = new HashSet<>(Arrays.asList("${i18n.one}", "${i18n.two}"));

    public Set<String> getStrings() {
        return strings;
    }
}
