package myapp.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import myapp.model.CV;
import myapp.model.Person;
import myapp.services.PersonManager;

import static org.junit.Assert.assertNotNull;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

class TestPersonManager {

	@EJB
	PersonManager pm;

	static EJBContainer container;
	
    @BeforeAll
    static  public void beforeAll() throws Exception {
        container = EJBContainer.createEJBContainer();
    }
    
    @AfterAll
    static  public void afterAll() throws Exception {
        container.close();
    }

    @BeforeEach
    public void before() throws Exception {
        container.getContext().bind("inject", this);    
    }

	public Person createNewPerson() {
		Person p = pm.create();
		return p;
	}

	public Person createNewPersonInit() {
		Person p = new Person("a", "a", "a@a.a", "a.com", new Date(), "a");
		return p;
	}

	@Test
	public void testFindPersons() {
        assertNotNull(pm);
		Person person = createNewPersonInit();
		person = pm.save(person);

		Person person2 = createNewPersonInit();
		person = pm.save(person2);

		assert (!pm.findAll().isEmpty());
	}

	@Test
	public void testAddPerson() {
		Person person = createNewPersonInit();
		person = pm.save(person);
		Person same = pm.find(person.getId());
		assertNotNull(same);
	}

	@Test
	public void testUpdatePerson() {
		Person person = createNewPersonInit();
		person = pm.save(person);
		
		person.setFirstname("b");
		pm.save(person);
		
		Person same = pm.find(person.getId());
		assertTrue(same.getFirstname().equals("b"));
	}

	@Test
	public void testRemovePerson() {
		Person person = createNewPersonInit();
		person = pm.save(person);
		
		pm.delete(person);
		
		Person same = pm.find(person.getId());
		assertNull(same);
	}
	
	@Test
	public void testPersonLike() {
		Person person = createNewPersonInit();
		person.setFirstname("Eucbert");
		person = pm.save(person);
		
		Person person2 = createNewPersonInit();
		person2.setLastname("Bertran");
		person2 = pm.save(person2);
		
		List<Person> same = pm.findLike("ert");
		assertTrue(same.size() >= 2);
	}

	@Test
	public void testPersonByEmail() {
		Person person = createNewPersonInit();
		person.setEmail("bertrand.blanc@gmail.com");
		person = pm.save(person);
		
		Person same = pm.findByEmail("bertrand.blanc@gmail.com");
		
		assertTrue(same.equals(person));
	}
	
	@Test
	public void testAddCV() {
		Person person = createNewPersonInit();
		
		CV cv = new CV();
		person.setCv(cv);
		
		person = pm.save(person);
		
		Person same = pm.find(person.getId());
		
		assertNotNull(same.getCv());
	}

}
