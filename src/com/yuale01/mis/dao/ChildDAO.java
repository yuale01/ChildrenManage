package com.yuale01.mis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yuale01.mis.exception.BadRequestException;
import com.yuale01.mis.exception.CommonException;
import com.yuale01.mis.exception.ErrorCode;
import com.yuale01.mis.exception.InternalServerErrorException;
import com.yuale01.mis.manage.ConnectionPoolManager;
import com.yuale01.mis.po.BasicInfo;
import com.yuale01.mis.po.BodyInfo;
import com.yuale01.mis.po.Child;
import com.yuale01.mis.po.ContactInfo;
import com.yuale01.mis.utils.Constants;

public class ChildDAO  implements IChildDAO
{

	@Override
	public Child getChild(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Child> getChildren() throws CommonException 
	{
		ComboPooledDataSource ds = null;
		Connection conn = null;
		Statement stat = null;
		ResultSet result = null;
		List<Child> children = null;
		try 
		{
			String sql = "select * from basic_info baseinfo, body_info bodyinfo, contact_info coninfo where baseinfo.id = bodyinfo.id and bodyinfo.id = coninfo.id;";
			ds = ConnectionPoolManager.getDataSource();
			conn = ds.getConnection();
			stat = conn.createStatement();
			result = stat.executeQuery(sql);
			while (result.next())
			{
				if (children == null)
					children = new ArrayList<Child>();
				Child child = new Child();
				BasicInfo basicInfo = new BasicInfo();
				ContactInfo contactInfo = new ContactInfo();
				BodyInfo bodyInfo = new BodyInfo();
				
				long id = result.getLong(Constants.BASIC_INFO_ID);
				basicInfo.setId(id);
				basicInfo.setName(result.getString(Constants.BASIC_INFO_NAME));
				basicInfo.setGrade(result.getString(Constants.BASIC_INFO_GRADE));
				basicInfo.setClassName(result.getString(Constants.BASIC_INFO_CLASS_NAME));
				basicInfo.setGender(result.getInt(Constants.BASIC_INFO_GENDER));
				basicInfo.setNation(result.getString(Constants.BASIC_INFO_NATION));
				basicInfo.setBirthday(result.getString(Constants.BASIC_INFO_BIRTHDAY));
				basicInfo.setIdCardNo(result.getString(Constants.BASIC_INFO_ID_CARD_NO));
				basicInfo.setHuKou(result.getString(Constants.BASIC_INFO_HUKOU));
				basicInfo.setHuKouAddr(result.getString(Constants.BASIC_INFO_HUKOU_ADDR));
				basicInfo.setMigaration(result.getBoolean(Constants.BASIC_INFO_MIGRATION));
				basicInfo.setOnlyChild(result.getBoolean(Constants.BASIC_INFO_MIGRATION));
				basicInfo.setMinLiving(result.getBoolean(Constants.BASIC_INFO_MIN_LIVING));
				basicInfo.setImburse(result.getBoolean(Constants.BASIC_INFO_IMBURSE));
				basicInfo.setOrphan(result.getBoolean(Constants.BASIC_INFO_ORPHAN));
				basicInfo.setPathography(result.getBoolean(Constants.BASIC_INFO_PATHOGRAPHY));
				basicInfo.setSpecialPerformance(result.getString(Constants.BASIC_INFO_SPECIAL_PERFORMANCE));
				basicInfo.setOtherAnnouncement(result.getString(Constants.BASIC_INFO_OTHER_ANNOUNCEMENT));
				
				contactInfo.setId(id);
				contactInfo.setMotherName(result.getString(Constants.CONTACT_INFO_MOTHER_NAME));
				contactInfo.setMotherCompany(result.getString(Constants.CONTACT_INFO_MOTHER_COMPANY));
				contactInfo.setMotherContact(result.getString(Constants.CONTACT_INFO_MOTHER_CONTACT));
				contactInfo.setMotherIdCard(Constants.CONTACT_INFO_MOTHER_ID_CARD);
				contactInfo.setFatherName(result.getString(Constants.CONTACT_INFO_FATHER_NAME));
				contactInfo.setFatherCompany(result.getString(Constants.CONTACT_INFO_FATHER_COMPANY));
				contactInfo.setFatherContact(result.getString(Constants.CONTACT_INFO_FATHER_CONTACT));
				contactInfo.setFatherIdCard(Constants.CONTACT_INFO_FATHER_ID_CARD);
				contactInfo.setLivingAddr(result.getString(Constants.CONTACT_INFO_LIVING_ADDR));
				contactInfo.setOtherContact(result.getString(Constants.CONTACT_INFO_OTHER_CONTACT));
				
				bodyInfo.setId(id);
				bodyInfo.setDoffDon(result.getInt(Constants.BODY_INFO_DOFF_DON));
				bodyInfo.setEating(result.getInt(Constants.BODY_INFO_EATING));
				bodyInfo.setToileting(result.getInt(Constants.BODY_INFO_TOILETING));
				bodyInfo.setSleeping(result.getInt(Constants.BODY_INFO_SLEEPING));
				bodyInfo.setSleepingInfo(result.getString(Constants.BODY_INFO_SLEEPING_INFO));
				bodyInfo.setEatingSpeed(result.getInt(Constants.BODY_INFO_EATING_SPEED));
				bodyInfo.setAppetite(result.getInt(Constants.BODY_INFO_APPETITE));
				bodyInfo.setPickyEating(result.getInt(Constants.BODY_INFO_PICKY_EATING));
				bodyInfo.setPickyEatingInfo(result.getString(Constants.BODY_INFO_PICKY_EATING_INFO));
				bodyInfo.setEatingAbility(result.getInt(Constants.BODY_INFO_EATING_ABILITY));
				bodyInfo.setFoodAllergy(result.getInt(Constants.BODY_INFO_FOOD_ALLERGY));
				bodyInfo.setFoodAllergyInfo(result.getString(Constants.BODY_INFO_FOOD_ALLERGY_INFO));
				bodyInfo.setHealthStatus(result.getInt(Constants.BODY_INFO_HEALTH_STATUS));
				
				child.setBasicInfo(basicInfo);
				child.setBodyInfo(bodyInfo);
				child.setContactInfo(contactInfo);
				children.add(child);
			}
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalServerErrorException(ErrorCode.error_get_children, e.getMessage());
		}
		finally
		{
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
				}
				stat = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
				conn = null;
			}
		}
		return children;
	}
	
	@Override
	public Child updateChild(Child child)
	{
		return null;
	}
	
	@Override
	public void deleteChildren(Long[] ids) throws CommonException
	{
		ComboPooledDataSource ds = null;
		Connection conn = null;
		PreparedStatement prestat = null;
		List<Long> errorIds = new ArrayList<Long>();
		try
		{
			ds = ConnectionPoolManager.getDataSource();
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			for (Long id : ids)
			{
				try
				{
					String sql = "delete from basic_info where id = ?";
					prestat = conn.prepareStatement(sql);
					prestat.setLong(1, id);
					prestat.executeUpdate();
					prestat.close();
					
					sql = "delete from contact_info where id = ?";
					prestat = conn.prepareStatement(sql);
					prestat.setLong(1, id);
					prestat.executeUpdate();
					prestat.close();
					
					sql = "delete from body_info where id = ?";
					prestat = conn.prepareStatement(sql);
					prestat.setLong(1, id);
					prestat.executeUpdate();
					prestat.close();
					
					conn.commit();
				} catch (SQLException e) {
					try {
						conn.rollback();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					errorIds.add(id);
				}
				
			}
			if (!errorIds.isEmpty())
			{
				StringBuffer sb = new StringBuffer();
				for (Long id : errorIds)
				{
					sb.append(id);
					sb.append(", ");
				}
				throw new InternalServerErrorException(ErrorCode.error_delete_children, sb.substring(0, sb.length()-2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalServerErrorException(ErrorCode.error_delete_children, e.getMessage());
		} 
		finally
		{
			if (prestat != null) {
				try {
					prestat.close();
				} catch (SQLException e) {
				}
				prestat = null;
			}
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
				}
				conn = null;
			}
		} 
	}

	
	@Override
	public Child createChild(Child child) throws CommonException
	{
		long id = Constants.getCurrentLongTime();
		BasicInfo basicInfo = child.getBasicInfo();
		ContactInfo contactInfo = child.getContactInfo();
		BodyInfo bodyInfo = child.getBodyInfo();
		ComboPooledDataSource ds = null;
		Connection conn = null;
		PreparedStatement prestat1 = null;
		PreparedStatement prestat2 = null;
		PreparedStatement prestat3 = null;
		try
		{
			if (basicInfo == null || contactInfo == null || bodyInfo == null)
				throw new BadRequestException(ErrorCode.bad_input_child_body, "lack of child information, cannot create");
			basicInfo.setId(id);
			contactInfo.setId(id);
			bodyInfo.setId(id);
			
			ds = ConnectionPoolManager.getDataSource();
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String basicSql = "insert into basic_info (id, name, grade, class_name, gender, nation, birthday,"
					+ "id_card_no, hukou, hukou_addr, migration, only_child, min_living, imburse, orphan,"
					+ "pathography, special_performance, other_announcement) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			prestat1 = conn.prepareStatement(basicSql);
			prestat1.setLong(1, id);
			prestat1.setString(2, basicInfo.getName());
			prestat1.setString(3, basicInfo.getGrade());
			prestat1.setString(4, basicInfo.getClassName());
			prestat1.setInt(5, basicInfo.getGender());
			prestat1.setString(6, basicInfo.getNation());
			prestat1.setString(7, basicInfo.getBirthday());
			prestat1.setString(8, basicInfo.getIdCardNo());
			prestat1.setString(9, basicInfo.getHuKou());
			prestat1.setString(10, basicInfo.getHuKouAddr());
			prestat1.setBoolean(11, basicInfo.isMigaration());
			prestat1.setBoolean(12, basicInfo.isOnlyChild());
			prestat1.setBoolean(13, basicInfo.isMinLiving());
			prestat1.setBoolean(14, basicInfo.isImburse());
			prestat1.setBoolean(15, basicInfo.isOrphan());
			prestat1.setBoolean(16, basicInfo.isPathography());
			prestat1.setString(17, basicInfo.getSpecialPerformance());
			prestat1.setString(18, basicInfo.getOtherAnnouncement());
			prestat1.executeUpdate();
			
			String contactSql = "insert into contact_info (id, mother_name, mother_company, "
					+ "mother_contact, mother_id_card, father_name, father_company, father_contact, "
					+ "father_id_card, living_addr, other_contact) values (?,?,?,?,?,?,?,?,?,?,?)";
			prestat2 = conn.prepareStatement(contactSql);
			prestat2.setLong(1, id);
			prestat2.setString(2, contactInfo.getMotherName());
			prestat2.setString(3, contactInfo.getMotherCompany());
			prestat2.setString(4, contactInfo.getMotherContact());
			prestat2.setString(5, contactInfo.getMotherIdCard());
			prestat2.setString(6, contactInfo.getFatherName());
			prestat2.setString(7, contactInfo.getFatherCompany());
			prestat2.setString(8, contactInfo.getFatherContact());
			prestat2.setString(9, contactInfo.getFatherIdCard());
			prestat2.setString(10, contactInfo.getLivingAddr());
			prestat2.setString(11, contactInfo.getOtherContact());
			prestat2.executeUpdate();
			
			String bodySql = "insert into body_info (id, doff_don, eating, toileting, sleeping, sleeping_info, "
					+ "eating_speed, appetite, picky_eating, picky_eating_info, eating_ability, food_allergy, "
					+ "food_allergy_info, health_status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			prestat3 = conn.prepareStatement(bodySql);
			prestat3.setLong(1, id);
			prestat3.setInt(2, bodyInfo.getDoffDon());
			prestat3.setInt(3, bodyInfo.getEating());
			prestat3.setInt(4, bodyInfo.getToileting());
			prestat3.setInt(5, bodyInfo.getSleeping());
			prestat3.setString(6, bodyInfo.getSleepingInfo());
			prestat3.setInt(7, bodyInfo.getEatingSpeed());
			prestat3.setInt(8, bodyInfo.getAppetite());
			prestat3.setInt(9, bodyInfo.getPickyEating());
			prestat3.setString(10, bodyInfo.getPickyEatingInfo());
			prestat3.setInt(11, bodyInfo.getEatingAbility());
			prestat3.setInt(12, bodyInfo.getFoodAllergy());
			prestat3.setString(13, bodyInfo.getFoodAllergyInfo());
			prestat3.setInt(14, bodyInfo.getHealthStatus());
			prestat3.executeUpdate();
			
			conn.commit();
			
			return child;
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw new InternalServerErrorException(ErrorCode.error_create_child, e.getMessage());
		}
		finally
		{
			if (prestat1 != null) {
				try {
					prestat1.close();
				} catch (SQLException e) {
				}
				prestat1 = null;
			}
			if (prestat2 != null) {
				try {
					prestat2.close();
				} catch (SQLException e) {
				}
				prestat2 = null;
			}
			if (prestat3 != null) {
				try {
					prestat3.close();
				} catch (SQLException e) {
				}
				prestat3 = null;
			}
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
				}
				conn = null;
			}
		} 
	}

}
