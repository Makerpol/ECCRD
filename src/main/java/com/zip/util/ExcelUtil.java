package com.zip.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.Flushable;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil implements AutoCloseable, Flushable {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private FileInputStream fileSystem;
	private Workbook workbook;
	
	/**
	 * 包含文件路径的构造函数
	 * @param path
	 * @throws IOException
	 */
	public ExcelUtil(String path) throws IOException {
		fileSystem = new FileInputStream(new File(path));
		if (path.trim().toLowerCase().endsWith(".xls")) {
			workbook = new HSSFWorkbook(fileSystem);
		} else if (path.trim().toLowerCase().endsWith(".xlsx")) {
			workbook = new XSSFWorkbook(fileSystem);
		}
	}
	
	/**
	 * 获取所有的行数
	 * @param index sheet的索引
	 * @return
	 */
	public int getRowNum(int index) {
		return getSheet(index).getLastRowNum() + 1;
	}
	
	/**
	 * 获取所有的行数
	 * @param sheet 
	 * @return
	 */
	public int getRowNum(Sheet sheet) {
		return sheet.getLastRowNum() + 1;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public String getSheetName(int index) {
		return workbook.getSheetAt(index).getSheetName();
	}
	
	/**
	 * 读取第一个Sheet的名字
	 * @return
	 */
	public String getSheetName() {
		return workbook.getSheetAt(0).getSheetName();
	}
	
	/**
	 * 获取指定的Sheet
	 * @param index
	 * @return
	 */
	private Sheet getSheet(int index) {
		return workbook.getSheetAt(index);
	}
	
	/**
	 * 通过名字获取Sheet
	 * @param name
	 * @return
	 */
	private Sheet getSheet(String name) {
		return workbook.getSheet(name);
	}
	
	/**
	 * 获取行信息
	 * @param sheet
	 * @param index
	 * @return
	 */
	public Object[] getRow(Sheet sheet, int index) {
		Row row = sheet.getRow(index);
		if (row == null) {
			return null;
		}
		Object val[] = new Object[row.getLastCellNum()];
		for (int i = 0; i < val.length; i++) {
			if (row.getCell(i) == null) {
				val[i] = "";
				continue;
			}
			switch (row.getCell(i).getCellTypeEnum()) {
			case BLANK:
			case ERROR:
			case _NONE:
				val[i] = "";
				break;
			case NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(row.getCell(i))) {
					Date date = row.getCell(i).getDateCellValue();
					val[i] = dateFormat.format(date);
				} else {
					val[i] = BigDecimal.valueOf(row.getCell(i).getNumericCellValue()).stripTrailingZeros().toPlainString();
				}
				break;
			case BOOLEAN:
				val[i] = row.getCell(i).getBooleanCellValue();
				break;
			case FORMULA:
			default:
				val[i] = row.getCell(i).getStringCellValue().trim();
				break;
			}
			
		}
		return val;
	}

	/**
	 * 获取第一个sheet的所有数据
	 * @return
	 */
	public List<List<Object>> getExcelData() {
		return getExcelData(0);
	}
	
	/**
	 * 通过名称获取Sheet的所有数据
	 * @param name
	 * @return
	 */
	public List<List<Object>> getExcelData(String name) {
		Sheet sheet = getSheet(name);
		List<List<Object>> data = new ArrayList<>();
		for (int i = 0; i < getRowNum(sheet); i++) {
			Object val[] = getRow(sheet, i);
			if (val != null) {
				data.add(Arrays.asList(val));
			}
		}
		return data;
	}
	
	/**
	 * 获取置顶的Sheet的所有数据
	 * @param sheetIndex
	 * @return
	 */
	public List<List<Object>> getExcelData(int sheetIndex) {
		Sheet sheet = getSheet(sheetIndex);
		List<List<Object>> data = new ArrayList<>();
		int max = getRowNum(sheet);
		for (int i = 0; i < max; i++) {
			Object val[] = getRow(sheet, i);
			if (val != null) {
				data.add(Arrays.asList(val));
			}
		}
		return data;
	}
	
	/**
	 * 加载数据
	 * @param rowIndex 开始写入的行数
	 * @param sheetIndex 开始写入的sheet
	 * @param dataList 数据
	 * @throws IOException 
	 */
	public ServletOutputStream loadData(int rowIndex, int sheetIndex, List<Map<String, Object>> dataList, ServletOutputStream outputStream) throws IOException {
		Sheet sheet = getSheet(sheetIndex);
		IntStream.range(0, dataList.size()).forEach(i -> {
			Row row = sheet.createRow(i + rowIndex);
			row.createCell(0).setCellValue(dataList.get(i).get("DATE").toString());
			
			String authName = Optional.ofNullable(dataList.get(i).get("AUTH_NAME")).map(s -> s.toString()).orElse("");
			row.createCell(1).setCellValue(authName);
			
			row.createCell(2).setCellValue(dataList.get(i).get("NEW_CNT").toString());
			row.createCell(3).setCellValue(dataList.get(i).get("NEW_MONEY").toString());
			
			row.createCell(4).setCellValue(dataList.get(i).get("DELAY_CNT").toString());
			row.createCell(5).setCellValue(dataList.get(i).get("DELAY_MONEY").toString());
			
			row.createCell(6).setCellValue(dataList.get(i).get("CHANGE_CNT").toString());
			row.createCell(7).setCellValue(dataList.get(i).get("CHANGE_MONEY").toString());
			
			row.createCell(8).setCellValue(dataList.get(i).get("REPLACE_CNT").toString());
			row.createCell(9).setCellValue(dataList.get(i).get("REPLACE_MONEY").toString());
			
			row.createCell(10).setCellValue(dataList.get(i).get("SUM_CNT").toString());
			row.createCell(11).setCellValue(dataList.get(i).get("SUM_MONEY").toString());
		});
		workbook.write(outputStream);
		return outputStream;
	}

	/**
	 * 自动关闭接口
	 */
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		workbook.close();
		fileSystem.close();
	}

	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub
	}
}
