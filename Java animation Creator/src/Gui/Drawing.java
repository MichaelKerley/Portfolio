package Gui;

import java.awt.AlphaComposite;

import ShapeDataStruct.DLLS;
import ShapeDataStruct.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import PageDataStruct.*;
import ShapeDataStruct.DNodeS;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;

// code inspired by http://www.ssaurel.com/blog/learn-how-to-make-drawshape-swing-painting-and-drawing-application/
public class Drawing extends JComponent
{

    // Image in which we're going to draw

    public Image image;
    Graphics2D g2;
    DLLS drawshape = new DLLS();
    DLLS undolist = new DLLS();
    DLLP pagelist = new DLLP();
    Pages p = new Pages(drawshape, undolist, 1);
    
    private int currentX, currentY, oldX, oldY;

    public Drawing()
    {
        //inserting placeholder so now there is no null pointer
        pagelist.insert(p);


        MouseAdapter ma = new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent ev)
            {
                
                oldX = ev.getX();
                oldY = ev.getY();
            }
        };

        MouseMotionAdapter mma = new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent ev)
            {
                // coord x,y when drag mouse
                currentX = ev.getX();
                currentY = ev.getY();
                Shape s1 = new Shape(oldX, ev.getX(), oldY, ev.getY(), g2.getStroke(), g2.getColor());
                pagelist.current.data.pa.insert(s1);
                //System.out.println(dpp);

                if (g2 != null)
                {
                    // draw line if g2 context not null                    
                    g2.drawLine(s1.x1, s1.y1, s1.x2, s1.y2);
                    //refresh draw area to repaint
                    repaint();
                    // store current coords x,y as olds x,y
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        };
        this.addMouseListener(ma);
        this.addMouseMotionListener(mma);
    }
    

    @Override
    protected void paintComponent(Graphics g)
    {
        if (image == null)
        {
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            //enable antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear(false);
        }
        g.drawImage(image, 0, 0, null);
    }

    public void clear(boolean hardclear)
    {
        if (!hardclear) //soft clear
        {
            g2.setPaint(Color.WHITE);
            g2.fillRect(0, 0, getSize().width, getSize().height);
            if (!drawshape.isEmpty())
            {
                g2.setPaint(drawshape.head().c);
            } else
            {
                g2.setPaint(Color.black);
            }
        }
        //head = newest tail== oldest
        if (hardclear && !drawshape.isEmpty())
        {
            g2.setPaint(Color.WHITE);
            g2.setStroke(new BasicStroke(1000));
            Shape s2 = new Shape(0, getSize().width, 0, getSize().height, g2.getStroke(), g2.getColor());
            drawshape.insert(s2);
            drawshape.current = drawshape.tailNode();
            RedrawFromPage(pagelist);
            DNodeS temp = drawshape.current;
            drawshape.current = drawshape.headNode();
            g2.setPaint(drawshape.getOlderS().c);
            g2.setStroke(drawshape.getOlderS().s);
            drawshape.current = temp;
        }
        repaint();
    }

    public void redraw(DLLS listofshapes)
    {
        g2.drawString("PageNumber" + pagelist.getCurrentP().pagenumber, 10, 10);
        int x0 = listofshapes.getCurrentS().x1;
        int xf = listofshapes.getCurrentS().x2;
        int y0 = listofshapes.getCurrentS().y1;
        int yf = listofshapes.getCurrentS().y2;
        Stroke s = listofshapes.getCurrentS().s;
        Color c = listofshapes.getCurrentS().c;
        g2.setPaint(c);
        g2.setStroke(s);
        g2.drawLine(x0, y0, xf, yf);
        listofshapes.foward();//tail to head
    }

    public void RedrawFromPage(DLLP pagelist)
    {
        Pages page = pagelist.getCurrentP();
        DLLS shapelist = page.pa;
        shapelist.current = shapelist.tailNode();
        while (shapelist.current != null)
        {
            redraw(shapelist);
        }
        shapelist.current = shapelist.headNode();
    }

    public void RedrawFromPagePP(DLLP pagelist, boolean x)
    {
        if (x && pagelist.current.next != null)
        {
            Pages pp = pagelist.getOlderP();
            DLLS ppshapelist = pp.pa;
            ppshapelist.current = ppshapelist.tailNode();
            while (ppshapelist.current != null)
            {
                if (ppshapelist.current.data.x2 == getSize().width)
                {
                    ppshapelist.tail = ppshapelist.current.prev;
                }
                ppshapelist.foward();
            }

            ppshapelist.current = ppshapelist.tailNode();
            while (ppshapelist.current != null)
            {
                if (ppshapelist.getCurrentS().c == Color.WHITE)
                {
                    int x0 = ppshapelist.getCurrentS().x1;
                    int xf = ppshapelist.getCurrentS().x2;
                    int y0 = ppshapelist.getCurrentS().y1;
                    int yf = ppshapelist.getCurrentS().y2;
                    Stroke s = ppshapelist.getCurrentS().s;
                    Color c = ppshapelist.getCurrentS().c;
                    g2.setPaint(c);
                    g2.setStroke(s);
                    g2.drawLine(x0, y0, xf, yf);
                } 
                
                else
                {
                    float alpha = (float) 0.1; //casting because it only takes float
                    AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                    g2.setComposite(ac);    
                    int x0 = ppshapelist.getCurrentS().x1;
                    int xf = ppshapelist.getCurrentS().x2;
                    int y0 = ppshapelist.getCurrentS().y1;
                    int yf = ppshapelist.getCurrentS().y2;
                    Stroke s = ppshapelist.getCurrentS().s;
                    Color c = ppshapelist.getCurrentS().c;
                    g2.setPaint(c);
                    g2.setStroke(s);
                    g2.drawLine(x0, y0, xf, yf);
                }
                float alpha2 = (float) 1; //casting to float
                AlphaComposite ac2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha2);
                g2.setComposite(ac2);
                ppshapelist.foward();//tail to head
            }
        }
        
        else
        {
            System.out.println("RedrawPrev is not true or no prev page to redraw");
        }
    }
}
