package laba3b;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel  extends AbstractTableModel {

	 private Double[] coeff;
	 private Double from;
	 private Double to;
	 private Double step;
	 
	public GornerTableModel(Double [] coeff, Double from, Double to, Double step) {
		this.coeff=coeff;
		this.from=from;
		this.to=to;
		this.step=step;
	}
	
	public Double[] getCoeff() {
        return coeff;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }
    
	
	public int getColumnCount() {
		return 3;
	}

	
	public int getRowCount() {
		return new Double(Math.ceil((to-from)/step)).intValue()+1;
	}
	
	public String getColumnName(int col) {
        switch (col)
        {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена при прямом порядке коэффицентов";
          
            default:
                return "Целая часть палиндром?";
        }
    }
	
	public Class<?> getColumnClass(int col) {
		if(col==2) {
			return Boolean.class;
		}
        return Double.class;
    }
	
	/*boolean isPalindrome(Double s) {
		
		  int n = s.length();
		  for (int i = 0; i < (n/2); ++i) {
		     if (s.charAt(i) != s.charAt(n - i - 1)) {
		         return false;
		     }
		  }

		  return true;
		}*/
	
	public Object getValueAt(int row, int col) {
		
		Double x=from+step*row;
        Double result1=0.0;
        

        for (int i = 0; i < coeff.length; i++) {
        	result1 = result1 * x + coeff[i];
        	
        }
        
        if(col==0) {
        	return x;
        }            
        else if (col==1)
            return result1;
       else if (col==2) {
    	   		int integer=result1.intValue();
    	   		String src=Integer.toString (integer);
    	   		
    	        src = src.replaceAll("[\\s]", "").toLowerCase();
    	        boolean result = true;
    	        for (int i = 0; i < src.length() / 2; i++) {
    	            if (src.charAt(i) != src.charAt(src.length() - i - 1)) {
    	                result = false;
    	                break;
    	            }
    	        }
    	        return result;
    	    
       }    	   
            
		return 0;
       
		
		
		
		
		}

	}


