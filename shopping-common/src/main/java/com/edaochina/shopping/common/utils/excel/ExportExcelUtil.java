package com.edaochina.shopping.common.utils.excel;

import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.alioss.AliyunOSSUtil;
import com.edaochina.shopping.common.utils.date.LocalDateUtil;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import org.apache.poi.hssf.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @since : 2019/6/21 10:08
 */
public final class ExportExcelUtil {

    public final static class Builder {

        private HSSFWorkbook hssfWorkbook;

        private List<HSSFSheet> hssfSheets = new ArrayList<>();

        private List<String> fields = new ArrayList<>();

        private HSSFSheet firstSheet;

        private AtomicInteger rowCount = new AtomicInteger(0);

        private Map<String, Function<Object, String>> translate = new HashMap<>();

        public static Builder newInstance() {
            Builder builder = new Builder();
            builder.setHssfWorkbook(new HSSFWorkbook());
            return builder;
        }

        public Builder putTranslateFunction(String key, Function<Object, String> function) {
            translate.put(key, function);
            return this;
        }

        public Builder createSheet(String title) {
            HSSFSheet sheet = hssfWorkbook.createSheet(title);
            //设置表格默认列宽15个字节
            sheet.setDefaultColumnWidth(15);
            hssfSheets.add(sheet);
            if (hssfSheets.size() == 1) {
                firstSheet = sheet;
            }
            return this;
        }

        /**
         * 创建表格头，默认在第一页创建
         * 当没有创建表或者已经开始导数据时跳过
         *
         * @param headers 表格头列表
         * @return 表格构建器
         */
        public Builder createHeader(List<String> headers) {
            //生成表格标题
            if (rowCount.get() != 0 || firstSheet == null) {
                return this;
            }
            HSSFRow row = firstSheet.createRow(0);
            row.setHeight((short) 300);
            HSSFCell cell;
            HSSFCellStyle style = createStyle();
            for (int i = 0; i < headers.size(); i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(new HSSFRichTextString(headers.get(i)));
            }
            return this;
        }

        /**
         * 导出数据，根据fields确定字段名和顺序
         *
         * @param items   要导出的数据列表
         * @param <T>数据类型
         * @return 表格构建器
         */
        public <T> Builder put(List<T> items) {
            if (items == null) {
                return this;
            }
            items.forEach(t -> {
                try {
                    HSSFRow row = firstSheet.createRow(rowCount.incrementAndGet());
                    for (int j = 0; j < fields.size(); j++) {
                        HSSFCell cell = row.createCell(j);
                        String methodName = fields.get(j);
                        Method getMethod = t.getClass().getMethod(methodName);
                        Object value = getMethod.invoke(t);
                        cell.setCellValue(translate(methodName, value));
                    }
                } catch (Exception e) {
                    LoggerUtils.error(ExportExcelUtil.class, e.getMessage());
                }
            });
            return this;
        }

        private String translate(String methodName, Object v) {
            if (v == null) {
                return "";
            }
            if (translate.containsKey(methodName)) {
                return translate.get(methodName).apply(v);
            }
            return v.toString();
        }

        /**
         * 结束导出
         *
         * @return Excel下载链接
         */
        public String export() {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String fileName = IdUtils.nextId() + ".xls";
            String[] index = fileName.split("\\.");
            try {
                hssfWorkbook.write(outputStream);
                byte[] buff = outputStream.toByteArray();
                for (int i = 0; i < buff.length; i++) {
                    ByteArrayInputStream bin = new ByteArrayInputStream(buff);
                    if (!isEmpty(fileName)) {
                        if (index.length != 0) {
                            String key = LocalDateUtil.localDateTime2String(LocalDateTime.now()) + "/" + IdUtils.nextId() + "." + index[index.length - 1];
                            AliyunOSSUtil.uploadFile(bin, key, bin.available());
                            return AliyunOSSUtil.imgUrl + "/" + key;
                        }
                    }
                }
            } catch (Exception e) {
                LoggerUtils.error(ExcelUtil.class, e.getMessage());
            } finally {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    LoggerUtils.error(ExcelUtil.class, e.getMessage());
                }
            }
            return "";
        }

        private HSSFCellStyle createStyle() {
            //生成一个样式
            HSSFCellStyle style = ExcelUtil.getCellStyle(hssfWorkbook);
            //生成一个字体
            HSSFFont font = ExcelUtil.getFont(hssfWorkbook);
            //把字体应用到当前样式
            style.setFont(font);
            return style;
        }

        void setHssfWorkbook(HSSFWorkbook hssfWorkbook) {
            this.hssfWorkbook = hssfWorkbook;
        }

        public Builder setFields(List<String> fields) {
            this.fields = fields;
            return this;
        }
    }

}
