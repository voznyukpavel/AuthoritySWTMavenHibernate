package pDAO;

import pojos.Person;

import util.HibernateUtil;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

/**
 *
 * @author Pavel
 */
public class DAOClass implements DAOInterface {

    public void addPerson(Person person) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(person);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "РћС€РёР±РєР° I/O Add", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    public void updatePerson(Person person) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(person);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "РћС€РёР±РєР° I/O updateStudent", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    public List<Person> findbyLogin(String login,boolean fullmatching) throws SQLException {
        Session session = null;

        List<Person> persons = new ArrayList<Person>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria crit = session.createCriteria(Person.class);
            if(!fullmatching) {
            	persons = crit.add(Expression.like("login", login + "%")).list();
            }else {
            	persons = crit.add(Expression.like("login", login)).list();
            }

        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "РћС€РёР±РєР° I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return persons;
    }


    public List findAll() throws SQLException {
        Session session = null;
        List<Person> persons = new ArrayList<Person>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            persons = session.createCriteria(Person.class).list();
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "РћС€РёР±РєР° I/O getAllStudents()", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return persons;
    }


    public void deletePerson(Person person) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(person);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "РћС€РёР±РєР° I/O deleteStudent", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}

