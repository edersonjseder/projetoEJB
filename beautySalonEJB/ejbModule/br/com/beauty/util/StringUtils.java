package br.com.beauty.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {
	
    public String bigDecimalToString(BigDecimal valores){
        DecimalFormat dFormat = null;
        try
        {
            dFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
            dFormat.setPositivePrefix(dFormat.getDecimalFormatSymbols().getCurrencySymbol() + " ");
        }
        catch(Exception e){}
        
        return dFormat.format(valores.doubleValue());
    }
    
    public String bigDecimalToStringSimple(BigDecimal valores){
    	DecimalFormat dFormat = null;
    	try
    	{
    		dFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
    	}
    	catch(Exception e){}
    	
    	return dFormat.format(valores.doubleValue());
    }
    
    public String dateToString(Date data){
        String dataConvertida = null;
        try
        {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            if(data != null)
                dataConvertida = formato.format(data);
        }
        catch(Exception e){}
        
        return dataConvertida;
    }

}
