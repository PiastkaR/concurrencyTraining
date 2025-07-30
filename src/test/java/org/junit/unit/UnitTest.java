package org.junit.unit;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {

    @Test
    void shouldUnloadCargo() {
        Cargo cargo = new Cargo("Walizki", 25);
        Coordinates coordinates = new Coordinates(15, 35);
        Unit unit = new Unit(coordinates, 60, 50);
        unit.loadCargo(cargo);
        unit.unloadCargo(cargo);

        assertEquals(unit.getLoad(), 0);
    }

    @Test
    void shouldUnloadAllCargo() {
        Cargo cargo = new Cargo("Walizki", 25);
        Cargo cargo2 = new Cargo("Wstążki", 25);
        Coordinates coordinates = new Coordinates(15, 35);
        Unit unit = new Unit(coordinates, 60, 50);
        unit.loadCargo(cargo);
        unit.loadCargo(cargo2);
        unit.unloadAllCargo();

        assertEquals(unit.getLoad(), 0);
    }

    @Test
    void shouldLoadCargo() {
        Cargo cargo = new Cargo("Walizki", 25);
        Coordinates coordinates = new Coordinates(15, 35);
        Unit unit = new Unit(coordinates, 60, 50);
        unit.loadCargo(cargo);

        assertEquals(unit.getLoad(), 25);
    }

    @Test
    void shouldBeAbleToMoveAfterTankingUp() {
        //given
        Cargo cargo = new Cargo("Walizki", 25);
        Coordinates coordinates = new Coordinates(15, 35);
        Unit unit = new Unit(coordinates, 60, 50);
        unit.loadCargo(cargo);
        //when
        unit.move(15, 15);

        assertThat(unit.getFuel(), is(lessThanOrEqualTo(30)));
        //and
        unit.tankUp();
        assertThat(unit.getFuel(), is(greaterThanOrEqualTo(31)));

    }

}
