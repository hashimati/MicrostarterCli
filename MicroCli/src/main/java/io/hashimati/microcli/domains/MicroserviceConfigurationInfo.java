package io.hashimati.microcli.domains;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MicroserviceConfigurationInfo
{

    private String name;
    private ArrayList<String> services = new ArrayList<>();
}
