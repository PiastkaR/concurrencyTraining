package org.junit.order;

import org.junit.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<Meal> meals = new ArrayList<>();
private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void addMealToOrdedr(Meal meal) {
        this.meals.add(meal);
    }

    public void removeMealToOrdedr(Meal meal) {
        this.meals.remove(meal);
    }

    void cancel() {
        this.meals.clear();
    }

    public List<Meal> getMeals() {
        return meals;
    }


    int totalPrice() {
        int sum = this.meals.stream()
                .mapToInt(meal -> meal.getPrice())
                .sum();
        if (sum < 0) {
            throw new IllegalArgumentException("Price limit excepition exceeded");
        } else {
            return sum;
        }
    }

    public void changeOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }
    @Override
    public String toString() {
        return "Order{" +
                "meals=" + meals +
                '}';
    }
}
