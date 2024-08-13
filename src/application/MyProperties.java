package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * プロパティファイル読み書き
 */
public class MyProperties extends Properties{
	
	/** シリアライズ */
	private static final long serialVersionUID = -6639651726149554727L;

	/** プロパティファイル相対パス（jpackage(Wix)で作成するパス） */
	final static String PROP_PATH = ".\\app\\prop.xml";
	
	/**
	 * プロパティファイル読み込み
	 */
	public void readProp() {
		try (FileInputStream is = new FileInputStream(PROP_PATH)) {
			this.loadFromXML(is);
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
	/**
	 * プロパティファイル書き出し
	 */
	public void writeProp() {
        try (OutputStream os = new FileOutputStream(PROP_PATH)) {
            this.storeToXML(os, new Date().toString());
        } catch (IOException e) {
			e.printStackTrace();
		}
	}}
