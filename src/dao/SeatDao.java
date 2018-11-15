package dao;

import java.util.List;


import entity.Table_Seat;

public interface SeatDao {
	/**
	 * ������λ
	 * @param seat
	 * @return
	 */
	public int addSeat(Table_Seat seat);
	/**
	 * ���ݴ����idɾ��ָ������λ
	 * 
	 * @param lid �����Ҫɾ������λid
	 * @return ����sql������������
	 */
	public int deleteSeat(int sid);

	/**
	 * ���ݴ����id�޸�ָ������λ��Ϣ
	 * 
	 * @param level �����޸Ķ���
	 * @param lid   ����Ҫ�޸ĵ���λid
	 * @return ����sql������������
	 */
	public int updateSeat(int id,int hid,int sid);

	/**
	 * ����������λ
	 * 
	 * @return ���ز��ҵ���λ���󼯺�
	 */
	public List<Table_Seat> findAll();

	/**
	 * ���ݴ����Ӱ��id���Ҷ�Ӧ����λ����
	 * 
	 * @param lid ����Ҫ���ҵ�id
	 * @return ���ز��ҵ�����λ����
	 */
	public Table_Seat getSeatById(int sid);

	/**
	 * ģ����ѯ
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public List<Table_Seat> getSeatLikeByColumn(String column, Object value);
	
}