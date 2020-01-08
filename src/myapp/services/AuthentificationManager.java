package myapp.services;

import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;

import myapp.model.Person;

@Stateful(name="userAuth")
@SessionScoped
public class AuthentificationManager implements IAuthentificationManager{
    
    private Person user;
    
    public void login(Person user) {
    	setUser(user);
    	System.out.println("User connected");
    }
    
    public void logout() {
    	setUser(null);
    	System.out.println("User disconnected");
    }
    
    public boolean isConnected() {
    	return getUser() != null;
    }
    
	public Person getUser() {
		return user;
	}

	private void setUser(Person user) {
		this.user = user;
	}
	
}
