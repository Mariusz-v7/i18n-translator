package pl.mrugames.social.i18n.model;

import pl.mrugames.social.i18n.Translatable;

import java.util.Arrays;
import java.util.List;

public class ModelToTranslate implements Translatable {
    private String value = "${i18n.one}";
    private double justTest = 123; // only strings should be replaced
    private final String regularString = "regularString"; // fields without placeholders (${...}) may be final
    private final NestedModelToTranslate nestedModelToTranslate = new NestedModelToTranslate();
    private final List<NestedModelToTranslate> list = Arrays.asList(new NestedModelToTranslate(), new NestedModelToTranslate());
    private final Object nullObj = null;
    private final Object nestedModelToTranslateAsObject = new NestedModelToTranslate();


    public String getValue() {
        return value;
    }

    public NestedModelToTranslate getNestedModelToTranslate() {
        return nestedModelToTranslate;
    }

    public Object getNestedModelToTranslateAsObject() {
        return nestedModelToTranslateAsObject;
    }

    public List<NestedModelToTranslate> getList() {
        return list;
    }
}
