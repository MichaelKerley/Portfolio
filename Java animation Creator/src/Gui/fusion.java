/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author 964045
 */
public class fusion
{

    Drawing DrawingInstance = new Drawing();
    ToolBar ToolBarInstance = new ToolBar(this);
    Menus MenusInstance = new Menus(this);
    JFrame frame = new JFrame("IA MK6");
    Container content = frame.getContentPane();
    
    public void ShowEverything()
    {
        frame.setJMenuBar(MenusInstance.Mb);
        content.setLayout(new BorderLayout());
        content.add(DrawingInstance, BorderLayout.CENTER);
        content.add(ToolBarInstance.JPanelToolBar, BorderLayout.NORTH);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        content.setCursor(cursor);
        frame.setVisible(true);
    }
    
}
