package com.example.application.backend;

public interface FormatString {
    /*
   Formats the name entry to keep clean data and ease queries
   Called in most of the entity functions
    */
    String FormatName(String name);

    /*
   Formats the postcodes
   All the UK postcodes are a mix of upper case letters and digits. There is a space before the 3 last characters.
    */
    String FormatPostcode(String postcode);
}
