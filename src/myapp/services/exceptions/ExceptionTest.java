package myapp.services.exceptions;

import javax.ejb.ApplicationException;

// d�faire les modifications d�j� effectu�es.
@ApplicationException(rollback = true)
public class ExceptionTest extends Exception{
    public ExceptionTest(String msg) {
        super(msg);
    }
}