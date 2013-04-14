/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Recursos;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jorge
 */
public class MiModeloTabla extends AbstractTableModel {
     private String[] columnNames;
        private Object[][] data;
        private final int EDITABLE;

        public MiModeloTabla(String[] columnNames, Object[][] data, int edit) {
            this.columnNames = columnNames;
            this.data = data;
            this.EDITABLE = edit;
        }
        
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col == this.EDITABLE) {
                return true;
            } else {
                return false;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        @Override
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
}
