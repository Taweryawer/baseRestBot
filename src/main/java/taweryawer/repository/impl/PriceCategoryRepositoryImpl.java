package taweryawer.repository.impl;

import org.springframework.stereotype.Repository;
import taweryawer.entities.PriceCategory;
import taweryawer.entities.PriceLabel;
import taweryawer.repository.PriceCategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PriceCategoryRepositoryImpl implements PriceCategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PriceCategory> getAllPriceCategories() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PriceCategory> cq = cb.createQuery(PriceCategory.class);
        Root<PriceCategory> root = cq.from(PriceCategory.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public PriceCategory getPriceCategoryByTitle(String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PriceCategory> cq = cb.createQuery(PriceCategory.class);
        Root<PriceCategory> root = cq.from(PriceCategory.class);
        cq.select(root).where(cb.equal(root.get("title"), title));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public void save(PriceCategory priceCategory) {
        entityManager.persist(priceCategory);
    }

    @Override
    public void savePriceLabel(PriceLabel priceLabel) {
        entityManager.persist(priceLabel);
    }
}
