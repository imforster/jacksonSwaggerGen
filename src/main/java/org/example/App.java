package org.example;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class App {

  public static void main(String[] args) throws IOException {
//    System.out.println("hello");

    ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));

    mapper.findAndRegisterModules();

    Order order = mapper.readValue(new File("src/main/resources/order.yaml"), Order.class);

    System.out.println(order);

    List<OrderLine> lines = new ArrayList<>();
    lines.add(new OrderLine("Copper Wire (200ft)", 1,
        new BigDecimal(50.67).setScale(2, RoundingMode.HALF_UP)));
    lines.add(new OrderLine("Washers (1/4\")", 24,
        new BigDecimal(.15).setScale(2, RoundingMode.HALF_UP)));
    Order order2 = new Order(
        "B-9910",
        LocalDate.parse("2019-04-18", DateTimeFormatter.ISO_DATE),
        "Customer, Jane",
        lines);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    mapper.writeValue(new File("src/main/resources/orderOutput.yaml"), order2);

    System.out.println(order2);

    Info info = new Info();
    info.setDescription("Descriptive text");
    info.setTitle("title");
    info.setVersion("1.0");

    ApiResponse response = new ApiResponse();
    response.setDescription("Success");
    ApiResponse response1 = new ApiResponse();
    response1.setDescription("Inavlid Input");

    ApiResponses responses = new ApiResponses();
    responses.addApiResponse("200", response);
    responses.addApiResponse("405", response1);
    Operation operation = new Operation();
    operation.setTags(List.of("store"));
    operation.setResponses(responses);
    PathItem item = new PathItem();
    item.setGet(operation);

    Paths paths = new Paths();
    paths.addPathItem("/hello", item);

    OpenAPI api = new OpenAPI();
    api.setInfo(info);
    api.setPaths(paths);

    System.out.println(api);
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.writeValue(new File("src/main/resources/api-docs.yaml"), api);
  }
}
