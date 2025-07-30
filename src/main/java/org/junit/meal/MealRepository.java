package org.junit.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealRepository {

    private List<Meal> meals = new ArrayList<>();

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public List<Meal> getAllMeals() {
        return meals;

    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
    }

    public List<Meal> findByName(String mealName, boolean exactMatch) {
        List<Meal> result = new ArrayList<>();
        if (exactMatch) {
            result = meals.stream().filter(
                    meal -> meal.getName().equals(mealName)
            ).toList();
        } else {
            result = meals.stream().filter(meal -> meal.getName().startsWith(mealName))
                    .collect(Collectors.toList());
        }

        return result;
    }

    public List<Meal> findByPrice(int i) {
        return meals.stream().filter(meal -> meal.getPrice() == i)
                .toList();
    }
}
