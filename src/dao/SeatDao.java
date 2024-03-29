package dao;

import java.util.List;


import entity.Table_Seat;

public interface SeatDao {
	/**
	 * 添加座位
	 * @param seat
	 * @return
	 */
	public int addSeat(Table_Seat seat);
	/**
	 * 根据传入的id删除指定的座位
	 * 
	 * @param lid 传入的要删除的座位id
	 * @return 返回sql语句操作的行数
	 */
	public int deleteSeat(int sid);

	/**
	 * 根据传入的id修改指定的座位信息
	 * 
	 * @param level 传入修改对象
	 * @param lid   传入要修改的座位id
	 * @return 返回sql语句操作的行数
	 */
	public int updateSeat(int id,int hid,int sid);

	/**
	 * 查找所有座位
	 * 
	 * @return 返回查找的座位对象集合
	 */
	public List<Table_Seat> findAll();

	/**
	 * 根据传入的影厅id查找对应的座位对象
	 * 
	 * @param lid 传入要查找的id
	 * @return 返回查找到的座位对象
	 */
	public Table_Seat getSeatById(int sid);

	/**
	 * 模糊查询
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public List<Table_Seat> getSeatLikeByColumn(String column, Object value);
	
}
