package org.paumard.flightmonitoring;

import org.paumard.flightmonitoring.business.FlightMonitoring;
import org.paumard.flightmonitoring.business.model.FlightID;
import org.paumard.flightmonitoring.business.service.DBService;
import org.paumard.flightmonitoring.business.service.FlightGUIService;
import org.paumard.flightmonitoring.business.service.PriceMonitoringService;
import org.paumard.flightmonitoring.db.FlightDBService;
import org.paumard.flightmonitoring.gui.FlightGUI;
import org.paumard.flightmonitoring.pricemonitoring.FlightPriceMonitoringService;

public class Main {

    public static void main(String[] args) {

        DBService dbService = new FlightDBService();
        FlightGUIService flightGUIService = new FlightGUI();
        PriceMonitoringService monitoringService = new FlightPriceMonitoringService();
        FlightMonitoring flightMonitoring = new FlightMonitoring(dbService, monitoringService, flightGUIService);

        monitoringService.updatePrices();
        flightMonitoring.launchDisplay();

        var f1 = new FlightID("PaAt");
        var f2 = new FlightID("AmNY");
        var f3 = new FlightID("LoMi");
        var f4 = new FlightID("FrWa");

        flightMonitoring.followFlight(f1);
        flightMonitoring.followFlight(f2);
        flightMonitoring.followFlight(f3);
        flightMonitoring.followFlight(f4);

        flightMonitoring.monitorFlight(f1);
        flightMonitoring.monitorFlight(f2);
        flightMonitoring.monitorFlight(f3);
        flightMonitoring.monitorFlight(f4);

        while (true) {

        }
    }
}