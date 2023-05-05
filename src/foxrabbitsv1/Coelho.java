package foxrabbitsv1;
import java.util.List;
import java.util.Random;

public class Coelho extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The rabbit's age.
    private int age;
    // Whether the rabbit is alive or not.
    private boolean alive;
    // The rabbit's position.
    private Localizacao location;
    // The field occupied.
    private Campo field;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Coelho(boolean randomAge, Campo field, Localizacao location)
    {
        super(field, location);
        age = 0;
        alive = true;
        this.field = field;
        setLocation(location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void run(List<Animal> newRabbits)
    {
        incrementAge();
        if(alive) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Localizacao newLocation = field.freeAdjacentLocation(location);
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    /**
     * Check whether the rabbit is alive or not.
     * @return true if the rabbit is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }
    
    /** incrementAge();
        incrementHunger();
        if(alive) {
            alive = false;
            if(location != null) {
                field.clear(location);
                location = null;
                field = null;
            }
        }
        giveBirth(newAnimal);
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
     * Indicate that the rabbit is no longer alive.
     * It is removed from the field.
     */
    public void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    @Override
    public void act(List<Animal> newAnimal) {
        incrementAge();
        if(alive) {
            alive = false;
            if(location != null) {
                field.clear(location);
                location = null;
                field = null;
            }
        }
        giveBirth(newAnimal);
        Localizacao newLocation = field.freeAdjacentLocation(location);
        if(newLocation != null) {
            setLocation(newLocation);
        }
        else {
            setDead();
        }
    }

    /**
     * Return the rabbit's location.
     * @return The rabbit's location.
     */
    public Localizacao getLocation()
    {
        return location;
    }
    
    /**
     * Place the rabbit at the new location in the given field.
     * @param newLocation The rabbit's new location.
     */

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Animal> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Localizacao> free = field.getFreeAdjacentLocations(location);
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Localizacao loc = free.remove(0);
            Coelho young = new Coelho(false, field, loc);
            newRabbits.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
