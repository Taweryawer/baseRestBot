package taweryawer.service;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import taweryawer.statemachine.actions.*;

@Service
public class ActionFactory {


    @Lookup
    public StartAction startAction() {
        return null;
    }

    @Lookup
    public ErrorAction errorAction() {
        return null;
    }

    @Lookup
    public PersistNameAction persistNameAction() {
        return  null;
    }

    @Lookup
    public PersistPhoneNumberAction persistPhoneNumberAction(){
        return null;
    }

    @Lookup
    public PersistAddressAction persistAddressAction() {
        return null;
    }

    @Lookup
    public MessageAction messageAction() {
        return null;
    }

    @Lookup
    public ProfileAction profileAction() {
        return null;
    }
}
