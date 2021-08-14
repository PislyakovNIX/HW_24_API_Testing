import com.fasterxml.jackson.annotation.JsonProperty;

public class GamePutRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;

    @JsonProperty("id")
    private int id;

    public GamePutRequest(String name, String category, int id) {
        this.name = name;
        this.category = category;
        this.id = id;
    }
}
