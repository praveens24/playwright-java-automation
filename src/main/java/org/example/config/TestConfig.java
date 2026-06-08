package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;


public final class TestConfig {
    private static final Properties PROPERTIES = new Properties();
    static {
        try(InputStream input =TestConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")){
            if(input==null){
                throw new IllegalStateException("application.properties not found in src/test/resources");

            }
            PROPERTIES.load(input);
        }catch (IOException e){
            throw new IllegalStateException("Incorrect application property",e);
        }
    }
    private TestConfig(){

    }
    public static String baseUrl(){
        return PROPERTIES.getProperty("base.url");
    }
    public static String getUser(){
        String envUser = System.getenv("SAUCE_USERNAME");
        return (envUser!=null&& !envUser.isEmpty()) ?envUser:PROPERTIES.getProperty("username");
    }
    public static String getPassword(){
        String envPassword = System.getenv("SAUCE_PASSWORD");
        return (envPassword!=null && !envPassword.isEmpty() ? envPassword : PROPERTIES.getProperty("password"));
    }
    public static boolean headLessStatus() {
        return Optional.ofNullable(System.getenv("HEADLESS_STATUS"))
                .map(Boolean::parseBoolean)
                .orElse(Boolean.parseBoolean(PROPERTIES.getProperty("headless")));
    }
    public static String getBrowser(){
        String envBrowser = System.getenv("BROWSER");
        return (envBrowser!=null && !envBrowser.isEmpty() ? envBrowser : PROPERTIES.getProperty("browser"));
    }
}
