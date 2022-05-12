package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

import java.awt.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;
    private Map<Class, EntityPart> parts;

    public Entity(int minVertices, int maxVertices) {
        this();
        int vertices = minVertices + new Random().nextInt(maxVertices - minVertices);
        this.shapeX = new float[vertices];
        this.shapeY = new float[vertices];
    }
    public Entity(int vertices) {
        this();
        this.shapeX = new float[vertices];
        this.shapeY = new float[vertices];
    }
    public Entity() {
        parts = new ConcurrentHashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if (!(o instanceof Entity)) {
            return false;
        }
        Entity other = (Entity) o;

        return Objects.equals(this.getID(), other.getID());
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }
    
    public void remove(Class partClass) {
        parts.remove(partClass);
    }
    
    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }
    
    public void setRadius(float r){
        this.radius = r;
    }
    
    public float getRadius(){
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }
}
