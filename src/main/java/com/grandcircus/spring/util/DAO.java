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
 * Class description
 *
 * @author Sarah Guarino
 * @version 1.0
 */

@Repository
@Transactional
public class DAO {
    private static Configuration configurationObject = new Configuration().configure("hibernate.cfg.xml");
    private static SessionFactory sessionFactory = configurationObject.buildSessionFactory();

    public static FamiliesEntity newFamily(String famName) {
        Session browsingSession = sessionFactory.openSession();
        Transaction databaseTransaction = browsingSession.beginTransaction();

        FamiliesEntity newFamily = new FamiliesEntity();
        newFamily.setName(famName);

        browsingSession.save(newFamily);
        databaseTransaction.commit();
        browsingSession.close();

        return newFamily;
    }

    public static void newUser(String fName, String lName,
                               String email, String password,
                               int usergroup, int familyid) {
        Session browsingSession = sessionFactory.openSession();
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
        browsingSession.close();
    }

    public static void updateUserCoordinates(String checkinLat,
                                             String checkinLong,
                                             String userId) {
        Session browsingSession = sessionFactory.openSession();
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
        browsingSession.close();
    }

    public static boolean doesUserExist(String email) {
        // this will pass if the email exists, or fail if the user does not exist.
        try {
            Session browsingSession = sessionFactory.openSession();
            Criteria usersCriteria = browsingSession.createCriteria(UsersEntity.class);

            UsersEntity newUser = (UsersEntity) usersCriteria
                    .add(Restrictions.eq("email", email))
                    .uniqueResult();
            String doesThisExist = newUser.getEmail();

            browsingSession.close();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean doesFamilyExist(int famId) {
        try {
            Session browsingSession = sessionFactory.openSession();
            Criteria familyCriteria = browsingSession.createCriteria(FamiliesEntity.class);

            FamiliesEntity family = (FamiliesEntity) familyCriteria
                    .add(Restrictions.eq("familyid", famId))
                    .uniqueResult();
            int doesThisExist = family.getFamilyid();

            browsingSession.close();

            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static UsersEntity getUserByEmail(String email) {
        Session browsingSession = sessionFactory.openSession();
        Criteria userCriteria = browsingSession.createCriteria(UsersEntity.class);

        UsersEntity user = (UsersEntity) userCriteria
                .add(Restrictions.eq("email", email))
                .uniqueResult();

        browsingSession.close();

        return user;
    }

    public static UsersEntity loadThisAccount(String userId) {
        Session browsingSession = sessionFactory.openSession();
        Criteria userCriteria = browsingSession.createCriteria(UsersEntity.class);

        UsersEntity user = (UsersEntity) userCriteria
                .add(Restrictions.eq("userid",
                        Integer.parseInt(userId)))
                .uniqueResult();

        browsingSession.close();

        return user;
    }

    public static ArrayList<UsersEntity> loadChildAccounts(int familyId) {
        Session browsingSession = sessionFactory.openSession();

        Criteria childCriteria = browsingSession.createCriteria(UsersEntity.class);

        ArrayList<UsersEntity> children = (ArrayList<UsersEntity>) childCriteria
                .add(Restrictions.eq("familyid", familyId))
                .add(Restrictions.eq("usergroup", 1))
                .list();

        browsingSession.close();

        return children;
    }

    public static FamiliesEntity loadFamily(int familyId) {
        Session browsingSession = sessionFactory.openSession();
        Criteria familyCriteria = browsingSession.createCriteria(FamiliesEntity.class);

        FamiliesEntity family = (FamiliesEntity) familyCriteria
                .add(Restrictions.eq("familyid", familyId))
                .uniqueResult();

        browsingSession.close();

        return family;
    }

    public static UsersEntity loadParentAccount(int familyId) {
        Session browsingSession = sessionFactory.openSession();
        Criteria adminCriteria = browsingSession.createCriteria(UsersEntity.class);
        UsersEntity parent = (UsersEntity) adminCriteria
                .add(Restrictions.eq("familyid", familyId))
                .add(Restrictions.eq("usergroup", 0))
                .uniqueResult();

        browsingSession.close();

        return parent;
    }
}
