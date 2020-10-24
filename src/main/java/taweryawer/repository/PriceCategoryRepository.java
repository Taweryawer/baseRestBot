package taweryawer.repository;

import taweryawer.entities.PriceCategory;

import java.util.List;

public interface PriceCategoryRepository {
    List<PriceCategory> getAllPriceCategories();

    PriceCategory getPriceCategoryByTitle(String title);
}
