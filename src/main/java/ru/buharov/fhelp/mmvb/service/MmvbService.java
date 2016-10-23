package ru.buharov.fhelp.mmvb.service;

import java.util.Map;

public interface MmvbService {

    String USD = "USD";
    String EUR = "EUR";

    Map<String, Double> getCurrentCbrfRates();
}
