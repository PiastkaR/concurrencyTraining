package org.junit.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {
    @InjectMocks
    private UnitService us = new UnitService();
    @Mock
    private UnitRepository unitRepository;
    @Mock
    private CargoRepository cargoRepository;

    @Test
    void addCargoByName() {
        //given
        Coordinates coordinates = new Coordinates(10, 20);
        Unit unit = new Unit(coordinates, 100, 100);
        Optional<Cargo> firstCargo = Optional.of(new Cargo("FirstCargo", 20));
        Optional<Cargo> secondCargo = Optional.of(new Cargo("SecondCargo", 50));
        //when
        when(cargoRepository.findCargoByName("FirstCargo")).thenReturn(firstCargo);
        when(cargoRepository.findCargoByName("SecondCargo")).thenReturn(secondCargo);

        us.addCargoByName(unit, "FirstCargo");
        us.addCargoByName(unit, "SecondCargo");
        //then
        assertEquals(unit.getLoad(), 70);
    }

    @Test
    void shouldGetUnitOnCoordinates() {
        //given
        Coordinates coordinates = new Coordinates(10, 20);
        Unit unit = new Unit(coordinates, 50, 80);
        //when
        when(unitRepository.getUnitByCoordinates(coordinates)).thenReturn(unit);
        Unit resultUnit = us.getUnitOn(coordinates);

        assertEquals(resultUnit.getCoordinates(),coordinates);
    }

    @Test
    void shouldThrowExceptionForAUnit() {
        //given
        Coordinates coordinates = new Coordinates(10, 20);
        //when
        when(unitRepository.getUnitByCoordinates(coordinates)).thenReturn(null);
        //then
        assertThrows(NoSuchElementException.class, () -> us.getUnitOn(coordinates));
    }

    @Test
    void shouldThrowExceptionForACargo() {
        //given
        Coordinates coordinates = new Coordinates(10, 20);
        Unit unit = new Unit(coordinates, 100, 100);
        //when
        when(cargoRepository.findCargoByName("FirstCargo")).thenReturn(Optional.empty());
        //then
        assertThrows(NoSuchElementException.class, () -> us.addCargoByName(unit, "FirstCargo"));
    }

}