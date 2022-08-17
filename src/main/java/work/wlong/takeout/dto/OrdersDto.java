package work.wlong.takeout.dto;


import lombok.Data;
import work.wlong.takeout.entity.OrderDetail;
import work.wlong.takeout.entity.Orders;

import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
