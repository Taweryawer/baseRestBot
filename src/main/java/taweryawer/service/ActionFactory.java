package taweryawer.service;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import taweryawer.statemachine.actions.ErrorAction;
import taweryawer.statemachine.actions.PersistNameAction;
import taweryawer.statemachine.actions.StartAction;

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
}
