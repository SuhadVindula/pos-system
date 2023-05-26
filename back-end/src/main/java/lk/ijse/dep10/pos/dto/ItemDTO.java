package lk.ijse.dep10.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Integer code;
    private String description;
    private String unitPrice;
    private String stock;
}