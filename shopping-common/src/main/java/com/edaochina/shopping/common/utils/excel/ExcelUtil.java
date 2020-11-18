package com.edaochina.shopping.common.utils.excel;

import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.domain.base.excel.ArtUserDto;
import lombok.experimental.UtilityClass;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Excel工具类
 *
 * @author yuxinwei
 */
@UtilityClass
public class ExcelUtil {

    /**
     * 导出数据到Excel
     *
     * @param list
     * @param headers
     * @param title
     * @param os
     * @param <T>
     */
    public <T> void exportDataToExcel(List<T> list, String[] headers, String title, OutputStream os) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        //设置表格默认列宽15个字节
        sheet.setDefaultColumnWidth(15);
        //生成一个样式
        HSSFCellStyle style = getCellStyle(workbook);
        //生成一个字体
        HSSFFont font = getFont(workbook);
        //把字体应用到当前样式
        style.setFont(font);
        //生成表格标题
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 300);
        HSSFCell cell;
        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //将数据放入sheet中
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            T t = list.get(i);
            //利用反射，根据JavaBean属性的先后顺序，动态调用get方法得到属性的值
            Field[] fields = t.getClass().getDeclaredFields();
            try {
                for (int j = 0; j < fields.length; j++) {
                    cell = row.createCell(j);
                    Field field = fields[j];
                    String fieldName = field.getName();
                    String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method getMethod = t.getClass().getMethod(methodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    if (null == value) {
                        value = "";
                    }
                    cell.setCellValue(value.toString());
                }
            } catch (Exception e) {
                LoggerUtils.error(ExcelUtil.class, e.getMessage());
            }
        }
        try {
            workbook.write(os);
        } catch (Exception e) {
            LoggerUtils.error(ExcelUtil.class, e.getMessage());
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                LoggerUtils.error(ExcelUtil.class, e.getMessage());
            }
        }
    }


    /**
     * @param @param  workbook
     * @param @return
     * @return HSSFCellStyle
     * @throws
     * @Title: getCellStyle
     * @Description: 获取单元格格式
     */
    public HSSFCellStyle getCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return style;
    }


    /**
     * @param @param  workbook
     * @param @return
     * @return HSSFFont
     * @throws
     * @Title: getFont
     * @Description: 生成字体样式
     */
    public HSSFFont getFont(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        return font;
    }

    /**
     * 导出数据到Excel
     *
     * @param list
     * @param headers
     * @param title
     */
    public void exportDataToExcels(List<ArtUserDto> list, String[] headers, String title, int size, int count) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        //设置表格默认列宽15个字节
        sheet.setDefaultColumnWidth(15);
        //生成一个样式
        HSSFCellStyle style = getCellStyle(workbook);
        //生成一个字体
        HSSFFont font = getFont(workbook);
        //把字体应用到当前样式
        style.setFont(font);
        //生成表格标题
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 300);
        HSSFCell cell;
        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //将数据放入sheet中
        for (int i = 0; i < list.size(); i++) {
            if (count == 0) {
                row = sheet.createRow(i + 1);
            } else {
                row = sheet.createRow(i + 1 + size);
            }
            ArtUserDto t = list.get(i);
            //利用反射，根据JavaBean属性的先后顺序，动态调用get方法得到属性的值
            Field[] fields = t.getClass().getDeclaredFields();
            try {
                for (int j = 0; j < fields.length; j++) {
                    cell = row.createCell(j);
                    Field field = fields[j];
                    String fieldName = field.getName();
                    String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method getMethod = t.getClass().getMethod(methodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    if (null == value) {
                        value = "";
                    }
                    cell.setCellValue(value.toString());
                }
            } catch (Exception e) {
                LoggerUtils.error(ExcelUtil.class, e.getMessage());
            }
        }
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("E:\\article\\art.xls");
            workbook.write(fout);
        } catch (Exception e) {
            LoggerUtils.error(ExcelUtil.class, e.getMessage());
        } finally {
            if (fout != null) {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    LoggerUtils.error(ExcelUtil.class, e.getMessage());
                }
            }
        }
    }

    public <T> void exportDataToExcel(List<T> list, String[] headers, String dir) {
        POIFSFileSystem ps = null;//使用POI提供的方法得到excel的信息         
        FileOutputStream out = null;
        try {
            //FileInputStream fs = new FileInputStream("E:\\article\\bottle_pick.xls");//获取d://LongIdTest.xls  
            FileInputStream fs = new FileInputStream("dir");//获取d://LongIdTest.xls         
            ps = new POIFSFileSystem(fs);
            HSSFWorkbook wb = new HSSFWorkbook(ps);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = sheet.getRow(1);
            LoggerUtils.info(ExcelUtil.class, sheet.getLastRowNum() + " "); //分别得到最后一行的行号，和一条记录的最后一个单元格                   
            out = new FileOutputStream("E:\\article\\bottle_pick.xls"); //向d://LongIdTest.xls中写数据         
            HSSFCell cell;
            //将数据放入sheet中
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow((short) (sheet.getLastRowNum() + 1)); //在现有行号后追加数据  
                T t = list.get(i);
                //利用反射，根据JavaBean属性的先后顺序，动态调用get方法得到属性的值
                Field[] fields = t.getClass().getDeclaredFields();
                try {
                    for (int j = 0; j < fields.length; j++) {
                        cell = row.createCell(j);
                        Field field = fields[j];
                        String fieldName = field.getName();
                        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method getMethod = t.getClass().getMethod(methodName, new Class[]{});
                        Object value = getMethod.invoke(t, new Object[]{});
                        if (null == value) {
                            value = "";
                        }
                        cell.setCellValue(value.toString());
                    }
                } catch (Exception e) {
                    LoggerUtils.error(ExcelUtil.class, e.getMessage());
                }
            }
            out.flush();
            wb.write(out);
            out.close();
        } catch (IOException e) {
            LoggerUtils.error(ExcelUtil.class, e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LoggerUtils.error(ExcelUtil.class, e.getMessage());
                }
            }
        }
    }

}
