package application;

import application.listener.AFormatFieldChangeListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.lang.reflect.Field;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;

public class MainController implements Initializable {

	final MyProperties prop = new MyProperties();
    @FXML
    private Button aButton1;
    @FXML
    private Button aButton2;
    @FXML
    private Button aButton3;
    @FXML
    private Button aButton4;
    @FXML
    private Button aButton5;

    @FXML
    private TextField aFormatField1;
    @FXML
    private TextField aFormatField2;
    @FXML
    private TextField aFormatField3;
    @FXML
    private TextField aFormatField4;
    @FXML
    private TextField aFormatField5;

    /**
     * パネルA：日付フォーマット
     * ボタン1クリック
     * @param	event	イベント
     */
    @FXML
    void onActionAButton(ActionEvent event) {
    	// TODO 動的にします。
    	Button o = (Button)event.getSource();
    	String id = o.getId();

    	Matcher matcher = Pattern.compile("aButton(\\d)").matcher(id);
    	if(matcher.matches()) {
    		System.out.println("aFormatField" + matcher.group(1));
    		try {
    			Field field = this.getClass().getDeclaredField("aFormatField" + matcher.group(1));
    			TextField textField = (TextField)field.get(this);
    			
            	String text = textField.getText();
            	SimpleDateFormat formatter = new SimpleDateFormat(text);
            	String formttedString = formatter.format(new Date());
            	System.out.println(formttedString);
            	
            	Clipboard cb = Clipboard.getSystemClipboard();
            	final Map<DataFormat, Object> content = new HashMap<>();
            	content.put(DataFormat.PLAIN_TEXT, formttedString);
            	cb.setContent(content);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
	
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.prop.readProp();
		
		for(Field field: this.getClass().getDeclaredFields()) {
			String name = field.getName();
			System.out.println(name + " " + field.getType());
			String value = this.prop.getProperty(name);
			System.out.println(value);
			if (value != null && value.length() > 0) {
				if (field.getType().equals(TextField.class)) {
					try {
						((TextField)field.get(this)).setText(value);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			}
		}
		this.aFormatField1.textProperty().addListener(new AFormatFieldChangeListener(this.prop, this.aFormatField1));
		this.aFormatField2.textProperty().addListener(new AFormatFieldChangeListener(this.prop, this.aFormatField2));
		this.aFormatField3.textProperty().addListener(new AFormatFieldChangeListener(this.prop, this.aFormatField3));
		this.aFormatField4.textProperty().addListener(new AFormatFieldChangeListener(this.prop, this.aFormatField4));
		this.aFormatField5.textProperty().addListener(new AFormatFieldChangeListener(this.prop, this.aFormatField5));
	}

}
