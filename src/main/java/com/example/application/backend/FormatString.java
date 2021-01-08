package com.example.application.backend;

public interface FormatString {
    String FormatName(String name);

    //the postcode has the same format (3 letters right of the space)
    String FormatPostcode(String postcode);
}
