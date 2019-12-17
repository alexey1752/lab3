package laba3b;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class mainframe extends  JFrame {
	private static final int width = 1200;
    private static final int height = 500;

    private Double[] coeff;

    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem searchValueMenuItem;

   

    private JTextField from_field;
    private JTextField to_field;
    private JTextField step_field;
    private Box BoxResult;

    private GornerTableCell cell = new GornerTableCell();

    private GornerTableModel data;
    private JFileChooser fileChooser = null;

    


    public mainframe(Double[] coeff) {
        super("Табулирование многочлена на отрезке по схеме Горнера");
        this.coeff = coeff;
        setSize(width, height);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - width) / 2,
                (kit.getScreenSize().height - height) / 2);

        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        JMenu fileMenu = new JMenu("Файл");
        menu.add(fileMenu);
        JMenu tableMenu = new JMenu("Таблица");
        menu.add(tableMenu);
       

        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if(fileChooser.showSaveDialog(mainframe.this)==JFileChooser.APPROVE_OPTION)
                   saveToTextFile(fileChooser.getSelectedFile());
            }
        };
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        saveToTextMenuItem.setEnabled(true);

        Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения графика") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                 if(fileChooser.showSaveDialog(mainframe.this)==JFileChooser.APPROVE_OPTION)
                   saveToGraphicsFile(fileChooser.getSelectedFile());
            }
        };
        saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
        saveToGraphicsMenuItem.setEnabled(false);

       

   

        Action searchValueAction = new AbstractAction("Найти значение многочлена") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String value = JOptionPane.showInputDialog(mainframe.this,
                        "Введите значение для поиска", "Поиск значения",
                        JOptionPane.QUESTION_MESSAGE);
                cell.setNeedle(value);
                getContentPane().repaint();
            }
        };
        searchValueMenuItem = tableMenu.add(searchValueAction);
     // По умолчанию пункт меню является недоступным (данных ещѐ нет)
        searchValueMenuItem.setEnabled(false);


     // Создать область с полями ввода для границ отрезка и шага
     // Создать подпись для ввода левой границы отрезка
     JLabel labelForFrom = new JLabel("X изменяется на интервале от:");
     // Создать текстовое поле для ввода значения длиной в 10 символов
     // со значением по умолчанию 0.0
     from_field = new JTextField("0.0", 10);
     // Установить максимальный размер равный предпочтительному, чтобы
     // предотвратить увеличение размера поля ввода
     from_field.setMaximumSize(from_field.getPreferredSize());
     // Создать подпись для ввода левой границы отрезка
     JLabel labelForTo = new JLabel("до:");
     // Создать текстовое поле для ввода значения длиной в 10 символов
     // со значением по умолчанию 1.0
     to_field = new JTextField("1.0", 10);
     // Установить максимальный размер равный предпочтительному, чтобы
     // предотвратить увеличение размера поля ввода
     to_field.setMaximumSize(to_field.getPreferredSize());
     // Создать подпись для ввода шага табулирования
     JLabel labelForStep = new JLabel("с шагом:");
     // Создать текстовое поле для ввода значения длиной в 10 символов
     // со значением по умолчанию 1.0
     step_field = new JTextField("0.1", 10);
     // Установить максимальный размер равный предпочтительному, чтобы
     // предотвратить увеличение размера поля ввода
     step_field.setMaximumSize(step_field.getPreferredSize());
     // Создать контейнер 1 типа "коробка с горизонтальной укладкой"
     Box hboxRange = Box.createHorizontalBox();
     // Задать для контейнера тип рамки "объѐмная"
     hboxRange.setBorder(BorderFactory.createBevelBorder(1));
     // Добавить "клей" C1-H1
     hboxRange.add(Box.createHorizontalGlue());
     // Добавить подпись "От"
     hboxRange.add(labelForFrom);
     // Добавить "распорку" C1-H2
     hboxRange.add(Box.createHorizontalStrut(10));
     // Добавить поле ввода "От"
     hboxRange.add(from_field);
     // Добавить "распорку" C1-H3
     hboxRange.add(Box.createHorizontalStrut(20));
     // Добавить подпись "До"
     hboxRange.add(labelForTo);
     // Добавить "распорку" C1-H4
     hboxRange.add(Box.createHorizontalStrut(10));
     // Добавить поле ввода "До"
     hboxRange.add(to_field);
     // Добавить "распорку" C1-H5
     hboxRange.add(Box.createHorizontalStrut(20));
     // Добавить подпись "с шагом"
     hboxRange.add(labelForStep);
     // Добавить "распорку" C1-H6
     hboxRange.add(Box.createHorizontalStrut(10));
     // Добавить поле для ввода шага табулирования
     hboxRange.add(step_field);
     // Добавить "клей" C1-H7
     hboxRange.add(Box.createHorizontalGlue());
     // Установить предпочтительный размер области равным удвоенному
     // минимальному, чтобы при компоновке область совсем не сдавили
     hboxRange.setPreferredSize(new Dimension(
     
     new Double(hboxRange.getMaximumSize().getWidth()).intValue(),
     new Double(hboxRange.getMinimumSize().getHeight()).intValue()*2));
     // Установить область в верхнюю (северную) часть компоновки
     getContentPane().add(hboxRange, BorderLayout.NORTH);
     
     
     
     
       
        JButton buttonCalc = new JButton("Вычислить");
     // Задать действие на нажатие "Вычислить" и привязать к кнопке
     buttonCalc.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent ev) {
     try {
	     // Считать значения начала и конца отрезка, шага
	     Double from = Double.parseDouble(from_field.getText());
	     Double to = Double.parseDouble(to_field.getText());
	     Double step = Double.parseDouble(step_field.getText());
	     // На основе считанных данных создать новый      экземпляр модели таблицы
	     data = new GornerTableModel(mainframe.this.coeff, from, to, step);
	     // Создать новый экземпляр таблицы
	     JTable table = new JTable(data);
	     // Установить в качестве визуализатора ячеек для     класса Double разработанный визуализатор
	     table.setDefaultRenderer(Double.class,cell);
	     // Установить размер строки таблицы в 30     пикселов
	     table.setRowHeight(30);
	     // Удалить все вложенные элементы из контейнера     hBoxResult    
	     BoxResult.removeAll();
	     // Добавить в hBoxResult таблицу, "обѐрнутую" в      панель с полосами прокрутки
	     BoxResult.add(new JScrollPane(table));
	     // Обновить область содержания главного окна
	     getContentPane().validate();
	     // Пометить ряд элементов меню как доступных
	     saveToTextMenuItem.setEnabled(true);
	     saveToGraphicsMenuItem.setEnabled(true);
	     searchValueMenuItem.setEnabled(true);
     } catch (NumberFormatException ex) {
		     // В случае ошибки преобразования чисел показать     сообщение об ошибке
		     JOptionPane.showMessageDialog(mainframe.this,
		     "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
		     JOptionPane.WARNING_MESSAGE);
     		}
     	}
     });

     
     

        JButton resetButton = new JButton("Очистить поля");
        // Задать действие на нажатие "Очистить поля" и привязать к кнопке
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // Установить в полях ввода значения по умолчанию
                from_field.setText("0.0");
                to_field.setText("1.0");
                step_field.setText("0.1");
                // Удалить все вложенные элементы контейнера BoxResult
                BoxResult.removeAll();
                // Добавить в контейнер пустую панель
                BoxResult.add(new JPanel());
                // Пометить элементы меню как недоступные
                saveToTextMenuItem.setEnabled(false);
                saveToGraphicsMenuItem.setEnabled(false);
                searchValueMenuItem.setEnabled(false);
                // Обновить область содержания главного окна
                getContentPane().validate();
            }
        });

        
        Box buttonBox=Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(buttonCalc);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(resetButton);
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.setPreferredSize(new
                Dimension(new Double(buttonBox.getMaximumSize().getWidth()).intValue(), new
                Double(buttonBox.getMinimumSize().getHeight()).intValue()*2));
        getContentPane().add(buttonBox, BorderLayout.SOUTH);

        BoxResult=Box.createHorizontalBox();
        BoxResult.add(new JPanel());
        getContentPane().add(BoxResult, BorderLayout.CENTER);

    }

    protected void saveToGraphicsFile(File selectedFile) {
        try {
            // Создать новый байтовый поток вывода, направленный в указанный файл
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
            // Записать в поток вывода попарно значение X в точке, значение многочлена в точке
            for (int i = 0; i < data.getRowCount(); i++) {
                out.writeDouble((Double) data.getValueAt(i, 0));
                out.writeDouble((Double) data.getValueAt(i, 1));
            } // Закрыть поток вывода
            out.close();
        } catch (Exception e) {
            // Исключительную ситуацию "ФайлНеНайден" в данном случае можно не обрабатывать, так как мы файл создаѐм, а не открываем для чтения
        }
    }

    protected void saveToTextFile(File selectedFile) {
        try {
            // Создать новый символьный поток вывода, направленный в указанный файл
            PrintStream out = new PrintStream(selectedFile);
            // Записать в поток вывода заголовочные сведения
            out.println("Результаты табулирования многочлена по схеме Горнера");
            out.print("Многочлен: ");
            for (int i = 0; i < coeff.length; i++) {
                out.print(coeff[i] + "*X^" + (coeff.length - i - 1));
                if (i != coeff.length - 1)
                    out.print(" + ");
            }
            out.println("");
            out.println("Интервал от " + data.getFrom() + " до " + data.getTo() + " с шагом " + data.getStep());
            out.println("====================================================");
            // Записать в поток вывода значения в точках
            for (int i = 0; i < data.getRowCount(); i++) {
                out.println("Значение в точке " + data.getValueAt(i, 0) + " равно " + data.getValueAt(i, 1));
            }
            // Закрыть поток
            out.close();
        } catch (FileNotFoundException e) {
            // Исключительную ситуацию "ФайлНеНайден" можно не // обрабатывать, так как мы файл создаѐм, а не открываем
        }
    }
public static void main(String[] args) {
	// Если не задано ни одного аргумента командной строки -
	// Продолжать вычисления невозможно, коэффиценты неизвестны
	if (args.length==0) {
	System.out.println("Невозможно табулировать многочлен, для 		которого не задано ни одного коэффициента!");
	System.exit(-1);
	}
	// Зарезервировать места в массиве коэффициентов столько, сколько 		аргументов командной строки
	Double[] coefficients = new Double[args.length];
	int i = 0;
	try {
	// Перебрать аргументы, пытаясь преобразовать их в Double
	for (String arg: args) {
	coefficients[i++] = Double.parseDouble(arg);
	}
	}
	catch (NumberFormatException ex) {
	// Если преобразование невозможно - сообщить об ошибке и 		завершиться
	System.out.println("Ошибка преобразования строки '" +	args[i] + "' в число типа Double");
	System.exit(-2);
	}
	// Создать экземпляр главного окна, передав ему коэффициенты
	mainframe frame = new mainframe(coefficients);
	// Задать действие, выполняемое при закрытии окна
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	}}

 



