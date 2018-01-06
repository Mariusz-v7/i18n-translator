package pl.mrugames.social.i18n;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

@Service
public class I18nObjectTranslator {
    private final I18nReplacer replacer;

    I18nObjectTranslator(I18nReplacer replacer) {
        this.replacer = replacer;
    }

    public void translate(Object object) throws IllegalAccessException {

        for (Field field : object.getClass().getDeclaredFields()) {
            boolean accessible = field.isAccessible();

            try {
                if (!accessible) {
                    field.setAccessible(true);
                }

                Object nestedValue = field.get(object);

                if (field.getType().isAssignableFrom(String.class)) {
                    String str = (String) nestedValue;
                    String translated = replacer.replace(str);

                    if (!str.equals(translated) && Modifier.isFinal(field.getModifiers())) {
                        throw new IllegalArgumentException("Field: " + object.getClass().getSimpleName() + "#" + field.getName() + " is final. Cannot apply translation.");
                    }

                    if (!str.equals(translated)) {
                        field.set(object, translated);
                    }
                } else if (Translatable.class.isAssignableFrom(field.getType())) {
                    translate(nestedValue);
                } else if (nestedValue instanceof Collection) {

                    Collection<?> collection = (Collection<?>) nestedValue;
                    for (Object element : collection) {
                        if (element instanceof Translatable) {
                            translate(element);
                        }
                    }
                }
            } finally {
                if (!accessible) {
                    field.setAccessible(false);
                }
            }
        }
    }

    public String translateString(String str) {
        return replacer.replace(str);
    }
}