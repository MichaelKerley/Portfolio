/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.util.Timer;
import java.util.TimerTask;
import PageDataStruct.DNodeP;
import PageDataStruct.Pages;
import ShapeDataStruct.DLLS;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author 964045
 */
public class Menus
{

    JMenuBar Mb = new JMenuBar();
    public int FramesPerSec;
    public boolean drawpp = false;
    Timer time = new Timer();

    public Menus(fusion FusionInstance)
    {
        JMenu File = new JMenu("File");
        Mb.add(File);
        JMenu Edit = new JMenu("Edit");
        Mb.add(Edit);
        JMenu Options = new JMenu("Options");
        Mb.add(Options);
        ActionListener Sal = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DNodeP currentNode = FusionInstance.DrawingInstance.pagelist.current;
                FusionInstance.DrawingInstance.pagelist.current = FusionInstance.DrawingInstance.pagelist.tailNode();
                FusionInstance.DrawingInstance.clear(false);
                while (FusionInstance.DrawingInstance.pagelist.current != null)
                {

                    Pages P = FusionInstance.DrawingInstance.pagelist.getCurrentP();
                    FusionInstance.DrawingInstance.RedrawFromPage(FusionInstance.DrawingInstance.pagelist);
                    BufferedImage img = new BufferedImage(FusionInstance.DrawingInstance.getWidth(), FusionInstance.DrawingInstance.getHeight(), BufferedImage.TYPE_INT_RGB);
                    FusionInstance.DrawingInstance.paint(img.getGraphics());
                    File outputfile = new File("/Users/964045/Desktop/CSIA/Page" + P.pagenumber + ".png");
                    try
                    {
                        ImageIO.write(img, "png", outputfile);
                    } 
                    catch (IOException ex)
                    {
                        System.out.println("Somthing went wrong");
                    }
                    FusionInstance.DrawingInstance.clear(false);
                    FusionInstance.DrawingInstance.pagelist.foward();
                }

                FusionInstance.DrawingInstance.pagelist.current = currentNode;
                FusionInstance.DrawingInstance.RedrawFromPage(FusionInstance.DrawingInstance.pagelist);
            }
        };
        JMenuItem Save = new JMenuItem("Save");
        Save.addActionListener(Sal);
        File.add(Save);

        //https://stackoverflow.com/questions/32111740/how-can-i-reset-a-timer-in-java
        
        ActionListener playAl = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                time.cancel();
                TimerTask tt = new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        
                        FusionInstance.DrawingInstance.clear(false);
                        FusionInstance.DrawingInstance.RedrawFromPage(FusionInstance.DrawingInstance.pagelist);
                        FusionInstance.DrawingInstance.repaint();
                        if (FusionInstance.DrawingInstance.pagelist.current.prev == null)
                        {
                            this.cancel();
                            FusionInstance.DrawingInstance.RedrawFromPage(FusionInstance.DrawingInstance.pagelist);
                        } 
                        else
                        {
                            FusionInstance.DrawingInstance.pagelist.foward();
                        }
                    }
                };
                Timer time = new Timer();
                if (FramesPerSec == 0)
                {
                    FusionInstance.DrawingInstance.pagelist.current = FusionInstance.DrawingInstance.pagelist.tailNode();
                    time.scheduleAtFixedRate(tt, 1000, 1000);
                } 
                else
                {
                    FusionInstance.DrawingInstance.pagelist.current = FusionInstance.DrawingInstance.pagelist.tailNode();
                    time.scheduleAtFixedRate(tt, FramesPerSec * 100, FramesPerSec * 100);
                }
            }
        };
        JMenuItem Play = new JMenuItem("Play From Begining");
        Play.addActionListener(playAl);
        File.add(Play);

        ActionListener showpp = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (drawpp == false)
                {
                    drawpp = true;
                } else
                {
                    drawpp = false;
                }
                FusionInstance.DrawingInstance.clear(false);
                FusionInstance.DrawingInstance.RedrawFromPagePP(FusionInstance.DrawingInstance.pagelist, drawpp);
                FusionInstance.DrawingInstance.RedrawFromPage(FusionInstance.DrawingInstance.pagelist);
            }
        };
        JMenuItem dpp = new JMenuItem("Highlight Pervious pages?");
        dpp.addActionListener(showpp);
        Options.add(dpp);

        ActionListener IPBal = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (FusionInstance.DrawingInstance.pagelist.current != FusionInstance.DrawingInstance.pagelist.tailNode())
                {
                    DNodeP currentnode = FusionInstance.DrawingInstance.pagelist.current;
                    FusionInstance.DrawingInstance.drawshape = new DLLS(); //reseting the list of shapes
                    FusionInstance.DrawingInstance.undolist = new DLLS();
                    Pages cp = FusionInstance.DrawingInstance.pagelist.getCurrentP();
                    Pages np = new Pages(FusionInstance.DrawingInstance.drawshape, FusionInstance.DrawingInstance.undolist, cp.pagenumber); //reseting the page object and putting in the new list of shapes
                    FusionInstance.DrawingInstance.pagelist.InsertBefore(np);
                    while (FusionInstance.DrawingInstance.pagelist.current != null)
                    {
                        FusionInstance.DrawingInstance.pagelist.getCurrentP().pagenumber++;
                        FusionInstance.DrawingInstance.pagelist.foward();
                    }
                    FusionInstance.DrawingInstance.pagelist.current = currentnode.next;

                    FusionInstance.DrawingInstance.drawshape.deleteAll();
                    FusionInstance.DrawingInstance.clear(false);

                    FusionInstance.DrawingInstance.RedrawFromPagePP(FusionInstance.DrawingInstance.pagelist, FusionInstance.MenusInstance.drawpp);
                } else
                {
                    DNodeP currentnode = FusionInstance.DrawingInstance.pagelist.current;
                    FusionInstance.DrawingInstance.drawshape = new DLLS(); //reseting the list of shapes
                    FusionInstance.DrawingInstance.undolist = new DLLS();

                    Pages cp = FusionInstance.DrawingInstance.pagelist.getCurrentP();
                    Pages np = new Pages(FusionInstance.DrawingInstance.drawshape, FusionInstance.DrawingInstance.undolist, cp.pagenumber);
                    FusionInstance.DrawingInstance.pagelist.Insertbeforetail(np);
                    FusionInstance.DrawingInstance.pagelist.current = currentnode;
                    while (FusionInstance.DrawingInstance.pagelist.current != null)
                    {
                        FusionInstance.DrawingInstance.pagelist.getCurrentP().pagenumber++;
                        FusionInstance.DrawingInstance.pagelist.foward();
                    }
                    FusionInstance.DrawingInstance.pagelist.current = currentnode.next;
                    FusionInstance.DrawingInstance.drawshape.deleteAll();
                    FusionInstance.DrawingInstance.clear(false);
                }
            }
        };
        JMenuItem IPB = new JMenuItem("Insert new page Before");
        IPB.addActionListener(IPBal);
        Edit.add(IPB);

        ActionListener IPAal = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (FusionInstance.DrawingInstance.pagelist.current != FusionInstance.DrawingInstance.pagelist.headNode())
                {
                    DNodeP currentnode = FusionInstance.DrawingInstance.pagelist.current;
                    FusionInstance.DrawingInstance.drawshape = new DLLS(); //reseting the list of shapes
                    FusionInstance.DrawingInstance.undolist = new DLLS();

                    Pages cp = FusionInstance.DrawingInstance.pagelist.getCurrentP();
                    int npn = cp.pagenumber + 1;

                    Pages np = new Pages(FusionInstance.DrawingInstance.drawshape, FusionInstance.DrawingInstance.undolist, npn); //reseting the page object and putting in the new list of shapes
                    FusionInstance.DrawingInstance.pagelist.InsertAfter(np);
                    FusionInstance.DrawingInstance.pagelist.current = currentnode.prev.prev;
                    while (FusionInstance.DrawingInstance.pagelist.current != null)
                    {
                        FusionInstance.DrawingInstance.pagelist.getCurrentP().pagenumber++;
                        FusionInstance.DrawingInstance.pagelist.foward();//towards the head
                    }
                    FusionInstance.DrawingInstance.pagelist.current = currentnode.prev;

                    FusionInstance.DrawingInstance.drawshape.deleteAll();
                    FusionInstance.DrawingInstance.clear(false);

                    FusionInstance.DrawingInstance.RedrawFromPagePP(FusionInstance.DrawingInstance.pagelist, FusionInstance.MenusInstance.drawpp);
                } else
                {
                    System.out.println("nooooooooo!!!!!!!");
                }
            }
        };
        JMenuItem IPA = new JMenuItem("Insert new page after");
        IPA.addActionListener(IPAal);
        Edit.add(IPA);
    }
}
/*
                    DLLS temp = P.pa;
                    temp.current = temp.tailNode();
                    while (temp.current != null)
                    {
                        x.DrawingInstance.redraw(temp);
                    }
 */
 /*
                    Pages P = x.DrawingInstance.pagelist.getCurrentP();
                    DLLS temp = P.pa;
                    temp.current = temp.tailNode();
 */
 /*
                    while(temp.current != null)
                    {
                        x.DrawingInstance.redraw(temp);
                        System.out.println("wokring");
                    }
 */

 /*
                    try
                    {
                        wait(100);
                    } catch (InterruptedException ex)
                    {
                        Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                        notify();
                    }
 */

 
 /*
                while(x.DrawingInstance.pagelist.current != null)
                {
                    x.DrawingInstance.RedrawFromPage(x.DrawingInstance.pagelist);
                    //x.DrawingInstance.image.flush();
                    x.DrawingInstance.repaint();
                    //x.DrawingInstance.clear(false);
                    x.DrawingInstance.pagelist.foward();
                }
                
                x.DrawingInstance.pagelist.current = currentNode;
                x.DrawingInstance.RedrawFromPage(x.DrawingInstance.pagelist);
 */
