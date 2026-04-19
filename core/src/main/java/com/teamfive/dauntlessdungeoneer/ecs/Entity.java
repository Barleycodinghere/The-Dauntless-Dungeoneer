package com.teamfive.dauntlessdungeoneer.ecs;
import java.util.HashMap;
import java.util.Map;

public class Entity {

    private final int id;
    private final Map<Class<? extends Component>, Component> components;

    public Entity(int id) {
        this.id = id;
        this.components = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public <T extends Component> void addComponent(Class<T> type, T component) {
        components.put(type, component);
    }

    public <T extends Component> T getComponent(Class<T> type) {
        return type.cast(components.get(type));
    }

    public <T extends Component> boolean hasComponent(Class<T> type) {
        return components.containsKey(type);
    }

    public <T extends Component> void removeComponent(Class<T> type) {
        components.remove(type);
    }
}
