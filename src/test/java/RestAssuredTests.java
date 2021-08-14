import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.lessThan;

public class RestAssuredTests {

    // 1. получить все видео игры, сделать проверку время получения ответа
    @Test
    public void getAllGames(){

        Response response = given().header("Accept", "application/json")
                .when()
                .get("http://localhost:8080/app/videogames");

        response.then()
                .assertThat()
                .statusCode(200)
                .assertThat().contentType(JSON)
        .and().time(lessThan(1700L));

        List<String> games = response.body().jsonPath().get("'name'");
        games.forEach(System.out::println);
    }

    //2. создать видеоигру используя Serialization json реквеста
    @Test
    public void createGame(){

        GamePostRequestPayLoad gamePostRequestPayLoad = new GamePostRequestPayLoad(50, "2021-12-30", "AutomationQA adventure 2",
                "95", 20, "RPG");

        given()
                .header("Content-Type", "application/json")
                .body(gamePostRequestPayLoad)
                .when()
                .post("http://localhost:8080/app/videogames")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .log().body();
    }

    //3. получить одну видеоигру по id и сделать De-Serialization json ответа
    @Test
    public void getGameById(){

        int gameId = 15;

        Response response = given().header("Accept", "application/json")
                .when()
                .get("http://localhost:8080/app/videogames/" + gameId);

        GameByIdResponse gameByIdResponse = response.as(GameByIdResponse.class);
        System.out.println("*****************************************************************************");
        System.out.println("id = " + gameByIdResponse.getId());
        System.out.println("name = " + gameByIdResponse.getName());
        System.out.println("*****************************************************************************");
    }

    //4. обновить имя и категорию видео игры
    @Test
    public void UpdateGame(){
        int gameId = 2;
        String newName = "Love birds";
        String newCategory = "Race";

        Response response = given().header("Accept", "application/json")
                .when()
                .get("http://localhost:8080/app/videogames/" + gameId);

        GameByIdResponse gameByIdResponse = response.as(GameByIdResponse.class);
        gameByIdResponse.setName(newName);
        gameByIdResponse.setCategory(newCategory);

                given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(gameByIdResponse)
                .when()
                .put("http://localhost:8080/app/videogames/" + gameId)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .log().body();
    }

    //Сделать десереализацию данного json http://jsonplaceholder.typicode.com/users
    @Test
    public void deserialization(){
        Response response = given()
                .when()
                .get("http://jsonplaceholder.typicode.com/users");

        //TODO падает на этой строчке, не могу разобраться почему
        ResponseForDeserialization responseForDeserialization = response.as(ResponseForDeserialization.class);

        //Для эксперимента повыводим данные из объекта
        System.out.println("Name = " + responseForDeserialization.getResponseForDeserialization().get(0).getName());
    }
}
