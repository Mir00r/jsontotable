import dev.sayem.jsontotable.HtmlTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonToHtmlTableTest {

    @Test
    void jsonToHtmlTableTest() {
        String json = """
                [
                    {
                      "name": "Apple",
                      "color": "Red",
                      "quantity": 10,
                      "isFresh": true
                    },
                    {
                      "name": "Banana",
                      "color": "Yellow",
                      "quantity": 15,
                      "isFresh": true
                    },
                    {
                      "name": "Orange",
                      "color": "Orange",
                      "quantity": 8,
                      "isFresh": false
                    },
                    {
                      "name": "Grapes",
                      "color": "Purple",
                      "quantity": 20,
                      "isFresh": true
                    }
                  ]
                """;

        String htmlTable = HtmlTable.fromJson(json, List.of("boobies"));
        Assertions.assertEquals("""
                <table class="boobies"><thead><tr><th>quantity</th><th>color</th><th>name</th><th>isFresh</th></tr></thead><tbody><tr><td>10</td><td>Red</td><td>Apple</td><td>true</td></tr><tr><td>15</td><td>Yellow</td><td>Banana</td><td>true</td></tr><tr><td>8</td><td>Orange</td><td>Orange</td><td>false</td></tr><tr><td>20</td><td>Purple</td><td>Grapes</td><td>true</td></tr></tbody></table>
                """.trim(), htmlTable.trim());
    }

    @Test
    void testEmptyJson() {
        String emptyJson = "";
        List<String> cssClasses = Arrays.asList("table", "striped");
        assertThrows(RuntimeException.class, () -> HtmlTable.fromJson(emptyJson, cssClasses));
    }

    @Test
    void testInvalidJson() {
        String invalidJson = "This is not a JSON string";
        List<String> cssClasses = Arrays.asList("table", "striped");
        assertThrows(RuntimeException.class, () -> HtmlTable.fromJson(invalidJson, cssClasses));
    }

    @Test
    void testSingleObjectJson() {
        String singleObjectJson = "{\"id\": 1, \"name\": \"John\"}";
        List<String> cssClasses = Arrays.asList("table", "striped");
        String htmlTable = HtmlTable.fromJson(singleObjectJson, cssClasses);
        Assertions.assertNotNull(htmlTable);
        assertTrue(htmlTable.contains("<table"));
        assertTrue(htmlTable.contains("<th>id</th>"));
        assertTrue(htmlTable.contains("<td>1</td>"));
        assertTrue(htmlTable.contains("<th>name</th>"));
        assertTrue(htmlTable.contains("<td>John</td>"));
    }

    @Test
    void testArrayOfObjectsJson() {
        String arrayOfObjectsJson = "[{\"id\": 1, \"name\": \"John\"}, {\"id\": 2, \"name\": \"Alice\"}]";
        List<String> cssClasses = Arrays.asList("table", "striped");
        String htmlTable = HtmlTable.fromJson(arrayOfObjectsJson, cssClasses);
        Assertions.assertNotNull(htmlTable);
        assertTrue(htmlTable.contains("<table"));
        assertTrue(htmlTable.contains("<th>id</th>"));
        assertTrue(htmlTable.contains("<td>1</td>"));
        assertTrue(htmlTable.contains("<td>2</td>"));
        assertTrue(htmlTable.contains("<th>name</th>"));
        assertTrue(htmlTable.contains("<td>John</td>"));
        assertTrue(htmlTable.contains("<td>Alice</td>"));
    }

    @Test
    void testNestedObjectsInJson() {
        String nestedJson = "{\"id\": 1, \"name\": \"John\", \"address\": {\"city\": \"New York\", \"country\": \"USA\"}}";
        List<String> cssClasses = Arrays.asList("table", "striped");
        String htmlTable = HtmlTable.fromJson(nestedJson, cssClasses);
        Assertions.assertNotNull(htmlTable);
        assertTrue(htmlTable.contains("<table"));
        assertTrue(htmlTable.contains("<th>id</th>"));
        assertTrue(htmlTable.contains("<td>1</td>"));
        assertTrue(htmlTable.contains("<th>name</th>"));
        assertTrue(htmlTable.contains("<td>John</td>"));
        assertTrue(htmlTable.contains("<th>address</th>"));
        assertTrue(htmlTable.contains("<td>"));
        assertTrue(htmlTable.contains("<table"));
        assertTrue(htmlTable.contains("<th>city</th>"));
        assertTrue(htmlTable.contains("<td>New York</td>"));
        assertTrue(htmlTable.contains("<th>country</th>"));
        assertTrue(htmlTable.contains("<td>USA</td>"));
    }

    @Test
    void testEmptyArrayJson() {
        String emptyArrayJson = "[]";
        List<String> cssClasses = Arrays.asList("table", "striped");
        String htmlTable = HtmlTable.fromJson(emptyArrayJson, cssClasses);
        Assertions.assertNotNull(htmlTable);
        Assertions.assertEquals("", htmlTable);
    }
}
