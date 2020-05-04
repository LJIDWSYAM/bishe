package springcloud.dao;

import com.atguigu.springcloud.entities.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: liujun
 * \* Date: 2019/6/24
 * \* Time: 8:47
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Repository
@Mapper
public interface OrderDao {
    List<Address> selectAddressByUserAccount(String user_account);
    void reduceMiaoshaGoodsNum(String miaoshagoods_id);
    void insertOrderInfo(OrderDetailInfo orderdetailInfo);
    OrderDetailInfoVo selectAllInfoByOrderNo(String order_no);
    void updateOrder(OrderDetailInfoVo orderInfoVo);
    void insertOrder(Order order);
    Order isRepeatOrder(MiaoShaMessage miaoShaMessage);
    void updateOrderState(MiaoShaMessage miaoShaMessage);

}