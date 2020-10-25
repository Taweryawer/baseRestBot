package taweryawer.repository;

import taweryawer.entities.PriceCategory;
import taweryawer.entities.PriceLabel;

import java.util.List;

public interface PriceCategoryRepository {
    List<PriceCategory> getAllPriceCategories();

    PriceCategory getPriceCategoryByTitle(String title);

    void save(PriceCategory priceCategory);

    void savePriceLabel(PriceLabel priceLabel);
}
