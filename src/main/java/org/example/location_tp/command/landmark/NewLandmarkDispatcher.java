package org.example.location_tp.command.landmark;

import org.example.location_tp.command.Dispatcher;

public class NewLandmarkDispatcher extends Dispatcher {
    public NewLandmarkDispatcher() {
        this
                .add(new Create(), "create")
                .add(new Delete(), "delete")
                .add(new List(), "list")
                .add(new Teleport(), "tp");
    }
}
