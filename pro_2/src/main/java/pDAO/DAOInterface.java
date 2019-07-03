package pDAO;

import pojos.Person;

import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author Pavel
 */
public interface DAOInterface {

    public void addPerson(Person person) throws SQLException;

    public void updatePerson(Person person) throws SQLException;

    public List<Person> findbyLogin(String login, boolean fullmatching) throws SQLException;

    public List<Person> findAll() throws SQLException;

    public void deletePerson(Person person) throws SQLException;
}
