/**
 *
 * Copyright 2018 Afterschool Creatives "Captivating Creativity"
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package gov.dict.ams.ui.home;

import com.jfoenix.controls.JFXButton;
import gov.dict.ams.ApplicationForm;
import gov.dict.ams.Properties;
import gov.dict.ams.models.AttendeeModel;
import gov.dict.ams.models.AttendeeModel.Gender;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.afterschoolcreatives.polaris.java.io.FileTool;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 *
 * @author Joemar
 */
public class Settings extends ApplicationForm {

    @FXML
    private ImageView imgvw_logo;
    
    @FXML
    private Label lbl_event_name;

    @FXML
    private Label lbl_venue;

    @FXML
    private Label lbl_date;

    @FXML
    private Label lbl_male_count;

    @FXML
    private Label lbl_female_count;

    @FXML
    private JFXButton btn_export;

    @FXML
    private JFXButton btn_import;
    
    @FXML
    private JFXButton btn_upload;

    @FXML
    private JFXButton btn_back;
    
    @FXML
    private TextField txt_event_name;

    @FXML
    private TextField txt_venue;

    @FXML
    private TextField txt_date;

    @FXML
    private JFXButton btn_save_changes;
    
    @FXML
    private Label lbl_show_folder;
    
    @FXML
    private ImageView btn_about;
    
    @Override
    protected void setup() {
        this.lbl_show_folder.setVisible(false);
        this.loadText();
        this.refreshStatus();
        this.eventHandler();
    }
    
    private void eventHandler() {
        this.btn_back.setOnMouseClicked((MouseEvent value) -> {
            this.changeRoot(new SystemHome().load());
        });
        
        this.btn_export.setOnMouseClicked((MouseEvent value) -> {
            this.exportExcel();
        });
        
        this.btn_upload.setOnMouseClicked((MouseEvent value) -> {
            this.upload();
        });
        this.btn_save_changes.setOnMouseClicked((MouseEvent value) -> {
            this.saveText();
        });
        this.lbl_show_folder.setOnMouseClicked((MouseEvent value) -> {
            this.openDirFolder();
        });
        this.btn_about.setOnMouseClicked((MouseEvent value) -> {
            this.changeRoot(new About().load());
        });
        this.btn_import.setOnMouseClicked((MouseEvent value) -> {
            try {
                this.importExcel();
            } catch (IOException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.imgvw_logo.setOnMouseClicked((MouseEvent value) -> {
            this.changeRoot(new SystemHome().load());
        });
    }
    
    private void upload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PDF File", "*.pdf")
        );
        File file = fileChooser.showOpenDialog(this.getStage());

        if(file == null) {
            return;
        }

        try {
            if (!FileTool.checkFolders("template")) {
                System.out.println("IM HERE");
            }
            boolean res = FileTool.copyQuietly(file, new File("template" + File.separator + "certification_template.pdf"));
            if(res) {
                this.showInformationMessage("Upload Successfully", "Your new template is successfully uploaded.");
            } else {
                this.showErrorMessage("Unable to Upload", "Please try again the upload later.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getCellStringValue(HSSFRow row, int cell) {
        try {
            return row.getCell(cell, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
        } catch (java.lang.NullPointerException e) {
            return "";
        } catch(java.lang.IllegalStateException a) {
            return (String.valueOf(row.getCell(cell, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue()));
        }
    }
    
    private void importExcel() throws IOException, SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import an Excel File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (xlsx and xls)", "*.xlsx", "*.xls");
        fileChooser.getExtensionFilters().setAll(extFilter);
        // select
        File selectedFile = fileChooser.showOpenDialog(this.getStage());

        if (selectedFile == null) {
            return;
        }
        ArrayList<ExcelObject> collection = this.readContent(selectedFile);
        int added = 0, notAdded = 0, retrieved = 0, notRetrieved = 0;
        for(ExcelObject obj : collection) {
            List<AttendeeModel> models = null;  
            try {
                models = AttendeeModel.getByID((obj.id));
                System.out.println("ID " + obj.id);
            } catch (NullPointerException e) {
            }
            if(models == null || models.isEmpty()) {
                System.out.println("NO FOUND");
                AttendeeModel modelNew = new AttendeeModel();
                modelNew.setActive(1);
                modelNew.setEmail(obj.email.toLowerCase().trim());
                modelNew.setFirstName(obj.firstName.toUpperCase().trim());
                modelNew.setGender((obj.gender.trim().equalsIgnoreCase(Gender.FEMALE) || obj.gender.equalsIgnoreCase("F")? "F" : "M"));
                modelNew.setLastName(obj.lastName.toUpperCase().trim());
                modelNew.setMiddleInitial(obj.middleInitial.toUpperCase().trim());
                modelNew.setSuffix(obj.suffix.toUpperCase().trim());
                modelNew.setAddress(obj.address.toUpperCase().trim());
                modelNew.setContact_number(obj.contact_no.toUpperCase().trim());
                if(AttendeeModel.insert(modelNew)) {
                    added++;
                } else {
                    notAdded++;
                }
            } else {
                System.out.println("ID IS EXISTING");
                for(AttendeeModel each : models) {
                    if(!each.getActive().equals(1)) {
                        each.setActive(1);
                        if(AttendeeModel.update(each)) {
                            retrieved++;
                        } else {
                            notRetrieved++;
                        }
                    }
                }
            }
        }
        String msg = added + " record added\n" + 
                notAdded + " failed in add\n" +
                retrieved + " record retrieved\n" +
                notRetrieved + " failed in retrieve";
        this.showInformationMessage("Summary", msg);
    }
    
    public final static int COL_ID = 0;
    public final static int COL_LAST_NAME = 1;
    public final static int COL_FIRST_NAME = 2;
    public final static int COL_MIDDLE_INITIAL = 3;
    public final static int COL_SUFFIX = 4;
    public final static int COL_ADDRESS = 5;
    public final static int COL_GENDER = 6;
    public final static int COL_EMAIL = 7;
    public final static int COL_CONTACT_NO = 8;
    private ArrayList<ExcelObject> readContent(File excelFile) throws FileNotFoundException, IOException {
        
        FileInputStream excelFileStream = null;
        try {
            // process excel
            excelFileStream = new FileInputStream(excelFile);
            HSSFWorkbook excel = new HSSFWorkbook(excelFileStream);
            /**
             * The excel Sheet.
             */
            HSSFSheet excelSheet = excel.getSheetAt(0);

            final ArrayList<ExcelObject> excelContents = new ArrayList<>();
            for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                HSSFRow excelRow = excelSheet.getRow(row);
                if(!getCellStringValue(excelRow, COL_LAST_NAME).trim().isEmpty() && !getCellStringValue(excelRow, COL_FIRST_NAME).trim().isEmpty()) {
                    ExcelObject object = new ExcelObject();
                    try {
                        object.setId(Integer.valueOf(getCellStringValue(excelRow, COL_ID)));
                    } catch (NumberFormatException e) {
                    }
                    object.setLastName(getCellStringValue(excelRow, COL_LAST_NAME));
                    object.setFirstName(getCellStringValue(excelRow, COL_FIRST_NAME));
                    object.setMiddleInitial(getCellStringValue(excelRow, COL_MIDDLE_INITIAL));
                    object.setSuffix(getCellStringValue(excelRow, COL_SUFFIX));
                    object.setGender(getCellStringValue(excelRow, COL_GENDER));
                    object.setEmail(getCellStringValue(excelRow, COL_EMAIL));
                    object.setAddress(getCellStringValue(excelRow, COL_ADDRESS));
                    object.setContact_no(getCellStringValue(excelRow, COL_CONTACT_NO));
                    excelContents.add(object);
                }
            }

            return excelContents;
        } finally {
            if (excelFileStream != null) {
                try {
                    excelFileStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
    
    
    /**
     * Excel Object representing the columns of the excel file record.
     */
    public static class ExcelObject {

        private Integer id;
        private String lastName;
        private String firstName;
        private String middleInitial;
        private String gender;
        private String email;
        private String suffix;
        private String address;
        private String contact_no;

        /**
         * Initialize Strings with empty content.
         */
        public ExcelObject() {
            this.id = null;
            this.lastName = "";
            this.firstName = "";
            this.middleInitial = "";
            this.gender = "";
            this.email = "";
            this.suffix = "";
            this.address = "";
            this.contact_no = "";
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setMiddleInitial(String middleInitial) {
            this.middleInitial = middleInitial;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setContact_no(String contact_no) {
            this.contact_no = contact_no;
        }

    } // end of excel object.
    
    private void openDirFolder() {
        boolean suuported = false;
        if (Desktop.isDesktopSupported()) {
            if (Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                try {
                    Desktop.getDesktop().open(selectedDirectory);
                } catch (Exception e) {
                    this.showErrorMessage("Failed","The folder is not existing or is empty. Try creating certificates first before opening.");
                }
                suuported = true;
            }
        }
        if (!suuported) {
            this.showWarningMessage("Failed","Action Not Supported in this Operating System");
        }
    }
    
    private void loadText() {
        Properties.instantiate();
        // set label
        this.lbl_date.setText(Properties.getProperty("lbl_date"));
        this.lbl_event_name.setText(Properties.getProperty("lbl_event_name"));
        this.lbl_venue.setText(Properties.getProperty("lbl_venue"));
        // set textfields
        this.txt_date.setText(Properties.getProperty("lbl_date"));
        this.txt_event_name.setText(Properties.getProperty("lbl_event_name"));
        this.txt_venue.setText(Properties.getProperty("lbl_venue"));
    }
    
    private void saveText() {
        Properties.instantiate();
        Properties.setProperty("lbl_date", this.txt_date.getText().trim());
        Properties.setProperty("lbl_event_name", this.txt_event_name.getText().trim());
        Properties.setProperty("lbl_venue", this.txt_venue.getText().trim());
        if(Properties.saveProperty()) {
            this.showInformationMessage("Saved Successfully", "Successfully updated the Header Display.");
            this.loadText();
        } else {
            this.showErrorMessage("Cannot Save Changes", "Please try saving again later.");
        }
    }
    
    private File selectedDirectory = null;
    private void exportExcel() {
        //choose location
        DirectoryChooser directoryChooser = new DirectoryChooser();
        selectedDirectory = directoryChooser.showDialog(this.getStage());
        if(selectedDirectory ==  null) {
            return;
        }
        
        //create workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        //create sheet
        sheet = workbook.createSheet("Attendee");
        
        // font
        HSSFFont font1 = createHSSFFont(workbook, 11, true);
        
        // style
        HSSFCellStyle style1 = createHSSFCellStyle(workbook, true, true, font1);
        
        // row
        HSSFRow row1 = sheet.createRow((short) 0);
        HSSFRow row2 = sheet.createRow((short) 1);
        HSSFRow row3 = sheet.createRow((short) 2);
        
        //create header
        createHSSFCell("ID", row1, COL_ID, style1);
        createHSSFCell("LAST NAME", row1, COL_LAST_NAME, style1);
        createHSSFCell("FIRST NAME", row1, COL_FIRST_NAME, style1);
        createHSSFCell("MI", row1, COL_MIDDLE_INITIAL, style1);
        createHSSFCell("SUFFIX", row1, COL_SUFFIX, style1);
        createHSSFCell("GENDER", row1, COL_GENDER, style1);
        createHSSFCell("EMAIL ADDRESS", row1, COL_EMAIL, style1);
        createHSSFCell("CURRENT ADDRESS", row1, COL_ADDRESS, style1);
        createHSSFCell("CONTACT NO.", row1, COL_CONTACT_NO, style1);

        try {
            List<AttendeeModel> content = AttendeeModel.listAllActive(null);
            int ctrRow = 1, countMale = 0, countFemale = 0;
            
            HSSFFont font2 = createHSSFFont(workbook, 10, false);
            HSSFCellStyle style2 = createHSSFCellStyle(workbook, true, true, font2);
            HSSFCellStyle style3 = createHSSFCellStyle(workbook, true, false, font2);
            
            for(AttendeeModel eachModel: content) {
                // create row
                HSSFRow row = sheet.createRow((short) ctrRow);
                createHSSFCell(eachModel.getId() + "", row, COL_ID, style2);
                createHSSFCell(eachModel.getLastName(), row, COL_LAST_NAME, style3);
                createHSSFCell(eachModel.getFirstName(), row, COL_FIRST_NAME, style3);
                createHSSFCell(eachModel.getMiddleInitial(), row, COL_MIDDLE_INITIAL, style2);
                createHSSFCell(eachModel.getSuffix(), row, COL_SUFFIX, style2);
                createHSSFCell(eachModel.getGender(), row, COL_GENDER, style2);
                createHSSFCell(eachModel.getEmail(), row, COL_EMAIL, style2);
                createHSSFCell(eachModel.getAddress(), row, COL_ADDRESS, style3);
                createHSSFCell(eachModel.getContact_number(), row, COL_CONTACT_NO, style2);
                if(eachModel.getGender().equalsIgnoreCase("F")) {
                    countFemale++;
                } else {
                    countMale++;
                }
                ctrRow++;
            }
            
            // status display
            HSSFFont font3 = createHSSFFont(workbook, 10, true);
            HSSFCellStyle style4 = createHSSFCellStyle(workbook, true, false, font3);
            HSSFCellStyle style5 = createHSSFCellStyle(workbook, true, true, font3);
            int firstCol = COL_CONTACT_NO + 2;
            int secondCol = COL_CONTACT_NO + 3;
            createHSSFCell("Male               ", row1, firstCol, style4);
            createHSSFCell("   " + countMale + "   ", row1, secondCol, style5);
            createHSSFCell("Female", row2, firstCol, style4);
            createHSSFCell(countFemale + "", row2, secondCol, style5);
            createHSSFCell("Overall Total", row3, firstCol, style4);
            createHSSFCell((ctrRow-1) + "", row3, secondCol, style5);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
            String filePath = selectedDirectory.getAbsolutePath() + "\\AMS_" + sdf.format(new Date()) + "_EXPORTED.xls";
            
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            this.lbl_show_folder.setVisible(true);
            this.showInformationMessage("Exported Successfully", "Your exported excel file is saved. " + filePath);
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private HSSFSheet sheet;
    private HSSFCell createHSSFCell(String text, HSSFRow row, 
            int col, 
            HSSFCellStyle style) {
        HSSFCell cell = row.createCell((short) col);
        cell.setCellValue(text);
        cell.setCellStyle(style);
        sheet.autoSizeColumn(col);
        return cell;
    }
    
    private HSSFFont createHSSFFont(HSSFWorkbook workbook, 
            int fontHeight, 
            boolean bold) {
        HSSFFont font = (HSSFFont)workbook.createFont();
        font.setFontName("Century Gothic");
        font.setFontHeightInPoints((short)fontHeight);
        font.setBold(bold);
        return font;
    }
    
    private HSSFCellStyle createHSSFCellStyle(HSSFWorkbook workbook, 
            boolean border, 
            boolean center,
            HSSFFont font) {
        HSSFCellStyle style = workbook.createCellStyle();
        if(border) {
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setLeftBorderColor(HSSFColor.BLACK.index);
            style.setRightBorderColor(HSSFColor.BLACK.index);
            style.setTopBorderColor(HSSFColor.BLACK.index);
        }
        if(center) {
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        style.setFont(font);
        return style;
    }
    
    private void refreshStatus() {
        Integer maleCount = 0, femaleCount = 0;
        List<AttendeeModel> content;
        try {
            content = AttendeeModel.listAllActive(null);
            for(AttendeeModel each: content) {
                if(each.getGender() != null) {
                    if(each.getGender().equalsIgnoreCase("M")) {
                        maleCount++;
                    } else {
                        femaleCount++;
                    }
                }
            }
            
            this.lbl_female_count.setText(femaleCount + "");
            this.lbl_male_count.setText(maleCount + "");
            return;
        } catch (SQLException ex) {
            Logger.getLogger(SystemHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.lbl_female_count.setText("0");
        this.lbl_male_count.setText("0");
    }
}
