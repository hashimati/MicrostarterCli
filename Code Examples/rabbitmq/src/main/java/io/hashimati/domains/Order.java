package io.hashimati.domains;


import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {
    private String message;
    private String qoute;

}
