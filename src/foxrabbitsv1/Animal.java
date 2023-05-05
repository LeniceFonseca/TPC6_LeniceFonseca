package foxrabbitsv1;

import java.io.LineNumberInputStream;
import java.util.List;

public abstract class Animal {

    private boolean alive;
    private Campo field;
    private Localizacao location;

    public Animal(Campo field, Localizacao location) {
        alive = true;
        this.field = field;
        setLocation(location);
    }

    public boolean isAlive()
    {
        return alive;
    }

    protected Localizacao getLocation()
    {
        return location;
    }

    protected void setLocation(Localizacao newLocation) {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    protected void setDead() {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    protected Campo getField() {
        return field;
    }

    abstract public void act(List<Animal> newAnimal);

}
