import java.util.ArrayList;

public class Restaurant {
    private String restaurantName;
    private ArrayList<FoodItem> menu;

    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
        this.menu = new ArrayList<>();
    }

    public void addFood(FoodItem item) {
        menu.add(item);
    }

    public ArrayList<FoodItem> getMenu() {
        return menu;
    }

    public FoodItem getFood(int index) {
        return menu.get(index);
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
