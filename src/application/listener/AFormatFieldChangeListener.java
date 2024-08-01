package application.listener;

import application.MyProperties;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class AFormatFieldChangeListener implements ChangeListener<String> {
	
	MyProperties prop = null;
	
	TextField aFormatField = null;
	
	public AFormatFieldChangeListener(MyProperties prop, TextField aFormatField) {
		this.prop = prop;
		this.aFormatField = aFormatField;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String before, String after) {
		System.out.println("change");
		prop.setProperty(aFormatField.getId(), after);
		prop.writeProp();
	}

}
