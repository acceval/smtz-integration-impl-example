package com.acceval.core.filehandler.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.acceval.core.filehandler.FileHandlerException;
import com.acceval.core.microservice.model.LabelValue;

public class CommonHandler {

	public static final String KEY_COLUMN_DEF = "KEY_COLUMN_DEF";
	public static final String KEY_DATASOURCE = "KEY_DATASOURCE";
	public static final String DATASOURCE_ERROR = "DATASOURCE_ERROR";
	public static final String REQUIRED_ERROR = "REQUIRED_ERROR";

	private Workbook workbook;
	private Path filePath;
	private Map<String, Object> mapDef;

	public CommonHandler() {
		super();
	}

	public CommonHandler(Path filePath, Map<String, Object> mapDef) {
		super();
		this.filePath = filePath;
		this.mapDef = mapDef;
	}

	public CommonHandler(Map<String, Object> mapDef) {
		super();
		this.mapDef = mapDef;
	}

	private void initExcelFile() throws InvalidFormatException, IOException {
		this.workbook = new XSSFWorkbook(new File(filePath.toString()));
	}

	public String readExcelSheetName() throws InvalidFormatException, IOException {
		if (workbook == null) {
			this.initExcelFile();
		}
		if (workbook != null && workbook.sheetIterator().hasNext() && workbook.getSheetAt(0) != null) {
			Sheet sheet = workbook.getSheetAt(0);
			return sheet.getSheetName();
		}
		return null;
	}

	public List<Map<String, String>> readExcelFile() throws Exception {
		if (mapDef == null) {
			throw new Exception("No Column Definition is defined");
		}

		List<ColumnDef> lstColumnDef = (List<ColumnDef>) mapDef.get(KEY_COLUMN_DEF);
		List<Map<String, String>> lstMapRawValue = new ArrayList<>();

		try {
			if (workbook == null) {
				this.initExcelFile();
			}

			DataFormatter formatter = new DataFormatter();
			if (workbook != null && workbook.sheetIterator().hasNext() && workbook.getSheetAt(0) != null) {
				Sheet sheet = workbook.getSheetAt(0);

				boolean labelLoaded = false;
				for (Row row : sheet) {
					if (!labelLoaded) {
						labelLoaded = true;
						continue;
					}
					Map<String, String> mapRow = new HashMap<>();
					for (int sourceColIndex = 0; lstColumnDef.size() > sourceColIndex; sourceColIndex++) {
						Cell cell = row.getCell(sourceColIndex);

						ColumnDef columnDef = lstColumnDef.get(sourceColIndex);
						String labelAsKey = columnDef.getLabel();

						if (StringUtils.isNotBlank(columnDef.getDateFormat())) {
							Date obj = cell.getDateCellValue();

							if (obj != null) {
								SimpleDateFormat sdf = new SimpleDateFormat(columnDef.getDateFormat());
								mapRow.put(labelAsKey, sdf.format(obj));
							} else {
								mapRow.put(labelAsKey, null);
								// check mandate
								if (columnDef.isMandate()) {
									String errorMsg = "[" + columnDef.getLabel() + "] is required!";
									buildErrorMsg(mapRow, REQUIRED_ERROR, errorMsg);
								}
							}

						} else {
							String text = formatter.formatCellValue(cell);

							// check mandate
							if (StringUtils.isBlank(text) && columnDef.isMandate()) {
								String errorMsg = "[" + columnDef.getLabel() + "] is required!";
								buildErrorMsg(mapRow, REQUIRED_ERROR, errorMsg);
							}

							if (columnDef.getDatasource() != null) {
								String errorMsg = "[" + text + "] not found for [" + columnDef.getLabel() + "]!";
								text = columnDef.findValueFromDatasource(text);
								if (StringUtils.isBlank(text)) {
									buildErrorMsg(mapRow, DATASOURCE_ERROR, errorMsg);
								}
							}
							mapRow.put(labelAsKey, text);
						}
					}
					lstMapRawValue.add(mapRow);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return lstMapRawValue;
	}

	private void buildErrorMsg(Map<String, String> mapRow, String errorType, String errorMsg) {
		String existingError = mapRow.get(errorType);
		if (existingError == null) {
			mapRow.put(errorType, errorMsg);
		} else {
			mapRow.put(errorType, existingError + ", " + errorMsg);
		}
	}

	private Workbook getWorkbook(String sheetName) throws Exception {
		if (mapDef == null) {
			throw new Exception("No Column Definition is defined");
		}

		List<ColumnDef> lstColumnDef = (List<ColumnDef>) mapDef.get(KEY_COLUMN_DEF);
		List<ColumnDef> lstDatasource = (List<ColumnDef>) mapDef.get(KEY_DATASOURCE);

		Workbook workbook = null;

			workbook = new XSSFWorkbook();

			/** master sheet */
			Sheet workSheet = workbook.createSheet(sheetName == null ? "Upload Template" : sheetName);
			Row row = workSheet.createRow(0);
			int index = 0;
			for (ColumnDef columnDef : lstColumnDef) {
				Cell cell = row.createCell(index, CellType.STRING);
				cell.setCellValue(columnDef.getLabel());
				index++;
			}

			/** datasource sheet */
			if (CollectionUtils.isNotEmpty(lstDatasource)) {
				Sheet sheetDatasource = workbook.createSheet("Datasource");
				List<Row> lstRow = new ArrayList<>();
				Row rowLabel = sheetDatasource.createRow(0);
				lstRow.add(rowLabel);
				int columnIndex = 0;
				for (ColumnDef columnDef : lstDatasource) {
				//					if (CollectionUtils.isEmpty(columnDef.getDatasource())) continue;

					// first row, label
					Cell cell = rowLabel.createCell(columnIndex, CellType.STRING);
					cell.setCellValue(columnDef.getLabel());

					// datasource
					List<LabelValue> datasource = columnDef.getDatasource();
					int dsIndex = 0;
					for (LabelValue lv : datasource) {
						Row rowValue = null;
						if (lstRow.size() <= dsIndex + 1) {
							Row rowDS = sheetDatasource.createRow(lstRow.size());
							lstRow.add(rowDS);
						}
						rowValue = lstRow.get(dsIndex + 1);
						Cell cellValue = rowValue.createCell(columnIndex, CellType.STRING);
						cellValue.setCellValue(lv.getLabel());
						dsIndex++;
					}

					columnIndex++;
				}
			}
		return workbook;
	}

	public byte[] writeExcelFile(String sheetName) throws Exception {
		Workbook workbook = null;
		byte result[] = null;
		try {
			workbook = this.getWorkbook(sheetName);

			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			workbook.write(byteArray);
			result = byteArray.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
			throw new FileHandlerException(this.getClass(), e.getLocalizedMessage());
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public void writeExcelFileToStorage(String sheetName) throws Exception {

		Workbook workbook = null;
		try {
			workbook = this.getWorkbook(sheetName);
			FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath.toString()));
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new FileHandlerException(this.getClass(), e.getLocalizedMessage());
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void closeFile() {
		if (this.workbook != null) {
			try {
				this.workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public void setFilePath(Path filePath) {
		this.filePath = filePath;
	}

	public void setMapDef(Map<String, Object> mapDef) {
		this.mapDef = mapDef;
	}

}
