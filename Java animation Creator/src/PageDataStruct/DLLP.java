package PageDataStruct;
public class DLLP
{

    private DNodeP head; //newest
    private DNodeP tail; //oldest
    public DNodeP current = null;

    public boolean isEmpty()
    {
        if (head == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    // used to insert a node at the start of linked list
    public void insert(Pages data)
    {
      
        DNodeP newNode = new DNodeP();
        newNode.data = data;
        newNode.next = head;
        newNode.prev = null;
        if (head != null)
        {
            head.prev = newNode;
        }
        head = newNode;
        if (tail == null)
        {
            tail = newNode;
        }
        current = head;
        //
    }
    public void Insertbeforetail(Pages data)
    {
        DNodeP newNode = new DNodeP();
        newNode.data = data;
        newNode.prev = current;
        newNode.next = null;
        tail = newNode;
        current.next = newNode;
    }
    public void InsertBefore(Pages data)
    {
        DNodeP newNode = new DNodeP();
        newNode.data = data;
        newNode.prev = current;
        newNode.next = current.next;
        current.next.prev = newNode;
        current.next = newNode;
    }
    public void InsertAfter(Pages data)
    {
        DNodeP newNode = new DNodeP();
        newNode.data = data;
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
    }
    

    public Pages getCurrentP()
    {
        return current.data;
    }
    public Pages getOlderP()
    {
        return current.next.data;
    }

    public Pages deleteNewest()
    {

        if (head == null)
        {
            //throw new RuntimeException(" EMPTY!!!!!!!!");
            System.out.println("LIST IS EMPTY!!!!!!! D:");
            return null; 
        } 
        else
        {
            DNodeP temp = head;
            head = head.next;
            head.prev = null;
            return temp.data;
        }
    }

    public Pages head()
    {
        if (isEmpty())
        {
            return null;
        }
        Pages shapeontop = head.data;
        return shapeontop;
    }

    public Pages tail()
    {
        if (isEmpty())
        {
            return null;
        }
        Pages shapeonbottom = tail.data;
        return shapeonbottom;
    }

    public DNodeP headNode()
    {
        if (isEmpty())
        {
            return null;
        }
        DNodeP shapeontop = head;
        return shapeontop;
    }

    public DNodeP tailNode()
    {
        if (isEmpty())
        {
            return null;
        }
        DNodeP shapeonbottom = tail;
        return shapeonbottom;
    }

    public void back() //head to tail
    {
        current = current.next;
    }

    public void foward() //tail to head
    {
        current = current.prev;
    }

    public void deleteAll()
    {
        head = null;
        current = null;
        tail = null;
    }
}
