package laba3b;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
public class GornerTableCell  implements TableCellRenderer {
	 private JPanel panel=new JPanel();
	 private JLabel label=new JLabel();
	 
	 private String needle = null;
	 private DecimalFormat formatter =(DecimalFormat)NumberFormat.getInstance();
	 public GornerTableCell() {
	 // ���������� ������ 5 ������ ����� �������
	 formatter.setMaximumFractionDigits(5);
	 // �� ������������ ����������� (�.�. �� �������� ������
	 // �� ��������, �� ���������), �.�. ���������� ����� ��� "1000",
	 // � �� "1 000" ��� "1,000"
	 formatter.setGroupingUsed(false);
	 // ���������� � �������� ����������� ������� ����� �����, � ��
	 // �������. �� ���������, � ������������ ����������
	 // ������/�������� ������� ����� ���������� �������
	 DecimalFormatSymbols dottedDouble= formatter.getDecimalFormatSymbols();
	 dottedDouble.setDecimalSeparator('.');
	
	 formatter.setDecimalFormatSymbols(dottedDouble);
	 // ���������� ������� ������ ������
	 panel.add(label);
	 // ���������� ������������ ������� �� ������ ���� ������
	 panel.setLayout(new FlowLayout(FlowLayout.LEFT));
	 }
	 
	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		String formattedDouble = formatter.format(value);
		
		Double ValueOfCell = Double.valueOf(formattedDouble).doubleValue();
		
		// ���������� ����� ������� ������ ���������� ������������� �����
		label.setText(formattedDouble);
		long integer = Math.round((ValueOfCell - ValueOfCell.intValue())*10000);
	
		if(integer!=0 && ((ValueOfCell - ValueOfCell.intValue())>=0.1)) {
		     while(integer%10 == 0) {
			    integer/=10;
		    }
		}
		
		if((col == 0 || col==1) && ((ValueOfCell - ValueOfCell.intValue())>=0.1) && (integer == ValueOfCell.intValue())) {
	    	panel.setBackground(Color.GREEN);
		}
		
		else if (col==1 && needle!=null && needle.equals(formattedDouble)) {
		// ����� ������� = 1 (�.�. ������ �������) + ������ �� null
		// (������ ���-�� ����) +
		// �������� ������ ��������� �� ��������� ������ ������� -
		// �������� ������ ��� ������ � ������� ����
		panel.setBackground(Color.RED);
		} 
		else {
		// ����� - � ������� �����
		panel.setBackground(Color.WHITE);
		}
		return panel;	
		
	}
	public void setNeedle(String needle) {
		this.needle = needle;
		}

}
