package io.hashimati.microcli.domains;

import lombok.Data;

@Data
public class MService {

    private String name;
    private String description;
    private String path;
    private String type; // AUTH, NORMAL, GATEWAY, DISCOVERY.
}
