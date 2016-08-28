package br.com.beauty.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.beauty.utils.PropertiesUtil;

public class PropertiesUtil {
	
	private static Properties props = null;
	
	private static Properties getProperties() throws IOException {
		if(props == null){
			InputStream is = PropertiesUtil.class.getResourceAsStream("/br/com/beauty/bundle/messages.properties");
			props = new Properties();
			props.load(is);
			is.close();
		}
		return props;
	}
	
	public static String getProperty(String key){
		try{
			return getProperties().getProperty(key);
			
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
