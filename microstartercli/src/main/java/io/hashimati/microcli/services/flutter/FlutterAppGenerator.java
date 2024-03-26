package io.hashimati.microcli.services.flutter;


import io.hashimati.microcli.utils.PromptGui;
import jakarta.inject.Singleton;

import java.io.File;

@Singleton
public class FlutterAppGenerator
{
    public void generateApp(String workDir, String appName)
    {
        try
        {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String os = System.getProperty("os.name").toLowerCase();

            if(os.contains("win"))
            {
                processBuilder.command("cmd.exe", "/c", "flutter", "create", appName);
            }
            else
            {
                processBuilder.command("bash", "-c", "flutter", "create", appName);
            }

            processBuilder.directory(new File(workDir));
            Process process = processBuilder.start();
            process.waitFor();

            PromptGui.printlnSuccess("Flutter app " + appName + " has been created successfully.");
        }
        catch(Exception e)
        {

            PromptGui.printlnErr("Error: Failed to create Flutter app " + appName + ".");

        }
    }
}
