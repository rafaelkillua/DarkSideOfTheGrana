package view.categoria;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author Grupo
 * @version 1.0
 */
public class Renderizador extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        cell.setForeground(Color.black);

        if (column == 1) {
            cell.setBackground(new Color(Integer.parseInt((String) value)));
            cell.setForeground(new Color(Integer.parseInt((String) value)));
        } else {
            cell.setBackground(Color.white);
        }

        return cell;
    }
}
