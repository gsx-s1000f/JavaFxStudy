package application;

import java.util.Date;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;

public class MainController implements Initializable {
	
	final String PROP_PATH = ".\\app\\prop.xml";
	
	final Properties prop = new Properties();

    @FXML
    private Button aButton1;

    @FXML
    private TextField aFormatField1;

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
    	if(id == null) {
    		return;
    	} else if(id.equals("aButton1")) {
        	String text = this.aFormatField1.getText();
        	SimpleDateFormat formatter = new SimpleDateFormat(text);
        	String formttedString = formatter.format(new Date());
        	System.out.println(formttedString);
        	
        	Clipboard cb = Clipboard.getSystemClipboard();
        	final Map<DataFormat, Object> content = new HashMap<>();
        	content.put(DataFormat.PLAIN_TEXT, formttedString);
        	cb.setContent(content);
    	}
	
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.readProp();
		
		for(Field field: this.getClass().getDeclaredFields()) {
			String name = field.getName();
			System.out.println(name + " " + field.getType());
			String value = this.prop.getProperty(name);
			System.out.println(value);
			if (value != null && value.length() > 0) {
				if (field.getType().equals(TextField.class)) {
					System.out.println("あ");
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
		this.aFormatField1.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> observable, String before, String after) {
							System.out.println("change");
							prop.setProperty("aFormatField1", after);
							writeProp();
						}
				});
	}
	void readProp() {
		try (FileInputStream is = new FileInputStream(PROP_PATH)) {
			prop.loadFromXML(is);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void writeProp() {
        try (OutputStream os = new FileOutputStream(PROP_PATH)) {
            prop.storeToXML(os, new Date().toString());
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
