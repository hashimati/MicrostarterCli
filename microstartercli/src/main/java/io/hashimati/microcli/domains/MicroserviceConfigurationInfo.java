package io.hashimati.microcli.domains;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hashimati.microcli.utils.GeneratorUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Data
@NoArgsConstructor
public class MicroserviceConfigurationInfo
{
    private String name;
    private String version;
    private String description;
    private String author;
    private String license;
    LinkedHashSet<MService> services = new LinkedHashSet<MService>(  );
    private LinkedHashSet<String> features = new LinkedHashSet<>();

    public void addService(MService service)
    {
        services.add( service );
    }

    public void addFeature(String feature)
    {
        features.add( feature );
    }

    public void addServices(List<MService> services)
    {
        this.services.addAll( services );
    }

    public void addFeatures(List<String> features)
    {
        this.features.addAll( features );
    }

    public String tJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
       return mapper.writeValueAsString( this );
    }
    public void  writeToFile()
    {
        try {
            GeneratorUtils.dumpContentToFile(GeneratorUtils.getCurrentWorkingPath()+"/microservicesConfig.json",tJson());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static MicroserviceConfigurationInfo readFromFile()
    {
        try {
            return new ObjectMapper().readValue(GeneratorUtils.getCurrentWorkingPath()+"/microservicesConfig.json",MicroserviceConfigurationInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addFeatures(Collection<String> features)
    {
       this.features.addAll( features );
    }
}
