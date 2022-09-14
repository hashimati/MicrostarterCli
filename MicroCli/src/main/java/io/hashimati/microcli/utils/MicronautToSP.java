package io.hashimati.microcli.utils;


import java.util.HashMap;

public class MicronautToSP {

    private HashMap<String, String> MnToSP;

    public MicronautToSP()
    {
         MnToSP= new HashMap<>(){{
             put("@Controller", "@RestController\n@RequestMapping");
             put("@Inject", "@Autowired");
             put("@Singleton", "@Service");
             put("@Body", "@RequestBody");
             put("@QueryValue", "@RequestParam");
             //imports;
             put("import io.micronaut.http.annotation", "org.springframework.web.bind.annotation");
             put("import io.micronaut.context.annotation.Parameter", "");
             put("import io.micronaut.core.annotation.NonNull", "import org.springframework.lang.NonNull");
             put("import jakarta.inject.Inject","import org.springframework.beans.factory.annotation.Autowired");
             put("import io.micronaut.http.MediaType", "import org.springframework.http.MediaType");
             put("import io.micronaut.http.multipart.CompletedFileUpload", "import org.springframework.web.multipart.MultipartFile");
             put("io.micronaut.http.HttpHeaders", "org.springframework.http.HttpHeaders");
         }};
    }

    public String springify(String template){

        String result = template;

        for (String x : this.MnToSP.keySet()) {
            result = result.replace(x, MnToSP.get(x));
        }
        return result;
    }
}
