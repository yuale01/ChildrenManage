package com.yuale01.mis.controller;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.WorkbookUtil;

import com.yuale01.mis.dao.IChildDAO;
import com.yuale01.mis.exception.CommonException;
import com.yuale01.mis.po.Child;
import com.yuale01.mis.utils.Constants;
import com.yuale01.mis.utils.Tools;

public class ChildrenController {
    
    private IChildDAO childDAO = DAOFactory.getChildDAO();
    
    public void exportChildren(String locale) throws CommonException {
        // ResourceBundle bundle = ResourceBundle.getBundle("i18n/resources",
        // locale);
        
        Workbook wb = new HSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        String safeName = WorkbookUtil.createSafeSheetName("[Children Information*?]");
        Sheet sheet = wb.createSheet(safeName);
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setLocked(true);
        addHeader(sheet, locale, createHelper, style);
        
        try {
            FileOutputStream fileOut = new FileOutputStream("workbookPOI.xls");
            wb.write(fileOut);
            wb.close();
            fileOut.close();
        }
        catch (Exception e) {

        }

    }
    
    private void addHeader(Sheet sheet, String locale, CreationHelper helper, CellStyle style)
    {
        int validateRowLength = 200;
        // Create header
        Properties props = Tools.loadProperties(locale);
        Row row = sheet.createRow((short) 0);
        
        row.createCell(Constants.BASIC_INFO_NAME_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_NAME_cellI18NKey)));
        row.createCell(Constants.BASIC_INFO_GRADE_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_GRADE_cellI18NKey)));
        addDataValidation(sheet, Constants.GradeTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BASIC_INFO_GRADE_cellIndex, Constants.BASIC_INFO_GRADE_cellIndex));
        row.createCell(Constants.BASIC_INFO_CLASS_NAME_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_CLASS_NAME_cellI18NKey)));
        addDataValidation(sheet, Constants.ClassTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BASIC_INFO_CLASS_NAME_cellIndex, Constants.BASIC_INFO_CLASS_NAME_cellIndex));
        row.createCell(Constants.BASIC_INFO_GENDER_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_GENDER_cellI18NKey)));
        addDataValidation(sheet, Constants.GenderTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BASIC_INFO_GENDER_cellIndex, Constants.BASIC_INFO_GENDER_cellIndex));
        row.createCell(Constants.BASIC_INFO_NATION_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_NATION_cellI18NKey)));
        row.createCell(Constants.BASIC_INFO_BIRTHDAY_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_BIRTHDAY_cellI18NKey)));
        row.createCell(Constants.BASIC_INFO_ID_CARD_NO_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_ID_CARD_NO_cellI18NKey)));
        row.createCell(Constants.BASIC_INFO_HUKOU_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_HUKOU_cellI18NKey)));
        addDataValidation(sheet, Constants.HuKouTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BASIC_INFO_HUKOU_cellIndex, Constants.BASIC_INFO_HUKOU_cellIndex));
        row.createCell(Constants.BASIC_INFO_HUKOU_ADDR_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_HUKOU_ADDR_cellI18NKey)));
        row.createCell(Constants.BASIC_INFO_MIGRATION_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_MIGRATION_cellI18NKey)));
        addDataValidation(sheet, Constants.YesNoTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BASIC_INFO_MIGRATION_cellIndex, Constants.BASIC_INFO_MIGRATION_cellIndex));
        row.createCell(Constants.BASIC_INFO_ONLY_CHILD_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_ONLY_CHILD_cellI18NKey)));
        addDataValidation(sheet, Constants.YesNoTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BASIC_INFO_ONLY_CHILD_cellIndex, Constants.BASIC_INFO_ONLY_CHILD_cellIndex));
        row.createCell(Constants.BASIC_INFO_MIN_LIVING_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_MIN_LIVING_cellI18NKey)));
        addDataValidation(sheet, Constants.YesNoTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BASIC_INFO_MIN_LIVING_cellIndex, Constants.BASIC_INFO_MIN_LIVING_cellIndex));
        row.createCell(Constants.BASIC_INFO_IMBURSE_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_IMBURSE_cellI18NKey)));
        addDataValidation(sheet, Constants.YesNoTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BASIC_INFO_IMBURSE_cellIndex, Constants.BASIC_INFO_IMBURSE_cellIndex));
        row.createCell(Constants.BASIC_INFO_ORPHAN_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_ORPHAN_cellI18NKey)));
        addDataValidation(sheet, Constants.YesNoTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BASIC_INFO_ORPHAN_cellIndex, Constants.BASIC_INFO_ORPHAN_cellIndex));
        row.createCell(Constants.BASIC_INFO_PATHOGRAPHY_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_PATHOGRAPHY_cellI18NKey)));
        addDataValidation(sheet, Constants.YesNoTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BASIC_INFO_PATHOGRAPHY_cellIndex, Constants.BASIC_INFO_PATHOGRAPHY_cellIndex));
        row.createCell(Constants.BASIC_INFO_SPECIAL_PERFORMANCE_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_SPECIAL_PERFORMANCE_cellI18NKey)));
        row.createCell(Constants.BASIC_INFO_OTHER_ANNOUNCEMENT_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BASIC_INFO_OTHER_ANNOUNCEMENT_cellI18NKey)));
        row.createCell(Constants.CONTACT_INFO_MOTHER_NAME_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.CONTACT_INFO_MOTHER_NAME_cellI18NKey)));
        row.createCell(Constants.CONTACT_INFO_MOTHER_COMPANY_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.CONTACT_INFO_MOTHER_COMPANY_cellI18NKey)));
        row.createCell(Constants.CONTACT_INFO_MOTHER_CONTACT_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.CONTACT_INFO_MOTHER_CONTACT_cellI18NKey)));
        row.createCell(Constants.CONTACT_INFO_MOTHER_ID_CARD_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.CONTACT_INFO_MOTHER_ID_CARD_cellI18NKey)));
        row.createCell(Constants.CONTACT_INFO_FATHER_NAME_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.CONTACT_INFO_FATHER_NAME_cellI18NKey)));
        row.createCell(Constants.CONTACT_INFO_FATHER_COMPANY_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.CONTACT_INFO_FATHER_COMPANY_cellI18NKey)));
        row.createCell(Constants.CONTACT_INFO_FATHER_CONTACT_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.CONTACT_INFO_FATHER_CONTACT_cellI18NKey)));
        row.createCell(Constants.CONTACT_INFO_FATHER_ID_CARD_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.CONTACT_INFO_FATHER_ID_CARD_cellI18NKey)));
        row.createCell(Constants.CONTACT_INFO_LIVING_ADDR_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.CONTACT_INFO_LIVING_ADDR_cellI18NKey)));
        row.createCell(Constants.CONTACT_INFO_OTHER_CONTACT_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.CONTACT_INFO_OTHER_CONTACT_cellI18NKey)));
        row.createCell(Constants.BODY_INFO_DOFF_DON_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_DOFF_DON_cellI18NKey)));
        addDataValidation(sheet, Constants.DoffDonTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BODY_INFO_DOFF_DON_cellIndex, Constants.BODY_INFO_DOFF_DON_cellIndex));
        row.createCell(Constants.BODY_INFO_EATING_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_EATING_cellI18NKey)));
        addDataValidation(sheet, Constants.EatingTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BODY_INFO_EATING_cellIndex, Constants.BODY_INFO_EATING_cellIndex));
        row.createCell(Constants.BODY_INFO_TOILETING_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_TOILETING_cellI18NKey)));
        addDataValidation(sheet, Constants.ToiletingTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BODY_INFO_TOILETING_cellIndex, Constants.BODY_INFO_TOILETING_cellIndex));
        row.createCell(Constants.BODY_INFO_SLEEPING_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_SLEEPING_cellI18NKey)));
        addDataValidation(sheet, Constants.SleepingTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BODY_INFO_SLEEPING_cellIndex, Constants.BODY_INFO_SLEEPING_cellIndex));
        row.createCell(Constants.BODY_INFO_SLEEPING_INFO_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_SLEEPING_INFO_cellI18NKey)));
        row.createCell(Constants.BODY_INFO_EATING_SPEED_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_EATING_SPEED_cellI18NKey)));
        addDataValidation(sheet, Constants.EatingSpeedTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BODY_INFO_EATING_SPEED_cellIndex, Constants.BODY_INFO_EATING_SPEED_cellIndex));
        row.createCell(Constants.BODY_INFO_APPETITE_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_APPETITE_cellI18NKey)));
        addDataValidation(sheet, Constants.AppetiteTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BODY_INFO_APPETITE_cellIndex, Constants.BODY_INFO_APPETITE_cellIndex));
        row.createCell(Constants.BODY_INFO_PICKY_EATING_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_PICKY_EATING_cellI18NKey)));
        addDataValidation(sheet, Constants.PickyEatingTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BODY_INFO_PICKY_EATING_cellIndex, Constants.BODY_INFO_PICKY_EATING_cellIndex));
        row.createCell(Constants.BODY_INFO_PICKY_EATING_INFO_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_PICKY_EATING_INFO_cellI18NKey)));
        row.createCell(Constants.BODY_INFO_EATING_ABILITY_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_EATING_ABILITY_cellI18NKey)));
        addDataValidation(sheet, Constants.EatingAbilityTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BODY_INFO_EATING_ABILITY_cellIndex, Constants.BODY_INFO_EATING_ABILITY_cellIndex));
        row.createCell(Constants.BODY_INFO_FOOD_ALLERGY_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_FOOD_ALLERGY_cellI18NKey)));
        addDataValidation(sheet, Constants.FoodAllergyTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BODY_INFO_FOOD_ALLERGY_cellIndex, Constants.BODY_INFO_FOOD_ALLERGY_cellIndex));
        row.createCell(Constants.BODY_INFO_FOOD_ALLERGY_INFO_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_FOOD_ALLERGY_INFO_cellI18NKey)));
        row.createCell(Constants.BODY_INFO_HEALTH_STATUS_cellIndex).setCellValue(helper.createRichTextString(props.getProperty(Constants.BODY_INFO_HEALTH_STATUS_cellI18NKey)));
        addDataValidation(sheet, Constants.HealthStatusTranslated(props), 
                        new CellRangeAddressList(1, validateRowLength, Constants.BODY_INFO_HEALTH_STATUS_cellIndex, Constants.BODY_INFO_HEALTH_STATUS_cellIndex));
        
        for(int i = 0; i < row.getLastCellNum(); i++){//For each cell in the row 
            row.getCell(i).setCellStyle(style);//Set the style
            addDataValidation(sheet, new String[] {row.getCell(i).getStringCellValue()}, 
                            new CellRangeAddressList(0, 0, i, i));
        }
        
    }
    
    private void addDataValidation(Sheet sheet, String[] list, CellRangeAddressList address)
    {
        DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(list);
        DataValidation dataValidationGender = new HSSFDataValidation(address, dvConstraint);
        dataValidationGender.setSuppressDropDownArrow(false); // add arrow
        dataValidationGender.setEmptyCellAllowed(false);// cannot be empty
        sheet.addValidationData(dataValidationGender);
    }
    
    private void addContent(Sheet sheet, Properties props) throws CommonException
    {
        List<Child> children = childDAO.getChildren();
        if (children == null || children.isEmpty())
            return;
        
    }

}
