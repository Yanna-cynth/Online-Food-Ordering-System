package foodordering;

import foodordering.service.FoodOrderingSystem;
import foodordering.ui.ConsoleApp;

public class Main {
    public static void main(String[] args) {
        FoodOrderingSystem system = new FoodOrderingSystem();
        new ConsoleApp(system).run();
    }
}
