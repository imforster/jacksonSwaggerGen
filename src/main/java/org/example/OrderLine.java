package org.example;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {
  private String item;
  private int quantity;
  private BigDecimal unitPrice;
}