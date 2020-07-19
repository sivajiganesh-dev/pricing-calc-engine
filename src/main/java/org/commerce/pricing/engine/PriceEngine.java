package org.commerce.pricing.engine;


import org.commerce.pricing.engine.worker.CostCalculatorWorker;
import org.commerce.pricing.product.Product;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Thread pool executor engine with product configured
 */
public class PriceEngine {
    Product product;
    ThreadPoolExecutor executor;

    public PriceEngine(Product product) {
        this.product = product;
        initializePool();
    }

    /**
     * Initialize thread pool with default values.
     * Configured core by calling runtime and finding no cores
     */
    private void initializePool() {
        executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                10,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>()
        );
    }

    /**
     * Calculate the total price for the product with configured qty
     *
     * @throws InterruptedException exception whn interrupted
     */
    public void calculatePrice() throws InterruptedException {

        //Return if no components
        if (product.getComponents().size() == 0) {
            System.out.println("0 components added to product");
            return;
        }

        //if previously executed, and initializing again
        if (executor.isTerminated()) {
            initializePool();
        }

        //Produce work for calculator worker
        for (int i = 0; i < product.getQty(); i++) {
            executor.execute(new CostCalculatorWorker(product));
        }

        //Wait while work is being done
        while (executor.getActiveCount() > 0) {
            System.out.println("processing...");
            Thread.sleep(100L);
        }

        handleShutdown();
    }

    private void handleShutdown() throws InterruptedException {
        executor.shutdown();
        if (!executor.isTerminated())
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
    }
}
