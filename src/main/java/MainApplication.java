import org.commerce.pricing.data.DataSource;
import org.commerce.pricing.engine.PriceEngine;
import org.commerce.pricing.product.Product;
import org.commerce.pricing.ui.chain.impl.MainChain;

public class MainApplication {
    public static void main(String[] args) {
        System.out.println("Main Application");

        // Initialize configured data
        DataSource.getInstance();

        //Initialize product
        Product product = new Product();
        product.setName(DataSource.getInstance().getName());

        //Enable price engine with product
        PriceEngine priceEngine = new PriceEngine(product);

        //Start display of options
        MainChain
                .getInstance()
                .configure(product, priceEngine)
                .display();

        System.out.println("Thank you!");
    }
}
