package view.transacao;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import core.Sistema;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        try {
            if (column == 3) {
                cell.setBackground(Sistema.instance.pesquisaCategoria((String) value).getCor());
            } else {
                cell.setBackground(Color.white);
            }
        } catch (Exception e) {
        }

        return cell;
    }
}
