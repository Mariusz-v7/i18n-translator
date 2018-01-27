package pl.mrugames.social.i18n;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.mrugames.social.i18n.model.InheritanceModel;
import pl.mrugames.social.i18n.model.ModelToTranslate;
import pl.mrugames.social.i18n.model.NestedModelToTranslate;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {
        TestConfiguration.class
})
public class I18nSpec {
    @Autowired
    private I18nObjectTranslator translator;

    @Test
    public void simpleStringTranslation() throws IllegalAccessException {
        String result = translator.translateString("${i18n.simple_string}");

        assertThat(result).isEqualTo("Prosty ciąg znaków");
    }

    @Test
    public void givenRouterReturnsSimpleModel_thenReplaceString() throws IllegalAccessException {
        ModelToTranslate modelToTranslate = new ModelToTranslate();
        translator.translate(modelToTranslate);

        assertThat(modelToTranslate.getValue()).isEqualTo("raz");
    }

    @Test
    public void translateNestedObjectsSpec() throws IllegalAccessException {
        ModelToTranslate modelToTranslate = new ModelToTranslate();
        translator.translate(modelToTranslate);

        assertThat(modelToTranslate.getNestedModelToTranslate().getValue()).isEqualTo("dwa");
    }

    @Test
    public void translateCollections() throws IllegalAccessException {
        ModelToTranslate modelToTranslate = new ModelToTranslate();
        translator.translate(modelToTranslate);

        assertThat(modelToTranslate.getList().stream().map(NestedModelToTranslate::getValue).collect(Collectors.toList())).containsExactly("dwa", "dwa");
    }

    @Test
    public void shouldTranslateFieldInInheritedClass() throws IllegalAccessException {
        InheritanceModel inheritanceModel = new InheritanceModel();
        translator.translate(inheritanceModel);

        assertThat(inheritanceModel.getValue()).isEqualTo("dwa");
    }

    @Test
    public void shouldTranslateSuperValue() throws IllegalAccessException {
        InheritanceModel inheritanceModel = new InheritanceModel();
        translator.translate(inheritanceModel);

        assertThat(inheritanceModel.getSuperValue()).isEqualTo("raz");
    }

    @Test
    public void shouldTranslateFieldsDeclaredAsObjects() throws IllegalAccessException {
        ModelToTranslate modelToTranslate = new ModelToTranslate();
        translator.translate(modelToTranslate);
        NestedModelToTranslate nested = (NestedModelToTranslate) modelToTranslate.getNestedModelToTranslateAsObject();
        assertThat(nested.getValue()).isEqualTo("dwa");
    }

}
