package org.skan;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skan.data.Genre;
import org.skan.data.Locale;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class KinopoiskTest {
    @ValueSource(strings = {"Форрест Гамп","Зеленая миля", "Мстители"})
    @ParameterizedTest(name = "Поиск информации о фильме с названием {0}")
    void yandexSearchCommonTest(String testData){
        open("https://www.kinopoisk.ru/");
        $("input[name='kp_query']").setValue(testData).pressEnter();
        $("div[class='element most_wanted']").shouldHave(Condition.text(testData));
    }

    @CsvSource(value = {
            "Форрест Гамп, Полувековая история США глазами чудака из Алабамы",
            "Зеленая миля, Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора»",
            "Мстители, Команда супергероев дает отпор скандинавскому богу Локи"
            }
    )
    @ParameterizedTest
    void movieDescriptionText(String theme, String expetctedText){
        open("https://www.kinopoisk.ru/");
        $("input[name='kp_query']").setValue(theme).pressEnter();
        $("div[class='element most_wanted']").shouldHave(Condition.text(theme));
        $("p.name a").click();
        $("body p").shouldHave(Condition.text(expetctedText));


    }

    static Stream<Arguments> selenideSiteButtonsText(){

        return Stream.of(
                Arguments.of(Genre.Фильмы, List.of("250 лучших фильмов", "Лучшие фильмы 2021 года: выбор редакции",
                        "500 лучших фильмов", "Популярные фильмы", "Цифровые релизы", "Фильмы про вампиров","Фильмы про любовь",
                        "Фильмы про зомби","Фильмы про космос","Фильмы про подростков","Фильмы-катастрофы","Фильмы про акул","Фильмы про школу",
                        "Фильмы про танцы","Фильмы и сериалы про программистов","Семейные комедии","Мультфильмы для самых маленьких",
                        "Комедийные боевики","Романтические комедии","Лучшие фильмы для детей","Лучшие фильмы, основанные на комиксах",
                        "Лучшие фильмы про конец света","Лучшие фильмы о Великой Отечественной войне","Лучшие отечественные новогодние фильмы",
                        "Лучшие иностранные новогодние фильмы","Лучшие новогодние мультфильмы","Золотая коллекция «Союзмультфильма»")),
                Arguments.of(Genre.Сериалы,List.of("250 лучших сериалов", "Популярные сериалы", "Лучшие сериалы 2021 года: выбор редакции",
                        "Все сериалы онлайн", "Цифровые релизы", "Российские сериалы", "Лучшие сериалы мини-формата","Лучшие азиатские сериалы"))
        );
    }
    @MethodSource
    @ParameterizedTest(name = "Проверка отображения названия подборок для категории: {0} ")
    void selenideSiteButtonsText(Genre genre, List<String> topics){
        open("https://www.kinopoisk.ru/");
        $$("a").filter(Condition.visible).find(Condition.text(genre.name())).click();
        $$(".styles_name__G_1mq").shouldHave(CollectionCondition.texts(topics));


    }
}
