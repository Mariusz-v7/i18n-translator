package pl.mrugames.social.i18n.model;

public class InheritanceModel extends ModelToTranslate {
    private String value = "${i18n.two}";

    @Override
    public String getValue() {
        return value;
    }

    public String getSuperValue() {
        return super.getValue();
    }
}
