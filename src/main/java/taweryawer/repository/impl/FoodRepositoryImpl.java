package taweryawer.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import taweryawer.entities.Food;
import taweryawer.repository.FoodRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class FoodRepositoryImpl implements FoodRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private Log log = LogFactory.getLog(FoodRepositoryImpl.class);

    @Override
    public List<Food> getFoodByCategory(String category) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Food> cq = cb.createQuery(Food.class);
        Root<Food> root = cq.from(Food.class);
        cq.select(root).where(cb.equal(root.get("category").get("name"), category));
        TypedQuery<Food> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Food getFoodById(Long foodId) {
        return entityManager.find(Food.class, foodId);
    }

    @Override
    public List<Food> getAllFood() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Food> cq = cb.createQuery(Food.class);
        Root<Food> root = cq.from(Food.class);
        cq.select(root);
        TypedQuery<Food> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void save(Food food) {
        entityManager.persist(food);
    }

    @Override
    public void update(Food food) {
        entityManager.merge(food);
    }
}
