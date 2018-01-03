package com.func.tools;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 *
 * Auhor  chenwangming
 *
 * Created by chenwangming on 2017/8/2.
 */
public class readexeclhelper {
    /** 总行数 */
    private int totalRows = 0;
    /** 总列数 */
    private int totalCells = 0;
    /** 错误信息 */
    private String errorInfo;
    /** 构造方法 */
    public readexeclhelper() {}
    /**
     *
     * @描述：得到总行数
     *
     * @作者：陈王明
     *
     * @时间：2017-7-30
     *
     * @参数：@return
     *
     * @返回值：int
     */
    public int getTotalRows()
    {
        return totalRows;
    }
    /**
     *
     * @描述：得到总列数
     *
     * @作者：陈王明
     *
     * @时间：2017-7-30
     *
     * @参数：@return
     *
     * @返回值：int
     */
    public int getTotalCells()
    {
        return totalCells;
    }

    /**
     *
     * @描述：得到错误信息
     *
     * @作者：陈王明
     *
     * @时间：2017-7-30
     *
     * @参数：@return
     *
     * @返回值：String
     */
    public String getErrorInfo()
    {
        return errorInfo;
    }

    /**
     *
     * @描述：验证excel文件
     *
     * @作者：陈王明
     *
     * @时间：2017-7-30
     *
     * @参数：@param filePath　文件完整路径
     *
     * @参数：@return
     *
     * @返回值：boolean
     */
    public boolean validateExcel(String filePath)
    {
        /** 检查文件名是否为空或者是否是Excel格式的文件 */
        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath)))
        {
            errorInfo = "文件名不是excel格式";
            return false;
        }
        /** 检查文件是否存在 */
        File file = new File(filePath);
        if (file == null || !file.exists())
        {
            errorInfo = "文件不存在";
            return false;
        }
        return true;
    }
    /**
     *
     * @描述：根据文件名读取excel文件
     *
     * @作者：陈王明
     *
     * @时间：2017-7-30
     *
     * @参数：@param filePath 文件完整路径
     *
     * @参数：@return
     *
     * @返回值：List
     */
    public List<List<String>> read(String filePath)
    {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        InputStream is = null;
        try
        {
            /** 验证文件是否合法 */
            if (!validateExcel(filePath))
            {
                System.out.println(errorInfo);
                return null;
            }
            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(filePath))
            {
                isExcel2003 = false;
            }
            /** 调用本类提供的根据流读取的方法 */
            File file = new File(filePath);
            is = new FileInputStream(file);
            dataLst = read(is, isExcel2003);
            is.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        /** 返回最后读取的结果 */
        return dataLst;
    }

    /**
     *
     * @描述：根据流读取Excel文件
     *
     * @作者：陈王明
     *
     * @时间：2017-7-30
     *
     * @参数：@param inputStream
     *
     * @参数：@param isExcel2003
     *
     * @参数：@return
     *
     * @返回值：List
     */

    public List<List<String>> read(InputStream inputStream, boolean isExcel2003) throws IOException {
        List<List<String>> dataLst = null;
        try
        {
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            if (isExcel2003)
            {
                wb = new HSSFWorkbook(inputStream);
            }
            else
            {
                wb = new XSSFWorkbook(inputStream);
            }
            dataLst = read(wb);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return dataLst;
    }

    /**
     *
     * @描述：读取数据
     *
     * @作者：陈王明
     *
     * @时间：2017-7-30
     *
     * @参数：@param Workbook
     *
     * @参数：@return
     *
     * @返回值：List<List<String>>
     */

    private List<List<String>> read(Workbook wb)
    {

        List<List<String>> dataLst = new ArrayList<List<String>>();
       if(wb.getNumberOfSheets()>0)
       {
           for (int i=0;i<wb.getNumberOfSheets();i++)
           {
               /** 得到第一个shell */
               Sheet sheet = wb.getSheetAt(i);
               /** 得到Excel的行数 */
               this.totalRows = sheet.getPhysicalNumberOfRows();
               /** 得到Excel的列数 */
               if (this.totalRows >= 1 && sheet.getRow(0) != null)
               {
                   this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
               }

               //region
               /** 循环Excel的行 */
               for (int r = 0; r < this.totalRows; r++)
               {
                   Row row = sheet.getRow(r);
                   if (row == null)
                   {
                       continue;
                   }
                   List<String> rowLst = new ArrayList<String>();
                   //region
                   /** 循环Excel的列 */
                   for (int c = 0; c < this.getTotalCells(); c++)
                   {
                       Cell cell = row.getCell(c);
                       String cellValue = "";
                       //region
                       if (null != cell)
                       {

                           // 以下是判断数据的类型
                           switch (cell.getCellType())
                           {
                               case HSSFCell.CELL_TYPE_NUMERIC: // 数字

                                   if (HSSFDateUtil.isCellDateFormatted(cell))
                                   {
                                       Date date = cell.getDateCellValue() ;
                                       cellValue=date.toString();
                                   }else{
                                       cellValue = cell.getNumericCellValue() + "";
                                   }
                                   //cellValue= cell.getDateCellValue();
                                   break;
                               case HSSFCell.CELL_TYPE_STRING: // 字符串
                                   cellValue = cell.getStringCellValue();
                                   break;
                               case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                                   cellValue = cell.getBooleanCellValue() + "";
                                   break;
                               case HSSFCell.CELL_TYPE_FORMULA: // 公式
                                   cellValue = cell.getCellFormula() + "";
                                   break;

                               case HSSFCell.CELL_TYPE_BLANK: // 空值
                                   cellValue = "";
                                   break;
                               case HSSFCell.CELL_TYPE_ERROR: // 故障
                                   cellValue = "非法字符";
                                   break;
                               default:
                                   cellValue = "未知类型";
                                   break;
                           }
                       }

                       //endregion
                       rowLst.add(cellValue);
                   }
                   /** 保存第r行的第c列 */
                   //endregion
                   dataLst.add(rowLst);
               }
               //endregion

           }
       }
        return dataLst;
    }
}
/**
 *
 * @描述：工具类
 *
 * @作者：陈王明
 *
 * @时间：2017-7-30
 */

class WDWUtil
{

    /**
     *
     * @描述：是否是2003的excel，返回true是2003
     *
     * @作者：陈王明
     *
     * @时间：2017-7-30
     *
     * @参数：@param filePath　文件完整路径
     *
     * @参数：@return
     *
     * @返回值：boolean
     */

    public static boolean isExcel2003(String filePath)
    {

        return filePath.matches("^.+\\.(?i)(xls)$");

    }

    /**
     *
     * @描述：是否是2007的excel，返回true是2007
     *
     * @作者：陈王明
     *
     * @时间：2017-7-30
     *
     * @参数：@param filePath　文件完整路径
     *
     * @参数：@return
     *
     * @返回值：boolean
     */

    public static boolean isExcel2007(String filePath)
    {

        return filePath.matches("^.+\\.(?i)(xlsx)$");

    }
}