package taweryawer.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import taweryawer.entities.User;
import taweryawer.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private Log log = LogFactory.getLog(UserRepositoryImpl.class);

    @Override
    public User getUserByTelegramId(String telegramId) throws Exception {
        log.info("Acquiring user " + telegramId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(cb.equal(root.get("machineId"), telegramId));
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

    @Override
    public void changeUserName(String telegramId, String newName) throws Exception {
        User user = getUserByTelegramId(telegramId);
        log.info("Changing user " + user.getId() + " name from " + user.getName() + " to " + newName);
        user.setName(newName);
        entityManager.persist(user);
        log.info("Changed user " + user.getId() + " name successfully");
    }

    @Override
    public void changeUserPhoneNumber(String telegramId, String newNumber) throws Exception {
        User user = getUserByTelegramId(telegramId);
        log.info("Changing user " + user.getId() + " phone number from " + user.getName() + " to " + newNumber);
        user.setPhoneNumber(newNumber);
        entityManager.persist(user);
        log.info("Changed user " + user.getId() + " phone number successfully");
    }

    @Override
    public void changeUserAddress(String telegramId, String address) throws Exception {
        User user = getUserByTelegramId(telegramId);
        log.info("Changing user " + user.getId() + " address from " + user.getAddress() + " to " + address);
        user.setAddress(address);
        entityManager.persist(address);
        log.info("Changed user " + user.getId() + " address successfully");
    }


    private User createNewUser(String telegramId) {
        User user = new User();
        user.setMachineId(telegramId);
        entityManager.persist(user);
        return user;
    }
}
