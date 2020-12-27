package ShapeDataStruct;
public class DLLS 
{
        private DNodeS head;
	public DNodeS tail;
        public DNodeS current = null;
        
	public boolean isEmpty() 
        {
            if(head == null)
            {
                return true;
            }
            return false;
	}
        
	public void insert(Shape data) 
        {
		DNodeS newNode = new DNodeS();
		newNode.data = data;
		newNode.next = head;
		newNode.prev=null;
		if(head != null)
                {
                    head.prev=newNode;
                }
		head = newNode;
		if(tail==null)
                {
                    tail = newNode;
                }
                current = head;
	}
        public Shape getCurrentS()
        {
            return current.data;
        }
        public Shape getOlderS()
        {
            return current.next.data;
        }
        
        public Shape deleteNewest() 
        {
 
		if (head == null) 
                {
                    System.out.println("LIST IS EMPTY");
                    return null;
                }
                else
                {
                    DNodeS temp = head;
                    head = head.next;
                    head.prev = null;
                    return temp.data;
                }	
	}
        
        public Shape head()
        {
            if (isEmpty()) 
            {
                return null;
            }
            Shape shapeontop = head.data;
            return shapeontop;
        }
        
        public Shape tail()
        {
            if(isEmpty())
            {
                return null;
            }
            Shape shapeonbottom = tail.data;
            return shapeonbottom;
        }
        
        public DNodeS headNode()
        {
            if(isEmpty()){
                return null;
            }
            DNodeS shapeontop = head;
            return shapeontop;
        }
        
        public DNodeS tailNode()
        {
            if(isEmpty())
            {
                return null;
            }
            DNodeS shapeonbottom = tail;
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
