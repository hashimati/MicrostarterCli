package io.hashimati.microcli.test;

import picocli.CommandLine.ITypeConverter;

public class MyOptionConverter implements ITypeConverter<MyOptions> {
    @Override
    public MyOptions convert(String value) throws Exception {
        String[] values = value.split("_");

        if(values.length == 2)
        {
            MyOptions myOptions = new MyOptions();
            myOptions.setName(values[0]);
            try{
                myOptions.setAge(Integer.parseInt(values[1]));
                return myOptions;
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else
            throw new Exception("Error");
        return null;
    }
}
