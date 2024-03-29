package dao.Impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.ScheduleDao;
import dao.SeatDao;
import entity.Table_Schedule;
import entity.Table_Seat;
import util.DBUtil;

public class SeatDaoImpl implements SeatDao{

	@Override
	public int addSeat(Table_Seat seat) {
		int num=0;
		String sql="insert into table_seat (hid,seat_row,seat_column,seat_isactive1,seat_isactive2,seat_isactive3,seat_isactive4)values(?,?,?,?,?,?,?)";
		num=DBUtil.executeupdate(sql, seat.getHid(),seat.getSeat_row(),seat.getSeat_column(),seat.getSeat_isactive1(),seat.getSeat_isactive2(),seat.getSeat_isactive3(),seat.getSeat_isactive4());
		return num;
	}

	@Override
	public int deleteSeat(int seat_id) {
		int num=0;
		String sql="delete from table_seat where seat_id=?";
		num=DBUtil.executeupdate(sql, seat_id);
		return num;
	}

	@Override
	public int updateSeat(int id,int hid,int sid) {
		System.out.println(id+"paipian "+hid+"��λ"+sid);
		int num=0;
		String sql;
		sql="update table_seat set seat_isactive"+id+"=0  where hid="+hid+" and seat_id="+sid+"";
	
		num=DBUtil.executeupdate(sql);
		return num;
	}

	@Override
	public List<Table_Seat> findAll() {
			List<Table_Seat> list=new ArrayList<>();
			Table_Seat seat=null;
			ResultSet rs=null;
			String sql="select * from table_seat";
			try {
				rs=DBUtil.executequery(sql);
				while(rs.next()) {
					seat=new Table_Seat();
					seat.setHid(rs.getInt("hid"));
					seat.setSeat_id(rs.getInt("seat_id"));
					seat.setSeat_row(rs.getInt("seat_row"));
					seat.setSeat_column(rs.getInt("seat_column"));
					seat.setSeat_isactive1(rs.getInt("seat_isactive1"));
					seat.setSeat_isactive2(rs.getInt("seat_isactive2"));
					seat.setSeat_isactive3(rs.getInt("seat_isactive3"));
					seat.setSeat_isactive4(rs.getInt("seat_isactive4"));
					list.add(seat);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				DBUtil.closeAll();
			}
		return list;
	}

	@Override
	public Table_Seat getSeatById(int sid) {
		Table_Seat seat=null;
		ResultSet rs=null;
		String sql="select * from table_seat where seat_id=?";
		try {
			rs=DBUtil.executequery(sql,sid);
			while(rs.next()) {
				seat=new Table_Seat();
				seat.setHid(rs.getInt("hid"));
				seat.setSeat_id(rs.getInt("seat_id"));
				seat.setSeat_row(rs.getInt("seat_row"));
				seat.setSeat_column(rs.getInt("seat_column"));
				seat.setSeat_isactive1(rs.getInt("seat_isactive1"));
				seat.setSeat_isactive2(rs.getInt("seat_isactive2"));
				seat.setSeat_isactive3(rs.getInt("seat_isactive3"));
				seat.setSeat_isactive4(rs.getInt("seat_isactive4"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.closeAll();
		}
	return seat;
	}

	@Override
	public List<Table_Seat> getSeatLikeByColumn(String column, Object value) {
		List<Table_Seat> list=new ArrayList<>();
		Table_Seat seat=null;
		ResultSet rs=null;
		String sql="select * from table_seat where "+column+" like ?";
		try {
			rs=DBUtil.executequery(sql,"%"+value+"%");
			while(rs.next()) {
				seat=new Table_Seat();
				seat.setHid(rs.getInt("hid"));
				seat.setSeat_id(rs.getInt("seat_id"));
				seat.setSeat_row(rs.getInt("seat_row"));
				seat.setSeat_column(rs.getInt("seat_column"));
				seat.setSeat_isactive1(rs.getInt("seat_isactive1"));
				seat.setSeat_isactive2(rs.getInt("seat_isactive2"));
				seat.setSeat_isactive3(rs.getInt("seat_isactive3"));
				seat.setSeat_isactive4(rs.getInt("seat_isactive4"));
				list.add(seat);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.closeAll();
		}
	return list;
	}

}
