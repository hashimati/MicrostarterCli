package io.hashimati.microcli.test;

/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import picocli.CommandLine.Option;


public class MyOptions {


    @Option(names ={"-n", "--name"}, description = "Person's Name", descriptionKey = "Enter the name here: ")
    private String name;
    @Option(names={"--age", "-a"}, descriptionKey = "Enter the age: ", description = "Person's age")
    private int age;

    public  MyOptions(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
