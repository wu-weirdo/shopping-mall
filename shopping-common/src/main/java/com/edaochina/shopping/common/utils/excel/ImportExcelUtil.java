package com.edaochina.shopping.common.utils.excel;

import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.common.exception.YidaoException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: zm
 * @createDate: 2018/7/4$ 16:51$
 * @updateRemark: 修改内容
 * @description:
 */
public class ImportExcelUtil {
    public static final String[] arrAttribute = {"name", "description", "minref", "maxref", "code"};
    public static final String[] arrAttribute2 = {"phone", "name", "score"};
    public static final int START_ROW = 9;
    public static final int MAX_ROW = 10000;
    public static final int MAX_CELL = 5;
    public static final int NAME_MAX_LENGTH = 10;
    public static final int PHONE_MAX_LENGTH = 11;
    public static final int DESCRIPTION_MAX_LENGTH = 200;
    //2003- 版本的excel
    private final static String EXCEL_2003L = ".xls";
    //2007+ 版本的excel
    private final static String EXCEL_2007U = ".xlsx";

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static List<JSONObject> getBankListByExcel(InputStream in, String fileName) throws Exception {
        List<JSONObject> list = null;

        //创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            //遍历当前sheet中的所有行
            if (sheet.getLastRowNum() < START_ROW - 1) {
                continue;
            }

            //从第9行开始读取数据
            for (int j = START_ROW - 1; j < MAX_ROW; j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                //遍历所有的列

                JSONObject jsonObject = new JSONObject();
                if (row.getFirstCellNum() > 0) {
                    throw new YidaoException("名称不能为空");
                }

                for (int y = row.getFirstCellNum(); y < MAX_CELL; y++) {
                    cell = row.getCell(y);
                    if (y == 0) {
                        if (cell == null) {
                            throw new YidaoException("名称不能为空");
                        }
                        String value = getCellValue(cell).toString();
                        if (StringUtils.isBlank(value)) {
                            throw new YidaoException("名称不能为空");
                        }
                        if (value.length() > NAME_MAX_LENGTH) {
                            throw new YidaoException("名称长度超过限制");
                        }
                        jsonObject.put(arrAttribute[y], value);
                    } else {
                        if (cell == null) {
                            jsonObject.put(arrAttribute[y], "");
                        } else {
                            String value = getCellValue(cell).toString();
                            if (StringUtils.isNotBlank(value) && value.length() > DESCRIPTION_MAX_LENGTH) {
                                throw new YidaoException("描述长度超过限制");
                            }
                            jsonObject.put(arrAttribute[y], value);
                        }
                    }

                }
                list.add(jsonObject);
            }
        }
        work.close();
        return list;
    }


    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static List<JSONObject> getCustListByExcel(InputStream in, String fileName, int maxCell) throws Exception {
        List<JSONObject> list = null;

        //创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new YidaoException("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            //遍历当前sheet中的所有行
            if (sheet.getLastRowNum() < START_ROW - 1) {
                continue;
            }

            //从第9行开始读取数据
            for (int j = START_ROW - 1; j < MAX_ROW; j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                //遍历所有的列

                JSONObject jsonObject = new JSONObject();
                if (row.getFirstCellNum() > 0) {
                    throw new YidaoException("手机号不能为空");
                }

                for (int y = row.getFirstCellNum(); y < maxCell; y++) {
                    cell = row.getCell(y);
                    if (y == 0) {
                        if (cell == null) {
                            throw new YidaoException("手机号不能为空");
                        }
                        String value = getCellValue(cell).toString();
                        if (StringUtils.isBlank(value)) {
                            throw new YidaoException("手机号不能为空");
                        }
                        if (!value.matches("^[0-9]*$")) {
                            throw new YidaoException("手机号格式不正确");
                        }
                        if (value.length() != PHONE_MAX_LENGTH) {
                            throw new YidaoException("手机号长度不符合限制");
                        }
                        jsonObject.put(arrAttribute2[y], value);
                    } else {
                        if (cell == null) {
                            jsonObject.put(arrAttribute2[y], "");
                        } else {
                            String value = getCellValue(cell).toString();
                            jsonObject.put(arrAttribute2[y], value);
                        }
                    }

                }
                list.add(jsonObject);
            }
        }
        work.close();
        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (EXCEL_2003L.equals(fileType)) {
            //2003-
            wb = new HSSFWorkbook(inStr);
        } else if (EXCEL_2007U.equals(fileType)) {
            //2007+
            wb = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object getCellValue(Cell cell) {
        //格式化number String字符
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");
        //日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        //格式化数字
        DecimalFormat df2 = new DecimalFormat("0.00");

        switch (cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }


}
