package main;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class VRtable {
	PlayerController p = new PlayerController();

    protected void initUI() {
        final DefaultTableModel model = new DefaultTableModel() {

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return Integer.class;
                }
                return super.getColumnClass(columnIndex);
            }
            
  
            @Override
            public Object getValueAt(int row, int column) {
            	super.setValueAt("Gold", 0, 0);
            	super.setValueAt(30, 0, 2);
            	super.setValueAt(10, 0, 5);
            	if(row == 1){
            		super.setValueAt("Notoriety", 1, 0);
            		super.setValueAt(20, 1, 2);
            		super.setValueAt(0, 1, 5);
            		super.setValueAt(10, 1, 5);
            		
            	}
            	if(row == 2){
            		super.setValueAt("Fame", 2, 0);
            		super.setValueAt(10, 2, 2);
            		super.setValueAt(0, 2, 5);
            		//super.setValueAt("Total", 3, 0);
            	}
            	if(row == 3){
                     super.setValueAt("Total", 3, 0);
                
            	}
                if (column == 6) {
                    Integer i = (Integer) getValueAt(row, 1);
                    Integer d = (Integer) getValueAt(row, 2);
                    Integer t = (Integer) getValueAt(row, 5);
                    if (i != null && d != null && t != null) {
                        return i * d + t;
                    }
                    else {
                        return 0;
                    }
                }
                
                return super.getValueAt(row, column);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2 || column == 3 || column == 4 || column == 5;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
            	if(column > 0 || row > 0){
                super.setValueAt(aValue, row, column);
                 fireTableCellUpdated(row, 6);
                 fireTableCellUpdated(row, 3);
            	}
            }

            @Override
            public String getColumnName(int column) {
                switch (column) {
                case 0:
                    return "Category";
                case 1:
                    return "Points";
                case 2:
                    return "X";
                case 3:
                    return "Need";
                case 4:
                    return "Record";
                case 5:
                    return "Own";
                case 6:
                    return "Total";      
                }
                return super.getColumnName(column);
            }
            
            
            @Override
            public int getColumnCount() {
                return 7;
            }
            

        };
        final JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (table.rowAtPoint(e.getPoint()) < 0) {
                        model.addRow(new Vector());
                    }
                }
            }
        });
        model.addRow(new Vector());
        JFrame frame = new JFrame(VRtable.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scrollpane = new JScrollPane(table);
        frame.add(scrollpane, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

//    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
//            UnsupportedLookAndFeelException {
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        SwingUtilities.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                new VRtable().initUI();
//            }
//        });
//    }
    }
