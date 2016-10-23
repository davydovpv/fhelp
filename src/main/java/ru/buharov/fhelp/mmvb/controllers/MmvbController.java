package ru.buharov.fhelp.mmvb.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.buharov.fhelp.mmvb.service.MmvbService;

import java.util.Map;

@Controller
@RequestMapping("/mmvb")
public class MmvbController {

    private Logger log = Logger.getLogger(MmvbController.class);

    @Autowired
    private MmvbService mmvbService;

    @RequestMapping(value="/cbrf/rates", method = RequestMethod.GET)
    public String getCurrentCbrfRates(ModelMap model) {
        Map<String, Double> rates =  mmvbService.getCurrentCbrfRates();
        model.addAttribute("message", createRatesString(rates));
        return "hello";
    }

    private String createRatesString(Map<String, Double> rates) {
        StringBuilder sb = new StringBuilder();
        appendRate(MmvbService.USD, rates, sb);
        appendRate(MmvbService.EUR, rates, sb);
        return sb.toString();
    }

    private void appendRate(String rateName, Map<String, Double> rates, StringBuilder sb) {
        sb.append(rateName);
        sb.append(": ");
        Double value = rates.get(rateName);
        sb.append(value != null ? value : "???");
        sb.append("\n");
    }

}
