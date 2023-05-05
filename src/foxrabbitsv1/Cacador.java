package foxrabbitsv1;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Cacador {

    private static final int MAX_AGE = 1200;

    Campo field;
    Localizacao location;
    int age;
    boolean alive;
    Random random = new Random();

    public Cacador(Campo field, Localizacao location)
    {
        this.field = field;
        this.location = location;
        alive = true;
        age = random.nextInt(MAX_AGE);
    }

    public Localizacao act()
    {
        List<Localizacao> adjacent = field.adjacentLocations(location);
        Iterator<Localizacao> it = adjacent.iterator();
        while(it.hasNext()) {
            Localizacao where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Lobo) {
                Lobo lobo = (Lobo) animal;
                cacar(lobo);
            }
        }
        return null;
    }

    public void cacar(Animal animal) {
        if (animal.isAlive()) {
            animal.setDead();
        }
    }


}
