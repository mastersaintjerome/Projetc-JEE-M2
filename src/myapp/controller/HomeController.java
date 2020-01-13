package myapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import org.mindrot.jbcrypt.BCrypt;

import myapp.model.Activity;
import myapp.model.ActivityType;
import myapp.model.CV;
import myapp.model.Person;
import myapp.services.ActivityManager;
import myapp.services.CVManager;
import myapp.services.PersonManager;

/**
 * The Class HomeController.
 */
@ManagedBean(name = "home")
public class HomeController {

	/** The Person Manager. */
	@EJB
	PersonManager pm;

	/** The CV Manager. */
	@EJB
	CVManager cvm;

	/** The Activity Manager. */
	@EJB
	ActivityManager am;

	/** List of Person */
	public List<Person> persons = new ArrayList<>();

	/**
	 * Init the Database.
	 */
	@PostConstruct
	public void init() {
		Person person, lastPerson = null;
		
		CV cv;
		Activity activity;
		long time;
		if (pm.findAll().size() < 100) {
			for (int i = 0; i < 100; i++) {

				person = new Person();
				time = System.currentTimeMillis();
				person.setFirstname("firstName" + time);
				person.setLastname("lastName" + time);
				person.setEmail("test" + time + "@gmail.com");
				person.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
				
				if(lastPerson != null)
					person.setInvitedBy(lastPerson);

				cv = new CV();
				cv = cvm.save(cv);
				activity = new Activity();
				activity.setTitle("activity 1");
				activity.setYear(new Date(time));
				activity.setType(ActivityType.Formation);
				activity.setCv(cv);
				activity = am.save(activity);
				cv.addActivity(activity);

				activity = new Activity();
				activity.setTitle("activity 2");
				activity.setYear(new Date(time));
				activity.setType(ActivityType.Profesionnal);
				activity.setCv(cv);
				activity = am.save(activity);
				cv.addActivity(activity);

				person.setCv(cv);
				person = pm.save(person);
				
				lastPerson = person;
			}
		}
	}

	/**
	 * Gets some persons.
	 *
	 * @return the some persons
	 */
	public List<Person> getSomePersons() {
		return pm.findSome(10);
	}
}
