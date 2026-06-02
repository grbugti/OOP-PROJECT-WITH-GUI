@echo off
echo Compiling Food Delivery GUI...
cd src
javac FoodItem.java Customer.java Restaurant.java Cart.java FoodDeliveryApp.java
if %errorlevel% == 0 (
    echo Compilation successful! Starting app...
    java FoodDeliveryApp
) else (
    echo Compilation failed. Make sure Java JDK is installed.
    pause
)
