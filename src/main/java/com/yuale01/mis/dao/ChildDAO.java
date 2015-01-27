package com.yuale01.mis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yuale01.mis.controller.ConnectionPoolManager;
import com.yuale01.mis.exception.BadRequestException;
import com.yuale01.mis.exception.CommonException;
import com.yuale01.mis.exception.ConflictException;
import com.yuale01.mis.exception.ErrorCode;
import com.yuale01.mis.exception.InternalServerErrorException;
import com.yuale01.mis.exception.NotFoundException;
import com.yuale01.mis.po.BasicInfo;
import com.yuale01.mis.po.BodyInfo;
import com.yuale01.mis.po.Child;
import com.yuale01.mis.po.ContactInfo;
import com.yuale01.mis.utils.Constants;
import com.yuale01.mis.utils.Tools;

public class ChildDAO  implements IChildDAO
{

	@Override
	public Child getChild(Long id) throws CommonException
	{
		// TODO Auto-generated method stub
		ComboPooledDataSource ds = null;
		Connection conn = null;
		PreparedStatement prestat = null;
		ResultSet result = null;
		Child child = null;
		try 
		{
			String sql = "select * from basic_info baseinfo, body_info bodyinfo, contact_info coninfo where baseinfo.id = ? and bodyinfo.id = ? and coninfo.id = ?;";
			ds = ConnectionPoolManager.getDataSource();
			conn = ds.getConnection();
			prestat = conn.prepareStatement(sql);
			prestat.setLong(1, id);
			prestat.setLong(2, id);
			prestat.setLong(3, id);
			
			result = prestat.executeQuery();
			
			while (result.next())
			{
				child = new Child();
				BasicInfo basicInfo = new BasicInfo();
				ContactInfo contactInfo = new ContactInfo();
				BodyInfo bodyInfo = new BodyInfo();
				
				basicInfo.setId(id);
				basicInfo.setName(result.getString(Constants.BASIC_INFO_NAME));
				basicInfo.setGrade(result.getString(Constants.BASIC_INFO_GRADE));
				basicInfo.setClassName(result.getString(Constants.BASIC_INFO_CLASS_NAME));
				basicInfo.setGender(result.getInt(Constants.BASIC_INFO_GENDER));
				basicInfo.setNation(result.getString(Constants.BASIC_INFO_NATION));
				basicInfo.setBirthday(Tools.convertLongTimeToStr(result.getLong(Constants.BASIC_INFO_BIRTHDAY)));
				basicInfo.setIdCardNo(result.getString(Constants.BASIC_INFO_ID_CARD_NO));
				basicInfo.setHuKou(result.getInt(Constants.BASIC_INFO_HUKOU));
				basicInfo.setHuKouAddr(result.getString(Constants.BASIC_INFO_HUKOU_ADDR));
				basicInfo.setMigration(result.getBoolean(Constants.BASIC_INFO_MIGRATION));
				basicInfo.setOnlyChild(result.getBoolean(Constants.BASIC_INFO_MIGRATION));
				basicInfo.setMinLiving(result.getBoolean(Constants.BASIC_INFO_MIN_LIVING));
				basicInfo.setImburse(result.getBoolean(Constants.BASIC_INFO_IMBURSE));
				basicInfo.setOrphan(result.getBoolean(Constants.BASIC_INFO_ORPHAN));
				basicInfo.setPathography(result.getBoolean(Constants.BASIC_INFO_PATHOGRAPHY));
				basicInfo.setSpecialPerformance(result.getString(Constants.BASIC_INFO_SPECIAL_PERFORMANCE));
				basicInfo.setOtherAnnouncement(result.getString(Constants.BASIC_INFO_OTHER_ANNOUNCEMENT));
				basicInfo.setTimeStamp(result.getLong(Constants.BASIC_INFO_TIMESTAMP));
				
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
				contactInfo.setTimeStamp(result.getLong(Constants.CONTACT_INFO_TIMESTAMP));
				
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
				bodyInfo.setTimeStamp(result.getLong(Constants.BODY_INFO_TIMESTAMP));
				
				child.setBasicInfo(basicInfo);
				child.setBodyInfo(bodyInfo);
				child.setContactInfo(contactInfo);
				child.setId(id);
			}
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalServerErrorException(ErrorCode.error_child_get, e.getMessage());
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
					conn.close();
				} catch (SQLException e) {
				}
				conn = null;
			}
		}
		
		if (child == null)
			throw new NotFoundException(ErrorCode.error_child_common_invalidid, "Child: " + id + " Not Exist.");
		
		return child;
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
				basicInfo.setBirthday(Tools.convertLongTimeToStr(result.getLong(Constants.BASIC_INFO_BIRTHDAY)));
				basicInfo.setIdCardNo(result.getString(Constants.BASIC_INFO_ID_CARD_NO));
				basicInfo.setHuKou(result.getInt(Constants.BASIC_INFO_HUKOU));
				basicInfo.setHuKouAddr(result.getString(Constants.BASIC_INFO_HUKOU_ADDR));
				basicInfo.setMigration(result.getBoolean(Constants.BASIC_INFO_MIGRATION));
				basicInfo.setOnlyChild(result.getBoolean(Constants.BASIC_INFO_MIGRATION));
				basicInfo.setMinLiving(result.getBoolean(Constants.BASIC_INFO_MIN_LIVING));
				basicInfo.setImburse(result.getBoolean(Constants.BASIC_INFO_IMBURSE));
				basicInfo.setOrphan(result.getBoolean(Constants.BASIC_INFO_ORPHAN));
				basicInfo.setPathography(result.getBoolean(Constants.BASIC_INFO_PATHOGRAPHY));
				basicInfo.setSpecialPerformance(result.getString(Constants.BASIC_INFO_SPECIAL_PERFORMANCE));
				basicInfo.setOtherAnnouncement(result.getString(Constants.BASIC_INFO_OTHER_ANNOUNCEMENT));
				basicInfo.setTimeStamp(result.getLong(Constants.BASIC_INFO_TIMESTAMP));
				
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
				contactInfo.setTimeStamp(result.getLong(Constants.CONTACT_INFO_TIMESTAMP));
				
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
				bodyInfo.setTimeStamp(result.getLong(Constants.BODY_INFO_TIMESTAMP));
				
				child.setBasicInfo(basicInfo);
				child.setBodyInfo(bodyInfo);
				child.setContactInfo(contactInfo);
				child.setId(id);
				children.add(child);
			}
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalServerErrorException(ErrorCode.error_children_get, e.getMessage());
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
	public Child updateChild(Child child, Long id) throws CommonException
	{
		if (child == null)
			throw new BadRequestException(ErrorCode.error_child_update_badinput, "child information isempty, cannot update");
		
		BasicInfo basicInfo = child.getBasicInfo();
		ContactInfo contactInfo = child.getContactInfo();
		BodyInfo bodyInfo = child.getBodyInfo();
		
		if ((basicInfo == null && contactInfo == null && bodyInfo == null) || id == null)
			throw new BadRequestException(ErrorCode.error_child_update_badinput, "lack of child information, cannot update");
		
		Child originChild = getChild(id);
		
		ComboPooledDataSource ds = null;
		Connection conn = null;
		Statement stat = null;
		try
		{
			ds = ConnectionPoolManager.getDataSource();
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			if (basicInfo != null)
			{
				if (basicInfo.getTimeStamp() == null || !basicInfo.getTimeStamp().equals(originChild.getBasicInfo().getTimeStamp()))
					throw new ConflictException(ErrorCode.error_child_update_conflict, "basic info is not the latest.");
				
				StringBuffer sqlBuffer = new StringBuffer("update basic_info set ");
				if (basicInfo.getIdCardNo() != null)
					sqlBuffer.append(Constants.BASIC_INFO_ID_CARD_NO).append("='").append(basicInfo.getIdCardNo()).append("', ");
				if (basicInfo.getName() != null)
					sqlBuffer.append(Constants.BASIC_INFO_NAME).append("='").append(basicInfo.getName()).append("', ");
				if (basicInfo.getGrade() != null)
					sqlBuffer.append(Constants.BASIC_INFO_GRADE).append("='").append(basicInfo.getGrade()).append("', ");
				if (basicInfo.getClassName() != null)
					sqlBuffer.append(Constants.BASIC_INFO_CLASS_NAME).append("='").append(basicInfo.getClassName()).append("', ");
				if (basicInfo.getGender() != null)
					sqlBuffer.append(Constants.BASIC_INFO_GENDER).append("=").append(basicInfo.getGender()).append(", ");
				if (basicInfo.getNation() != null)
					sqlBuffer.append(Constants.BASIC_INFO_NATION).append("='").append(basicInfo.getNation()).append("', ");
				if (basicInfo.getBirthday() != null)
				{
					Long time = Tools.parseTimeFromStr(basicInfo.getBirthday());
					if (time == null)
						throw new BadRequestException(ErrorCode.error_child_update_badinput, "Birthday is not recognized.");
					sqlBuffer.append(Constants.BASIC_INFO_BIRTHDAY).append("='").append(time).append("', ");
				}
				if (basicInfo.getHuKou() != null)
					sqlBuffer.append(Constants.BASIC_INFO_HUKOU).append("='").append(basicInfo.getHuKou()).append("', ");
				if (basicInfo.getHuKouAddr() != null)
					sqlBuffer.append(Constants.BASIC_INFO_HUKOU_ADDR).append("='").append(basicInfo.getHuKouAddr()).append("', ");
				if (basicInfo.getMigration() != null)
					sqlBuffer.append(Constants.BASIC_INFO_MIGRATION).append("=").append(basicInfo.getMigration()).append(", ");
				if (basicInfo.getOnlyChild() != null)
					sqlBuffer.append(Constants.BASIC_INFO_ONLY_CHILD).append("=").append(basicInfo.getOnlyChild()).append(", ");
				if (basicInfo.getMinLiving() != null)
					sqlBuffer.append(Constants.BASIC_INFO_MIN_LIVING).append("=").append(basicInfo.getMinLiving()).append(", ");
				if (basicInfo.getImburse() != null)
					sqlBuffer.append(Constants.BASIC_INFO_IMBURSE).append("=").append(basicInfo.getImburse()).append(", ");
				if (basicInfo.getOrphan() != null)
					sqlBuffer.append(Constants.BASIC_INFO_ORPHAN).append("=").append(basicInfo.getOrphan()).append(", ");
				if (basicInfo.getPathography() != null)
					sqlBuffer.append(Constants.BASIC_INFO_PATHOGRAPHY).append("=").append(basicInfo.getPathography()).append(", ");
				if (basicInfo.getSpecialPerformance() != null)
					sqlBuffer.append(Constants.BASIC_INFO_SPECIAL_PERFORMANCE).append("='").append(basicInfo.getSpecialPerformance()).append("', ");
				if (basicInfo.getOtherAnnouncement() != null)
					sqlBuffer.append(Constants.BASIC_INFO_OTHER_ANNOUNCEMENT).append("='").append(basicInfo.getOtherAnnouncement()).append("', ");
				
				if (sqlBuffer.length() != "update basic_info set ".length())
				{
					sqlBuffer.append(Constants.BASIC_INFO_TIMESTAMP).append("=").append(System.currentTimeMillis());
					sqlBuffer.append(" where id = ").append(id);
					stat = conn.createStatement();
					stat.executeUpdate(sqlBuffer.toString());
					stat.close();
				}
			}
			
			if (contactInfo != null)
			{
				if (contactInfo.getTimeStamp() == null || !contactInfo.getTimeStamp().equals(originChild.getContactInfo().getTimeStamp()))
					throw new ConflictException(ErrorCode.error_child_update_conflict, "contact info is not the latest.");
				
				StringBuffer sqlBuffer = new StringBuffer("update contact_info set ");
				if (contactInfo.getMotherName() != null)
					sqlBuffer.append(Constants.CONTACT_INFO_MOTHER_NAME).append("='").append(contactInfo.getMotherName()).append("', ");
				if (contactInfo.getMotherCompany() != null)
					sqlBuffer.append(Constants.CONTACT_INFO_MOTHER_COMPANY).append("='").append(contactInfo.getMotherCompany()).append("', ");
				if (contactInfo.getMotherContact() != null)
					sqlBuffer.append(Constants.CONTACT_INFO_MOTHER_CONTACT).append("='").append(contactInfo.getMotherContact()).append("', ");
				if (contactInfo.getMotherIdCard() != null)
					sqlBuffer.append(Constants.CONTACT_INFO_MOTHER_ID_CARD).append("='").append(contactInfo.getMotherIdCard()).append("', ");
				if (contactInfo.getFatherName() != null)
					sqlBuffer.append(Constants.CONTACT_INFO_FATHER_NAME).append("='").append(contactInfo.getFatherName()).append("', ");
				if (contactInfo.getFatherCompany() != null)
					sqlBuffer.append(Constants.CONTACT_INFO_FATHER_COMPANY).append("='").append(contactInfo.getFatherCompany()).append("', ");
				if (contactInfo.getFatherContact() != null)
					sqlBuffer.append(Constants.CONTACT_INFO_FATHER_CONTACT).append("='").append(contactInfo.getFatherContact()).append("', ");
				if (contactInfo.getFatherContact() != null)
					sqlBuffer.append(Constants.CONTACT_INFO_FATHER_ID_CARD).append("='").append(contactInfo.getFatherIdCard()).append("', ");
				if (contactInfo.getLivingAddr() != null)
					sqlBuffer.append(Constants.CONTACT_INFO_LIVING_ADDR).append("='").append(contactInfo.getLivingAddr()).append("', ");
				if (contactInfo.getOtherContact() != null)
					sqlBuffer.append(Constants.CONTACT_INFO_OTHER_CONTACT).append("='").append(contactInfo.getOtherContact()).append("', ");
				
				if (sqlBuffer.length() != "update contact_info set ".length())
				{
					sqlBuffer.append(Constants.CONTACT_INFO_TIMESTAMP).append("=").append(System.currentTimeMillis());
					sqlBuffer.append(" where id = ").append(id);
					stat = conn.createStatement();
					stat.executeUpdate(sqlBuffer.toString());
					stat.close();
				}
			}
			
			if (bodyInfo != null)
			{
				if (bodyInfo.getTimeStamp() == null || !bodyInfo.getTimeStamp().equals(originChild.getBodyInfo().getTimeStamp()))
					throw new ConflictException(ErrorCode.error_child_update_conflict, "body info is not the latest.");
				
				StringBuffer sqlBuffer = new StringBuffer("update body_info set ");
				if (bodyInfo.getDoffDon() != null)
					sqlBuffer.append(Constants.BODY_INFO_DOFF_DON).append("=").append(bodyInfo.getDoffDon()).append(", ");
				if (bodyInfo.getEating() != null)
					sqlBuffer.append(Constants.BODY_INFO_EATING).append("=").append(bodyInfo.getEating()).append(", ");
				if (bodyInfo.getToileting() != null)
					sqlBuffer.append(Constants.BODY_INFO_TOILETING).append("=").append(bodyInfo.getEating()).append(", ");
				if (bodyInfo.getSleeping() != null)
					sqlBuffer.append(Constants.BODY_INFO_SLEEPING).append("=").append(bodyInfo.getSleeping()).append(", ");
				if (bodyInfo.getSleepingInfo() != null)
					sqlBuffer.append(Constants.BODY_INFO_SLEEPING_INFO).append("='").append(bodyInfo.getSleepingInfo()).append("', ");
				if (bodyInfo.getEatingSpeed() != null)
					sqlBuffer.append(Constants.BODY_INFO_EATING_SPEED).append("=").append(bodyInfo.getEatingSpeed()).append(", ");
				if (bodyInfo.getAppetite() != null)
					sqlBuffer.append(Constants.BODY_INFO_APPETITE).append("=").append(bodyInfo.getAppetite()).append(", ");
				if (bodyInfo.getPickyEating() != null)
					sqlBuffer.append(Constants.BODY_INFO_PICKY_EATING).append("=").append(bodyInfo.getPickyEating()).append(", ");
				if (bodyInfo.getPickyEatingInfo() != null)
					sqlBuffer.append(Constants.BODY_INFO_PICKY_EATING_INFO).append("='").append(bodyInfo.getPickyEatingInfo()).append("', ");
				if (bodyInfo.getEatingAbility() != null)
					sqlBuffer.append(Constants.BODY_INFO_EATING_ABILITY).append("=").append(bodyInfo.getEatingAbility()).append(", ");
				if (bodyInfo.getFoodAllergy() != null)
					sqlBuffer.append(Constants.BODY_INFO_FOOD_ALLERGY).append("=").append(bodyInfo.getFoodAllergy()).append(", ");
				if (bodyInfo.getFoodAllergyInfo() != null)
					sqlBuffer.append(Constants.BODY_INFO_FOOD_ALLERGY_INFO).append("='").append(bodyInfo.getFoodAllergyInfo()).append("', ");
				if (bodyInfo.getHealthStatus() != null)
					sqlBuffer.append(Constants.BODY_INFO_HEALTH_STATUS).append("=").append(bodyInfo.getHealthStatus()).append(", ");
				
				if (sqlBuffer.length() != "update body_info set ".length())
				{
					sqlBuffer.append(Constants.BODY_INFO_TIMESTAMP).append("=").append(System.currentTimeMillis());
					sqlBuffer.append(" where id = ").append(id);
					stat = conn.createStatement();
					stat.executeUpdate(sqlBuffer.toString());
					stat.close();
				}
			}
			
			conn.commit();
			return null;
			
		} catch (Exception e)
		{
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			if (e instanceof CommonException)
				throw (CommonException)e;
			else
				throw new InternalServerErrorException(ErrorCode.error_child_update, e.getMessage());
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
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
				}
				conn = null;
			}
		} 
	}
	
	@Override
	public void deleteChildren(Long[] ids) throws CommonException
	{
		if (ids == null)
			throw new BadRequestException(ErrorCode.error_children_delete_idsnull, "Children IDs are null.");
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
				} catch (Exception e) {
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
				throw new InternalServerErrorException(ErrorCode.error_children_delete, StringUtils.join(errorIds, ","));
			}

		}  catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CommonException)
				throw (CommonException)e;
			else
				throw new InternalServerErrorException(ErrorCode.error_children_delete, e.getMessage());
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
		long id = Tools.getCurrentLongTime();
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
			if (basicInfo == null)
				throw new BadRequestException(ErrorCode.error_child_create_badinput, "lack of child information, cannot create");
			if (contactInfo == null)
				contactInfo = new ContactInfo();
			if (bodyInfo == null)
				bodyInfo = new BodyInfo();
			basicInfo.setId(id);
			contactInfo.setId(id);
			bodyInfo.setId(id);
			
			ds = ConnectionPoolManager.getDataSource();
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String basicSql = "insert into basic_info (id, name, grade, class_name, gender, nation, birthday,"
					+ "id_card_no, hukou, hukou_addr, migration, only_child, min_living, imburse, orphan,"
					+ "pathography, special_performance, other_announcement, timestamp) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			prestat1 = conn.prepareStatement(basicSql);
			prestat1.setLong(1, id);
			prestat1.setString(2, basicInfo.getName());
			prestat1.setString(3, basicInfo.getGrade());
			prestat1.setString(4, basicInfo.getClassName());
			prestat1.setInt(5, basicInfo.getGender() == null ? 0 : basicInfo.getGender());
			prestat1.setString(6, basicInfo.getNation());
			Long time = Tools.parseTimeFromStr(basicInfo.getBirthday());
			if (time == null)
				throw new BadRequestException(ErrorCode.error_child_create_badinput, "Birthday is not recognized.");
			prestat1.setLong(7, time);
			prestat1.setString(8, basicInfo.getIdCardNo());
			prestat1.setInt(9, basicInfo.getHuKou());
			prestat1.setString(10, basicInfo.getHuKouAddr());
			prestat1.setBoolean(11, basicInfo.getMigration() == null ? false : basicInfo.getMigration());
			prestat1.setBoolean(12, basicInfo.getOnlyChild() == null ? false : basicInfo.getOnlyChild());
			prestat1.setBoolean(13, basicInfo.getMinLiving() == null ? false : basicInfo.getMinLiving());
			prestat1.setBoolean(14, basicInfo.getImburse() == null ? false : basicInfo.getImburse());
			prestat1.setBoolean(15, basicInfo.getOrphan() == null ? false : basicInfo.getOrphan());
			prestat1.setBoolean(16, basicInfo.getPathography() == null ? false : basicInfo.getPathography());
			prestat1.setString(17, basicInfo.getSpecialPerformance());
			prestat1.setString(18, basicInfo.getOtherAnnouncement());
			prestat1.setLong(19, System.currentTimeMillis());
			prestat1.executeUpdate();
			
			String contactSql = "insert into contact_info (id, mother_name, mother_company, "
					+ "mother_contact, mother_id_card, father_name, father_company, father_contact, "
					+ "father_id_card, living_addr, other_contact, timestamp) values (?,?,?,?,?,?,?,?,?,?,?,?)";
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
			prestat2.setLong(12, System.currentTimeMillis());
			prestat2.executeUpdate();
			
			String bodySql = "insert into body_info (id, doff_don, eating, toileting, sleeping, sleeping_info, "
					+ "eating_speed, appetite, picky_eating, picky_eating_info, eating_ability, food_allergy, "
					+ "food_allergy_info, health_status, timestamp) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			prestat3 = conn.prepareStatement(bodySql);
			prestat3.setLong(1, id);
			prestat3.setInt(2, bodyInfo.getDoffDon() == null ? 0 : bodyInfo.getDoffDon());
			prestat3.setInt(3, bodyInfo.getEating() == null ? 0 : bodyInfo.getEating());
			prestat3.setInt(4, bodyInfo.getToileting() == null ? 0 : bodyInfo.getToileting());
			prestat3.setInt(5, bodyInfo.getSleeping() == null ? 0 : bodyInfo.getSleeping());
			prestat3.setString(6, bodyInfo.getSleepingInfo());
			prestat3.setInt(7, bodyInfo.getEatingSpeed() == null ? 0 : bodyInfo.getEatingSpeed());
			prestat3.setInt(8, bodyInfo.getAppetite() == null ? 0 : bodyInfo.getAppetite());
			prestat3.setInt(9, bodyInfo.getPickyEating() == null ? 0 : bodyInfo.getPickyEating());
			prestat3.setString(10, bodyInfo.getPickyEatingInfo());
			prestat3.setInt(11, bodyInfo.getEatingAbility() == null ? 0 : bodyInfo.getEatingAbility());
			prestat3.setInt(12, bodyInfo.getFoodAllergy() == null ? 0 : bodyInfo.getFoodAllergy());
			prestat3.setString(13, bodyInfo.getFoodAllergyInfo());
			prestat3.setInt(14, bodyInfo.getHealthStatus() == null ? 0 : bodyInfo.getHealthStatus());
			prestat3.setLong(15, System.currentTimeMillis());
			prestat3.executeUpdate();
			
			conn.commit();
			
			return child;
		} catch (Exception e)
		{
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			if (e instanceof CommonException)
				throw (CommonException)e;
			else
				throw new InternalServerErrorException(ErrorCode.error_child_create, e.getMessage());
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

	@Override
	public void deleteChild(Long id) throws CommonException 
	{
		
		if (id == null)
			throw new BadRequestException(ErrorCode.error_child_delete_idnull, "Child id is null.");
		ComboPooledDataSource ds = null;
		Connection conn = null;
		PreparedStatement prestat = null;
		try
		{
			ds = ConnectionPoolManager.getDataSource();
			conn = ds.getConnection();
			conn.setAutoCommit(false);
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

		}  catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (e instanceof CommonException)
				throw (CommonException)e;
			else
				throw new InternalServerErrorException(ErrorCode.error_child_delete, e.getMessage());
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

}
