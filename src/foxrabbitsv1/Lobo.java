package foxrabbitsv1;


import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Lobo extends Animal{
    private static final int BREEDING_AGE = 35;
    private static final int MAX_AGE = 300;
    private static final double BREEDING_PROBABILITY = 0.08;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int RABBIT_FOOD_VALUE = 20;
    private static final int FOX_FOOD_VALUE = 35;
    private static final Random rand = Randomizer.getRandom();

    private int age;
    private boolean alive;
    private Localizacao location;
    private Campo field;
    private int foodLevel;


    public Lobo(boolean randomAge, Campo field, Localizacao location) {
        super(field, location);
        alive = true;
        this.field = field;
        setLocation(location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
    }

    @Override
    public void act(List<Animal> newAnimal) {
        incrementAge();
        incrementHunger();
        if(alive) {
            giveBirth(newAnimal);
            if(location != null) {
                field.clear(location);
                location = null;
                field = null;
            }
        }
        // Move towards a source of food if found.
        Localizacao newLocation = findFood();
        if(newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = field.freeAdjacentLocation(location);
        }
        // See if it was possible to move.
        if(newLocation != null) {
            setLocation(newLocation);
        }
        else {
            // Overcrowding.
            setDead();
        }
    }

    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    private Localizacao findFood()
    {
        List<Localizacao> adjacent = field.adjacentLocations(location);
        Iterator<Localizacao> it = adjacent.iterator();
        while(it.hasNext()) {
            Localizacao where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Coelho) {
                Coelho rabbit = (Coelho) animal;
                if(rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = animal instanceof Coelho ? RABBIT_FOOD_VALUE : FOX_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    private void giveBirth(List<Animal> newRaposas)
    {
        List<Localizacao> free = field.getFreeAdjacentLocations(location);
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Localizacao loc = free.remove(0);
            Raposa young = new Raposa(false, field, loc);
            newRaposas.add(young);
        }
    }

    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
