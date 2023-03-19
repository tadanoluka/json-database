package responses;

import com.google.gson.JsonElement;

public class ValueResponse extends OKResponse implements Response {
    JsonElement value;

    public ValueResponse (JsonElement value) {
        this.value = value;
    }
}
