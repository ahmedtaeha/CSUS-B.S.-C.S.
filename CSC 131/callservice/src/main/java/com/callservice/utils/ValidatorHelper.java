package com.callservice.utils;

public class ValidatorHelper {

    public static boolean validFilter(String filter) {
        return (filter.equalsIgnoreCase("available") || filter.equalsIgnoreCase("busy")
                || filter.equalsIgnoreCase("logged-out") || filter.equalsIgnoreCase("loggedout")
                || filter.equalsIgnoreCase("preview")
                || filter.equalsIgnoreCase("after"));
    }
}
