package ru.vat78.homeMoney.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.model.User;

import static org.testng.Assert.*;

public class UsersDaoTest extends CommonEntryDaoTest{

    @Autowired
    UsersDao usersDao;

    @Test(groups = {"dao", "security"})
    public void usersDaoTest() {

        long cnt = usersDao.getCount();
        long adm = usersDao.getAllAdmins().size();

        User user = new User();
        user.setName("test");
        user.setPassword("test");
        user.setAdmin(true);
        User saved = usersDao.save(user);
        assertEquals(usersDao.getCount(),cnt+1);
        assertEquals(usersDao.getAllAdmins().size(), adm+1);

        User result = usersDao.findByName(saved.getName());
        assertSame(result, saved);

        result = (User) usersDao.findById(result.getId());
        assertSame(result, saved);

        usersDao.delete(result);
        assertEquals(usersDao.getCount(),cnt);

    }

    @Test(groups = {"dao", "security"}, expectedExceptions = org.hibernate.exception.ConstraintViolationException.class)
    public void emptyPasswordTest() {
        User user = new User();
        user.setName("test");
        user = usersDao.save(user);
        assertNull(user.getId());
        usersDao.delete(user);
    }

    /*
    @Test(groups = {"dao", "security"}, expectedExceptions = javax.validation.ConstraintViolationException.class)
    public void shortPasswordTest() {
        User user = new User();
        user.setName("test");
        user.setPassword("1");
        user = usersDao.save(user);
        assertNull(user.getId());
        usersDao.delete(user);
    }
    */

    @Test(groups = {"dao", "security"}, expectedExceptions = javax.validation.ConstraintViolationException.class)
    public void spacesInNameTest() {
        User user = new User();
        user.setPassword("11111");
        user.setName("test name");
        user = usersDao.save(user);
        assertNull(user.getId());
        usersDao.delete(user);
    }

    @Test(groups = {"dao", "security"}, expectedExceptions = javax.validation.ConstraintViolationException.class)
    public void shortNameTest() {
        User user = new User();
        user.setPassword("11111");
        user.setName("t");
        user = usersDao.save(user);
        assertNull(user.getId());
        usersDao.delete(user);
    }

    @Test(groups = {"dao", "security"}, expectedExceptions = javax.validation.ConstraintViolationException.class)
    public void symbolsInNameTest() {
        User user = new User();
        user.setPassword("11111");
        user.setName("test$name");
        user = usersDao.save(user);
        assertNull(user.getId());
        usersDao.delete(user);
    }


}