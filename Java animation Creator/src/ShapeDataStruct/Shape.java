/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShapeDataStruct;

import java.awt.Color;
import java.awt.Stroke;

public class Shape
{
    public int x1;
    public int x2;
    public int y1;
    public int y2;
    public Stroke s;
    public Color c;
    public Shape(int myx, int mmyx, int myy, int mmyy, Stroke mys, Color myc)
    {
        this.x1 = myx;
        this.x2 = mmyx;
        this.y1 = myy;
        this.y2 = mmyy;
        this.s = mys;
        this.c = myc;
    }
}

