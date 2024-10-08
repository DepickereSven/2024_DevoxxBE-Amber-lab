package org.paumard.flightmonitoring.business;

import org.paumard.flightmonitoring.business.model.Flight;
import org.paumard.flightmonitoring.business.model.FlightID;
import org.paumard.flightmonitoring.business.service.DBService;
import org.paumard.flightmonitoring.business.service.FlightGUIService;
import org.paumard.flightmonitoring.business.service.PriceMonitoringService;
import org.paumard.flightmonitoring.business.service.FlightConsumer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FlightMonitoring {

    private static final Map<FlightID, Flight> monitoredFlights = new ConcurrentHashMap<>();

    private final DBService dbService;
    private final PriceMonitoringService priceMonitoringService;
    private final FlightGUIService flightGUIService;

    public FlightMonitoring(DBService dbService, PriceMonitoringService priceMonitoringService, FlightGUIService flightGUIService) {
        this.dbService = dbService;
        this.priceMonitoringService = priceMonitoringService;
        this.flightGUIService = flightGUIService;
    }

    public void followFlight(FlightID flightID) {
        Flight flight = dbService.fetchFlight(flightID);
        FlightConsumer flightConsumer = flight::updatePrice;
        priceMonitoringService.followPrice(flightID, flightConsumer);
    }

    public void monitorFlight(FlightID flightID) {
        Flight flight = dbService.fetchFlight(flightID);
        monitoredFlights.put(flightID, flight);
    }

    public void launchDisplay() {
        var executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
//            System.out.println("Displaying " + monitoredFlights.size() + " flights");
            for (var flight : monitoredFlights.values()) {
                flightGUIService.displayFlight(flight);
            }
        };
        executor.scheduleAtFixedRate(task, 0, 500, TimeUnit.MILLISECONDS);
    }
}