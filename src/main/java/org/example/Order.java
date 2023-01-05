package org.example;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private String orderNo;
  private LocalDate date;
  private String customerName;
  private List<OrderLine> orderLines;
}
