package com.psbc.invres.resarchlibrary.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 智能 Excel 导出工具类
 *
 * 功能：
 * 1. 生成带标题行的 Excel 文件
 * 2. 自动合并表头区域中相同值的单元格
 * 3. 支持设置行列合并规则
 */
public class SmartExcelExporter {

    /**
     * 生成 Excel 二进制内容（核心方法）
     *
     * 生成顺序：
     * 1. 标题行（可选，整行合并）
     * 2. 表头行
     * 3. 数据行
     * 4. 表头区域单元格合并
     * 5. 自动调整列宽
     */
    public static byte[] buildExcelBytesWithHeaderAreaMerge(
            List<List<String>> headList,
            List<List<String>> dataLists,
            String sheetName,
            int topDimCount,
            int leftDimCount,
            String title) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName == null ? "Sheet1" : sheetName);

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);

        int rowIndex = 0;
        int totalColCount = getMaxColumnCount(headList, dataLists);

        // 1. 写入标题行
        if (title != null && !title.trim().isEmpty()) {
            Row titleRow = sheet.createRow(rowIndex);
            List<String> titleCells = new ArrayList<>(Collections.nCopies(Math.max(totalColCount, 1), ""));
            titleCells.set(0, title);
            writeRowData(titleRow, titleCells, headerStyle);
            if (totalColCount > 1) {
                try {
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, totalColCount - 1));
                } catch (Exception ignored) {
                    // ignore
                }
            }
            rowIndex++;
        }

        // 2. 写入表头
        if (headList != null && !headList.isEmpty()) {
            for (List<String> headerRow : headList) {
                Row row = sheet.createRow(rowIndex);
                writeRowData(row, headerRow, headerStyle);
                rowIndex++;
            }
        }
        int headerRowCount = rowIndex;

        // 3. 写入数据
        if (dataLists != null && !dataLists.isEmpty()) {
            for (List<String> dataRow : dataLists) {
                Row row = sheet.createRow(rowIndex);
                writeRowData(row, dataRow, dataStyle);
                rowIndex++;
            }
        }

        // 4. 表头区域单元格合并
        int headerStartRow = (title != null && !title.trim().isEmpty()) ? 1 : 0;
        int endHeaderMergeRow = Math.min(headerStartRow + Math.max(topDimCount, -1), headerRowCount - 1);
        if (endHeaderMergeRow >= headerStartRow && totalColCount > 0) {
            mergeSameValueRectangles(sheet, headerStartRow, endHeaderMergeRow, 0, totalColCount - 1);
        }

        // 5. 自动调整列宽
        autoSizeColumns(sheet, totalColCount);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            workbook.close();
            return bos.toByteArray();
        }
    }

    // ==================== HTTP 响应导出方法 ====================

    /**
     * 导出 Excel 到 HTTP 响应（带表头区域合并）
     */
    public static void exportExcelWithHeaderAreaMerge(
            List<List<String>> headList,
            List<List<String>> dataLists,
            String sheetName,
            String fileName,
            int topDimCount,
            int leftDimCount,
            HttpServletResponse response) throws IOException {
        byte[] bytes = buildExcelBytesWithHeaderAreaMerge(headList, dataLists, sheetName, topDimCount, leftDimCount, null);
        setResponseHeaders(fileName, response);
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }

    /**
     * 导出 Excel 到 HTTP 响应（带标题行和表头区域合并）
     */
    public static void exportExcelWithHeaderAreaMerge(
            List<List<String>> headList,
            List<List<String>> dataLists,
            String sheetName,
            String fileName,
            int topDimCount,
            int leftDimCount,
            String title,
            HttpServletResponse response) throws IOException {
        byte[] bytes = buildExcelBytesWithHeaderAreaMerge(headList, dataLists, sheetName, topDimCount, leftDimCount, title);
        setResponseHeaders(fileName, response);
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }

    /**
     * 导出 Excel 并按指定行列规则合并
     */
    public static void exportExcelWithMerge(
            List<List<String>> headList,
            List<List<String>> dataLists,
            String sheetName,
            String fileName,
            List<Integer> mergeRowIndexes,
            List<Integer> mergeColumnIndexes,
            HttpServletResponse response) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);

        int rowIndex = 0;

        // 写入表头
        if (headList != null && !headList.isEmpty()) {
            for (List<String> headerRow : headList) {
                Row row = sheet.createRow(rowIndex);
                writeRowData(row, headerRow, headerStyle);
                rowIndex++;
            }
        }

        int totalRowCount = rowIndex;
        int totalColCount = getMaxColumnCount(headList, dataLists);

        // 写入数据
        if (dataLists != null && !dataLists.isEmpty()) {
            for (List<String> dataRow : dataLists) {
                Row row = sheet.createRow(rowIndex);
                writeRowData(row, dataRow, dataStyle);
                rowIndex++;
            }
        }
        totalRowCount = rowIndex;

        // 应用行合并
        if (mergeRowIndexes != null && !mergeRowIndexes.isEmpty()) {
            applyRowMerges(sheet, mergeRowIndexes, 0, totalColCount - 1);
        }

        // 应用列合并
        if (mergeColumnIndexes != null && !mergeColumnIndexes.isEmpty()) {
            applyColumnMerges(sheet, mergeColumnIndexes, 0, totalRowCount - 1);
        }

        // 自动调整列宽
        autoSizeColumns(sheet, totalColCount);

        // 设置响应头并写入
        setResponseHeaders(fileName, response);
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
        }
        workbook.close();
    }

    // ==================== 单元格合并方法 ====================

    /**
     * 应用行合并：遍历指定行，将连续相同的单元格合并
     */
    private static void applyRowMerges(Sheet sheet, List<Integer> mergeRowIndexes, int startCol, int endCol) {
        for (int rowIdx : mergeRowIndexes) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) {
                continue;
            }

            String prevValue = null;
            int startMergeCol = startCol;

            for (int col = startCol; col <= endCol; col++) {
                Cell cell = row.getCell(col);
                String currentValue = getCellStringValue(cell);

                if (prevValue == null) {
                    prevValue = currentValue;
                    startMergeCol = col;
                } else if (!prevValue.equals(currentValue) || col == endCol) {
                    int endMergeCol = (col == endCol && prevValue.equals(currentValue)) ? col : col - 1;

                    if (endMergeCol > startMergeCol) {
                        CellRangeAddress region = new CellRangeAddress(rowIdx, rowIdx, startMergeCol, endMergeCol);
                        try {
                            sheet.addMergedRegion(region);
                        } catch (Exception ignored) {
                            // ignore
                        }
                    }

                    prevValue = currentValue;
                    startMergeCol = col;
                }
            }
        }
    }

    /**
     * 应用列合并：遍历指定列，将连续相同的单元格合并
     */
    private static void applyColumnMerges(Sheet sheet, List<Integer> mergeColumnIndexes, int startRow, int endRow) {
        for (int colIndex : mergeColumnIndexes) {
            String prevValue = null;
            int startMergeRow = startRow;

            for (int row = startRow; row <= endRow; row++) {
                Row rowObj = sheet.getRow(row);
                if (rowObj == null) {
                    continue;
                }

                Cell cell = rowObj.getCell(colIndex);
                String currentValue = getCellStringValue(cell);

                if (prevValue == null) {
                    prevValue = currentValue;
                    startMergeRow = row;
                } else if (!prevValue.equals(currentValue) || row == endRow) {
                    int endMergeRow = (row == endRow && prevValue.equals(currentValue)) ? row : row - 1;

                    if (endMergeRow > startMergeRow) {
                        CellRangeAddress region = new CellRangeAddress(startMergeRow, endMergeRow, colIndex, colIndex);
                        try {
                            sheet.addMergedRegion(region);
                        } catch (Exception ignored) {
                            // ignore
                        }
                    }

                    prevValue = currentValue;
                    startMergeRow = row;
                }
            }
        }
    }

    /**
     * 在指定矩形范围内，将相同字符串值的单元格合并成不重叠的矩形区域
     *
     * 扫描策略：从左到右、从上到下
     * 对每个未访问的单元格，找到最大同值矩形并合并
     */
    private static void mergeSameValueRectangles(Sheet sheet, int startRow, int endRow, int startCol, int endCol) {
        if (sheet == null) {
            return;
        }
        if (startRow > endRow || startCol > endCol) {
            return;
        }

        int rows = endRow - startRow + 1;
        int cols = endCol - startCol + 1;
        boolean[][] visited = new boolean[rows][cols];

        for (int r = startRow; r <= endRow; r++) {
            Row rowObj = sheet.getRow(r);
            for (int c = startCol; c <= endCol; c++) {
                int vr = r - startRow;
                int vc = c - startCol;
                if (visited[vr][vc]) {
                    continue;
                }

                String v = getCellStringValue(rowObj == null ? null : rowObj.getCell(c));
                if (v == null) {
                    v = "";
                }
                if (v.trim().isEmpty()) {
                    visited[vr][vc] = true;
                    continue;
                }

                // 1) 向右扩展最大列
                int maxCol = c;
                while (maxCol + 1 <= endCol) {
                    Row rr = sheet.getRow(r);
                    String nv = getCellStringValue(rr == null ? null : rr.getCell(maxCol + 1));
                    if (v.equals(nv)) {
                        maxCol++;
                    } else {
                        break;
                    }
                }

                // 2) 向下扩展最大行（要求整行区间 [c..maxCol] 全部相同）
                int maxRow = r;
                while (maxRow + 1 <= endRow) {
                    boolean ok = true;
                    Row nr = sheet.getRow(maxRow + 1);
                    for (int cc = c; cc <= maxCol; cc++) {
                        String nv = getCellStringValue(nr == null ? null : nr.getCell(cc));
                        if (!v.equals(nv)) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        maxRow++;
                    } else {
                        break;
                    }
                }

                // 标记已访问
                for (int rr = r; rr <= maxRow; rr++) {
                    for (int cc = c; cc <= maxCol; cc++) {
                        visited[rr - startRow][cc - startCol] = true;
                    }
                }

                // 合并单元格
                if (maxRow > r || maxCol > c) {
                    try {
                        sheet.addMergedRegion(new CellRangeAddress(r, maxRow, c, maxCol));
                    } catch (Exception ignored) {
                        // ignore
                    }
                }
            }
        }
    }

    /**
     * 获取单元格的字符串值
     */
    private static String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double num = cell.getNumericCellValue();
                    if (num == Math.floor(num) && !Double.isInfinite(num)) {
                        return String.valueOf((int) num);
                    } else {
                        return String.valueOf(num);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    // ==================== 样式和辅助方法 ====================

    /**
     * 创建表头样式：加粗、灰色背景、边框、居中对齐
     */
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 创建数据样式：边框、自动换行
     */
    private static CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        return style;
    }

    /**
     * 写入行数据
     */
    private static void writeRowData(Row row, List<String> cellValues, CellStyle style) {
        if (cellValues == null) {
            return;
        }

        for (int i = 0; i < cellValues.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(cellValues.get(i) != null ? cellValues.get(i) : "");
            if (style != null) {
                cell.setCellStyle(style);
            }
        }
    }

    /**
     * 自动调整列宽
     */
    private static void autoSizeColumns(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * 获取最大列数（表头和数据中的最大值）
     */
    private static int getMaxColumnCount(List<List<String>> headList, List<List<String>> dataLists) {
        int maxColumns = 0;
        if (headList != null) {
            for (List<String> row : headList) {
                if (row != null && row.size() > maxColumns) {
                    maxColumns = row.size();
                }
            }
        }
        if (dataLists != null) {
            for (List<String> row : dataLists) {
                if (row != null && row.size() > maxColumns) {
                    maxColumns = row.size();
                }
            }
        }
        return maxColumns;
    }

    /**
     * 设置 HTTP 响应头
     */
    private static void setResponseHeaders(String fileName, HttpServletResponse response) {
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "export";
        }
        if (!fileName.endsWith(".xlsx")) {
            fileName = fileName + ".xlsx";
        }

        try {
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
            response.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        }
    }
}
