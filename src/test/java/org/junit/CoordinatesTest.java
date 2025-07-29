package org.junit;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoordinatesTest {
    @Test
    void shouldCreateCorrectCoordinates() {
        //given
        Coordinates coordinates = new Coordinates(15, 35);
        //when
        //then
        assertThat(coordinates.getX(), is(lessThanOrEqualTo(100)));
        assertThat(coordinates.getX(), is(not(lessThan(0))));
        assertThat(coordinates.getY(), is(lessThanOrEqualTo(100)));
        assertThat(coordinates.getY(), is(not(lessThan(0))));
    }

    @Test
    void shouldThrowIAException() {
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(150, 35));
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(10, -35));
    }

    @Test
    void shouldCheckCopyMethodWithHappyPath() {
        //given
        Coordinates coordinates = new Coordinates(15, 35);
        //when
        Coordinates result = Coordinates.copy(coordinates, 3, 7);
        //then
        assertEquals(18, result.getX());
        assertEquals(42, result.getY());
    }


}
