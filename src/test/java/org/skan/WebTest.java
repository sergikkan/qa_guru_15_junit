package org.skan;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skan.data.Locale;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;

public class WebTest {

    @ValueSource(strings = {"Selenide","JUnit"})
    @ParameterizedTest(name = "Проверка числа результатов поиска в Яндексе для запроса {0}")
    void yandexSearchCommonTest(String testData){
        open("https://ya.ru");
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        $$("li.serp-item")
                .shouldHave(CollectionCondition.size(10))
                .first()
                .shouldHave(Condition.text(testData));
    }

    @CsvSource(value = {
            "IDE, IDE (англ. Integrated Drive Electronics) — параллельный интерфейс",
            "Junit, JUnit 5 is the next generation of JUnit. The goal is to create an up-to-date foundation for developer-side testing on the JVM"
    })
    @ParameterizedTest
    void googleSearchCommonTestDifferentExpectedText(String searchQuery, String exptctedText){
        open("https://www.google.com/");
        $("[name='q']").setValue(searchQuery).pressEnter();
        $$x("//div[@id='rso']/div")
                .shouldHave(CollectionCondition.size(10))
                .first()
                .shouldHave(Condition.text(exptctedText));

    }

    static Stream<Arguments> selenideSiteButtonsText(){

        return Stream.of(
                Arguments.of(Locale.RU,List.of("С чего начать?", "Док", "ЧАВО", "Блог", "Javadoc", "Пользователи", "Отзывы")),
                Arguments.of(Locale.EN,List.of("Quick start", "Docs", "FAQ", "Blog", "Javadoc", "Users", "Quotes"))
        );
    }
    @MethodSource
    @ParameterizedTest(name = "Проверка отображения названия кнопок для локали: {0} ")
    void selenideSiteButtonsText(Locale locale, List<String> buttonsText){
        open("https://selenide.org");
        $$("#languages a").find(Condition.text(locale.name())).click();
        $$(".main-menu-pages a").filter(Condition.visible).shouldHave(CollectionCondition.texts(buttonsText));

    }
}
