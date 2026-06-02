#!/bin/bash
echo "Compiling Food Delivery GUI..."
cd src
javac FoodItem.java Customer.java Restaurant.java Cart.java FoodDeliveryApp.java
if [ $? -eq 0 ]; then
    echo "Compilation successful! Starting app..."
    java FoodDeliveryApp
else
    echo "Compilation failed. Make sure Java JDK is installed."
fi
