import java.util.ArrayList;

public class Cart {
    private ArrayList<FoodItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public void addToCart(FoodItem item) {
        cartItems.add(item);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < cartItems.size()) {
            cartItems.remove(index);
        }
    }

    public void clearCart() {
        cartItems.clear();
    }

    public ArrayList<FoodItem> getCartItems() {
        return cartItems;
    }

    public double getTotal() {
        double total = 0;
        for (FoodItem item : cartItems) {
            total += item.getPrice();
        }
        return total;
    }

    public int getItemCount() {
        return cartItems.size();
    }
}
