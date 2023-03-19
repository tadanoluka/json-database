package client;

import com.beust.jcommander.Parameter;
import com.google.gson.JsonElement;

public class Args {
    @Parameter(names = {"-t"}, description = "Type of action")
    String type;
    @Parameter(names = {"-k"}, description = "Key in database")
    String key;
    @Parameter(names = {"-v"}, description = "Value to put in database")
    String value;
    @Parameter(names = {"-in"}, description = "Name of json request file")
    String filename;
}
