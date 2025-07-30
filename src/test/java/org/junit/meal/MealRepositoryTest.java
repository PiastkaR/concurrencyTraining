package org.junit.meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class MealRepositoryTest {
    MealRepository mealRepository = new MealRepository();

    @BeforeEach
    void cleanUp(){
        mealRepository.getAllMeals().clear();
    }

    @Test
    void shouldAddMeal(){
        Meal meal = new Meal(12, "pizza");

        mealRepository.addMeal(meal);

        assertThat(mealRepository.getAllMeals().get(0), is(meal));
    }

    @Test
    void shouldRemoveMeal(){
        Meal meal = new Meal(12, "pizza");
        mealRepository.addMeal(meal);
        mealRepository.removeMeal(meal);

        assertThat(mealRepository.getAllMeals(), not(contains(meal)));
    }

    @Test
    void shouldBeAbeToFindByExactName(){
        Meal meal = new Meal(12, "pizza");
        Meal meal2 = new Meal(14, "pi");
        mealRepository.addMeal(meal);
        mealRepository.addMeal(meal2);

        List<Meal> result =  mealRepository.findByName("pizza", true);

        assertThat(result.size(), is(1));
    }

    @Test
    void shouldBeAbeToFindByStartingLetters(){
        Meal meal = new Meal(12, "pizza");
        Meal meal2 = new Meal(14, "pi");
        mealRepository.addMeal(meal);
        mealRepository.addMeal(meal2);

        List<Meal> result =  mealRepository.findByName("pizza", false);

        assertThat(result.size(), is(1));
    }

    @Test
    void shouldBeAbleToFindByPrice(){
        Meal meal = new Meal(12, "pizza");
        mealRepository.addMeal(meal);

        List<Meal> byPrice = mealRepository.findByPrice(12);

        assertThat(byPrice.size(), is(1));
    }


}
