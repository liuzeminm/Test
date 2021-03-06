package cn.dao;

import cn.pojo.Shoppingtrolley;
import cn.pojo.ShoppingtrolleyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IShoppingtrolleyDao {
    int countByExample(ShoppingtrolleyExample example);

    int deleteByExample(ShoppingtrolleyExample example);

    int deleteByPrimaryKey(Integer stid);
    int deleteByPrimaryKeyone(Integer stid,String name);

    int insert(Shoppingtrolley record);

    int insertSelective(Shoppingtrolley record);

    List<Shoppingtrolley> selectByExample(ShoppingtrolleyExample example);

    Shoppingtrolley selectByPrimaryKey(Integer stid);

    int updateByExampleSelective(@Param("record") Shoppingtrolley record, @Param("example") ShoppingtrolleyExample example);

    int updateByExample(@Param("record") Shoppingtrolley record, @Param("example") ShoppingtrolleyExample example);

    int updateByPrimaryKeySelective(Shoppingtrolley record);

    int updateByPrimaryKey(Shoppingtrolley record);

    /**
     * 心愿单加入购物车
     * @param sr
     * @return
     */
    int inserttrolley(@Param("sr")Shoppingtrolley sr);

    /**
     * 是否是同一件商品
     * @param id
     * 商品id或收藏id
     * @return
     */
    int equality(@Param("cid")Integer id);

    /**
     * 数量加一
     * @param commoditycount
     * 商品id或收藏id
     * @return
     */
    int quantity(Integer commoditycount , Integer id);

    /**
     * 商品数量
     * @param id
     * @return
     */
    int count(Integer id);

    /**
     * 购物车全部信息
     * @return
     */
    List<Shoppingtrolley> shoppingshow(Integer userid);

    /**
     * 清空购物车
     * @param userid
     * @return
     */
    int delshopAll(Integer userid);

    /**
     * 删除单条记录
     * @param comid
     * @return
     */
    int delshopping(Integer comid,Integer userid);
}