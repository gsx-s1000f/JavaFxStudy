package application.listener;

import application.MyProperties;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * パネルA：テキストフィールドの変更を受けるリスナー
 */
public class AFormatFieldChangeListener implements ChangeListener<String> {
	
	MyProperties prop = null;
	
	TextField aFormatField = null;
	
	/**
	 * コンストラクタ
	 * @param prop	
	 * @param aFormatField
	 */
	public AFormatFieldChangeListener(MyProperties prop, TextField aFormatField) {
		this.prop = prop;
		this.aFormatField = aFormatField;
	}

	/**
	 * 変更受付
	 */
	@Override
	public void changed(ObservableValue<? extends String> observable, String before, String after) {
		System.out.println("change");
		prop.setProperty(aFormatField.getId(), after);
		prop.writeProp();
	}

}
