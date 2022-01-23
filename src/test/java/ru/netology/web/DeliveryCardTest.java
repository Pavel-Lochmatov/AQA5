package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class DeliveryCardTest {

    public String generateDate (int count){
    String date;
    LocalDate localDate = LocalDate.now().plusDays(count);
    date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(localDate);
    return date;
}
    @BeforeEach
    void openUrl() {
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @Test
    void shouldTestSuccessOrderIfPlusThreeDays() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Самара");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE,date));
        $("[data-test-id='name'] .input__control").setValue("Анна Иванова-Петрова");
        $("[data-test-id='phone'] .input__control").setValue("+79877770011");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Встреча успешно забронирована")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована")).shouldBe(Condition.text(date));
    }

    @Test
    void shouldTestFailOrderIfPlusTwoDays() {
        String date = generateDate(2);
        $("[data-test-id='city'] .input__control").setValue("Самара");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE,date));
        $("[data-test-id='name'] .input__control").setValue("Павел");
        $("[data-test-id='phone'] .input__control").setValue("+79877770011");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailOrderIfNoName() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Самара");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE,date));
        $("[data-test-id='phone'] .input__control").setValue("+79877770011");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailOrderIfNoCity() {
        String date = generateDate(3);
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE,date));
        $("[data-test-id='name'] .input__control").setValue("Павел");
        $("[data-test-id='phone'] .input__control").setValue("+79877770011");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailOrderIfNoNumber() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Самара");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Павел");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailOrderIfNoDate() {
        $("[data-test-id='city'] .input__control").setValue("Самара");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE));
        $("[data-test-id='name'] .input__control").setValue("Павел");
        $("[data-test-id='phone'] .input__control").setValue("+79877770011");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Неверно введена дата")).shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailOrderIfCityIncorrect() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Питер");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE,date));
        $("[data-test-id='name'] .input__control").setValue("Павел");
        $("[data-test-id='phone'] .input__control").setValue("+79877770011");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailOrderIfNameIncorrect() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Самара");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE,date));
        $("[data-test-id='name'] .input__control").setValue("Павел7");
        $("[data-test-id='phone'] .input__control").setValue("+79877770011");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("только русские буквы, пробелы и дефисы")).shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailOrderIfPhoneIncorrect() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Самара");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE,date));
        $("[data-test-id='name'] .input__control").setValue("Павел");
        $("[data-test-id='phone'] .input__control").setValue("+7987777001");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Телефон указан неверно")).shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailOrderIfNoClickCheckbox() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Самара");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE,date));
        $("[data-test-id='name'] .input__control").setValue("Павел");
        $("[data-test-id='phone'] .input__control").setValue("+79877770011");
        $$("button").find(Condition.exactText("Забронировать")).click();
        String text = $(".checkbox__text").getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", text);
    }
}
