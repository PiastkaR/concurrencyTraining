package org.junit.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

public class Unit {
    private static Random random = new Random();

    public Coordinates getCoordinates() {
        return coordinates;
    }

    private Coordinates coordinates;
    private int fuel;
    private int maxFuel;
    private List<Cargo> cargo;
    private int maxCargoWeight;
    private int currentCargoWeight;

    Unit(Coordinates startCoordinates, int maxFuel, int maxCargoWeight) {

        this.coordinates = startCoordinates;
        this.maxFuel = maxFuel;
        this.fuel = maxFuel;
        this.maxCargoWeight = maxCargoWeight;
        this.currentCargoWeight = 0;
        this.cargo = new ArrayList<>();

    }

    Coordinates move(int x, int y) {

        if (this.fuel - (x + y) < 0) {
            throw new IllegalStateException("Unit cannot move that far");
        }

        Coordinates coordinatesAfterMove = Coordinates.copy(this.coordinates, x, y);
        this.coordinates = coordinatesAfterMove;

        this.fuel = this.fuel - (x + y);

        return coordinatesAfterMove;
    }

    void tankUp() {

        int restPoints = random.nextInt(10);

        if (restPoints + this.fuel >= maxFuel) {
            this.fuel = maxFuel;
        } else {
            this.fuel += restPoints;
        }

    }

    void loadCargo(Cargo cargo) {

        if (currentCargoWeight + cargo.getWeight() > maxCargoWeight) {
            throw new IllegalStateException("Can not load any more cargo");
        }

        this.cargo.add(cargo);

        this.currentCargoWeight = calculateCargoWeight();

    }

    void unloadCargo(Cargo cargo) {
        this.cargo.remove(cargo);
        this.currentCargoWeight = calculateCargoWeight();
    }

    void unloadAllCargo() {
        cargo.clear();
        this.currentCargoWeight = 0;
    }


    private int calculateCargoWeight() {
        return cargo.stream().mapToInt(Cargo::getWeight).sum();
    }

    int getFuel() {
        return this.fuel;
    }

    int getLoad() {
        return this.currentCargoWeight;
    }

    public class UnitService {

        private CargoRepository cargoRepository = new CargoRepository();
        private UnitRepository unitRepository = new UnitRepository();

        void addCargoByName(Unit unit, String name) {

            Optional<Cargo> cargo = cargoRepository.findCargoByName(name);

            if (cargo.isPresent()) {
                unit.loadCargo(cargo.get());
            } else {
                throw new NoSuchElementException("Unable to find cargo");
            }
        }

        Unit getUnitOn(Coordinates coordinates) {

            Unit u = unitRepository.getUnitByCoordinates(coordinates);

            if (u == null) {
                throw new NoSuchElementException("Unable to find any unit");
            } else {
                return u;
            }
        }
    }
}
