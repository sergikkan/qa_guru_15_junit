package org.skan;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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
            "Selenide, Selenide - это фреймворк для автоматизированного тестирования веб-приложений на основе Selenium WebDriver",
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
}
