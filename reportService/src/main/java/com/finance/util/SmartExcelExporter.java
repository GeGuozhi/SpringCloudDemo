package com.finance.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * 智能Excel导出工具类
 * 支持指定行和列，自动合并相同值的单元格
 */
public class SmartExcelExporter {

    /**
     * 生成 Excel 的二进制内容（用于：单文件下载 或 zip 多文件打包）
     */
    public static byte[] buildExcelBytesWithHeaderAreaMerge(
            List<List<String>> headList,
            List<List<String>> dataLists,
            String sheetName,
            int topDimCount,
            int leftDimCount,
            String title) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName == null ? "sheet" : sheetName);

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);

        int rowIndex = 0;
        int totalColCount = getMaxColumnCount(headList, dataLists);

        // 0) 写入 title（第一行整行合并）
        if (title != null && !title.trim().isEmpty()) {
            Row titleRow = sheet.createRow(rowIndex);
            List<String> titleCells = new ArrayList<>(Collections.nCopies(Math.max(totalColCount, 1), ""));
            titleCells.set(0, title);
            writeRowData(titleRow, titleCells, headerStyle);
            if (totalColCount > 1) {
                try {
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, totalColCount - 1));
                } catch (Exception ignored) {
                }
            }
            rowIndex++;
        }

        // 写入表头
        if (headList != null && !headList.isEmpty()) {
            for (List<String> headerRow : headList) {
                Row row = sheet.createRow(rowIndex);
                writeRowData(row, headerRow, headerStyle);
                rowIndex++;
            }
        }
        int headerRowCount = rowIndex;

        // 写入数据
        if (dataLists != null && !dataLists.isEmpty()) {
            for (List<String> dataRow : dataLists) {
                Row row = sheet.createRow(rowIndex);
                writeRowData(row, dataRow, dataStyle);
                rowIndex++;
            }
        }

        // 表头合并区域：跳过 title 行（若存在），从 headerStartRow 开始合并
        int headerStartRow = (title != null && !title.trim().isEmpty()) ? 1 : 0;
        int endHeaderMergeRow = Math.min(headerStartRow + Math.max(topDimCount, -1), headerRowCount - 1);
        if (endHeaderMergeRow >= headerStartRow && totalColCount > 0) {
            mergeSameValueRectangles(sheet, headerStartRow, endHeaderMergeRow, 0, totalColCount - 1);
        }

        autoSizeColumns(sheet, totalColCount);

        try (java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream()) {
            workbook.write(bos);
            workbook.close();
            return bos.toByteArray();
        }
    }

    /**
     * 仅合并“表头区域”的专用导出方法：
     * 在完成表头填充后：
     * - 先对 0..topDimCount 行、所有列做横向合并（按行合并相同连续单元格）
     * - 再对 0..topDimCount 行、所有列做纵向合并（按列合并相同连续单元格）
     *
     * 说明：不会影响数据区（表头以下）的合并效果。
     */
    public static void exportExcelWithHeaderAreaMerge(
            List<List<String>> headList,
            List<List<String>> dataLists,
            String sheetName,
            String fileName,
            int topDimCount,
            int leftDimCount,
            HttpServletResponse response) throws IOException {
        exportExcelWithHeaderAreaMerge(headList, dataLists, sheetName, fileName, topDimCount, leftDimCount, null, response);
    }

    /**
     * 增强版：在第一行插入 title，并整行合并（不参与后续表头矩形合并）。
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
     * 导出Excel并自动合并指定行列
     *
     * @param headList 表头数据，格式为List<List<String>>
     * @param dataLists 数据行，格式为List<List<String>>
     * @param sheetName 工作表名称
     * @param fileName 导出文件名
     * @param mergeRowIndexes 需要合并的行索引列表（从0开始）
     * @param mergeColumnIndexes 需要合并的列索引列表（从0开始）
     * @param response HttpServletResponse
     */
    public static void exportExcelWithMerge(
            List<List<String>> headList,
            List<List<String>> dataLists,
            String sheetName,
            String fileName,
            List<Integer> mergeRowIndexes,
            List<Integer> mergeColumnIndexes,
            HttpServletResponse response) throws IOException {

        // 1. 创建Workbook和Sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // 2. 创建样式
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);

        int rowIndex = 0;

        // 3. 写入表头
        if (headList != null && !headList.isEmpty()) {
            for (List<String> headerRow : headList) {
                Row row = sheet.createRow(rowIndex);
                writeRowData(row, headerRow, headerStyle);
                rowIndex++;
            }
        }

        int totalRowCount = rowIndex; // 当前总行数（表头行数）

        // 4. 写入数据
        if (dataLists != null && !dataLists.isEmpty()) {
            for (List<String> dataRow : dataLists) {
                Row row = sheet.createRow(rowIndex);
                writeRowData(row, dataRow, dataStyle);
                rowIndex++;
            }
        }

        totalRowCount = rowIndex; // 最终总行数
        int totalColCount = getMaxColumnCount(headList, dataLists); // 总列数

        // 5. 应用行合并
        if (mergeRowIndexes != null && !mergeRowIndexes.isEmpty()) {
            applyRowMerges(sheet, mergeRowIndexes, 0, totalColCount - 1);
        }

        // 6. 应用列合并
        if (mergeColumnIndexes != null && !mergeColumnIndexes.isEmpty()) {
            applyColumnMerges(sheet, mergeColumnIndexes, 0, totalRowCount - 1);
        }

        // 7. 自动调整列宽
        autoSizeColumns(sheet, totalColCount);

        // 8. 设置响应头
        setResponseHeaders(fileName, response);

        // 9. 写入响应流
        writeToResponse(workbook, response);

        // 10. 关闭工作簿
        workbook.close();
    }

    /**
     * 应用行合并
     */
    private static void applyRowMerges(Sheet sheet, List<Integer> mergeRowIndexes, int startCol, int endCol) {
        for (int rowIndex : mergeRowIndexes) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) continue;

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
                        CellRangeAddress region = new CellRangeAddress(rowIndex, rowIndex, startMergeCol, endMergeCol);
                        try {
                            sheet.addMergedRegion(region);
                        } catch (Exception e) {
                            // 忽略合并异常
                        }
                    }

                    prevValue = currentValue;
                    startMergeCol = col;
                }
            }
        }
    }

    /**
     * 应用列合并
     */
    private static void applyColumnMerges(Sheet sheet, List<Integer> mergeColumnIndexes, int startRow, int endRow) {
        for (int colIndex : mergeColumnIndexes) {
            String prevValue = null;
            int startMergeRow = startRow;

            for (int row = startRow; row <= endRow; row++) {
                Row rowObj = sheet.getRow(row);
                if (rowObj == null) continue;

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
                        } catch (Exception e) {
                            // 忽略合并异常
                        }
                    }

                    prevValue = currentValue;
                    startMergeRow = row;
                }
            }
        }
    }

    /**
     * 在指定矩形范围内，将相同字符串值合并成不重叠的矩形区域。
     * 规则：从左到右、从上到下扫描；对每个未访问单元格，尽可能扩展到最大同值矩形并合并。
     */
    private static void mergeSameValueRectangles(Sheet sheet, int startRow, int endRow, int startCol, int endCol) {
        if (sheet == null) return;
        if (startRow > endRow || startCol > endCol) return;

        int rows = endRow - startRow + 1;
        int cols = endCol - startCol + 1;
        boolean[][] visited = new boolean[rows][cols];

        for (int r = startRow; r <= endRow; r++) {
            Row rowObj = sheet.getRow(r);
            for (int c = startCol; c <= endCol; c++) {
                int vr = r - startRow;
                int vc = c - startCol;
                if (visited[vr][vc]) continue;

                String v = getCellStringValue(rowObj == null ? null : rowObj.getCell(c));
                if (v == null) v = "";
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

                // 标记访问
                for (int rr = r; rr <= maxRow; rr++) {
                    for (int cc = c; cc <= maxCol; cc++) {
                        visited[rr - startRow][cc - startCol] = true;
                    }
                }

                // 合并
                if (maxRow > r || maxCol > c) {
                    try {
                        sheet.addMergedRegion(new CellRangeAddress(r, maxRow, c, maxCol));
                    } catch (Exception ignored) {
                        // 忽略合并异常
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

    // ================== 辅助方法 ==================

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

    private static CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        return style;
    }

    private static void writeRowData(Row row, List<String> cellValues, CellStyle style) {
        if (cellValues == null) return;

        for (int i = 0; i < cellValues.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(cellValues.get(i) != null ? cellValues.get(i) : "");
            if (style != null) cell.setCellStyle(style);
        }
    }

    private static void autoSizeColumns(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static int getMaxColumnCount(List<List<String>> headList, List<List<String>> dataLists) {
        int maxColumns = 0;
        if (headList != null) {
            for (List<String> row : headList) {
                if (row != null && row.size() > maxColumns) maxColumns = row.size();
            }
        }
        if (dataLists != null) {
            for (List<String> row : dataLists) {
                if (row != null && row.size() > maxColumns) maxColumns = row.size();
            }
        }
        return maxColumns;
    }

    private static void setResponseHeaders(String fileName, HttpServletResponse response) {
        if (fileName == null || fileName.trim().isEmpty()) fileName = "export";
        if (!fileName.endsWith(".xlsx")) fileName = fileName + ".xlsx";

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

    private static void writeToResponse(Workbook workbook, HttpServletResponse response) throws IOException {
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
        }
    }
}