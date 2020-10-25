package taweryawer.repository.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import taweryawer.entities.Category;
import taweryawer.repository.CategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Cacheable(value = "categories")
    public List<Category> getAllCategories() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        Root<Category> root = cq.from(Category.class);
        cq.select(root);
        TypedQuery<Category> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Category getCategoryByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        Root<Category> root = cq.from(Category.class);
        cq.select(root).where(cb.equal(root.get("name"), name));
        TypedQuery<Category> query = entityManager.createQuery(cq);
        return query.getSingleResult();
    }

    @Override
    public void save(Category category) {
        entityManager.persist(category);
    }
}
