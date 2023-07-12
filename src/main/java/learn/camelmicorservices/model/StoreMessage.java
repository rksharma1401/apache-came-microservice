package learn.camelmicorservices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StoreMessage (String name, String message,String createdAt ) { }
