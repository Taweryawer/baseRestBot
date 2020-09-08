package taweryawer.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import taweryawer.entities.User;
import taweryawer.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private Log log = LogFactory.getLog(UserRepositoryImpl.class);

    @Override
    public User getUserByTelegramId(String telegramId) {
        log.info("Acquiring user " + telegramId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(cb.equal(root.get("telegramId"), telegramId));
        TypedQuery<User> query = entityManager.createQuery(cq);
        try {
            User user = query.getSingleResult();
            log.info("User " + telegramId + " successfully acquired");
            return user;
        } catch (NoResultException e) {
            log.info("User " + telegramId + " not found, creating...");
            User newUser = createNewUser(telegramId);
            log.info("User " + telegramId + " has been sucessfully created.");
            return newUser;
        }
    }


    private User createNewUser(String telegramId) {
        User user = new User();
        user.setMachineId(telegramId);
        entityManager.persist(user);
        return user;
    }
}
