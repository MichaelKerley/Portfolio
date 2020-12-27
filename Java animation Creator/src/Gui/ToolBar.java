/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import ShapeDataStruct.*;
import PageDataStruct.*;
import java.awt.BasicStroke;
import java.awt.Color;
import static java.awt.Color.WHITE;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//Criterion C potential topics -Double linked List, Double linked list stack, Libraries Swing + AWT, Anonymous inner class/Lamba Statement
public class ToolBar
{

    JPanel JPanelToolBar = new JPanel();
    JButton clearBtn, eraserBtn, colorBtn, undoBtn, redoBtn, newpageBtn, fowardpageBtn, backpageBtn;
    JSlider sizeSlider, FrameSlider;
    int pageN = 2;
    
    public ToolBar(fusion FusionInstance)
    {
     
        ActionListener ClBal = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
                FusionInstance.content.setCursor(cursor);
                FusionInstance.content.setVisible(true);
                Color ccb = JColorChooser.showDialog(((Component) e.getSource()).getParent(), "CHOOSE A COLOR",Color.white);
                FusionInstance.DrawingInstance.g2.setPaint(ccb);
            }
        }; //@Override
        colorBtn = new JButton("Colors");
        colorBtn.addActionListener(ClBal);

        ActionListener EBal = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FusionInstance.DrawingInstance.g2.setPaint(WHITE);
                //FusionInstance.DrawingInstance.g2.setStroke(new BasicStroke(100));
                Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
                FusionInstance.content.setCursor(cursor);
                FusionInstance.content.setVisible(true);
            }

        };
        eraserBtn = new JButton("Eraser");
        eraserBtn.addActionListener(EBal);

        ActionListener CBal = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FusionInstance.DrawingInstance.clear(true);
            }
        };
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(CBal);

        ChangeListener Scl = new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                FusionInstance.DrawingInstance.g2.setStroke(new BasicStroke(sizeSlider.getValue()));
            }
        };
        sizeSlider = new JSlider(1, 10);
        sizeSlider.setBorder(BorderFactory.createTitledBorder("Size"));
        sizeSlider.addChangeListener(Scl);
        
        ChangeListener FramesCl = new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                FusionInstance.MenusInstance.FramesPerSec = FrameSlider.getValue();
            }
        };
        FrameSlider = new JSlider(1,30);
        FrameSlider.setBorder(BorderFactory.createTitledBorder("PlayBackSpeed"));
        FrameSlider.addChangeListener(FramesCl);

        ActionListener Ubal = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Pages p = FusionInstance.DrawingInstance.pagelist.getCurrentP();
                DLLS sl = p.pa;                  
                DLLS ul = p.up;
                Shape sha = sl.deleteNewest(); //delete head from pagelist
                ul.insert(sha); //move to undo list
                FusionInstance.DrawingInstance.clear(false);
                FusionInstance.DrawingInstance.RedrawFromPage(FusionInstance.DrawingInstance.pagelist);
                    
            }
        };
        undoBtn = new JButton("Undo");
        undoBtn.addActionListener(Ubal);

        ActionListener Rbal = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Pages p = FusionInstance.DrawingInstance.pagelist.getCurrentP();
                DLLS sl = p.pa;
                DLLS ul = p.up;
                Shape sha = ul.deleteNewest();
                sl.insert(sha);
                FusionInstance.DrawingInstance.clear(false);
                FusionInstance.DrawingInstance.RedrawFromPage(FusionInstance.DrawingInstance.pagelist);
            }
        }; //@Override
        redoBtn = new JButton("Redo");
        redoBtn.addActionListener(Rbal);
        
        
        ActionListener FPal = new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                if(FusionInstance.DrawingInstance.pagelist.current.prev == null)
                {
                    FusionInstance.DrawingInstance.drawshape = new DLLS();
                    FusionInstance.DrawingInstance.undolist = new DLLS();
                     //reseting the list of shapes
                    Pages np = new Pages(FusionInstance.DrawingInstance.drawshape,FusionInstance.DrawingInstance.undolist, pageN); //reseting the page object and putting in the new list of shapes
                    pageN++;
                    FusionInstance.DrawingInstance.pagelist.insert(np);
                    
                    FusionInstance.DrawingInstance.drawshape.deleteAll();
                    FusionInstance.DrawingInstance.clear(false);
                    FusionInstance.DrawingInstance.RedrawFromPagePP(FusionInstance.DrawingInstance.pagelist,FusionInstance.MenusInstance.drawpp);
                
                }
                else
                {
                    //FusionInstance.DrawingInstance.drawshape.deleteAll();
                    FusionInstance.DrawingInstance.pagelist.foward();
                    //tail to head
                    FusionInstance.DrawingInstance.clear(false);
                    FusionInstance.DrawingInstance.RedrawFromPagePP(FusionInstance.DrawingInstance.pagelist,FusionInstance.MenusInstance.drawpp);
                    FusionInstance.DrawingInstance.RedrawFromPage(FusionInstance.DrawingInstance.pagelist);
                    //FusionInstance.DrawingInstance.pagelist.current.data.pa.current = null;
                }
            }
        }; 
        fowardpageBtn = new JButton("FowardPage");
        fowardpageBtn.addActionListener(FPal);
        
        
        
        ActionListener BPal = new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (FusionInstance.DrawingInstance.pagelist.current.next == null)
                {
                    System.out.println("no page to fall back to");
                } 
                else
                {
                    //FusionInstance.DrawingInstance.drawshape.deleteAll();
                    FusionInstance.DrawingInstance.pagelist.back(); //head to tail
                    FusionInstance.DrawingInstance.clear(false);
                    FusionInstance.DrawingInstance.RedrawFromPagePP(FusionInstance.DrawingInstance.pagelist,FusionInstance.MenusInstance.drawpp);
                    FusionInstance.DrawingInstance.RedrawFromPage(FusionInstance.DrawingInstance.pagelist);
                }
            }
        };
        
        backpageBtn = new JButton("BackPage");
        backpageBtn.addActionListener(BPal);
        //backpageBtn.setToolTipText("Go to page " + FusionInstance.DrawingInstance.pagelist.getCurrentP().pagenumber);
        
        
        //adding all the buttons in the correct order
        JPanelToolBar.add(colorBtn);
        JPanelToolBar.add(sizeSlider);
        JPanelToolBar.add(FrameSlider);
        JPanelToolBar.add(eraserBtn);
        JPanelToolBar.add(clearBtn);
        JPanelToolBar.add(undoBtn);
        JPanelToolBar.add(redoBtn);
        JPanelToolBar.add(backpageBtn);
        JPanelToolBar.add(fowardpageBtn);
    }
}

                /* working but kind of dumb talk about in writing section
                BufferedImage bi = i.createImage(x.DrawingInstance);
                try
                {
                  i.writeImage(bi, "/Users/964045/Desktop/IBCSII/here.png");
                } 
                
                catch (IOException ex)
                {
                    Logger.getLogger(ToolBar.class.getName()).log(Level.SEVERE, null, ex);
                }
                */
                    
                /*
                try
                /*
                {
                    //talk about in writing section
                    //x.DrawingInstance.clear();
                    //BufferedImage image = new BufferedImage(x.frame.getWidth(),x.frame.getHeight(),BufferedImage.TYPE_INT_RGB);
                    //BufferedImage image = ImageIO.read(screen);
                    //File output= new File("file:///Users/964045/Desktop/IBCS/here.png");
                    
                    
                    
                    //File output= new File("/Users/964045/Desktop/IBCSII/here.png");
                    
                    //ImageIO.write(image,"png", output);
                }
                */
                /*
                catch (IOException ex)
                {
                    Logger.getLogger(ToolBar.class.getName()).log(Level.SEVERE, null, ex);
                }
                */

                    /*
                    Pages Pp  = x.DrawingInstance.pagelist.getPrevP();
                    DLLS ptemp = Pp.pa;
                    ptemp.current = ptemp.tailNode();
                    if(ReDrawPrev)
                    {
                        while(ptemp.current != null)
                        {
                            x.DrawingInstance.redrawprev(ptemp, ReDrawPrev);
                        }   
                    }
                    */

        //tb.add(redoBtn);

        /*
        ActionListener NPal = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(FusionInstance.DrawingInstance.pagelist.current == FusionInstance.DrawingInstance.pagelist.headNode())
                {
                    FusionInstance.DrawingInstance.drawshape = new DLLS();
                    FusionInstance.DrawingInstance.undolist = new DLLS();
                     //reseting the list of shapes
                    Pages np = new Pages(FusionInstance.DrawingInstance.drawshape,FusionInstance.DrawingInstance.undolist, pageN); //reseting the page object and putting in the new list of shapes
                    pageN++;
                    FusionInstance.DrawingInstance.pagelist.insert(np);
                    
                }
                else
                {
                    FusionInstance.DrawingInstance.drawshape = new DLLS(); //reseting the list of shapes
                    FusionInstance.DrawingInstance.undolist = new DLLS();
                    DNodeP currentnode = FusionInstance.DrawingInstance.pagelist.current;
                    Pages cp = FusionInstance.DrawingInstance.pagelist.getCurrentP();
                    Pages np = new Pages(FusionInstance.DrawingInstance.drawshape, FusionInstance.DrawingInstance.undolist, cp.pagenumber); //reseting the page object and putting in the new list of shapes
                    FusionInstance.DrawingInstance.pagelist.InsertBefore(np);
                    while(FusionInstance.DrawingInstance.pagelist.current != null)
                    {
                        FusionInstance.DrawingInstance.pagelist.getCurrentP().pagenumber ++;
                        FusionInstance.DrawingInstance.pagelist.foward();
                    }
                    FusionInstance.DrawingInstance.pagelist.current = currentnode;
                }
                FusionInstance.DrawingInstance.drawshape.deleteAll();
                FusionInstance.DrawingInstance.clear(false);
                FusionInstance.DrawingInstance.RedrawFromPagePP(FusionInstance.DrawingInstance.pagelist,FusionInstance.MenusInstance.drawpp);
            }
        }; 
        
        newpageBtn = new JButton("NewPage");
        newpageBtn.addActionListener(NPal);
        */