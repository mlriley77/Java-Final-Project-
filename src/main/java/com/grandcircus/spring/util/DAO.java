package com.grandcircus.spring.util;
import com.grandcircus.spring.controller.HomeController;
import com.grandcircus.spring.models.FamiliesEntity;
import com.grandcircus.spring.models.UsersEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

/**
 */

@Repository
@Transactional
public class DAO {
    private static Session loadSession() {
        Configuration configurationObject = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configurationObject.buildSessionFactory();
        return sessionFactory.openSession();
    }
    public static FamiliesEntity newFamily(String famName) {
        Session browsingSession = loadSession();
        Transaction databaseTransaction = browsingSession.beginTransaction();
        FamiliesEntity newFamily = new FamiliesEntity();
        newFamily.setName(famName);
        browsingSession.save(newFamily);
        databaseTransaction.commit();
        return newFamily;
    }
    public static void newUser(String fName, String lName,
                               String email, String password,
                               int usergroup, int familyid) {
        Session browsingSession = loadSession();
        Transaction databaseTransaction = browsingSession.beginTransaction();
        UsersEntity user = new UsersEntity();
        user.setFname(fName);
        user.setLname(lName);
        user.setEmail(email);
        user.setUsergroup(usergroup);
        user.setPassword(password);
        user.setFamilyid(familyid);
        browsingSession.save(user);
        databaseTransaction.commit();
    }
    public static void updateUserCoordinates(String checkinLat,
                                             String checkinLong,
                                             String userId) {
        Session browsingSession = loadSession();
        Transaction myTransaction = browsingSession.beginTransaction();
        Criteria criteria = browsingSession.createCriteria(UsersEntity.class);
        UsersEntity personCheckingIn = (UsersEntity) criteria
                .add(Restrictions.eq("userid", Integer.parseInt(userId)))
                .uniqueResult();
        personCheckingIn.setLastlat(checkinLat);
        personCheckingIn.setLastlong(checkinLong);
        personCheckingIn.setLasttime(HomeController.getCurrentTime());
        browsingSession.save(personCheckingIn);
        myTransaction.commit();
    }
    public static boolean doesUserExist(String email) {
        // this will pass if the email exists, or fail if the user does not exist.
        try {
            Session browsingSession = loadSession();
            Criteria usersCriteria = browsingSession.createCriteria(UsersEntity.class);
            UsersEntity newUser = (UsersEntity) usersCriteria
                    .add(Restrictions.eq("email", email))
                    .uniqueResult();
            String doesThisExist = newUser.getEmail();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
    public static boolean doesFamilyExist(int famId) {
        try {
            Session browsingSession = loadSession();
            Criteria familyCriteria = browsingSession.createCriteria(FamiliesEntity.class);
            FamiliesEntity family = (FamiliesEntity) familyCriteria
                    .add(Restrictions.eq("familyid", famId))
                    .uniqueResult();
            int doesThisExist = family.getFamilyid();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
    public static UsersEntity getUserByEmail(String email) {
        Session browsingSession = loadSession();
        Criteria userCriteria = browsingSession.createCriteria(UsersEntity.class);
        return (UsersEntity) userCriteria
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }
    public static UsersEntity loadThisAccount(String userId) {
        Session browsingSession = loadSession();
        Criteria userCriteria = browsingSession.createCriteria(UsersEntity.class);
        return (UsersEntity) userCriteria
                .add(Restrictions.eq("userid",
                        Integer.parseInt(userId)))
                .uniqueResult();
    }
    public static ArrayList<UsersEntity> loadChildAccounts(int familyId) {
        Session browsingSession = loadSession();
        Criteria childCriteria = browsingSession.createCriteria(UsersEntity.class);
        return (ArrayList<UsersEntity>) childCriteria
                .add(Restrictions.eq("familyid", familyId))
                .add(Restrictions.eq("usergroup", 1))
                .list();
    }
    public static FamiliesEntity loadFamily(int familyId) {
        Session browsingSession = loadSession();
        Criteria familyCriteria = browsingSession.createCriteria(FamiliesEntity.class);
        return (FamiliesEntity) familyCriteria
                .add(Restrictions.eq("familyid", familyId))
                .uniqueResult();
    }
    public static UsersEntity loadParentAccount(int familyId) {
        Session browsingSession = loadSession();
        Criteria adminCriteria = browsingSession.createCriteria(UsersEntity.class);
        return (UsersEntity) adminCriteria
                .add(Restrictions.eq("familyid", familyId))
                .add(Restrictions.eq("usergroup", 0))
                .uniqueResult();
    }
}