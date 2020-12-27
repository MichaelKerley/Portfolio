
package PageDataStruct;
import ShapeDataStruct.*;

public class Pages
{
    public DLLS pa;
    public DLLS up;
    public int pagenumber;
    public Pages(DLLS mpa)
    {
        this.pa = mpa;
    }
    public Pages(DLLS mpa,DLLS mup, int mpagenumber)
    {
        this.pa = mpa;
        this.up = mup;
        this.pagenumber = mpagenumber;
    }
    public void setPN(int pn)
    {
        this.pagenumber = pn;
    }
}
