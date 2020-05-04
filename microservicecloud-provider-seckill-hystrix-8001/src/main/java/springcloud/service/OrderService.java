package springcloud.service;



import com.atguigu.springcloud.entities.*;

import java.util.List;

/**
 * @author : liujun
 * @date : ${DATA}
 */
public interface OrderService {
    List<Address> selectAddressByUserAccount(String user_account);
    void reduceMiaoshaGoodsNum(String miaoshagoodsId);
    void insertOrderInfo(OrderDetailInfo orderdetailInfo);
    OrderDetailInfoVo selectAllInfoByOrderNo(String order_no);
    void updateOrder(OrderDetailInfoVo orderInfoVo);
    void insertOrder(Order order);
    void insertOrderANDreduceMiaoshaGoodsNum(String miaoshagoodsId, String user_account);
    Order isRepeatOrder(MiaoShaMessage miaoShaMessage);
    OrderDetailInfo insertOrderdetailInfo(String miaoshaGoodsId, String user_account, String address_id);

    void updateOrderState(MiaoShaMessage miaoShaMessage);

}
