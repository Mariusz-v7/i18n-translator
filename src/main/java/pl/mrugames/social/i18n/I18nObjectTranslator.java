package pl.mrugames.social.i18n;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class I18nObjectTranslator {
    private final I18nReplacer replacer;

    I18nObjectTranslator(I18nReplacer replacer) {
        this.replacer = replacer;
    }

    public void translate(Object object) throws IllegalAccessException {

        for (Field field : getAllFields(object.getClass(), new LinkedList<>())) {
            boolean accessible = field.isAccessible();

            try {
                if (!accessible) {
                    field.setAccessible(true);
                }

                Object nestedValue = field.get(object);
                if (nestedValue == null) {
                    continue;
                }

                if (nestedValue instanceof String) {
                    String str = (String) nestedValue;
                    String translated = replacer.replace(str);

                    if (!str.equals(translated) && Modifier.isFinal(field.getModifiers())) {
                        throw new IllegalArgumentException("Field: " + object.getClass().getSimpleName() + "#" + field.getName() + " is final. Cannot apply translation.");
                    }

                    if (!str.equals(translated)) {
                        field.set(object, translated);
                    }
                } else if (nestedValue instanceof Translatable) {
                    translate(nestedValue);
                } else if (nestedValue instanceof Collection) {

                    Collection<?> collection = (Collection<?>) nestedValue;
                    Object[] tmp = collection.toArray();

                    for (int i = 0; i < tmp.length; ++i) {
                        Object element = tmp[i];

                        if (element instanceof Translatable) {
                            translate(element);
                        }

                        if (element instanceof String) {
                            element = translateString((String) element);
                        }

                        tmp[i] = element;
                    }

                    Collection newCollection = createNewCollection(field, collection, tmp);
                    field.set(object, newCollection);
                }
            } finally {
                if (!accessible) {
                    field.setAccessible(false);
                }
            }
        }
    }

    private Collection createNewCollection(Field field, Collection oldOne, Object[] array) {
        return Arrays.asList(array); // TODO: recognize different collections and return proper one
    }

    public String translateString(String str) {
        return replacer.replace(str);
    }

    private List<Field> getAllFields(Class<?> clazz, List<Field> list) {
        list.addAll(Arrays.asList(clazz.getDeclaredFields()));

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            list = getAllFields(superclass, list);
        }

        return list;
    }
}
