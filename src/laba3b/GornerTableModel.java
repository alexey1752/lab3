package lab3;
import javax.swing.table.AbstractTableModel;

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
        return Double.class;
    }

	
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
       /* else if (col==2)
            return 0;//result2;*/
		return 0;
       
		
		
		
		
		}

	}


