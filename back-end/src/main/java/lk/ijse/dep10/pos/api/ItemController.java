package lk.ijse.dep10.pos.api;

import lk.ijse.dep10.pos.dto.ItemDTO;
import lk.ijse.dep10.pos.dto.ResponseErrorDTO;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin
public class ItemController {

    @Autowired
    private BasicDataSource pool;
    @GetMapping
    public Object getItems(@RequestParam(value = "q", required = false) String query) {
        if(query==null) query = "";
        try (Connection connection = pool.getConnection()) {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM item WHERE code LIKE ? OR description Like ? OR unitPrice Like ? OR stock Like ? ");
            query = "%" + query + "%";
            for (int i = 1; i <= 4; i++) {
                stm.setString(1, query);

            }
            ResultSet rst = stm.executeQuery();
            List<ItemDTO> itemLists = new ArrayList<>();
            while (rst.next()) {
                int code = rst.getInt("code");
                String description = rst.getString("description");
                String unitPrice = rst.getString("unit-price");
                String stock = rst.getString("stock");

                itemLists.add(new ItemDTO(code, description, unitPrice, stock));

            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Count", itemLists.size() + "");
            return new ResponseEntity<>(itemLists, headers, HttpStatus.OK);


        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseErrorDTO(500, e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveItem(@RequestBody ItemDTO item){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = pool.getConnection()) {
            PreparedStatement stm = connection.prepareStatement
                    ("INSERT INTO item (description, unitPrice, stock) VALUES (?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, item.getDescription());
            stm.setString(2, item.getUnitPrice());
            stm.setString(3, item.getStock());
            stm.executeUpdate();
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int code = generatedKeys.getInt(1);
            item.setCode(code);
            return new ResponseEntity<>(item, HttpStatus.CREATED);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")){
                return new ResponseEntity<>(
                        new ResponseErrorDTO(HttpStatus.CONFLICT.value(),e.getMessage()),
                        HttpStatus.CONFLICT);
            }else{
                return new ResponseEntity<>(
                        new ResponseErrorDTO(500, e.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
