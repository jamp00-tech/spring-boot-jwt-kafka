package cl.ey.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
            Object data) {

        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("date", LocalDate.now());

        if (data != null) 
            map.put("data", data);

        return new ResponseEntity<>(map, status);
    }

}
