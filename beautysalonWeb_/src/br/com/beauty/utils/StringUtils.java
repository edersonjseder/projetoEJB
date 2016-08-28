package br.com.beauty.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {
	
	public static final String ROLE_ADMINISTRACAO = "administracao";
	public static final String ROLE_ATENDENTE = "atendente";
	public static final String ROLE_CAIXA = "caixa";
	public static final String ROLE_GERENTE = "gerente";
	
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
    
    public Date stringToDate(String data){
    	Date dataConvertida = null;
    	try
    	{
    		DateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
    		if(data != null)
    			dataConvertida = formato.parse(data);
    	}
    	catch(Exception e){}
    	
    	return dataConvertida;
    }
    
    public Date stringFormatadoToDate(String data){
        Date dataConvertida = null;
        try
        {
            DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            if(data != null)
                dataConvertida = formato.parse(data);
        }
        catch(Exception e){}
        
        return dataConvertida;
    }

}
