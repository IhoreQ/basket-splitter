package pl.ibobek.basket.model;

public class Product {

    private final String name;
    private boolean taken;

    public Product(String name) {
        this.name = name;
        this.taken = false;
    }

    public String getName() {
        return name;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    @Override
    public String toString() {
        return name;
    }
}
