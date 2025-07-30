package org.junit.order;

import org.hamcrest.MatcherAssert;
import org.junit.meal.Meal;
import org.junit.extensions.BeforeAfterExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsEmptyCollection.emptyCollectionOf;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(BeforeAfterExtension.class)
class OrderTest {
    private Order order;

    @BeforeEach
    void initializeOrder() {
        System.out.println("Before each");
        order = new Order();
    }

    @AfterEach
    void cleanUp() {
        System.out.println("After each");
        order.cancel();
    }

    @Test
    void testAssertArrayEquals() {
        //given
        int[] inst1 = {1, 2, 3};
        int[] inst2 = {1, 2, 3};
        //then
        assertArrayEquals(inst1, inst2);
    }

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {
        //given
        //when
        //then
        assertThat(order.getMeals(), empty());
        assertThat(order.getMeals().size(), equalTo(0));
        assertThat(order.getMeals(), hasSize(0));
        MatcherAssert.assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        //given
        Meal meal = new Meal(15, "Burger");
        //when
        order.addMealToOrdedr(meal);
        //then
        assertThat(order.getMeals(), hasSize(1));
        assertThat(order.getMeals(), contains(meal));
        assertThat(order.getMeals(), hasItem(meal));
        assertThat(order.getMeals().get(0).getPrice(), equalTo(15));
    }

    @Test
    void removingMealToOrderShouldIncreaseOrderSize() {
        //given
        Meal meal = new Meal(15, "Burger");
        //when
        order.addMealToOrdedr(meal);
        order.removeMealToOrdedr(meal);
        //then
        assertThat(order.getMeals(), hasSize(0));
        assertThat(order.getMeals(), not(contains(meal)));
    }

    @Test
    void mealsShouldBeInCorrectOrder() {
        //given
        Meal meal = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");
        //when
        order.addMealToOrdedr(meal);
        order.addMealToOrdedr(meal2);
        //then
        assertThat(order.getMeals(), contains(meal, meal2));
        assertThat(order.getMeals(), containsInAnyOrder(meal2, meal));
    }

    @Test
    void checkIfCollectionsAreEqual() {
        //given
        Meal meal = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");
        //when
        List<Meal> meals = Arrays.asList(meal, meal2);
        List<Meal> meals2 = Arrays.asList(meal, meal2);
        //then
        assertThat(meals, is(meals2));
    }

    @Test
    void checkIfCollectionsAreNotEqual() {
        //given
        Meal meal = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");
        //when
        List<Meal> meals = Arrays.asList(meal, meal2);
        List<Meal> meals2 = Arrays.asList(meal);
        //then
        assertThat(meals, not(is(meals2)));
    }

    @Test
    void orderTotalPriceShouldNotExceedMax() {
        //given
        Meal meal = new Meal(Integer.MAX_VALUE, "Burger");
        Meal meal2 = new Meal(Integer.MAX_VALUE, "Sandwich");
        //when
        order.addMealToOrdedr(meal);
        order.addMealToOrdedr(meal2);
        //then
        assertThrows(IllegalArgumentException.class, () -> order.totalPrice());
    }

    @Test
    void emptyOrderTotalPriceShouldEqualToZero() {
        //order is created in beforeEach
        assertThat(order.totalPrice(), is(0));
    }

    @Test
    void cancelingOrderShouldRemoveAllItemsFromMealList() {
        //given
        Meal meal = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");
        //when
        order.addMealToOrdedr(meal);
        order.addMealToOrdedr(meal2);
        order.cancel();
        //then
        assertThat(order.getMeals().size(), is(0));
    }

}